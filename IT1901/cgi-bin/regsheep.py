#!/usr/bin/python

import psycopg2
import hashlib
import random
import cgi
import sys

#for debugging
import cgitb
cgitb.enable()

# Establish a connection to our database
conn = psycopg2.connect("dbname=findmysheep user=postgres host=localhost password=kohxooto")

# Fetch the parameters from the request
form = cgi.FieldStorage()

# Check if all the required parameters are sent
try:
    farmer = form['username'].value
    name = form['name'].value
    age = form['age'].value
    weight = form['weight'].value
    cur = conn.cursor()
except KeyError:
    # Return an error message if a parameter is missing
    print '''Status: 400
Content-type: text/html

'''        
    sys.exit()

try:
    comment = form['health'].value
except KeyError:
    comment = "None"

# Get the id of the farmer from the database
cur.execute("SELECT id FROM users WHERE username = '%s';" % farmer)
farmer_id = cur.fetchone()

# Insert a new sheep into the database with a relation to the farmer
cur.execute("INSERT INTO sheep (name, age, weight, bonde, comment) VALUES (%s, %s, %s, %s, %s)", (name, age, weight, farmer_id, comment))

# Commit the changes and close the transaction and database
conn.commit()
cur.close()
conn.close()

# Return OK
print '''Status: 200
Content-type: text/html

'''

