package com.pacman.metier;
import com.pacman.beans.*;
import com.pacman.dao.*;
import java.util.List;

public class MetierProfil {
	private PartieDao partieDao;

    public MetierProfil(PartieDao partieDao) {
        this.partieDao = partieDao;
    }

    public List<Partie> recupererHistorique(int idJoueur) {
        return partieDao.listerPartiesDuJoueur(idJoueur);
    }
}
