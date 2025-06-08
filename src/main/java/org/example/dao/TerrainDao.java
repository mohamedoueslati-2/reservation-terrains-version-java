package org.example.dao;
import org.example.model.Terrain;
import java.util.List;

public interface TerrainDao {
    void addTerrain(Terrain terrain);
    Terrain getTerrainById(int id);
    void updateTerrain(Terrain terrain);
    void deleteTerrain(int id);
    List<Terrain> getAllTerrains();
    List<Terrain> getTerrainsByType(String type);
    List<Terrain> getTerrainsByEtat(String etat);

    // Ajout pour compatibilité UI
    List<Terrain> listerTousLesTerrains();
}