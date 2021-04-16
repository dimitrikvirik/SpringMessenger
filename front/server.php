<?php
header('Content-Type: application/json');
header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Headers: *');
//Connect to Database
$dbh = new PDO('mysql:host=localhost;dbname=test', 'root', '');
//Get message
if(isset($_GET["msgId"])) {
    $sql = ("SELECT * FROM persons where msgId=" . $_GET["msgId"]);
    $getMsg = $dbh->prepare($sql);
    $getMsg->execute();
    $msg = $getMsg->fetch();
    $json =  json_encode($msg);
    echo $json;
}
//Send message
if(isset($_POST['msg'])){
    $data =  $_POST;
    $sql = "INSERT INTO persons (name, msg) VALUES (:name, :msg)";
    $stmt= $dbh->prepare($sql);
    $stmt->execute($data);
    $json =  json_encode($_POST);
    echo $json;
}
