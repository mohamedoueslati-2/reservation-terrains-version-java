package org.example.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "terrain")
public class Terrain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String etat;

    @Column(name = "tarif_horaire", nullable = false)
    private BigDecimal tarifHoraire;

    @Column
    private String images;

    public Terrain() {}

    public Terrain(int id, String nom, String type, String etat, BigDecimal tarifHoraire, String images) {
        this.id = id;
        this.nom = nom;
        this.type = type;
        this.etat = etat;
        this.tarifHoraire = tarifHoraire;
        this.images = images;
    }

    public Terrain(String nom, String type, String etat, BigDecimal tarifHoraire, String images) {
        this.nom = nom;
        this.type = type;
        this.etat = etat;
        this.tarifHoraire = tarifHoraire;
        this.images = images;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getEtat() { return etat; }
    public void setEtat(String etat) { this.etat = etat; }
    public BigDecimal getTarifHoraire() { return tarifHoraire; }
    public void setTarifHoraire(BigDecimal tarifHoraire) { this.tarifHoraire = tarifHoraire; }
    public String getImages() { return images; }
    public void setImages(String images) { this.images = images; }

    // Affichage personnalisé pour l'interface
    public String toStringPersonalise() {
        return nom + " (" + type + ")";
    }

    // Affichage technique/complet
    @Override
    public String toString() {
        return "Terrain{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", type='" + type + '\'' +
                ", etat='" + etat + '\'' +
                ", tarifHoraire=" + tarifHoraire +
                ", images='" + images + '\'' +
                '}';
    }
}