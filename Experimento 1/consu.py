from kafka import KafkaConsumer
import datetime
import requests
import smtplib

consumer = KafkaConsumer('alarmas',
                         group_id='my-group',
                         bootstrap_servers=['172.24.42.20:8090'])

location = 1001001
print("Consumidor de alertas conectado con Kafka")
for message in consumer:
		print("entro")
		msg = message.value.decode("utf-8").split(',')
		playload = {}
		alarm = float(msg[0].split(':')[1])
		trigger = float(msg[1].split(":")[1])
		locacion = msg[2].split(":")[2]
		temp = float(msg[3].split(':')[1])
		gas = float(msg[4].split(':')[1])
		ilum = float(msg[5].split(':')[1])
		noise = float(msg[6].split(':')[1])
		now = msg[7].split(':')[1]
		print(str(alarm) + "TIPO DE ALARMA")
		if alarm == 3:
			causa = ""
			if trigger == 1:
				causa = "Temperatura"
			elif trigger == 2:
				causa = "Ruido"
			elif trigger == 3:
				causa = "Gases"
			else :
				causa = "Luminocidad"
			mensaje = "Se detecto Alerta, actuador ineficiente, no se ha logrado corregir la alerta en la variable "+ causa + ", en la LOCACION " + str(location) + ", con TEMPERATURA " + str(temp) + "grados C, con GAS " + str(gas) + " ppm, con LUMINOSIDAD " + str(ilum) + " lux, RUIDO " + str(noise) + " decibeles" + " Fecha de la alarma " + str(now)
			fromaddr = 'grupo1losmineros1@gmail.com'
			toaddrs  = 'pabloalvaradoceron@gmail.com'
			# Datos
			username = 'grupo1losmineros1@gmail.com'
			password = 'grupo1losmineros'
			# Enviando el correo
			server = smtplib.SMTP('smtp.gmail.com:587')
			server.starttls()
			server.login(username,password)
			server.sendmail(fromaddr, toaddrs, mensaje)
			server.quit()
			print("salio 1")
		elif alarm == 2:
			causa = ""
			if trigger == 1:
				causa = "Temperatura"
			elif trigger == 2:
				causa = "Ruido"
			elif trigger == 3:
				causa = "Gases"
			else :
				causa = "Luminocidad"
			mensaje = "Se detecto Alerta, de medicion fuera de rango en la variable "+ causa + ", en la LOCACION " + str(location) + ", con TEMPERATURA " + str(temp) + "grados C, con GAS " + str(gas) + " ppm, con LUMINOSIDAD " + str(ilum) + " lux, RUIDO " + str(noise) + " decibeles" + " Fecha de la alarma " + str(now) 
			fromaddr = 'grupo1losmineros1@gmail.com'
			toaddrs  = 'pabloalvaradoceron@gmail.com'
			# Datos
			username = 'grupo1losmineros1@gmail.com'
			password = 'grupo1losmineros'
			# Enviando el correo
			server = smtplib.SMTP('smtp.gmail.com:587')
			server.starttls()
			server.login(username,password)
			server.sendmail(fromaddr, toaddrs, mensaje)
			server.quit()
			print("salio 2")
# Envio de correo a el correo pabloalvaradoceron@gmail

		
