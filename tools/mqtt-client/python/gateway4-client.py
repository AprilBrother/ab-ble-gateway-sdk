import time
import paho.mqtt.client as mqtt
import msgpack

def on_subscribe(mosq, obj, mid, granted_qos):
    print("Subscribed: " + str(mid))

def on_message(mosq, obj, msg):
    for d in msgpack.unpackb(msg.payload)[b'devices']:
      print("=============================================")
      # adv type
      print "type:", ord(d[0])
      print "mac:{:02X}{:02X}{:02X}{:02X}{:02X}{:02X}".format(ord(d[1]), ord(d[2]), ord(d[3]), ord(d[4]), ord(d[5]), ord(d[6]))
      print "rssi:", ord(d[7]) - 256
      hex_chars = map(hex,map(ord,d))
      del hex_chars[:8]
      print "adv:", hex_chars

def on_connect(mosq, obj,flags, rc):
    print("Connected with result code "+str(rc))
    mqttc.subscribe("hello", 0)
    print("Connected")


mqttc = mqtt.Client()
mqttc.on_connect = on_connect
mqttc.on_subscribe = on_subscribe
mqttc.on_message = on_message
mqttc.connect("mqtt.bconimg.com", 1883, 60)
mqttc.loop_forever()
