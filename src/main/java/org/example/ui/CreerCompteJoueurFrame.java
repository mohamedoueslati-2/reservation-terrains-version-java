package org.example.ui;

import org.example.dao.JoueurDao;
import org.example.dao.JoueurDaoImpl;
import org.example.model.Joueur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CreerCompteJoueurFrame extends JFrame {
    private JTextField nomField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JoueurDao joueurDao = new JoueurDaoImpl();

    public CreerCompteJoueurFrame() {
        setTitle("Créer un compte Joueur");
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Nom :"));
        nomField = new JTextField();
        panel.add(nomField);

        panel.add(new JLabel("Email :"));
        emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("Mot de passe :"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        JButton ajouterButton = new JButton("Ajouter Joueur");
        ajouterButton.addActionListener(this::creerCompteAction);
        panel.add(new JLabel());
        panel.add(ajouterButton);

        add(panel, BorderLayout.CENTER);
    }

    private void creerCompteAction(ActionEvent e) {
        String nom = nomField.getText();
        String email = emailField.getText();
        String motDePasse = new String(passwordField.getPassword());

        if (nom.isEmpty() || email.isEmpty() || motDePasse.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tous les champs sont obligatoires.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Joueur joueur = new Joueur();
        joueur.setNom(nom);
        joueur.setEmail(email);
        joueur.setMotDePasse(motDePasse);

        if (joueurDao.creerJoueur(joueur)) {
            JOptionPane.showMessageDialog(this, "Compte créé avec succès !");
            new LoginJoueurFrame().setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de la création du compte.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}