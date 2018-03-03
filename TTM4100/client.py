#!/bin/python2
'''
KTN-project 2013 / 2014
'''
import socket
import json
import sys
from threading import Thread
BUFF_SIZE = 4096

class Client(object):
    def __init__(self):
        self.connection = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    def start(self):
        print("Input \q at any time to quit")
        self.connect()
        self.login()
        thread = Thread(target=self.listener)
        thread.daemon = True
        thread.start()

        while True:
            message = self.get_input('')
            if message:
				self.send({'request':'message','message':message})

    def listener(self):
        while True:
            try:
                data = self.connection.recv(BUFF_SIZE)
                if data:
                    data = json.loads(data.strip())
                    if 'error' in data:
                        print("Error: %s" % data.get('error'))
                    if data.get('response') == 'logout':
                        print("Logged out.")
                        sys.exit()
                    else:
                        print(data.get('message'))
                else: print("NO DATA")
            except Exception:
                print ("Server disconnected")
                self.force_disconnect()

    def receive(self):
        return json.loads(self.connection.recv(BUFF_SIZE))

    def get_input(self, msg):
        inp = raw_input(msg)
        if inp == "\q":
            self.logout()
            self.force_disconnect()
        elif inp: return inp
        else: return none

    def send(self, data):
		try:
			self.connection.sendall(json.dumps(data))
		except Exception, e:
			self.force_disconnect()

    def force_disconnect(self):
        self.connection.close()
        sys.exit()

    def logout(self):
        try:
            self.send({'request':'logout'})
        except Exception, e:
            pass

    def connect(self):
        host = None
        while not host:
            host = self.get_input("Enter host address: ")
        port = None
        while not port:
            port = self.get_input("Enter port: ")
            try:
                port = int(port)
            except ValueError:
                print("Not a number")
                port = None
        try:
            self.connection.connect((host, port))
            print("Connected successfully ")
        except Exception, e:
            print("Could not connect: %s" % e)
            self.connect()

    def login(self):
        username = None
        while not username:
            username = self.get_input("Enter user name: ")
        data = {'request':'login','username':username}
        self.send(data)
        received_data = self.receive()
        if self.check_username_response(received_data) != True:
            #print("Logging in again..")
            self.login()
        else:
            if 'messages' in received_data:
                for msg in received_data.get('messages'):
                    print msg

    def check_username_response(self, data):
         if 'error' in data:
            print "User name not accepted by server: " + data.get("error")
            return False
         else:
            print ("Login successful")
            return True

if __name__ == "__main__":
    client = Client()
    client.start()
