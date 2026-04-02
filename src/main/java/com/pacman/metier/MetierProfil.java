package com.pacman.metier;
import com.pacman.beans.*;
import com.pacman.dao.*;
import java.util.List;

import javax.servlet.http.HttpSession;

public class MetierProfil {
	private PartieDao partieDao;
	private JoueurDao joueurDao;

    public MetierProfil(PartieDao partieDao, JoueurDao joueurDao) {
        this.partieDao = partieDao;
        this.joueurDao = joueurDao;
    }

    public List<Partie> recupererHistorique(int idJoueur) {
        return partieDao.listerPartiesDuJoueur(idJoueur);
    }
    
    public Joueur majJoueur(Joueur j) {
    	try {
    		Joueur tmp = joueurDao.trouver(j.getPseudo());
    		if(tmp != null) {
    			return tmp;
    		}else throw new DAOException("Joueur non MAJ.");
    	}catch(DAOException e) {
    		return null;
    	}
    }
}
