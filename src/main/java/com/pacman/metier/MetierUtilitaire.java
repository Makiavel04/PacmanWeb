package com.pacman.metier;

import javax.servlet.http.HttpServletRequest;

public class MetierUtilitaire {
	/*
     * Méthode utilitaire qui retourne null si un champ est vide, et son contenu
     * sinon.
     */
    public static String getValeurChamp( HttpServletRequest request, String nomChamp ) {
        String valeur = request.getParameter( nomChamp );
        if ( valeur == null || valeur.trim().length() == 0 ) {
            return null;
        } else {
            return valeur;
        }
    }
}
