package org.example.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import org.example.model.Reservation;
import java.util.List;

public interface ReservationDao {
    void ajouterReservation(Reservation reservation);
    void supprimerReservation(int id);
    void mettreAJourReservation(Reservation reservation);
    Reservation trouverReservationParId(int id);
    List<Reservation> listerToutesLesReservations();
    void annulerReservationsEnCoursPourJoueur(int joueurId);
    List<LocalTime> getHeuresReserveesParDateEtTerrain(LocalDate date, int terrainId);
}