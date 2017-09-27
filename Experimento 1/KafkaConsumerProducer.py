from kafka import KafkaProducer
from kafka import KafkaProducer
from kafka.errors import KafkaError
from datetime import datetime

consumer = KafkaConsumer(‘alta.piso1.local1’,
                         group_id='my-group',
                         bootstrap_servers=['localhost:8090’])

location = 001001

for message in consumer:
	msg = message.split(“,”) 
	now = msg[0].split(“:”)[1].replace(“\””,””)
	temp = msg[1].split(“:”)[2]
	noise = msg[2].split(“:”)[1]
	gas = msg[3].split(“:”)[1]
	ilum = msg[4].split(“:”)[1].split(“}”)[0]
	sendit = “{’location’:’” + location + “‘, ’temperature’:’” + temp + “‘, ‘gas’:’” + gas + “‘, ’light’:’” + ilum + “‘, ‘sound’:’” + noice + “‘, ‘timestamp:’”+ now+”’}”
	r = requests.post(’172.24.42.34/measurement’, msg)