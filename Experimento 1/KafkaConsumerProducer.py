from kafka import KafkaConsumer
import datetime
import requests
print("creando")
consumer = KafkaConsumer('data',
                         bootstrap_servers=['172.24.42.20:8090'],
                         sasl_plain_username = 'data',
                         sasl_plain_password = 'data',
                         security_protocol = 'SASL_PLAINTEXT',
                         sasl_mechanism = 'PLAIN')
print("saliendo")
location = 1001001

for message in consumer:
	#print('hola')
	payload={}
	msg = message.value.decode("utf-8").split(',')
	time = msg[0]
	now = msg[0].split(':')[1]+":"+msg[0].split(':')[2]+":"+msg[0].split(':')[3]
	temp = float(msg[1].split(':')[1])
	noise = float(msg[2].split(':')[1])
	gas = float(msg[3].split(':')[1])
	ilum = float(msg[4].split(':')[1].split('}')[0])	#print(msg)
	#print(msg)
	payload["location"]=location
	payload["temperature"]=temp
	payload["gas"]=gas
	payload["light"]=ilum
	payload["sound"]=noise
	payload["timestamp"]=now 
	r = requests.post("http://172.24.42.34:9000/measurement",json = payload)
	print(r)
