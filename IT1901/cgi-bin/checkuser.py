#!/usr/bin/python

import psycopg2
import hashlib
import sys
import cgi

#For debugging
import cgitb
cgitb.enable()

# Establish a connection
connection = psycopg2.connect("dbname=findmysheep user=postgres host=localhost password=kohxooto")

# Open a transaction
cur = connection.cursor()

# Get the parameters from the request
form = cgi.FieldStorage()

# See if all the params are present, if not return an error message and exit
try:
    username = form['username'].value
    password = form['password'].value
except KeyError:
    print '''Status: 400
Content-type: text/html

'''
    sys.exit() 

# Fetch the user with this username
cur.execute("SELECT * FROM users WHERE username = '%s';" % username)

# If no such user exists, return an error and exit
row = cur.fetchone()
if row == None:
    print '''Status: 404
Content-type: text/html

'''
    sys.exit()

# Read the information sent back by the request
original_password = row[5]
salt = row[6]

# Hash the password that was passed to us from the user together with the salt from the database
password_hash = hashlib.sha1(bytes(password + salt)).hexdigest()

# If the hashed password matches the hashed password in the database, return OK
if password_hash == original_password:
    print '''Status: 200
Content-type: text/html

'''
else:
    # The passwords didn't match
    print '''Status: 401
Content-type: text/html

'''

# Close the transaction and connection
cur.close()
connection.close()

