#!/usr/bin/python

import psycopg2
import hashlib
import random
import cgi
import sys

#for debugging
import cgitb
cgitb.enable()

# Establish a connection to the database
conn = psycopg2.connect("dbname=findmysheep user=postgres host=localhost password=kohxooto")

# Fetch parameters from the request
form = cgi.FieldStorage()

try:
    ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
    # Create a salt and hash the password
    salt = ''.join(random.choice(ALPHABET) for i in range(16))
    password_hash = hashlib.sha1(bytes(form['password'].value + salt)).hexdigest()

    # See if the parameters were sent
    username = form['username'].value
    email = form['email'].value
    name = form['name'].value
    tlf = form['tlf'].value
    cur = conn.cursor()
except KeyError:
    # If some of the parameters are missing, return an error message and cloexit.
    print '''Status: 400
Content-type: text/html

'''        
    sys.exit()

try:
    # Insert the user into the database
    cur.execute("INSERT INTO users (username, password, email, name, salt, tlf) VALUES (%s, %s, %s, %s, %s, %s)", (username, password_hash, email, name, salt, tlf))
except Exception, e:
    # If the transaction fails return an error and exit.
    # This will happen if the user already exists in the database
    print '''Status: 420
Content-type: text/html

'''
    print e.pgerror
    sys.exit()

# Commit the changes we have made to the database
conn.commit()

# Close the connection
cur.close()
conn.close()

# Return an OK status message
print '''Status: 200
Content-type: text/html

'''

