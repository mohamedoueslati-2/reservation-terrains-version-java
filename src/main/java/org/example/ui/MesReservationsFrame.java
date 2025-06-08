package org.example.ui;

import org.example.dao.ReservationDao;
import org.example.dao.ReservationDaoImpl;
import org.example.model.Joueur;
import org.example.model.Reservation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MesReservationsFrame extends JFrame {
    public MesReservationsFrame(Joueur joueur) {
        setTitle("Mes réservations");
        setSize(700, 300);
        setLocationRelativeTo(null);

        ReservationDao reservationDao = new ReservationDaoImpl();
        List<Reservation> reservations = reservationDao.listerToutesLesReservations();
        // Filtrer les réservations du joueur
        reservations.removeIf(r -> r.getJoueurId() != joueur.getId());

        String[] columns = {"Date", "Heure début", "Heure fin", "Terrain", "Statut", "Paiement", "Montant"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        for (Reservation r : reservations) {
            model.addRow(new Object[]{
                    r.getDate().toLocalDate(),
                    r.getHeureDebut().toLocalTime(),
                    r.getHeureFin().toLocalTime(),
                    r.getTerrainNom() != null ? r.getTerrainNom() : r.getTerrainId(),
                    r.getStatut(),
                    r.getPaiement(),
                    r.getMontantTotal()
            });
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }
}