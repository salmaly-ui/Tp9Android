<?php
header('Content-Type: application/json');
header("Access-Control-Allow-Origin: *"); // pour téléphone

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    include_once '../service/EtudiantService.php';
    include_once '../classes/Etudiant.php';
    include_once '../connexion/Connexion.php';

    $nom = $_POST['nom'];
    $prenom = $_POST['prenom'];
    $ville = $_POST['ville'];
    $sexe = $_POST['sexe'];

    $es = new EtudiantService();
    $es->create(new Etudiant(0, $nom, $prenom, $ville, $sexe));

    echo json_encode($es->findAllApi());
}
?>