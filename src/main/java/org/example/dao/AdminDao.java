package org.example.dao;

import org.example.model.Admin;
import java.util.List;

public interface AdminDao {
    void ajouterAdmin(Admin admin);
    Admin trouverAdminParId(int id);
    List<Admin> listerAdmins();
    void mettreAJourAdmin(Admin admin);
    void supprimerAdmin(int id);
    Admin trouverAdminParEmailEtMotDePasse(String email, String motDePasse);
}