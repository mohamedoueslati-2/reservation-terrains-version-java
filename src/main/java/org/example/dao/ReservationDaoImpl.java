package org.example.dao;
import java.time.LocalTime;
import java.time.LocalDate;
import org.example.model.Reservation;
import org.example.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ReservationDaoImpl implements ReservationDao {

    @Override
    public void ajouterReservation(Reservation r) {
        // Vérification de la date
        if (r.getDate().toLocalDate().isBefore(LocalDate.now())) {
            JOptionPane.showMessageDialog(null, "Impossible de réserver à une date passée.", "Alerte", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean dejaReserve = false;
        String checkSql = "SELECT COUNT(*) FROM reservation WHERE terrain_id = ? AND date = ? AND statut = 'confirmé' " +
                "AND heure_debut < ? AND heure_fin > ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
            checkStmt.setInt(1, r.getTerrainId());
            checkStmt.setDate(2, r.getDate());
            checkStmt.setTime(3, r.getHeureFin());
            checkStmt.setTime(4, r.getHeureDebut());
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                dejaReserve = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            dejaReserve = true;
        }

        if (dejaReserve) {
            JOptionPane.showMessageDialog(null, "Ce créneau est déjà réservé pour ce terrain.", "Alerte", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String sql = "INSERT INTO reservation (joueur_id, terrain_id, date, heure_debut, heure_fin, paiement, montant_total, statut) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, r.getJoueurId());
            ps.setInt(2, r.getTerrainId());
            ps.setDate(3, r.getDate());
            ps.setTime(4, r.getHeureDebut());
            ps.setTime(5, r.getHeureFin());
            ps.setString(6, r.getPaiement());
            ps.setBigDecimal(7, r.getMontantTotal());
            ps.setString(8, r.getStatut());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Réservation ajoutée avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors de l'ajout de la réservation.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void supprimerReservation(int id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mettreAJourReservation(Reservation r) {
        String sql = "UPDATE reservation SET joueur_id=?, terrain_id=?, date=?, heure_debut=?, heure_fin=?, paiement=?, montant_total=?, statut=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, r.getJoueurId());
            ps.setInt(2, r.getTerrainId());
            ps.setDate(3, r.getDate());
            ps.setTime(4, r.getHeureDebut());
            ps.setTime(5, r.getHeureFin());
            ps.setString(6, r.getPaiement());
            ps.setBigDecimal(7, r.getMontantTotal());
            ps.setString(8, r.getStatut());
            ps.setInt(9, r.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Reservation trouverReservationParId(int id) {
        String sql = "SELECT * FROM reservation WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSetToReservation(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Reservation> listerToutesLesReservations() {
        List<Reservation> list = new ArrayList<>();
        String sql = "SELECT r.*, j.nom AS joueur_nom, t.nom AS terrain_nom " +
                "FROM reservation r " +
                "JOIN joueur j ON r.joueur_id = j.id " +
                "JOIN terrain t ON r.terrain_id = t.id";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Reservation r = mapResultSetToReservation(rs);
                r.setJoueurNom(rs.getString("joueur_nom"));
                r.setTerrainNom(rs.getString("terrain_nom"));
                list.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private Reservation mapResultSetToReservation(ResultSet rs) throws SQLException {
        return new Reservation(
                rs.getInt("id"),
                rs.getInt("joueur_id"),
                rs.getInt("terrain_id"),
                rs.getDate("date"),
                rs.getTime("heure_debut"),
                rs.getTime("heure_fin"),
                rs.getString("paiement"),
                rs.getBigDecimal("montant_total"),
                rs.getString("statut")
        );
    }

    @Override
    public void annulerReservationsEnCoursPourJoueur(int joueurId) {
        String sql = "UPDATE reservation SET statut = 'annulé' WHERE joueur_id = ? AND statut = 'confirmé'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, joueurId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<LocalTime> getHeuresReserveesParDateEtTerrain(LocalDate date, int terrainId) {
        List<LocalTime> heures = new ArrayList<>();
        String sql = "SELECT heure_debut FROM reservation WHERE date = ? AND terrain_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, java.sql.Date.valueOf(date));
            stmt.setInt(2, terrainId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                heures.add(rs.getTime("heure_debut").toLocalTime());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return heures;
    }
}