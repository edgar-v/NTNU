#!/bin/python2

from socket import *

msg = "\r\n I love computer networks!"

endmsg = "\r\n.\r\n"

# Choose a mail server (e.g. Google mail server) and call it mailserver
mailserver = "smtp.samfundet.no"
# Create socket called clientSocket and establish a TCP connection with mailserver

clientSocket = socket(AF_INET, SOCK_STREAM)
clientSocket.connect((mailserver, 587))

recv = clientSocket.recv(1024)
print recv

if recv[:3] != '220':
    print '220 reply not received from server.'

# Send HELO command and print server response.
heloCommand = 'HELO Alice\r\n'
clientSocket.send(heloCommand)
recv1 = clientSocket.recv(1024)
print recv1
if recv1[:3] != '250':
    print '250 reply not received from server.'
# Send MAIL FROM command and print server response.

fromCommand = "MAIL FROM: edgar.vedvik@gmail.com\r\n"
clientSocket.send(fromCommand)
recv = clientSocket.recv(1024)
print recv

# Send RCPT TO command and print server response.

toCommand = "RCPT TO: edgarmv@samfundet.no\r\n"
clientSocket.send(toCommand)
recv = clientSocket.recv(1024)
print recv

# Send DATA command and print server response.
# Send message data.

dataCommand = "DATA\r\n"
clientSocket.send(dataCommand)
recv = clientSocket.recv(1024)
print recv

clientSocket.send(msg)
clientSocket.send(endmsg)
recv = clientSocket.recv(1024)
print recv

# Send QUIT command and get server response.

quitMessage = "QUIT\r\n"
clientSocket.send(quitMessage)
recv = clientSocket.recv(1024)
print recv
