package com.pacman.metier;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.mindrot.jbcrypt.BCrypt;

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

            if ( erreurs.isEmpty() ) {
            	if(joueurDao.trouver(pseudo) != null) this.setErreur(ATTR_PSEUDO,"Pseudo déjà utilisé");//S'il y a qqun avec ce pseudo on s'arrête
            	else{
            		traiterMotsDePasse( motDePasse, confirmation, joueur ); //Traite le mot de passe après avoir vérifié si pas déjà ce pseudo en base
            		if(erreurs.isEmpty()) {
            			joueurDao.creer( joueur );
            		}
            	}
            }
            resultat = this.erreurs.isEmpty() ? "Succès de l'inscription." : "Échec de l'inscription.";
        } catch ( DAOException e ) {
            resultat = "Échec de l'inscription : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
            this.setErreur(ATTR_DAO, "Erreur sur le serveur, veuillez réessayer dans quelques instants.");
            System.err.println("Erreur à l'inscription :" + e.getMessage());
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
	    if(erreurs.isEmpty()) { //Si déjà une erreur, on hash pas
	    	String hash = BCrypt.hashpw(motDePasse, BCrypt.gensalt());
	    	joueur.setMotDePasse(hash);
	    }//else joueur.setMotDePasse("");
	}
    
    /*
     * Ajoute un message correspondant au champ spécifié à la map des erreurs.
     */
    public void setErreur( String champ, String message ) {
        erreurs.put( champ, message );
    }

}
