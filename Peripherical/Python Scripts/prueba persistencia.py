from kafka import KafkaConsumer
import datetime
import requests
import datetime

def alarmsend(prod, typ, min, max, val, mes):
	if val<min or val>max:
		if False:
			print("hola")
		else:
			print("enviando")
			payload = {}
			payload["alert_type"]=0
			payload["trigger"]=typ
			mes = {}
			mes["location"]=1001001
			mes["temperature"]=20.0
			mes["gas"]=500
			mes["light"]=150.54
			mes["sound"]=84
			mes["timestamp"]="2017-10-26T00:02:58.070Z" 
			payload["measurement"]=mes
			r = requests.post("",json = payload)
			print("enviado")
			

	
