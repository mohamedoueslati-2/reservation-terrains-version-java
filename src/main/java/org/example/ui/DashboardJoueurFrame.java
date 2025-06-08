// src/main/java/org/example/ui/DashboardJoueurFrame.java
package org.example.ui;

import org.example.model.Joueur;

import javax.swing.*;
import java.awt.*;

public class DashboardJoueurFrame extends JFrame {
    public DashboardJoueurFrame(Joueur joueur) {
        setTitle("Tableau de bord Joueur");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Bienvenue, " + joueur.getNom() + " !");
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel centerPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        centerPanel.add(label);

        JButton editProfileButton = new JButton("Éditer mon profil");
        editProfileButton.addActionListener(e -> {
            new EditerProfilJoueurFrame(joueur).setVisible(true);
        });
        centerPanel.add(editProfileButton);

        JButton reservationButton = new JButton("Nouvelle réservation");
        reservationButton.addActionListener(e -> {
            new ReservationJoueurFrame(joueur).setVisible(true);
        });
        centerPanel.add(reservationButton);

        JButton consulterReservationsButton = new JButton("Consulter mes réservations");
        consulterReservationsButton.addActionListener(e -> {
            new MesReservationsFrame(joueur).setVisible(true);
        });
        centerPanel.add(consulterReservationsButton);

        add(centerPanel, BorderLayout.CENTER);
    }
}