const http = require('http');

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
    data = data.toString('hex');
    const head = decodeURIComponent(data.match(/^7b\S+0d0a0d0a/)[0].replace(/(\w{2})/g, '%$1'));
    data = data.replace(head, '');
    data = data.replace(/(0d0a)/gm, '$1\n');
    const bles = data.match(/fe\w+0d0a/gm);
    console.log(head)   // 元数据
    console.log(bles)   // 扫描到的蓝牙设备数据
    res.end('post')
  });
});

server.listen(8000);
