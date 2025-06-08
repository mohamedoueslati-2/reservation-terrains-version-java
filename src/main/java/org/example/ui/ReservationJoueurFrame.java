package org.example.ui;

import org.example.dao.ReservationDao;
import org.example.dao.ReservationDaoImpl;
import org.example.dao.TerrainDao;
import org.example.dao.TerrainDaoImpl;
import org.example.model.Joueur;
import org.example.model.Reservation;
import org.example.model.Terrain;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ReservationJoueurFrame extends JFrame {
    private JComboBox<LocalDate> dateCombo;
    private JComboBox<LocalTime> heureCombo;
    private JComboBox<Terrain> terrainCombo;
    private ReservationDao reservationDao = new ReservationDaoImpl();
    private TerrainDao terrainDao = new TerrainDaoImpl();
    private Joueur joueur;

    public ReservationJoueurFrame(Joueur joueur) {
        this.joueur = joueur;
        setTitle("Nouvelle réservation");
        setSize(400, 250);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Terrain :"));
        terrainCombo = new JComboBox<>();
        List<Terrain> terrains = terrainDao.getAllTerrains();
        for (Terrain t : terrains) {
            terrainCombo.addItem(t);
        }
        // Renderer personnalisé pour afficher nom (type)
        terrainCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Terrain) {
                    setText(((Terrain) value).toStringPersonalise());
                }
                return this;
            }
        });
        panel.add(terrainCombo);

        panel.add(new JLabel("Jour :"));
        dateCombo = new JComboBox<>();
        panel.add(dateCombo);

        panel.add(new JLabel("Heure :"));
        heureCombo = new JComboBox<>();
        panel.add(heureCombo);

        dateCombo.addActionListener(e -> chargerHeuresDisponibles());
        terrainCombo.addActionListener(e -> {
            chargerDatesDisponibles();
            chargerHeuresDisponibles();
        });

        JButton reserverBtn = new JButton("Réserver");
        reserverBtn.addActionListener(e -> {
            LocalDate date = (LocalDate) dateCombo.getSelectedItem();
            LocalTime heureDebut = (LocalTime) heureCombo.getSelectedItem();
            Terrain terrain = (Terrain) terrainCombo.getSelectedItem();
            if (date != null && heureDebut != null && terrain != null) {
                Reservation reservation = new Reservation();
                reservation.setJoueurId(joueur.getId());
                reservation.setTerrainId(terrain.getId());
                reservation.setDate(java.sql.Date.valueOf(date));
                reservation.setHeureDebut(java.sql.Time.valueOf(heureDebut));
                reservation.setHeureFin(java.sql.Time.valueOf(heureDebut.plusHours(1)));
                reservation.setPaiement("non payé");
                reservation.setMontantTotal(terrain.getTarifHoraire() != null ? terrain.getTarifHoraire() : BigDecimal.ZERO);
                reservation.setStatut("confirmé");
                reservationDao.ajouterReservation(reservation);
                JOptionPane.showMessageDialog(this, "Réservation effectuée !");
                dispose();
            }
        });
        panel.add(new JLabel());
        panel.add(reserverBtn);

        add(panel);
        chargerDatesDisponibles();
        chargerHeuresDisponibles();
    }

    private void chargerDatesDisponibles() {
        dateCombo.removeAllItems();
        Terrain terrain = (Terrain) terrainCombo.getSelectedItem();
        if (terrain == null) return;
        for (int i = 0; i < 7; i++) {
            LocalDate date = LocalDate.now().plusDays(i);
            List<LocalTime> heuresReservees = reservationDao.getHeuresReserveesParDateEtTerrain(date, terrain.getId());
            if (heuresReservees.size() < 13) { // 13 créneaux de 8h à 20h inclus
                dateCombo.addItem(date);
            }
        }
    }

    private void chargerHeuresDisponibles() {
        heureCombo.removeAllItems();
        LocalDate date = (LocalDate) dateCombo.getSelectedItem();
        Terrain terrain = (Terrain) terrainCombo.getSelectedItem();
        if (date == null || terrain == null) return;
        List<LocalTime> heuresReservees = reservationDao.getHeuresReserveesParDateEtTerrain(date, terrain.getId());
        for (int h = 8; h <= 20; h++) {
            LocalTime heure = LocalTime.of(h, 0);
            if (!heuresReservees.contains(heure)) {
                heureCombo.addItem(heure);
            }
        }
    }
}