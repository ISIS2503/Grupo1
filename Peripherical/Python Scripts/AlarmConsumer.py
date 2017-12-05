from kafka import KafkaConsumer
from kafka import KafkaProducer
import datetime
import redis
import requests
def alarminefficient(prod,typ,mes):
	cache = redis.Redis(host='localhost',port=8090,db=0)
	loc = str(mes["location"])
	cc = loc+str(typ)+"count"
	num = int(cache.get(cc).decode("utf-8"))
	print(num)
	if num >20:
		print("too many")
		payload = {}
		payload["alert_type"]=3
		payload["trigger"]=typ
		payload["maesurement"]=mes
		prod.send('alarmas', str(payload).encode("utf-8"))

def alarmoutsend(prod, typ, min, max, val, mes):
#prod.send('alarmas', str(payload).encode("utf-8"))
	print("creando alarma")
	cache = redis.Redis(host='localhost',port=8090,db=0)
	loc = str(mes["location"])
	lista = loc+str(typ)
	cc = lista+"count"
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
			cache.incr(cc)
			print(cache.get(cc).decode("utf-8"))
			print("alarma enviada")
		else:
			cache.set(cc,0)

print("creando")
consumer = KafkaConsumer('data',
                         bootstrap_servers=['172.24.42.20:8090'],
                         sasl_plain_username = 'data',
                         sasl_plain_password = 'data',
                         security_protocol = 'SASL_PLAINTEXT',
                         sasl_mechanism = 'PLAIN')
print("saliendo")
print("creando prod")
producer = KafkaProducer(bootstrap_servers=['172.24.42.20:8090'],
						 sasl_plain_username = 'data',
                         sasl_plain_password = 'data',
                         security_protocol = 'SASL_PLAINTEXT',
                         sasl_mechanism = 'PLAIN')
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
	alarminefficient(producer,1,payload)
	alarmoutsend(producer,2,80.0,85.0,noise,payload)
	alarminefficient(producer,2,payload)
	alarmoutsend(producer,3,0.0,350.0,gas,payload)
	alarminefficient(producer,3,payload)
	alarmoutsend(producer,4,100.0,500.0,ilum,payload)
	alarminefficient(producer,4,payload)