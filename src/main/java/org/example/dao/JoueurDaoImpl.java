package org.example.dao;

import org.example.model.Joueur;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JoueurDaoImpl implements JoueurDao {
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc", "root", "");
    }

    @Override
    public Joueur trouverJoueurParEmailEtMotDePasse(String email, String motDePasse) {
        String sql = "SELECT * FROM joueur WHERE email = ? AND mot_de_passe = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, motDePasse);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapJoueur(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void ajouterJoueur(Joueur joueur) {
        String sql = "INSERT INTO joueur (nom, email, mot_de_passe, num_telephone, statut) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, joueur.getNom());
            stmt.setString(2, joueur.getEmail());
            stmt.setString(3, joueur.getMotDePasse());
            stmt.setString(4, joueur.getNumTelephone());
            stmt.setString(5, joueur.getStatut());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean creerJoueur(Joueur joueur) {
        String sql = "INSERT INTO joueur (nom, email, mot_de_passe, num_telephone) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, joueur.getNom());
            stmt.setString(2, joueur.getEmail());
            stmt.setString(3, joueur.getMotDePasse());
            stmt.setString(4, joueur.getNumTelephone());
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void mettreAJourJoueur(Joueur joueur) {
        String sql = "UPDATE joueur SET nom=?, email=?, mot_de_passe=?, num_telephone=?, statut=? WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, joueur.getNom());
            stmt.setString(2, joueur.getEmail());
            stmt.setString(3, joueur.getMotDePasse());
            stmt.setString(4, joueur.getNumTelephone());
            stmt.setString(5, joueur.getStatut());
            stmt.setInt(6, joueur.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void supprimerJoueur(int id) {
        String sql = "DELETE FROM joueur WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Joueur trouverJoueurParId(int id) {
        String sql = "SELECT * FROM joueur WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapJoueur(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Joueur> listerTousLesJoueurs() {
        List<Joueur> joueurs = new ArrayList<>();
        String sql = "SELECT * FROM joueur";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                joueurs.add(mapJoueur(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return joueurs;
    }

    private Joueur mapJoueur(ResultSet rs) throws SQLException {
        Joueur joueur = new Joueur();
        joueur.setId(rs.getInt("id"));
        joueur.setNom(rs.getString("nom"));
        joueur.setEmail(rs.getString("email"));
        joueur.setMotDePasse(rs.getString("mot_de_passe"));
        joueur.setNumTelephone(rs.getString("num_telephone"));
        joueur.setStatut(rs.getString("statut"));
        return joueur;
    }

    @Override
    public boolean estJoueurBloque(int joueurId) {
        String sql = "SELECT statut FROM joueur WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, joueurId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return "bloqué".equalsIgnoreCase(rs.getString("statut"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}