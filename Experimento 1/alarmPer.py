from kafka import KafkaConsumer
import datetime
import requests
print("creando")
consumer = KafkaConsumer('alarmas',
						bootstrap_servers=['172.24.42.20:8090'],
						sasl_plain_username='alarmas',
						sasl_plain_password='alarmas',
						security_protocol='SASL_PLAINTEXT',
						sasl_mechanism='PLAIN')

location = 1001001
print("entrando")
for message in consumer:
	#print('hola')
	payload={}
	msg = message.value.decode("utf-8").split(',')
	alarm = int(msg[0].split(':')[1])
	trigger = int(msg[1].split(":")[1])
	locacion = msg[2].split(":")[2]
	temp = float(msg[3].split(':')[1])
	gas = float(msg[4].split(':')[1])
	ilum = float(msg[5].split(':')[1])
	noise = float(msg[6].split(':')[1])
	now = msg[7].split(':')[1]
	payload2={}
	payload2["location"]=location
	payload2["temperature"]=temp
	payload2["gas"]=gas
	payload2["light"]=ilum
	payload2["sound"]=noise
	payload2["timestamp"]=now
	#print(msg)
	payload["alert_type"]=alarm
	payload["trigger"]=trigger
	payload["measurement"]=payload2
	print(payload)
	if alarm==2:
		r = requests.post("http://172.24.42.34:9000/outOfBoundsAlert",json = payload)
	elif alarm==3:
		r = requests.post("http://172.24.42.34:9000/noChangeAlert",json = payload)
	else :
		r = requests.post("http://172.24.42.34:9000/offlineAlert",json = payload)
	print(r)