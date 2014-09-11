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
except KeyError:
    print '''Status: 400
Content-type: text/html

'''
    sys.exit()

# Check if the user exists
cur.execute("SELECT id FROM users WHERE username = '%s';" % username)
row = cur.fetchone()
if row  == None:
    print '''Status: 404
Content-type: text/html

'''
    sys.exit()

farmer_id = row[0]


# Insert it into the database and set recovery to null
cur.execute("SELECT * FROM sheep WHERE bonde = '%s';" % farmer_id)

sheep = cur.fetchall()

connection.commit()
cur.close()
connection.close()

print '''Status: 200
Content-type: text/html

'''
print sheep

