#!/bin/python2

from socket import *
serverSocket = socket(AF_INET, SOCK_STREAM)

serverSocket.bind(('', 8080))
serverSocket.listen(5)

while True:

#Establish the connection
    print 'Ready to serve...'
    connectionSocket, addr = serverSocket.accept()
    try:
        message = connectionSocket.recv(1024)
        filename = message.split()[1]
        f = open(filename[1:])
        outputdata = f.read()
        f.close()
#Send one HTTP header line into socket
        header = "HTTP/1.1 200 OK\r\n\r\n"
        connectionSocket.send(header)
#Send the content of the requested file to the client
        for i in range(0, len(outputdata)):
            connectionSocket.send(outputdata[i])
        connectionSocket.close()
    except IOError, IndexError:
        header = "HTTP/1.1 404 Not Found\r\n\r\n"
        connectionSocket.send(header)
        connectionSocket.close()
serverSocket.close()
