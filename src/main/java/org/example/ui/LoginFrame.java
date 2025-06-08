// src/main/java/org/example/ui/LoginFrame.java
package org.example.ui;

import org.example.dao.AdminDaoImpl;
import org.example.model.Admin;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private AdminDaoImpl adminDao = new AdminDaoImpl();

    public LoginFrame() {
        setTitle("Login Admin");
        setSize(350, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Email :"), gbc);
        gbc.gridx = 1;
        emailField = new JTextField(18);
        panel.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Mot de passe :"), gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(18);
        panel.add(passwordField, gbc);

        gbc.gridx = 1; gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton loginBtn = new JButton("Se connecter");
        panel.add(loginBtn, gbc);

        add(panel);

        loginBtn.addActionListener(e -> {
            String email = emailField.getText();
            String mdp = new String(passwordField.getPassword());
            Admin admin = adminDao.trouverAdminParEmailEtMotDePasse(email, mdp);            if (admin != null) {
                dispose();
                new DashboardFrame(admin).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Identifiants invalides", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}