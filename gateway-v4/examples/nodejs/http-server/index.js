const http = require('http');
const msgpack = require('msgpack5')();

const server = http.createServer((req, res) => {
  const chunks = [];
  let size = 0;

  req.on('data', (chunk) => {
    chunks.push(chunk);
    size += chunk.length;
  });

  req.on('end', () => {
    let data = null;
    switch (chunks.length) {
      case 0: data = chunks[0];
        break;
      case 1: data = chunks[0];
        break;
      default:
        data = new Buffer(size);
        for (let i = 0, pos = 0, len = chunks.length; i < len; i++) {
          const chunk = chunks[i];
          chunk.copy(data, pos);
          pos += chunk.length;
        }
        break;
    }

    if (data == null) {
        return;
    }

    data = msgpack.decode(data);
    console.log(data);
    res.end('post')
  });
});

server.listen(8000);
