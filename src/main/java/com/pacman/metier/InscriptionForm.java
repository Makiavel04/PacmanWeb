package com.pacman.metier;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import com.pacman.beans.Joueur;
import com.pacman.dao.DAOException;
import com.pacman.dao.JoueurDao;


public class InscriptionForm {
    private static final String ATTR_PSEUDO    = "pseudo";
	private static final String ATTR_MDP   = "motdepasse";
    private static final String ATTR_CONF   = "confirmation";
    private static final String ATTR_DAO= "dao";
    
    private String resultat;
    private Map<String, String> erreurs = new HashMap<String, String>();
    private JoueurDao joueurDao;

    public InscriptionForm( JoueurDao joueurDao ) {
        this.joueurDao = joueurDao;
    }
    
    public String getResultat() {
        return resultat;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }
    
    public Joueur inscrireUtilisateur( HttpServletRequest request ) {
        String pseudo = MetierUtilitaire.getValeurChamp( request, ATTR_PSEUDO );
        String motDePasse = MetierUtilitaire.getValeurChamp( request, ATTR_MDP );
        String confirmation = MetierUtilitaire.getValeurChamp( request, ATTR_CONF );

        Joueur joueur = new Joueur();
        try {
            traiterPseudo( pseudo, joueur);
            traiterMotsDePasse( motDePasse, confirmation, joueur );

            if ( erreurs.isEmpty() ) {
            	if(joueurDao.trouver(pseudo) != null) this.setErreur(ATTR_PSEUDO,"Pseudo déjà utilisé");//S'il y a qqun avec ce pseudo on s'arrête
            	else{
            		joueurDao.creer( joueur );
            	}
            }
            resultat = this.erreurs.isEmpty() ? "Succès de l'inscription." : "Échec de l'inscription.";
        } catch ( DAOException e ) {
            resultat = "Échec de l'inscription : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
            this.setErreur(ATTR_DAO, "Erreur sur le serveur, veuillez réessayer dans quelques instants.");
        } 


        return joueur;
    }
    
    private void validationPseudo( String pseudo ) {
        if ( pseudo != null && pseudo.isEmpty() ) {
        	this.setErreur(ATTR_PSEUDO, "Le nom d'utilisateur ne doit pas être vide." );
        }
    }

    private void validationMotsDePasse( String motDePasse, String confirmation ){
        if ( motDePasse != null && confirmation != null ) {
            if ( !motDePasse.equals( confirmation ) ) {
            	this.setErreur(ATTR_MDP, "Les mots de passe entrés sont différents, merci de les saisir à nouveau." );
            } else if ( motDePasse.length() < 3 ) {
            	this.setErreur(ATTR_MDP, "Les mots de passe doivent contenir au moins 3 caractères." );
            }
        } else {
        	this.setErreur(ATTR_MDP, "Merci de saisir et confirmer votre mot de passe." );
        }
    }
    
    private void traiterPseudo(String pseudo, Joueur joueur) {
	    validationPseudo(pseudo);
    	joueur.setPseudo(pseudo);
    }

    private void traiterMotsDePasse( String motDePasse, String confirmation, Joueur joueur ) {
	    validationMotsDePasse( motDePasse, confirmation );
	    	
	    /*
	     * Utilisation de la bibliothèque Jasypt pour chiffrer le mot de passe
	     * efficacement.
	     * 
	     * L'algorithme SHA-256 est ici utilisé, avec par défaut un salage
	     * aléatoire et un grand nombre d'itérations de la fonction de hashage.
	     * 
	     * La String retournée est de longueur 56 et contient le hash en Base64.
	     */
//	    ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
//	    passwordEncryptor.setAlgorithm( ALGO_CHIFFREMENT );
//	    passwordEncryptor.setPlainDigest( false );
//	    String motDePasseChiffre = passwordEncryptor.encryptPassword( motDePasse );
	
	    joueur.setMotDePasse( motDePasse );
	}
    
    /*
     * Ajoute un message correspondant au champ spécifié à la map des erreurs.
     */
    public void setErreur( String champ, String message ) {
        erreurs.put( champ, message );
    }

}
