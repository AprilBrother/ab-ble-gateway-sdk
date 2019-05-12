<?php
require __DIR__ . '/vendor/autoload.php';

use AprBrother\PacketParser;

function saveLog($msg) {
    // DATE_ATOM - msg
    $msgType = 3;
    $logFile = "devices_" . date("Ymd") . ".log";
    $msg = date(DATE_ATOM) . " - $msg\n";
    error_log($msg, $msgType, $logFile);
}

function saveData($data) {
    $msgType = 3;
    $logFile = "data_" . date("Ymd") . ".log";
    error_log($data, $msgType, $logFile);
}

$server = new swoole_websocket_server("0.0.0.0", 8000);

$server->on('open', function (swoole_websocket_server $server, $request) {
    echo "server: handshake success with fd{$request->fd}\n";
});

$server->on('message', function (swoole_websocket_server $server, $frame) {
    echo "===== RAW message ====\n";
    echo PacketParser::hexString($frame->data);

    list($meta, $data) = PacketParser::parse($frame->data);
    echo "\n===== meta ====\n";
    print_r($meta);
    echo "===== data ====\n";
    foreach($data as $v) {
        $iBeacon = (int)PacketParser::isIbeacon($v);
        echo "mac: $v->macAddress rssi: $v->rssi iBeacon: $iBeacon adv:";
        echo PacketParser::hexString($v->rawData);
        echo "\n";
    }
    //saveLog(json_encode($device));
});

$server->on('close', function ($ser, $fd) {
    echo "client {$fd} closed\n";
});

$server->start();
