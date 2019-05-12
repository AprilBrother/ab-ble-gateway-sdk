var mqtt=require('mqtt');
var client  = mqtt.connect('mqtt://192.168.31.35');
 
client.on('connect', function () {
  client.subscribe('beacons');
});
 
client.on('message', function (topic, data) {
    data = data.toString('hex');
    const head = decodeURIComponent(data.match(/^7b\S+0d0a0d0a/)[0].replace(/(\w{2})/g, '%$1'));
    data = data.replace(head, '');
    data = data.replace(/(0d0a)/gm, '$1\n');
    const bles = data.match(/fe\w+0d0a/gm);
    console.log(head)   // meta data
    console.log(bles)   // BLE advertisement data
});
