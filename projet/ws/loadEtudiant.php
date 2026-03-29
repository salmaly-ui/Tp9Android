<?php
header('Content-Type: application/json');
header("Access-Control-Allow-Origin: *"); // pour que le téléphone puisse accéder

include_once '../service/EtudiantService.php';  
include_once '../connexion/Connexion.php';     
$es = new EtudiantService();
echo json_encode($es->findAllApi());
?>