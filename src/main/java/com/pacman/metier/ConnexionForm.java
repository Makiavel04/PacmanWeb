package com.pacman.metier;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.mindrot.jbcrypt.BCrypt;

import com.pacman.beans.Cosmetique;
import com.pacman.beans.Joueur;
import com.pacman.dao.CosmetiqueDao;
import com.pacman.dao.DAOException;
import com.pacman.dao.JoueurDao;


public class ConnexionForm {
    public static final String ATTR_PSEUDO = "pseudo";
    public static final String ATTR_MDP = "motdepasse";
    public static final String ATTR_DAO = "dao";
    
    private String resultat;
    private Map<String, String> erreurs = new HashMap<String, String>();
    private JoueurDao joueurDao;
    private CosmetiqueDao cosmetiqueDao;

    public ConnexionForm( JoueurDao joueurDao ) {
        this.joueurDao = joueurDao;
    }

    public ConnexionForm( JoueurDao joueurDao, CosmetiqueDao cosmetiqueDao ) {
        this.joueurDao = joueurDao;
        this.cosmetiqueDao = cosmetiqueDao;
    }
    
    public String getResultat() {
        return resultat;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }
    
    public Joueur connecterJoueurWeb(HttpServletRequest request) {
    	/* Récupération des champs du formulaire */
    	String pseudo = MetierUtilitaire.getValeurChamp(request, ATTR_PSEUDO);
    	String motDePasse = MetierUtilitaire.getValeurChamp(request, ATTR_MDP);
    	return this.connecterJoueur(pseudo, motDePasse);
    }
    
    public Joueur connecterJoueurAPI(String pseudo, String motDePasse) {
    	Joueur j = this.connecterJoueur(pseudo, motDePasse);
    	//trouver cosmetique et la mettre dans j
    	if(this.cosmetiqueDao != null) {
    		try {
    			Cosmetique c = this.cosmetiqueDao.trouver(j.getIdCosmetiqueActif());
    			if(c == null) this.setErreur(ATTR_DAO, "Cosmétique introuvable.");
    			else {
    				j.setCosmetiqueActif(c);
    			}
    		}catch(DAOException e){
    			this.setErreur(ATTR_DAO, "Problème dans la base, réessayer plus tard.");
    		}
    		
    	}else {
    		j.setCosmetiqueActif(null);
    	}
    	return j;
    }
    
    private Joueur connecterJoueur(String pseudo, String motDePasse) {
    	
    	Joueur joueur = new Joueur();
    	
        try {
        	this.traiterPseudo(pseudo, joueur);
        	
        	if(erreurs.isEmpty()) {
        		Joueur jDao = joueurDao.trouver(joueur.getPseudo());
        		if(jDao == null) this.setErreur(ATTR_PSEUDO, "Joueur non existant");
        		else {
        			this.traiterMotDePasse(motDePasse);//On garde le mot de passe en clair à l'écart du joueur pour éviter qu'un passage du bean n'envoi le mdp en clair n'importe où
        			if(erreurs.isEmpty()) {
        				this.validationCorrespondance(joueur, motDePasse, jDao);
	            		if(erreurs.isEmpty()) {
	            			joueur = jDao;
	            		}
        			}
        			
        		}
        	}
        	resultat = this.erreurs.isEmpty() ? "Succès de la connexion." : "Échec de la connexion.";
        }catch(DAOException e) {
            resultat = "Échec de la connexion : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
        	this.setErreur(ATTR_DAO, "Erreur sur le serveur, veuillez réessayer dans quelques instants.");
        }
        return joueur;

    }
    
    
    private void traiterPseudo(String pseudo, Joueur joueur) {
    	this.validationPseudo(pseudo);
    	joueur.setPseudo(pseudo);
    }
    
    /**
     * Valide le pseudo saisi.
     */
    private void validationPseudo( String pseudo ){
        if ( pseudo == null || pseudo.isEmpty()) {
        	this.setErreur(ATTR_PSEUDO, "Pseudo non renseigné." );
        }
    }

    private void traiterMotDePasse(String mdp) {
    	this.validationMotsDePasse(mdp);
    }
    
    /**
     * Valide le mot de passe saisi.
     */
    private void validationMotsDePasse( String password ){
        if ( password != null ) {
            if ( password.length() < 3 ) {
                this.setErreur(ATTR_MDP, "Le mot de passe doit contenir au moins 3 caractères." );
            }
        } else {
        	this.setErreur(ATTR_MDP, "Merci de saisir votre mot de passe." );
        }
    }
    
    /*
     * Ajoute un message correspondant au champ spécifié à la map des erreurs.
     */
    public void setErreur( String champ, String message ) {
        erreurs.put( champ, message );
    }
    
    public void validationCorrespondance(Joueur jForm, String mdpForm, Joueur jDAO) {
    	if(jForm == null) {
    		this.setErreur(ATTR_PSEUDO, "Non renseigné");
    		this.setErreur(ATTR_MDP, "Non renseigné");
    	}else {
	    	if(jDAO == null) {
	    		this.setErreur(ATTR_DAO, "Joueur inexistant"); //pour eviter les soucis de Hacker qui utilisent ces infos pour savoir quoi cibler, on peut brouiller les pistes dans un attribut identifiant qui ne donne pas de détails.
	    	}else if(!jForm.getPseudo().equals(jDAO.getPseudo())) {
    			this.setErreur(ATTR_PSEUDO, "Pseudo incorrecte");//pour eviter les soucis de Hacker qui utilisent ces infos pour savoir quoi cibler, on peut brouiller les pistes dans un attribut identifiant qui ne donne pas de détails.
    		}else if(!BCrypt.checkpw(mdpForm ,jDAO.getMotDePasse())){//jForm.getMotDePasse().equals(jDAO.getMotDePasse())
    			this.setErreur(ATTR_MDP, "Mot de passe incorrecte");//pour eviter les soucis de Hacker qui utilisent ces infos pour savoir quoi cibler, on peut brouiller les pistes dans un attribut identifiant qui ne donne pas de détails.
    		}
    	}
    	
    	/* Initialisation du résultat global de la validation. */
        if ( erreurs.isEmpty() ) {
            resultat = "Succès de la connexion.";
        } else {
            resultat = "Échec de la connexion.";
        }
    }

}
