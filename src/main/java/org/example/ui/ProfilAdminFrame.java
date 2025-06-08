package org.example.ui;

import org.example.dao.AdminDaoImpl;
import org.example.model.Admin;

import javax.swing.*;
import java.awt.*;

public class ProfilAdminFrame extends JFrame {
    private JTextField nomField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private Admin admin;
    private AdminDaoImpl adminDao = new AdminDaoImpl();

    public ProfilAdminFrame(Admin admin) {
        this.admin = admin;
        setTitle("Profil Admin");
        setSize(350, 220);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.add(new JLabel("Nom :"));
        nomField = new JTextField(admin.getNom(), 15);
        panel.add(nomField);

        panel.add(new JLabel("Email :"));
        emailField = new JTextField(admin.getEmail(), 15);
        panel.add(emailField);

        panel.add(new JLabel("Mot de passe :"));
        passwordField = new JPasswordField(admin.getMotDePasse(), 15);
        panel.add(passwordField);

        add(panel, BorderLayout.CENTER);

        JButton saveBtn = new JButton("Enregistrer");
        add(saveBtn, BorderLayout.NORTH);

        saveBtn.addActionListener(e -> {
            admin.setNom(nomField.getText());
            admin.setEmail(emailField.getText());
            admin.setMotDePasse(new String(passwordField.getPassword()));
            adminDao.mettreAJourAdmin(admin);
            JOptionPane.showMessageDialog(this, "Profil mis à jour !", "Succès", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        });

        // Ajout du bouton retour en bas
        JButton btnRetour = new JButton("Retour au dashboard");
        btnRetour.addActionListener(e -> dispose());
        JPanel panelRetour = new JPanel();
        panelRetour.add(btnRetour);
        add(panelRetour, BorderLayout.SOUTH);
    }
}