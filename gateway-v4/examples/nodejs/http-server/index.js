const msgpack = require('msgpack5')()
const express = require('express')
const app = express()

let serverPort = 8000

app.use(express.raw({type: [
    'application/msgpack',
    'application/json'
]}))

app.post('/', function(req, res) {
    let type = req.get('Content-Type')
    let body = req.body

    if(type == 'application/msgpack') {
        let data = msgpack.decode(body)
        console.log(data)
    } else if(type == 'application/json') {
        let data = JSON.parse(body.toString())
        console.log(data)
    }
    res.end()
})
app.listen(serverPort)
console.log("Server start at port:", serverPort)

