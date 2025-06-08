package org.example.ui;

import org.example.dao.ReservationDao;
import org.example.dao.ReservationDaoImpl;
import org.example.model.Reservation;
import org.example.dao.JoueurDao;
import org.example.dao.JoueurDaoImpl;
import org.example.model.Joueur;
import org.example.dao.TerrainDao;
import org.example.dao.TerrainDaoImpl;
import org.example.model.Terrain;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class ReservationFrame extends JFrame {
    private ReservationDao reservationDao = new ReservationDaoImpl();
    private JoueurDao joueurDao = new JoueurDaoImpl();
    private TerrainDao terrainDao = new TerrainDaoImpl();
    private JTable table;
    private DefaultTableModel tableModel;

    public ReservationFrame() {
        setTitle("Gestion des réservations");
        setSize(1100, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        tableModel = new DefaultTableModel(new Object[]{
                "ID", "Joueur ID", "Nom Joueur", "Terrain ID", "Nom Terrain", "Date", "Heure début", "Heure fin", "Paiement", "Montant", "Statut"
        }, 0);
        table = new JTable(tableModel);
        rafraichirTable();

        JScrollPane scrollPane = new JScrollPane(table);

        JButton btnAjouter = new JButton("Ajouter");
        JButton btnSupprimer = new JButton("Supprimer");
        JButton btnModifier = new JButton("Modifier");
        JButton btnRafraichir = new JButton("Rafraîchir");

        btnAjouter.addActionListener(e -> ajouterReservation());
        btnSupprimer.addActionListener(e -> supprimerReservation());
        btnModifier.addActionListener(e -> modifierReservation());
        btnRafraichir.addActionListener(e -> rafraichirTable());

        JPanel panelBoutons = new JPanel();
        panelBoutons.add(btnAjouter);
        panelBoutons.add(btnModifier);
        panelBoutons.add(btnSupprimer);
        panelBoutons.add(btnRafraichir);

        add(scrollPane, BorderLayout.CENTER);
        add(panelBoutons, BorderLayout.NORTH);

        JButton btnRetour = new JButton("Retour au dashboard");
        btnRetour.addActionListener(e -> dispose());
        JPanel panelRetour = new JPanel();
        panelRetour.add(btnRetour);
        add(panelRetour, BorderLayout.SOUTH);
    }

    private void rafraichirTable() {
        tableModel.setRowCount(0);
        List<Reservation> reservations = reservationDao.listerToutesLesReservations();
        for (Reservation r : reservations) {
            tableModel.addRow(new Object[]{
                    r.getId(),
                    r.getJoueurId(),
                    r.getJoueurNom(),
                    r.getTerrainId(),
                    r.getTerrainNom(),
                    r.getDate(),
                    r.getHeureDebut(),
                    r.getHeureFin(),
                    r.getPaiement(),
                    r.getMontantTotal(),
                    r.getStatut()
            });
        }
    }

    private void ajouterReservation() {
        List<Joueur> joueurs = joueurDao.listerTousLesJoueurs();
        JComboBox<Joueur> comboJoueur = new JComboBox<>(joueurs.toArray(new Joueur[0]));
        comboJoueur.setRenderer((list, value, index, isSelected, cellHasFocus) ->
                new JLabel(value == null ? "" : value.getNom() + " (" + value.getEmail() + ")")
        );

        List<Terrain> terrains = terrainDao.listerTousLesTerrains();
        JComboBox<Terrain> comboTerrain = new JComboBox<>(terrains.toArray(new Terrain[0]));
        comboTerrain.setRenderer((list, value, index, isSelected, cellHasFocus) ->
                new JLabel(value == null ? "" : value.getNom() + " (" + value.getType() + ")")
        );

        JTextField tarif = new JTextField();
        // Champ éditable
        if (!terrains.isEmpty()) {
            Terrain t = terrains.get(0);
            tarif.setText(t.getTarifHoraire() != null ? t.getTarifHoraire().toString() : "");
        }
        comboTerrain.addActionListener(e -> {
            Terrain t = (Terrain) comboTerrain.getSelectedItem();
            if (t != null) {
                tarif.setText(t.getTarifHoraire() != null ? t.getTarifHoraire().toString() : "");
            }
        });

        JTextField date = new JTextField("2024-01-01");
        JTextField heureDebut = new JTextField("10:00:00");
        JTextField heureFin = new JTextField("11:00:00");
        JComboBox<String> paiement = new JComboBox<>(new String[]{"payé", "non payé"});
        JComboBox<String> statut = new JComboBox<>(new String[]{"confirmé", "annulé"});

        Object[] message = {
                "Joueur :", comboJoueur,
                "Terrain :", comboTerrain,
                "Tarif horaire :", tarif,
                "Date (YYYY-MM-DD):", date,
                "Heure début (HH:MM:SS):", heureDebut,
                "Heure fin (HH:MM:SS):", heureFin,
                "Paiement:", paiement,
                "Statut:", statut
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Ajouter une réservation", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                Joueur joueurSelectionne = (Joueur) comboJoueur.getSelectedItem();
                Terrain terrainSelectionne = (Terrain) comboTerrain.getSelectedItem();
                int idJoueur = joueurSelectionne.getId();
                int idTerrain = terrainSelectionne.getId();
                if (joueurDao.estJoueurBloque(idJoueur)) {
                    JOptionPane.showMessageDialog(this, "Ce joueur est bloqué et ne peut pas réserver.");
                    return;
                }
                BigDecimal montant = new BigDecimal(tarif.getText());
                Reservation r = new Reservation(
                        0,
                        idJoueur,
                        idTerrain,
                        Date.valueOf(date.getText()),
                        Time.valueOf(heureDebut.getText()),
                        Time.valueOf(heureFin.getText()),
                        paiement.getSelectedItem().toString(),
                        montant,
                        statut.getSelectedItem().toString()
                );
                reservationDao.ajouterReservation(r);
                rafraichirTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur de saisie : " + ex.getMessage());
            }
        }
    }

    private void supprimerReservation() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Sélectionnez une réservation à supprimer.");
            return;
        }
        int id = (int) tableModel.getValueAt(row, 0);
        reservationDao.supprimerReservation(id);
        rafraichirTable();
    }

    private void modifierReservation() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Sélectionnez une réservation à modifier.");
            return;
        }
        int id = (int) tableModel.getValueAt(row, 0);
        Reservation r = reservationDao.trouverReservationParId(id);

        List<Joueur> joueurs = joueurDao.listerTousLesJoueurs();
        JComboBox<Joueur> comboJoueur = new JComboBox<>(joueurs.toArray(new Joueur[0]));
        comboJoueur.setRenderer((list, value, index, isSelected, cellHasFocus) ->
                new JLabel(value == null ? "" : value.getNom() + " (" + value.getEmail() + ")")
        );
        for (int i = 0; i < joueurs.size(); i++) {
            if (joueurs.get(i).getId() == r.getJoueurId()) {
                comboJoueur.setSelectedIndex(i);
                break;
            }
        }

        List<Terrain> terrains = terrainDao.listerTousLesTerrains();
        JComboBox<Terrain> comboTerrain = new JComboBox<>(terrains.toArray(new Terrain[0]));
        comboTerrain.setRenderer((list, value, index, isSelected, cellHasFocus) ->
                new JLabel(value == null ? "" : value.getNom() + " (" + value.getType() + ")")
        );
        for (int i = 0; i < terrains.size(); i++) {
            if (terrains.get(i).getId() == r.getTerrainId()) {
                comboTerrain.setSelectedIndex(i);
                break;
            }
        }

        JTextField tarif = new JTextField();
        // Champ éditable
        Terrain terrainCourant = (Terrain) comboTerrain.getSelectedItem();
        if (terrainCourant != null) {
            tarif.setText(terrainCourant.getTarifHoraire() != null ? terrainCourant.getTarifHoraire().toString() : "");
        }
        comboTerrain.addActionListener(e -> {
            Terrain t = (Terrain) comboTerrain.getSelectedItem();
            if (t != null) {
                tarif.setText(t.getTarifHoraire() != null ? t.getTarifHoraire().toString() : "");
            }
        });

        JTextField date = new JTextField(r.getDate().toString());
        JTextField heureDebut = new JTextField(r.getHeureDebut().toString());
        JTextField heureFin = new JTextField(r.getHeureFin().toString());
        JComboBox<String> paiement = new JComboBox<>(new String[]{"payé", "non payé"});
        paiement.setSelectedItem(r.getPaiement());
        JComboBox<String> statut = new JComboBox<>(new String[]{"confirmé", "annulé"});
        statut.setSelectedItem(r.getStatut());

        Object[] message = {
                "Joueur :", comboJoueur,
                "Terrain :", comboTerrain,
                "Tarif horaire :", tarif,
                "Date (YYYY-MM-DD):", date,
                "Heure début (HH:MM:SS):", heureDebut,
                "Heure fin (HH:MM:SS):", heureFin,
                "Paiement:", paiement,
                "Statut:", statut
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Modifier la réservation", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                Joueur joueurSelectionne = (Joueur) comboJoueur.getSelectedItem();
                Terrain terrainSelectionne = (Terrain) comboTerrain.getSelectedItem();
                r.setJoueurId(joueurSelectionne.getId());
                r.setTerrainId(terrainSelectionne.getId());
                r.setDate(Date.valueOf(date.getText()));
                r.setHeureDebut(Time.valueOf(heureDebut.getText()));
                r.setHeureFin(Time.valueOf(heureFin.getText()));
                r.setPaiement(paiement.getSelectedItem().toString());
                r.setMontantTotal(new BigDecimal(tarif.getText()));
                r.setStatut(statut.getSelectedItem().toString());
                reservationDao.mettreAJourReservation(r);
                rafraichirTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur de saisie : " + ex.getMessage());
            }
        }
    }
}