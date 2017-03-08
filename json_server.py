import os
from bottle import route, run, template, response
from json import dumps

#Create our json endpoint
@route('/json')
def json():
	rv = {"title":"JSON test", "description":"Bottle server to test JSON response"}
	response.content_type = "application/json"
	return dumps(rv)

#Run the app if not an import
if __name__ == "__main__":
	port = int(os.environ.get('PORT', 8080))
	run(host='127.0.0.1', port = port, debug = True)
