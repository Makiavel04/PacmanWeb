package com.pacman.dao;

import java.util.List;
import com.pacman.beans.Joueur;

public interface JoueurDao {
    void creer(Joueur joueur) throws DAOException;
    Joueur trouver(String pseudo) throws DAOException;
    List<Joueur> getMeilleursScores() throws DAOException;
}