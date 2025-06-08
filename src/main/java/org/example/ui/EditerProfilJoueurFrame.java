package org.example.ui;

import org.example.dao.JoueurDao;
import org.example.dao.JoueurDaoImpl;
import org.example.model.Joueur;

import javax.swing.*;
import java.awt.*;

public class EditerProfilJoueurFrame extends JFrame {
    private JTextField nomField, emailField, telField;
    private JPasswordField passwordField;
    private JoueurDao joueurDao = new JoueurDaoImpl();
    private Joueur joueur;

    public EditerProfilJoueurFrame(Joueur joueur) {
        this.joueur = joueur;
        setTitle("Éditer mon profil");
        setSize(350, 250);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Nom:"));
        nomField = new JTextField(joueur.getNom());
        panel.add(nomField);

        panel.add(new JLabel("Email:"));
        emailField = new JTextField(joueur.getEmail());
        panel.add(emailField);

        panel.add(new JLabel("Téléphone:"));
        telField = new JTextField(joueur.getNumTelephone());
        panel.add(telField);

        panel.add(new JLabel("Mot de passe:"));
        passwordField = new JPasswordField(joueur.getMotDePasse());
        panel.add(passwordField);

        JButton saveButton = new JButton("Enregistrer");
        saveButton.addActionListener(e -> {
            joueur.setNom(nomField.getText());
            joueur.setEmail(emailField.getText());
            joueur.setNumTelephone(telField.getText());
            joueur.setMotDePasse(new String(passwordField.getPassword()));
            joueurDao.mettreAJourJoueur(joueur);
            JOptionPane.showMessageDialog(this, "Profil mis à jour !");
            dispose();
        });
        panel.add(new JLabel());
        panel.add(saveButton);

        add(panel);
    }
}