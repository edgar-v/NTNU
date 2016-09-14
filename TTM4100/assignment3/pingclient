#!/bin/python2

from socket import *
from time import time

sock = socket(AF_INET, SOCK_DGRAM)
sock.settimeout(1)
for i in range(10):
    try:
        starttime = time()
        msg = "Ping " + str(i+1) + " " + str(starttime)
        sock.sendto(msg, ("127.0.0.1", 12000))
        response = sock.recv(1024)
        responsetime = (time() - starttime) * 1000
        print response
        print '%.2f ms' % responsetime
    except timeout:
        print "Request time out"
        continue
