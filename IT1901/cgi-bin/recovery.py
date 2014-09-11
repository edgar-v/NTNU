#!/usr/bin/python

import psycopg2 # For communicating with the postgresql database
import sys
import cgi # Common Gateway Interface, allows us to process a HTTP request
import random
import smtplib # For communicating with SMTP
from email.mime.text import MIMEText # Creating text for the emails

#For debugging
import cgitb
cgitb.enable()

connection = psycopg2.connect("dbname=findmysheep user=postgres host=localhost password=kohxooto")

# Establish a cursor
cur = connection.cursor()

# Read the parameters from the HTTP request
form = cgi.FieldStorage()

# Extract the parameters we want
try:
    username = form['username'].value
except KeyError:
    print '''Status: 400
Content-type: text/html

'''
    sys.exit()

# Create a random 5 character long key
ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
recovery_key = ''.join(random.choice(ALPHABET) for i in range(5))

# Set the recovery field of the user specified to the recovery key
cur.execute("UPDATE users SET recovery = '%s' where username = '%s';" % (recovery_key, username))

# Get the email address of the user
cur.execute("SELECT email FROM users WHERE username = '%s';" % username)
email = cur.fetchone()
email = email[0]

# Send mail
msg = MIMEText('You have requested a password recovery for the account bound to %s.\n\nUsername: %s\nRecovery key: %s\n\nIf you did not ask for this password recovery, please ignore this email.\n-- \nFindMyHerd' % (email, username, recovery_key))
msg['Subject'] = 'Recovery Code'
msg['From'] = 'findmysheep'
msg['To'] = email
s = smtplib.SMTP('smtp.gmail.com:587')
s.starttls()
s.login('findmyherd@gmail.com', 'Supa!Boo8')
s.sendmail('findmysheep', email, msg.as_string())
s.quit()

# Commit our changes, close the cursor and database connection
connection.commit()
cur.close()
connection.close()

print '''Status: 200
Content-type: text/html

'''
