// src/main/java/org/example/model/Admin.java
package org.example.model;

public class Admin {
    private int id;
    private String nom;
    private String email;
    private String motDePasse;

    // Constructeur avec id
    public Admin(int id, String nom, String email, String motDePasse) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.motDePasse = motDePasse;
    }

    // Constructeur sans id (pour l'insertion)
    public Admin(String nom, String email, String motDePasse) {
        this.nom = nom;
        this.email = email;
        this.motDePasse = motDePasse;
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

    @Override
    public String toString() {
        return "Admin{id=" + id + ", nom='" + nom + "', email='" + email + "', motDePasse='" + motDePasse + "'}";
    }
}