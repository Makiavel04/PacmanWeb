package com.pacman.dao;
import com.pacman.beans.Partie;
import java.util.List;

public interface PartieDao {
    void enregistrerPartie(int idJoueur, Partie partie) throws DAOException; 
    List<Partie> listerPartiesDuJoueur(int idJoueur) throws DAOException;
}