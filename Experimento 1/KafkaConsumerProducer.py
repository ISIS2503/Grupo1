from kafka import KafkaConsumer
import datetime
import requests

consumer = KafkaConsumer('alta.piso1.area1',
                         group_id='my-group',
                         bootstrap_servers=['172.24.42.20:8090'])
location = 1001001

for message in consumer:
	msg = message.split(',') 
	payload = {}
	now = msg[0].split(':')[1]
	temp = float(msg[1].split(':')[1])
	noise = float(msg[2].split(':')[1])
	gas = float(msg[3].split(':')[1])
	ilum = float(msg[4].split(':')[1].split('}')[0])
	payload["location"]=location
	payload["temperature"]=temp
	payload["gas"]=gas
	payload["light"]=ilum
	payload["sound"]=noise
	payload["timestamp"]=now 
	r = requests.post("http://172.24.42.34:9000/measurement",json = payload)
	print(r)
