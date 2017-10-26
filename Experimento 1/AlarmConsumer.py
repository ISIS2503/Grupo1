from kafka import KafkaConsumer
from kafka import KafkaProducer
import datetime
import requests
def alarmsend(prod, typ, min, max, val, mes):
	if val<min or val>max:
		if False:
			print("hola")
		else:
			print("enviando")
			payload = {}
			payload["alert_type"]=1
			payload["trigger"]=typ
			payload["measurement"]=mes
			prod.send('alarmas', str(payload).encode("utf-8"))
			print("enviado")
print("creando")
consumer = KafkaConsumer('alta.piso1.area1',
                         group_id='my-group',
                         bootstrap_servers=['172.24.42.20:8090'])
print("saliendo")
print("creando prod")
producer = KafkaProducer(bootstrap_servers=['172.24.42.20:8090'])
print("saliendo prod")
location = 1001001

for message in consumer:
	#print('hola')
	payload={}
	msg = message.value.decode("utf-8").split(',')
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
	alarmsend(producer,1,100.0,1000.0,temp,payload)



