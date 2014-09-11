#!/usr/bin/python

import psycopg2
import hashlib
import sys
import cgi
import random
import smtplib

from email.mime.text import MIMEText

#For debugging
import cgitb
cgitb.enable()

connection = psycopg2.connect("dbname=findmysheep user=postgres host=localhost password=kohxooto")

cur = connection.cursor()

form = cgi.FieldStorage()

username = form['username'].value
ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
recovery_key = ''.join(random.choice(ALPHABET) for i in range(5))

cur.execute("UPDATE users SET recovery = '%s' where username = '%s';" % (recovery_key, username))

# Send mail

cur.execute("SELECT email FROM users WHERE username = '%s';" % username)
email = cur.fetchone()
email = email[0]

msg = MIMEText("lolcode")
msg['Subject'] = 'Recovery Code'
msg['From'] = 'findmysheep'
msg['To'] = email

s = smtplib.SMTP('smtp.gmail.com:587')
s.starttls()
s.login('findmyherd@gmail.com', 'Supa!Boo8')
s.sendmail('findmysheep', email, msg.as_string())
s.quit()

connection.commit()
cur.close()
connection.close()

print '''Status: 200
Content-type: text/html

'''
