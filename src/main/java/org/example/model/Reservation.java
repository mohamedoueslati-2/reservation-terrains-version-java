package org.example.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

public class Reservation {
    private int id;
    private int joueurId;
    private int terrainId;
    private Date date;
    private Time heureDebut;
    private Time heureFin;
    private String paiement;
    private BigDecimal montantTotal;
    private String statut;

    // Champs pour affichage
    private String joueurNom;
    private String terrainNom;

    public Reservation() {}

    public Reservation(int id, int joueurId, int terrainId, Date date, Time heureDebut, Time heureFin, String paiement, BigDecimal montantTotal, String statut) {
        this.id = id;
        this.joueurId = joueurId;
        this.terrainId = terrainId;
        this.date = date;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.paiement = paiement;
        this.montantTotal = montantTotal;
        this.statut = statut;
    }

    // Getters et setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getJoueurId() { return joueurId; }
    public void setJoueurId(int joueurId) { this.joueurId = joueurId; }
    public int getTerrainId() { return terrainId; }
    public void setTerrainId(int terrainId) { this.terrainId = terrainId; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    public Time getHeureDebut() { return heureDebut; }
    public void setHeureDebut(Time heureDebut) { this.heureDebut = heureDebut; }
    public Time getHeureFin() { return heureFin; }
    public void setHeureFin(Time heureFin) { this.heureFin = heureFin; }
    public String getPaiement() { return paiement; }
    public void setPaiement(String paiement) { this.paiement = paiement; }
    public BigDecimal getMontantTotal() { return montantTotal; }
    public void setMontantTotal(BigDecimal montantTotal) { this.montantTotal = montantTotal; }
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public String getJoueurNom() { return joueurNom; }
    public void setJoueurNom(String joueurNom) { this.joueurNom = joueurNom; }
    public String getTerrainNom() { return terrainNom; }
    public void setTerrainNom(String terrainNom) { this.terrainNom = terrainNom; }
}