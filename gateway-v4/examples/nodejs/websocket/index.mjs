import { WebSocketServer } from 'ws'
import msgpack5 from 'msgpack5'

const port = 8080
const wss = new WebSocketServer({ port: port });
const msgpack = msgpack5()

console.log('Websocket server started at port:', port);
wss.on('connection', function connection(ws) {
    ws.on('error', console.error);

    ws.on('message', function message(body) {
        try {
            let data = JSON.parse(body)
            console.log("JSON:", data)
        } catch(e) {
            let data = msgpack.decode(body)
            console.log("MsgPack:", data)
        }
    });

});
