# 📱 ProjetWS — Application Android de Gestion des Étudiants

Application Android permettant d'ajouter et de consulter des étudiants via un Web Service PHP/MySQL.

---

## 📋 Table des matières

- [Description](#description)
- [Architecture du projet](#architecture-du-projet)
- [Prérequis](#prérequis)
- [Installation](#installation)
- [Structure des fichiers](#structure-des-fichiers)
- [Fonctionnalités](#fonctionnalités)
- [Configuration réseau](#configuration-réseau)
- [Technologies utilisées](#technologies-utilisées)
- [API Web Service](#api-web-service)

---
# demo 


https://github.com/user-attachments/assets/428f3372-2e01-44c5-af39-7908a0f06c3a


## Description

**ProjetWS** est une application Android native qui communique avec un serveur PHP local pour gérer une liste d'étudiants. Elle permet d'ajouter un étudiant (nom, prénom, ville, sexe) et d'afficher la liste complète des étudiants enregistrés en base de données.

---

## Architecture du projet

```
ProjetWS/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/example/projetws/
│   │       │   ├── MainActivity.java          # Écran d'accueil
│   │       │   ├── AddEtudiant.java           # Formulaire d'ajout
│   │       │   ├── ListeEtudiant.java         # Liste des étudiants
│   │       │   └── beans/
│   │       │       └── Etudiant.java          # Modèle de données
│   │       ├── res/
│   │       │   ├── layout/
│   │       │   │   ├── activity_main.xml
│   │       │   │   ├── activity_add_etudiant.xml
│   │       │   │   ├── activity_liste_etudiant.xml
│   │       │   │   └── item_etudiant.xml
│   │       │   └── values/
│   │       │       └── strings.xml
│   │       └── AndroidManifest.xml
├── build.gradle
└── README.md
```

---

## Prérequis

### Côté Android
- Android Studio **Hedgehog** ou supérieur
- SDK Android **API 21+** (Android 5.0 minimum)
- Émulateur Android ou appareil physique

### Côté Serveur
- **XAMPP** (ou WampServer) avec Apache + MySQL
- PHP **7.4+**
- Base de données MySQL configurée

### Dépendances Gradle

Ajouter dans `build.gradle (app)` :

```gradle
dependencies {
    implementation 'com.android.volley:volley:1.2.1'
    implementation 'com.google.code.gson:gson:2.10.1'
}
```

---

## Installation

### 1. Cloner / importer le projet

Ouvrir le projet dans Android Studio :
```
File > Open > Sélectionner le dossier ProjetWS
```

### 2. Configurer le serveur PHP

Démarrer **XAMPP** et placer les fichiers PHP dans :
```
C:/xampp/htdocs/projet/ws/
```

Fichiers PHP nécessaires :
- `loadEtudiant.php` — retourne la liste des étudiants en JSON
- `createEtudiant.php` — insère un étudiant et retourne l'objet créé en JSON

### 3. Créer la base de données

```sql
CREATE DATABASE projet;

USE projet;

CREATE TABLE etudiant (
    id     INT AUTO_INCREMENT PRIMARY KEY,
    nom    VARCHAR(50)  NOT NULL,
    prenom VARCHAR(50)  NOT NULL,
    ville  VARCHAR(50)  NOT NULL,
    sexe   VARCHAR(10)  NOT NULL
);
```

### 4. Lancer l'application

- Lancer l'émulateur Android dans Android Studio
- Cliquer sur **Run ▶** (ou `Shift + F10`)

---

## Structure des fichiers

| Fichier | Rôle |
|---|---|
| `MainActivity.java` | Écran principal avec 2 boutons de navigation |
| `AddEtudiant.java` | Formulaire d'ajout d'un étudiant via POST |
| `ListeEtudiant.java` | Affichage de la liste via RecyclerView |
| `Etudiant.java` | Bean Java (modèle) avec getters/setters |
| `activity_main.xml` | Layout de l'écran d'accueil |
| `activity_add_etudiant.xml` | Layout du formulaire d'ajout |
| `activity_liste_etudiant.xml` | Layout de la liste (RecyclerView) |
| `item_etudiant.xml` | Layout d'un item de la liste |
| `strings.xml` | Chaînes de caractères + tableau des villes |
| `AndroidManifest.xml` | Déclaration des activités et permissions |

---

## Fonctionnalités

### 🏠 Écran principal (`MainActivity`)
- Bouton **"Ajouter un étudiant"** → navigue vers le formulaire
- Bouton **"Voir tous les étudiants"** → navigue vers la liste

### ➕ Ajout d'un étudiant (`AddEtudiant`)
- Champs : **Nom**, **Prénom**
- Sélection de la **Ville** via Spinner (Marrakech, Rabat, Casablanca)
- Sélection du **Sexe** via RadioButton (Homme / Femme)
- Validation des champs vides avant envoi
- Requête **HTTP POST** vers `createEtudiant.php`
- Toast de confirmation après ajout réussi
- Bouton **Retour** pour revenir à l'accueil

### 📋 Liste des étudiants (`ListeEtudiant`)
- Requête **HTTP GET** vers `loadEtudiant.php`
- Affichage dans un **RecyclerView** avec nom, prénom, ville et sexe
- Bouton **Retour** pour revenir à l'accueil

---

## Configuration réseau

L'émulateur Android accède au serveur local via l'adresse :

```
http://10.0.2.2/projet/ws/
```

> `10.0.2.2` est l'alias de `localhost` (127.0.0.1) depuis l'émulateur Android.

Les permissions suivantes sont déclarées dans `AndroidManifest.xml` :

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

Et pour autoriser le trafic HTTP non chiffré :

```xml
android:usesCleartextTraffic="true"
```

---

## Technologies utilisées

| Technologie | Usage |
|---|---|
| **Java** | Langage principal Android |
| **Volley** | Requêtes HTTP (GET / POST) |
| **Gson** | Désérialisation JSON → objets Java |
| **RecyclerView** | Affichage de la liste des étudiants |
| **PHP** | Web Service côté serveur |
| **MySQL** | Base de données |
| **XAMPP** | Serveur local de développement |

---

## API Web Service

### `GET /loadEtudiant.php`

Retourne la liste de tous les étudiants.

**Réponse :**
```json
[
  { "id": 1, "nom": "Alami", "prenom": "Youssef", "ville": "Marrakech", "sexe": "homme" },
  { "id": 2, "nom": "Benali", "prenom": "Sara", "ville": "Rabat", "sexe": "femme" }
]
```

### `POST /createEtudiant.php`

Ajoute un nouvel étudiant.

**Paramètres POST :**

| Paramètre | Type | Exemple |
|---|---|---|
| `nom` | string | `Alami` |
| `prenom` | string | `Youssef` |
| `ville` | string | `Marrakech` |
| `sexe` | string | `homme` |

**Réponse :**
```json
{ "id": 3, "nom": "Alami", "prenom": "Youssef", "ville": "Marrakech", "sexe": "homme" }
```

---

## Auteur

Projet réalisé dans le cadre d'un TP Android — Web Services avec PHP et MySQL.
