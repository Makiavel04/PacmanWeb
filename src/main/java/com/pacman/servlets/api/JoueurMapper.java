package com.pacman.servlets.api;

import org.json.JSONObject;

import com.pacman.beans.Joueur;

public class JoueurMapper {
    public static final String ATTR_ID_JOUEUR = "idJoueur";
	public static final String ATTR_PSEUDO = "pseudo";
    public static final String ATTR_COULEUR = "couleur";
    
	public static JSONObject toJSON(Joueur j) {
		JSONObject json = new JSONObject();
		json.put(ATTR_ID_JOUEUR, j.getId());
		json.put(ATTR_PSEUDO, j.getPseudo());
		if(j.getCosmetiqueActif()!=null) json.put(ATTR_COULEUR, j.getCosmetiqueActif().getCouleur());
		return json;
	}
}
