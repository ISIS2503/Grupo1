import paho.mqtt.client as paho
from kafka import KafkaProducer
global producer
def on_message(clnt, userdata, msg):
    print(msg.topic+" "+str(msg.payload))
    producer.send(msg.topic,msg.payload)
mqttc = paho.Client('bridge')
producer = KafkaProducer(bootstrap_servers=['172.24.42.20:8090'],
						sasl_plain_username = 'bridge',
                        sasl_plain_password = 'bridge',
                        security_protocol = 'SASL_PLAINTEXT',
                        sasl_mechanism = 'PLAIN')
mqttc.username_pw_set('bridge', password='bridge')
mqttc.on_message = on_message
mqttc.tls_set("/home/jssosa10/ssl/m2mqtt_srv.crt") # http://test.mosquitto.org/ssl/mosquitto.org.crt
mqttc.tls_insecure_set(True)
mqttc.connect("ec2-52-90-76-212.compute-1.amazonaws.com", 8883)
mqttc.subscribe("data")
mqttc.loop_forever()