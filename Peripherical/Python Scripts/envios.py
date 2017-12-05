import requests
import json
d  = 10
payload = {"location" : d,"temperature": 323.32,"gas" : 32.32,"light" : 323.32,"sound" : 323.32,"timestamp" : "23/12/2017"}
for i in range(1,100):
	r = requests.post("http://172.24.42.34:9000/measurement",json = payload)
	print(r)
	data = {}
	data["location"]= i
	data["temperature"] = 323.32
	data["gas"]=32.32
	data["light"]=323.33
	data["sound"]=323.3
	data["timestamp"]="23/12/2017"
	r = requests.post("http://172.24.42.34:9000/measurement",json = data)
	print(r)s