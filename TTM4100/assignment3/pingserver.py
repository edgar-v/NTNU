#!/bin/python2

# We will need the following module to generate randomized lost packets
import random
from socket import *

# Create a UDP socket
# Notice the use of SOCK_DGRAM for UDP packets

serverSocket = socket(AF_INET, SOCK_DGRAM)

serverSocket.bind(('', 12000))

while True:
    rand = random.randint(0, 10)
# Receive the client packet along with the address it is coming from
    message, address = serverSocket.recvfrom(1024)
    message = message.upper()
    print message
    if rand < 4:
        continue
    serverSocket.sendto(message, address)
