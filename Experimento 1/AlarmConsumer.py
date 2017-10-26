from kafka import KafkaConsumer
from kafka import KafkaProducer
import datetime
import redis
import requests
def alarmoutsend(prod, typ, min, max, val, mes):
#prod.send('alarmas', str(payload).encode("utf-8"))
	print("creando alarma")
	cache = redis.Redis(host='localhost',port=8090,db=0)
	loc = mes["location"]
	lista = str(location)+str(typ)
	longi = cache.llen(lista)
	if longi<=9:
		print("not enough data")
		cache.rpush(lista,val)
	else:
		print("calculando")
		sum = 0.0
		cache.lpop(lista)
		cache.rpush(lista,val)
		for j in range(0,longi):
			sum = sum + float(cache.lindex(lista,j).decode("utf-8"))
		prom = sum/10.0
		if prom<min or prom>max:
			payload = {}
			payload["alert_type"]=2
			payload["trigger"]=typ
			payload["maesurement"]=mes
			prod.send('alarmas', str(payload).encode("utf-8"))
			print("alarma enviada")

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
	alarmoutsend(producer,1,16.1,21.6,temp,payload)
	alarmoutsend(producer,2,80.0,85.0,noise,payload)
	alarmoutsend(producer,3,0.0,350.0,gas,payload)
	alarmoutsend(producer,4,100.0,500.0,ilum,payload)