import os
from bottle import route, get, post, run, response, request
from json import dumps
import json
from urllib2 import urlopen

#Create a user object to hold information about a single user
class User(object):
	
	#Constructor
	def __init__(self, u, p):
		#Set the username and password
		self.username = u
		self.password = p

#Create an empty list of users
users = []

#Create the index
@route('/')
def index():
	response.content_type = "application/json"
	return dumps({"message":"Welcome to test server"})

#Create our json endpoint
@route('/json')
def json():
	rv = {"title":"JSON test", "description":"Bottle server to test JSON response"}
	response.content_type = "application/json"
	return dumps(rv)

#Create a get request for logging in
@get('/login')
def login():
	return '''
        <form action="/login" method="post">
            Username: <input name="username" type="text" />
            Password: <input name="password" type="password" />
            <input value="Login" type="submit" />
        </form>
    	'''

#Create a get request for signing up
@get('/signup')
def signup():
	return '''
	<form action="/signup" method="post">
            Username: <input name="username" type="text" />
            Password: <input name="password" type="password" />
        <input value="Sign up" type="submit" />
	'''

#Create our post function for logging in
@post('/login')
def do_login():
	username = request.forms.get('username')
	password = request.forms.get('password')	
	print("Attempted login from account {} with password {}".format(username, password))
	rv = {"message":"successful login from {}".format(username)}
	response.content_type = "application/json"
	return dumps(rv)


#Create our post function for logging in on mobile
@post('/mlogin')
def do_mlogin():
	request.content_type = "application/json"
        username = request.json['username']
        password = request.json['password']
        print("Attempted login from account {} with password {}".format(username, password))
        rv = {"message":"successful login from {}".format(username)}
        response.content_type = "application/json"
        return dumps(rv)

#Create our post function for signing up
@post('/signup')
def do_signup():
	username = request.forms.get('username')
	password = request.forms.get('password')

	msg = "NULL"
	exists = False
	for u in users:
		if u.username == username:
			msg = "User already exists"
			exists = True
	if not exists:
		users.append(User(username, password))
		msg = "User created successfully"
	print(users)
	rv = {"message":msg}
	response.content_type = "application/json"
	return dumps(rv)

#Run the app if not an import
if __name__ == "__main__":
	port = int(os.environ.get('PORT', 8080))
	run(host='127.0.0.1', port = port, debug = True)
