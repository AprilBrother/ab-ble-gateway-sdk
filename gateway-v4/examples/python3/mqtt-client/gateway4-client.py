import time
import paho.mqtt.client as mqtt
import msgpack
from beacontools import parse_packet

def on_subscribe(mosq, obj, mid, granted_qos):
    print("Subscribed: " + str(mid))

def on_message(mosq, obj, msg):
    for d in msgpack.unpackb(msg.payload, raw = True)[b'devices']:
      #parse iBeacon data
      advData = d[8:]
      adv = parse_packet(advData)

      print("=============================================")
      print("mac: {:02X}{:02X}{:02X}{:02X}{:02X}{:02X}".format((d[1]), (d[2]), (d[3]), (d[4]), (d[5]), (d[6])))
      print("rssi:", (d[7]) - 256)

      if adv == None:
          print("adv:", advData);
      else:
          if hasattr(adv, "uuid"):
              print("UUID: %s" % adv.uuid)
              print("Major: %d" % adv.major)
              print("Minor: %d" % adv.minor)
              print("TX Power: %d" % adv.tx_power)

def on_connect(mosq, obj,flags, rc):
    mqttTopic = "gateway"
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
