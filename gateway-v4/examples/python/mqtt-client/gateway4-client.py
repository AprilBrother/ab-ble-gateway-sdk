import time
import paho.mqtt.client as mqtt
import msgpack
from beacontools import parse_packet

def on_subscribe(mosq, obj, mid, granted_qos):
    print("Subscribed: " + str(mid))

def on_message(mosq, obj, msg):
    for d in msgpack.unpackb(msg.payload)[b'devices']:
      #parse iBeacon data
      advData = d[8:]
      adv = parse_packet(advData)
      if adv == None:
          continue

      print("=============================================")
      print "mac:{:02X}{:02X}{:02X}{:02X}{:02X}{:02X}".format(ord(d[1]), ord(d[2]), ord(d[3]), ord(d[4]), ord(d[5]), ord(d[6]))
      print "rssi:", ord(d[7]) - 256

      print("UUID: %s" % adv.uuid)
      print("Major: %d" % adv.major)
      print("Minor: %d" % adv.minor)
      print("TX Power: %d" % adv.tx_power)

def on_connect(mosq, obj,flags, rc):
    mqttTopic = "hello"
    print("Connected with result code "+str(rc))
    mqttc.subscribe(mqttTopic, 0)
    print("Connected")

mqttHost    = "mqtt.bconimg.com"
mqttPort    = 1883
mqttc = mqtt.Client()
mqttc.on_connect = on_connect
mqttc.on_subscribe = on_subscribe
mqttc.on_message = on_message
mqttc.connect(mqttHost, mqttPort, 60)
mqttc.loop_forever()
