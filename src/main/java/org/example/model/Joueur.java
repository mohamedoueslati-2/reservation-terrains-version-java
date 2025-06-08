package org.example.model;

public class Joueur {
    private int id;
    private String nom;
    private String email;
    private String motDePasse;
    private String numTelephone;
    private String statut;

    public Joueur() {}

    public Joueur(int id, String nom, String email, String motDePasse, String numTelephone, String statut) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.numTelephone = numTelephone;
        this.statut = statut;
    }

    // Getters et setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }
    public String getNumTelephone() { return numTelephone; }
    public void setNumTelephone(String numTelephone) { this.numTelephone = numTelephone; }
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
}