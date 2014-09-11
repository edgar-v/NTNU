#!/usr/bin/python

import psycopg2
import hashlib
import sys
import cgi
import random

#For debugging
import cgitb
cgitb.enable()


connection = psycopg2.connect("dbname=findmysheep user=postgres host=localhost password=kohxooto")
cur = connection.cursor()

form = cgi.FieldStorage()

try:
    username = form['username'].value
    recovery_key = form['recoveryKey'].value
    password = form['password'].value
except KeyError:
    print '''Status: 400
Content-type: text/html

'''
    sys.exit()

# Check if the user exists
cur.execute("SELECT recovery FROM users WHERE username = '%s';" % username)
row = cur.fetchone()
if row  == None:
    print '''Status: 404
Content-type: text/html

'''
    sys.exit()

# Check if the recovery key is correct
if row[0] != recovery_key:
    print '''Status: 401
Content-type: text/html

'''
    sys.exit()


# Create a hash of the new password
ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
salt = ''.join(random.choice(ALPHABET) for i in range(16))
password_hash = hashlib.sha1(bytes(password + salt)).hexdigest()

# Insert it into the database and set recovery to null
cur.execute("UPDATE users SET password='%s', salt='%s', recovery=null where username='%s';" % (password_hash, salt, username))

connection.commit()
cur.close()
connection.close()

print '''Status: 200
Content-type: text/html

'''
