import requests
payload = {"location" : 213423,"temperature": 323.32,"gas" : 32.32,"light" : 323.32,"sound" : 323.32,"timestamp" : "23/12/2017"}
for i in range(1,100):
	r = requests.post("http://172.24.42.34:9000/measurement",json = payload)
	print(r)