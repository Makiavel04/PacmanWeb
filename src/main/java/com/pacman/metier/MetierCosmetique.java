package com.pacman.metier;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.pacman.beans.Cosmetique;
import com.pacman.beans.Joueur;
import com.pacman.dao.CosmetiqueDao;
import com.pacman.dao.JoueurDao;
import com.pacman.dao.DAOException;

public class MetierCosmetique {
    private JoueurDao joueurDao;
    private CosmetiqueDao cosmetiqueDao;
    private String erreur;

    public MetierCosmetique(JoueurDao joueurDao, CosmetiqueDao cosmetiqueDao) {
        this.joueurDao = joueurDao;
        this.cosmetiqueDao = cosmetiqueDao;
    }

    public String getErreur() {
        return erreur;
    }

    public List<Cosmetique> recupererInventaire(Joueur joueur) {
        try {
            return cosmetiqueDao.listerInventaireJoueur(joueur.getId());
        } catch (DAOException e) {
            this.erreur = "Impossible inventaire.";
            return null;
        }
    }

    public List<Cosmetique> recupererBoutique() {
        try {
            return cosmetiqueDao.listerTous();
        } catch (DAOException e) {
            this.erreur = "Impossible de charger la boutique globale.";
            return null;
        }
    }

    public void traiterAction(HttpServletRequest request, Joueur joueurConnecte) {
        String action = request.getParameter("action");
        String idCosmetiqueStr = request.getParameter("idCosmetique");

        if (action == null || idCosmetiqueStr == null) {
            this.erreur = "Action pas cool.";
            return;
        }

        try {
            int idCosmetique = Integer.parseInt(idCosmetiqueStr);
            int idJoueur = joueurConnecte.getId();

            if ("acheter".equals(action)) {
                joueurDao.ajouterCosmetique(idJoueur, idCosmetique);
                
            } else if ("equiper".equals(action)) {
                joueurDao.equiperCosmetique(idJoueur, idCosmetique);
                joueurConnecte.setIdCosmetiqueActif(idCosmetique);
                
            } else {
                this.erreur = "Action non reconnue.";
            }
        } catch (NumberFormatException e) {
            this.erreur = "L'id du cosmétique est invalide.";
        } catch (DAOException e) {
            this.erreur = "Action impossible : Tu possèdes déjà cet objet (neuil) !";
        }
    }
}