package com.pacman.servlets.api;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.pacman.beans.Joueur;
import com.pacman.dao.DAOFactory;
import com.pacman.dao.JoueurDao;

@WebServlet("/api/connexion")
public class ConnexionAPIServlet extends HttpServlet{
	private static final String CONFIG_DAO_FACTORY = "daofactory";
	private static final String ATTR_USERNAME = "username";
	private static final String ATTR_PASSWORD = "password";
	private static final String ATTR_ID = "idClient";
	private static final String ATTR_COULEUR= "couleur";
	
	private JoueurDao joueur_dao;
	
	public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO Utilisateur */
        this.joueur_dao = ( (DAOFactory) getServletContext().getAttribute( CONFIG_DAO_FACTORY ) ).getJoueurDao();
    }

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StringBuilder sb = new StringBuilder();
	    String line;
	    try (BufferedReader reader = request.getReader()) {
	        while ((line = reader.readLine()) != null) {
	            sb.append(line);
	        }
	    }

	    // 2. Parser la chaîne en objet JSON
	    JSONObject jsonEntree = new JSONObject(sb.toString());
	    String username = jsonEntree.getString("username"); // Utilisez vos constantes RequetesJSON ici
	    String password = jsonEntree.getString("password");
	    
	    try {
            Joueur joueur = this.joueur_dao.trouver(username);
            
            if (joueur != null && joueur.getMotDePasse().equals(password)) {
                HttpSession session = request.getSession();
                session.setAttribute("sessionJoueur", joueur);
                //response.sendRedirect(request.getContextPath() + "/menu");
            } else {
                request.setAttribute("erreurConnexion", "Pseudo ou mot de passe incorrect.");
                //this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
            }
        } catch (Exception e) {
        	
        }

	}
}
