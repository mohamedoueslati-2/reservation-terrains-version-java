package org.example.dao;

import org.example.model.Joueur;
import java.util.List;

public interface JoueurDao {
    Joueur trouverJoueurParEmailEtMotDePasse(String email, String motDePasse);
    void ajouterJoueur(Joueur joueur);
    void mettreAJourJoueur(Joueur joueur);
    void supprimerJoueur(int id);
    Joueur trouverJoueurParId(int id);
    List<Joueur> listerTousLesJoueurs();
    boolean estJoueurBloque(int joueurId);
    boolean creerJoueur(Joueur joueur);
}