#!/bin/bash
#script para iniciar el servidor mosquitto
MQTTC=/etc/mosquitto/mosquitto.conf
sudo /opt/zookeeper-3.4.10/bin/zkServer.sh start
gnome-terminal -e 'bash -c "redis-server; exec bash"'
gnome-terminal -e 'python init.py'
gnome-terminal -e 'python server.py'
gnome-terminal -e 'node-red'
gnome-terminal -e 'sudo /opt/kafka_2.11-0.11.0.0/bin/kafka-server-start.sh /opt/kafka_2.11-0.11.0.0/config/server.properties'
sudo mosquitto -c $MQTTC
