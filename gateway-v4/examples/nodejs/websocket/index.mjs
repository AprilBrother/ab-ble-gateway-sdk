import { WebSocketServer } from 'ws';

const port = 8080
const wss = new WebSocketServer({ port: port });

console.log('Websocket server started at port:', port);
wss.on('connection', function connection(ws) {
  ws.on('error', console.error);

  ws.on('message', function message(data) {
    console.log('received: %s', data);
  });

});
