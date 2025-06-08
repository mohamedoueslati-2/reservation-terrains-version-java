DROP DATABASE IF EXISTS jdbc;

CREATE DATABASE jdbc ;

USE jdbc;
-- Table Admin
CREATE TABLE admin (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       nom VARCHAR(100),
                       email VARCHAR(100) UNIQUE,
                       mot_de_passe VARCHAR(255)
);

-- Table Joueur
CREATE TABLE joueur (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        nom VARCHAR(100),
                        email VARCHAR(100) UNIQUE,
                        mot_de_passe VARCHAR(255),
                        num_telephone VARCHAR(20),
                        statut ENUM('actif', 'bloqué') DEFAULT 'actif'
);

-- Table Terrain
CREATE TABLE terrain (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         nom VARCHAR(100),
                         type ENUM('football', 'volleyball', 'basketball'),
                         etat ENUM('bon', 'en maintenance', 'hors service') DEFAULT 'bon',
                         tarif_horaire DECIMAL(10,2),
                         images TEXT
);

-- Table Reservation (
CREATE TABLE reservation (
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             joueur_id INT,
                             terrain_id INT,
                             date DATE,
                             heure_debut TIME,
                             heure_fin TIME,
                             paiement ENUM('payé', 'non payé') DEFAULT 'non payé',
                             montant_total DECIMAL(10,2),
                             statut ENUM('confirmé', 'annulé') DEFAULT 'confirmé',
                             FOREIGN KEY (joueur_id) REFERENCES joueur(id) ON DELETE CASCADE,
                             FOREIGN KEY (terrain_id) REFERENCES terrain(id) ON DELETE CASCADE
);
