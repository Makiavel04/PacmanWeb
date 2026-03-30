package com.pacman.servlets.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.pacman.beans.Joueur;
import com.pacman.dao.CosmetiqueDao;
import com.pacman.dao.DAOFactory;
import com.pacman.dao.JoueurDao;
import com.pacman.metier.ConnexionForm;

@WebServlet("/api/connexion")
public class ConnexionAPIServlet extends HttpServlet{
	private static final String CONFIG_DAO_FACTORY = "daofactory";
	public static final String ATTR_PSEUDO = "pseudo";
    public static final String ATTR_MDP = "motdepasse";
    public static final String ATTR_RESULTAT = "resultat";
    public static final String ATTR_ERREUR = "erreur";
    public static final String ATTR_COULEUR= "couleur";
	
	private JoueurDao joueurDao;
	private CosmetiqueDao cosmetiqueDao;
	
	public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO Utilisateur */
        this.joueurDao = ( (DAOFactory) getServletContext().getAttribute( CONFIG_DAO_FACTORY ) ).getJoueurDao();
        this.cosmetiqueDao = ( (DAOFactory) getServletContext().getAttribute( CONFIG_DAO_FACTORY ) ).getCosmetiqueDao();
    }

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StringBuilder sb = new StringBuilder();
	    String line;
	    try (BufferedReader reader = request.getReader()) {
	        while ((line = reader.readLine()) != null) {
	            sb.append(line);
	        }
	    }

	    JSONObject jsonEntree = new JSONObject(sb.toString());
	    String pseudo = jsonEntree.getString(ATTR_PSEUDO); 
	    String mdp = jsonEntree.getString(ATTR_MDP);
	    
	    ConnexionForm form_connexion = new ConnexionForm(this.joueurDao, this.cosmetiqueDao);
        Joueur joueur = form_connexion.connecterJoueurAPI(pseudo, mdp);

        JSONObject json = null;
	    if(form_connexion.getErreurs().isEmpty() && (joueur != null)) {
	    	json = JoueurMapper.toJSON(joueur);
	    	json.put(ATTR_RESULTAT, true);
	    	response.setStatus(HttpServletResponse.SC_OK);
	    }else {
	    	json = new JSONObject();
	    	json.put(ATTR_RESULTAT, false);
	    	json.put(ATTR_ERREUR, new JSONObject(form_connexion.getErreurs()));
	    	response.setStatus(HttpServletResponse.SC_OK);
	    }
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    PrintWriter out = response.getWriter();
	    out.print(json.toString());
	    out.flush();
	}
}
