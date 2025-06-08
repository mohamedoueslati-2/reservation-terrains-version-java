package org.example.ui;

import org.example.dao.JoueurDao;
import org.example.dao.JoueurDaoImpl;
import org.example.model.Joueur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginJoueurFrame extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JoueurDao joueurDao = new JoueurDaoImpl();

    public LoginJoueurFrame() {
        setTitle("Connexion Joueur");
        setSize(350, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Email :"));
        emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("Mot de passe :"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        JButton loginButton = new JButton("Se connecter");
        loginButton.addActionListener(this::loginAction);
        panel.add(new JLabel()); // Espace vide
        panel.add(loginButton);

        JButton creerCompteButton = new JButton("Créer un compte");
        creerCompteButton.addActionListener(e -> {
            new CreerCompteJoueurFrame().setVisible(true);
            this.dispose();
        });
        panel.add(new JLabel()); // Espace vide
        panel.add(creerCompteButton);

        add(panel, BorderLayout.CENTER);
    }

    private void loginAction(ActionEvent e) {
        String email = emailField.getText();
        String motDePasse = new String(passwordField.getPassword());
        Joueur joueur = joueurDao.trouverJoueurParEmailEtMotDePasse(email, motDePasse);
        if (joueur != null) {
            if ("bloqué".equalsIgnoreCase(joueur.getStatut())) {
                JOptionPane.showMessageDialog(this, "Votre compte est bloqué. Veuillez contacter l'administrateur.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(this, "Connexion réussie ! Bienvenue " + joueur.getNom());
            new DashboardJoueurFrame(joueur).setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Email ou mot de passe incorrect.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}