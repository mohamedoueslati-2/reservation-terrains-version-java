package org.example.ui;

import org.example.model.Admin;
import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {
    private Admin admin;

    public DashboardFrame(Admin admin) {
        this.admin = admin;
        setTitle("Dashboard Admin");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new FlowLayout());
        JButton gestionTerrainsBtn = new JButton("Gestion des terrains");
        JButton gestionReservationsBtn = new JButton("Gestion des réservations");
        JButton profilBtn = new JButton("Profil");
        JButton gererJoueursBtn = new JButton("Gérer Joueurs");
        JButton logoutBtn = new JButton("Logout");

        gestionTerrainsBtn.addActionListener(e -> {
            new GestionTerrainsFrame().setVisible(true);
        });

        gestionReservationsBtn.addActionListener(e -> {
            new ReservationFrame().setVisible(true);
        });

        profilBtn.addActionListener(e -> {
            new ProfilAdminFrame(admin).setVisible(true);
        });

        gererJoueursBtn.addActionListener(e -> {
            new GererJoueurFrame().setVisible(true);
        });

        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });

        panel.add(gestionTerrainsBtn);
        panel.add(gestionReservationsBtn);
        panel.add(profilBtn);
        panel.add(gererJoueursBtn);
        panel.add(logoutBtn);
        add(panel);
    }
}