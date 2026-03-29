package com.pacman.dao;

import java.util.List;
import com.pacman.beans.Joueur;

public interface JoueurDao {
    void creer(Joueur joueur) throws DAOException;
    Joueur trouver(String pseudo) throws DAOException;
    List<Joueur> listerMeilleursScores() throws DAOException;
    
    
    void ajouterCosmetique(int idJoueur, int idCosmetique) throws DAOException;
    void equiperCosmetique(int idJoueur, int idCosmetique) throws DAOException;
    
    //void updateScore(int idJoueur, int score, String role) throws DAOException;
}