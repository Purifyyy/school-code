const WebSocket = require('ws');
const https = require('https');
const fs = require('fs');

const server = https.createServer({
    cert: fs.readFileSync("..."),
    key: fs.readFileSync("...")
})

server.listen(9000)

const ws = new WebSocket.Server({ server });

let messages = [];

let playersInGame = 0;
let id = 1;
let availableColors = ["purple", "yellow"];
let availableSpots = [[18,19], [21,19]]

ws.on('connection', ( socket ) => {
    messages.forEach(message => {
        socket.send(JSON.stringify(message));
    })
    socket.id = id++;
    socket.ready = 0;

    socket.on('message', ( data ) => {

        const msg = JSON.parse(data.toString());
        if(msg === "ready") {
            if(playersInGame >= 2) {
                socket.send(JSON.stringify({"text": "Match has already begun!"}));
            } else {
                playersInGame++;
                socket.ready = 1;
                socket.color = availableColors.pop()
                socket.send(JSON.stringify({"color": socket.color, "spot": availableSpots.pop(), "text": "Finding match, prepare yourself."}));
                checkIfPlayersReady();
            }
        }
        if(msg.color) {
            socket.color = msg.color;
        } else {
            msg.color = socket.color;
            if(availableColors.length !== 2) {
                messages.push(msg);
            }
        }
        ws.clients.forEach(client => {
            client.send(JSON.stringify(msg));
        })
        if(msg === "gg") {
            ws.clients.forEach(client => {
                if(client.id !== socket.id) {
                    client.send(JSON.stringify("gg"))
                    availableColors = ["purple", "yellow"];
                    availableSpots = [[18,19], [21,19]]
                    playersInGame = 0;
                    ws.clients.forEach((socket) => {
                        socket.close();
                    })
                    messages = []
                }
            })
        }
    })
})

function checkIfPlayersReady() {
    let sum = 0
    ws.clients.forEach(client => {
        if(client.ready) {
            sum++;
        }
    })
    if(sum === 2) {
        ws.clients.forEach(client => {
            client.send(JSON.stringify("begin"));
        })
    }
}
