package org.example.dao;

import org.example.model.Terrain;
import org.example.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TerrainDaoImpl implements TerrainDao {
    @Override
    public void addTerrain(Terrain terrain) {
        String query = "INSERT INTO terrain (nom, type, etat, tarif_horaire, images) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, terrain.getNom());
            stmt.setString(2, terrain.getType());
            stmt.setString(3, terrain.getEtat());
            stmt.setBigDecimal(4, terrain.getTarifHoraire());
            stmt.setString(5, terrain.getImages());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    terrain.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Terrain getTerrainById(int id) {
        String query = "SELECT * FROM terrain WHERE id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Terrain(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("type"),
                            rs.getString("etat"),
                            rs.getBigDecimal("tarif_horaire"),
                            rs.getString("images")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateTerrain(Terrain terrain) {
        String query = "UPDATE terrain SET nom = ?, type = ?, etat = ?, tarif_horaire = ?, images = ? WHERE id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, terrain.getNom());
            stmt.setString(2, terrain.getType());
            stmt.setString(3, terrain.getEtat());
            stmt.setBigDecimal(4, terrain.getTarifHoraire());
            stmt.setString(5, terrain.getImages());
            stmt.setInt(6, terrain.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteTerrain(int id) {
        String query = "DELETE FROM terrain WHERE id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Terrain> getAllTerrains() {
        List<Terrain> terrains = new ArrayList<>();
        String query = "SELECT * FROM terrain";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                terrains.add(new Terrain(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("type"),
                        rs.getString("etat"),
                        rs.getBigDecimal("tarif_horaire"),
                        rs.getString("images")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return terrains;
    }

    @Override
    public List<Terrain> getTerrainsByType(String type) {
        List<Terrain> terrains = new ArrayList<>();
        String query = "SELECT * FROM terrain WHERE type = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, type);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    terrains.add(new Terrain(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("type"),
                            rs.getString("etat"),
                            rs.getBigDecimal("tarif_horaire"),
                            rs.getString("images")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return terrains;
    }

    @Override
    public List<Terrain> getTerrainsByEtat(String etat) {
        List<Terrain> terrains = new ArrayList<>();
        String query = "SELECT * FROM terrain WHERE etat = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, etat);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    terrains.add(new Terrain(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("type"),
                            rs.getString("etat"),
                            rs.getBigDecimal("tarif_horaire"),
                            rs.getString("images")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return terrains;
    }

    // Méthode ajoutée pour compatibilité UI
    @Override
    public List<Terrain> listerTousLesTerrains() {
        return getAllTerrains();
    }
}