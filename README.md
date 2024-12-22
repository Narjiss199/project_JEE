# Système de Gestion d'Inventaire avec RMI

## Description du Projet
Ce projet est un système de gestion d'inventaire conçu pour une petite entreprise. Il permet de gérer les produits (CRUD) dans une base de données et offre un accès distant via RMI. Le projet comprend deux parties principales :
- **Serveur** : Gère la connexion à la base de données et expose les fonctionnalités via RMI.
- **Client** : Fournit une interface graphique Swing pour interagir avec le serveur.

## Fonctionnalités
1. Ajouter un produit.
2. Mettre à jour les informations d’un produit.
3. Supprimer un produit.
4. Afficher la liste des produits.
5. Accès distant sécurisé via RMI.

## Architecture
- **Base de données** : MySQL, utilisée pour stocker les données des produits.
- **Serveur** : Implémenté en Java avec RMI pour exposer les fonctionnalités.
- **Client** : Application Java Swing pour interagir avec le serveur.

## Prérequis
- **JDK** : Version 8 ou supérieure.
- **MySQL** : Serveur de base de données installé et configuré.
- **Bibliothèque JDBC** : `mysql-connector-java`.
- **IDE** : IntelliJ IDEA (ou un autre IDE compatible avec Java).

## Configuration et Installation
1. **Configurer la base de données** :
    - Exécutez le fichier schema.sql pour créer les tables nécessaires.
    - Exécutez le fichier data.sql pour insérer des données de test.
    - Assurez-vous que la base de données est connectée à votre projet via le fichier DatabaseConnection.

2. **Configurer le Projet** :
   - Clonez le dépôt ou téléchargez les fichiers.
   - Ajoutez la bibliothèque `mysql-connector-java` dans le chemin de votre projet.
   - Assurez-vous que les fichiers `Server` et `Client` sont bien configurés.

3. **Lancer le Serveur** :
   - Exécutez la classe `Server` située dans le package `server`.
   - Assurez-vous que le message `Serveur RMI prêt.` s'affiche dans la console.

4. **Lancer le Client** :
   - Exécutez la classe `LoginFrame` dans le package `client`.

## Instructions pour Exécuter
1. Démarrez le serveur en exécutant la classe `Server`.
2. Lancez le client en exécutant la classe `LoginFrame`.
3. Utilisez l’interface graphique de `MainFrame` pour effectuer des opérations sur l’inventaire :
   - Ajouter, rechercher, et mettre à jour ou supprimer un produit, .
   - Visualiser les produits existants.

## Structure du Projet

|-- src
    |-- server
    |   |-- auth
    |   |   |-- AuthService.java        # Gestion d'authentification
    |   |-- dao
    |   |   |-- ProductDAO.java         # Gestion des opérations CRUD
    |   |-- models
    |   |   |-- Employee.java           # Classe représentant un employe
    |   |   |-- Product.java            # Classe représentant un produit
    |   |-- rmi
    |   |   |-- ProductService.java     # Interface RMI pour les opérations sur les produits
    |   |   |-- ProductServiceImpl.java # Implémentation des fonctionnalités RMI
    |   |-- utils
    |   |   |-- DatabaseConnection.java # Gestion de la connexion à la base de données
    |   |-- Server.java                 # Classe principale pour démarrer le serveur RMI
    |
    |-- client
        |-- Client.java                 # Classe principale pour le client 
        |-- LoginFrame.java             # Classe de gestion d'authentification
        |-- MainFrame.java              # Classe de conception Swing de l'interface utilisateur
    |
|-- sql
    |-- schema.sql                      # Script SQL pour créer la base de données
    |-- data.sql                        # Script SQL pour insérer des données de test
|
|-- lib
    |-- mysql-connector-java-x.x.x.jar  # Connecteur JDBC pour MySQL
|
|-- README.md                           # Documentation du projet

