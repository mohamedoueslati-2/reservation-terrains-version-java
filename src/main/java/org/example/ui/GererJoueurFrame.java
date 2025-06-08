package org.example.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GererJoueurFrame extends JFrame {
    private JTextField emailField;
    private JTextField nomField;
    private JTextField telField;
    private JPasswordField passwordField;
    private JButton creerButton;

    public GererJoueurFrame() {
        setTitle("Créer un joueur");
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Email:"));
        emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("Nom:"));
        nomField = new JTextField();
        panel.add(nomField);

        panel.add(new JLabel("Téléphone:"));
        telField = new JTextField();
        panel.add(telField);

        panel.add(new JLabel("Mot de passe:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        creerButton = new JButton("Créer");
        creerButton.addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(GererJoueurFrame.this, "Joueur créé avec succès !");
            dispose();
        });
        panel.add(Box.createVerticalStrut(10));
        panel.add(creerButton);

        add(panel);
    }
}