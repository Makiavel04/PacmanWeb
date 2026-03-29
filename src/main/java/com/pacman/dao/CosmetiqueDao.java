package com.pacman.dao;
import com.pacman.beans.Cosmetique;
import java.util.List;

public interface CosmetiqueDao {
    void creer(Cosmetique cosmetique) throws DAOException;
    Cosmetique trouver(int id) throws DAOException;
    List<Cosmetique> listerTous() throws DAOException;
    List<Cosmetique> listerInventaireJoueur(int idJoueur) throws DAOException; 
}