package com.pacman.metier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.pacman.beans.Cosmetique;
import com.pacman.beans.Joueur;
import com.pacman.dao.CosmetiqueDao;
import com.pacman.dao.JoueurDao;
import com.pacman.dao.DAOException;

public class MetierCosmetique {
	private static final String ERR_INVENTAIRE= "inventaire";
	private static final String ERR_BOUTIQUE= "boutique";
	private static final String ERR_ACTION= "action";
	
    private JoueurDao joueurDao;
    private CosmetiqueDao cosmetiqueDao;
    private Map<String, String> erreurs;

    public MetierCosmetique(JoueurDao joueurDao, CosmetiqueDao cosmetiqueDao) {
        this.joueurDao = joueurDao;
        this.cosmetiqueDao = cosmetiqueDao;
        this.erreurs = new HashMap<String, String>();
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }

    public List<Cosmetique> recupererInventaire(Joueur joueur) {
        try {
            return cosmetiqueDao.listerInventaireJoueur(joueur.getId());
        } catch (DAOException e) {
            this.setErreur(ERR_INVENTAIRE, "Impossible de charger l'inventaire.");
        }
        return null;
    }

    public List<Cosmetique> recupererBoutique() {
        try {
            return cosmetiqueDao.listerTous();
        } catch (DAOException e) {
            this.setErreur(ERR_BOUTIQUE, "Impossible de charger la boutique.");
        }
        return null;
    }
    
    //public List<Cosmetique>

    public void traiterAction(String action, Integer idCosmetique, Joueur joueurConnecte) {

        if (action == null) this.setErreur(ERR_ACTION, "Action inexistante : " +action);
        	
        if(idCosmetique == null) {
        	this.setErreur(ERR_BOUTIQUE, "Aucune cosmétique donnée.");
        }else {
        	try {
        		Cosmetique cosmetique = this.cosmetiqueDao.trouver(idCosmetique);
        		if(cosmetique == null) this.setErreur(ERR_BOUTIQUE, "Cosmetique inexistant : "+idCosmetique);
        		else idCosmetique = cosmetique.getId();
        	}catch(DAOException e){
        		this.setErreur(ERR_BOUTIQUE, "Erreur lors de la récupération du cosmétique.");
        	}
        }
        
        Integer idJoueur = joueurConnecte.getId();
        if(idJoueur == null) this.setErreur(ERR_INVENTAIRE, "Joueur inexistant.");
        
        if(this.erreurs.isEmpty()) {
	        try {
	            if ("acheter".equals(action)) {
	                joueurDao.ajouterCosmetique(idJoueur, idCosmetique);
	                
	            } else if ("equiper".equals(action)) {
	                joueurDao.equiperCosmetique(idJoueur, idCosmetique);
	                joueurConnecte.setIdCosmetiqueActif(idCosmetique);
	                
	            } else {
	                this.setErreur(ERR_ACTION, "Action non reconnue.");
	            }
	        }  catch (DAOException e) {
	            this.setErreur(ERR_BOUTIQUE,"Action impossible : Tu possèdes déjà cet objet !");
	        }
    	}
    }
    
    /*
     * Ajoute un message correspondant au champ spécifié à la map des erreurs.
     */
    public void setErreur( String champ, String message ) {
        erreurs.put( champ, message );
    }
}