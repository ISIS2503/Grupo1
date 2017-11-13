#!/bin/bash
#script para iniciar el servidor mosquitto
MQTTC=/etc/mosquitto/mosquitto.conf
gnome-terminal -e 'bash -c "redis-server; exec bash"'
gnome-terminal -e 'python init.py'
gnome-terminal -e 'python server.py'
gnome-terminal -e 'node-red'
sudo mosquitto -c $MQTTC
