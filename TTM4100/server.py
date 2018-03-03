#!/bin/python2
'''
KTN-project 2013 / 2014
Very simple server implementation that should serve as a basis
for implementing the chat server
'''
import SocketServer
import json
import re
import time
import threading

connections = {}
prevMessages = []

def jsonParse(message):
    decodedJson = json.loads(message)
    return decodedJson

class ClientHandler(SocketServer.BaseRequestHandler):
    loggedIn = False

    def handleLogin(self, request):
        response = ""
        if re.match('^[\w_]+$', request["username"]) is None:
            response = json.dumps({"response": "login", "error":"Invalid username!", "username": request["username"]})
        elif request["username"] in connections.values():
            response = json.dumps({"response": "login", "error":"Name already taken!", "username": request["username"]})
        else:
            self.loggedIn = True
            connections[self.connection] = request["username"]

            response = json.dumps({"response": "login", "username": request["username"], "messages": prevMessages})
        return response


    def handleMessage(self, request):
        response = ""
        if self.loggedIn is False:
            response = json.dumps({"response":"message", "error":"You are not logged in!"})
        else:
            currentTime = time.strftime("%H:%M")
            message = u' '.join(("[" + currentTime + "]", "<" + connections[self.connection] + ">", request["message"])).encode('utf-8').strip()
            prevMessages.append(message)
            response = json.dumps({"response":"message", "message":message})
        return response

    def handleLogout(self, request):
        response = ""
        if self.loggedIn is False:
            print("r2")
            response = json.dumps({"response":"message", "error":"Not logged in!", "username": connections[self.connection]})
        else:
            print("r")
            response = json.dumps({"response":"logout", "username": connections[self.connection]})
        return response

    def handle(self):
        self.connection = self.request
        self.ip = self.client_address[0]
        self.port = self.client_address[1]
        print 'Client connected @' + self.ip + ':' + str(self.port)
        connections[self.connection] = None
        while True:
            try:
                data = self.connection.recv(1024)
                if data:
                    request = jsonParse(data.strip())
                    response = ""
                    if request["request"] == "login":
                        response = self.handleLogin(request)
                        self.connection.sendall(response)
                    elif request["request"] == "message":
                        response = self.handleMessage(request)
                        for con in connections:
                            con.sendall(response)
                    elif request["request"] == "logout":
                        response = self.handleLogout(request)
                        self.connection.sendall(response)
                    else:
                        print 'Client disconnected!'
                        connections.pop(self.connection)
                        break
            except Exception, e:
                print 'Client disconnected!'
                connections.pop(self.connection)
                break

class ThreadedTCPServer(SocketServer.ThreadingMixIn, SocketServer.TCPServer):
    daemon_threads = True
    pass

if __name__ == "__main__":
    HOST = 'localhost'
    PORT = 9999

    server = ThreadedTCPServer((HOST, PORT), ClientHandler)

    server_thread = threading.Thread(target=server.serve_forever)
    server_thread.daemon = True
    server_thread.start()
    server.serve_forever()

