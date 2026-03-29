package com.pacman.metier;

import java.util.HashMap;
import java.util.Map;

import com.pacman.beans.Partie;
import com.pacman.dao.DAOException;
import com.pacman.dao.PartieDao;

public class ReceptionScore {
    public static final String ATTR_VAINQUEUR= "vainqueur";
    public static final String ATTR_SCORE_PACMAN = "scorePacman";
    public static final String ATTR_SCORE_FANTOMES = "scoreFantomes";
    public static final String ATTR_PACMANS = "pacmans";
    public static final String ATTR_FANTOMES = "fantomes";
    public static final String ATTR_DAO = "dao";
	
	
	private PartieDao partieDao;
	private Map<String, String> erreurs;
	
	public ReceptionScore(PartieDao partieDao) {
		this.partieDao = partieDao;
		this.erreurs = new HashMap<String, String>();
	}
	
	public Map<String, String> getErreurs(){
		return this.erreurs;
	}
	
	public Integer enregistrerPartie(Partie partie) {
		this.verifierPartie(partie);
		Integer idPartie = -1;
		if(this.erreurs.isEmpty()) {
			try {
				idPartie = this.partieDao.enregistrerPartie(partie);
			} catch (DAOException e) {
				e.printStackTrace();
				System.err.println("Problème lors de la sauvegarde de la partie : " + e.getMessage());
				this.setErreur(ATTR_DAO, "Erreur lors de la sauvegarde de la partie en base.");
			}
		}
		return idPartie;
	}
	public void verifierPartie(Partie partie) {
		if(partie.getVainqueur()==null || (!partie.getVainqueur().equals("P") && !partie.getVainqueur().equals("F"))) {
			this.setErreur(ATTR_VAINQUEUR, "Erreur, vainqueur non ou mal renseigné.");
		}
		if(partie.getScoreFantomes()== null) {
			this.setErreur(ATTR_SCORE_FANTOMES, "Score fantomes non reçu.");
		}
		if(partie.getScorePacmans() == null) {
			this.setErreur(ATTR_SCORE_PACMAN, "Score pacmans non reçu.");
		}		
		if(partie.getIdFantomes() == null) {
			this.setErreur(ATTR_FANTOMES, "Les fantomes n'ont pas été envoyés.");
		}
		if (partie.getIdPacmans() == null) {
			this.setErreur(ATTR_PACMANS, "Les pacmans n'ont pas été envoyés.");
		}
	}
	
	/*
     * Ajoute un message correspondant au champ spécifié à la map des erreurs.
     */
    public void setErreur( String champ, String message ) {
        erreurs.put( champ, message );
    }
}
