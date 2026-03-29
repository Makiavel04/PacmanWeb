package com.pacman.servlets.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import com.pacman.beans.Partie;
import com.pacman.dao.DAOFactory;
import com.pacman.dao.PartieDao;
import com.pacman.metier.ReceptionScore;

@WebServlet("/api/resultat_partie")
public class ScorePartieAPIServlet extends HttpServlet {
	private static final String CONFIG_DAO_FACTORY = "daofactory";
	private static final String ATTR_VAINQUEUR = "vainqueur";
	private static final String ATTR_SCORE_PACMAN = "scorePacman";
	private static final String ATTR_SCORE_FANTOME = "scoreFantome";
	private static final String ATTR_ID_PACMANS = "idPacmans";
	private static final String ATTR_ID_FANTOMES = "idFantomes";
    public static final String ATTR_ERREUR = "erreur";
    public static final String ATTR_ID_PARTIE = "idPartie";
	
	private PartieDao partieDao;
	
	public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO Utilisateur */
        this.partieDao = ( (DAOFactory) getServletContext().getAttribute( CONFIG_DAO_FACTORY ) ).getPartieDao();
    }
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StringBuilder sb = new StringBuilder();
	    String line;
	    try (BufferedReader reader = request.getReader()) {
	        while ((line = reader.readLine()) != null) {
	            sb.append(line);
	        }
	    }
	    
	    JSONObject jsonRep = null;
	    try {
		    JSONObject jsonEntree = new JSONObject(sb.toString());
		    Partie partie = new Partie();
		    if(jsonEntree.has(ATTR_VAINQUEUR)) partie.setVainqueur(jsonEntree.getString(ATTR_VAINQUEUR));
		    if(jsonEntree.has(ATTR_SCORE_PACMAN)) partie.setScorePacmans(jsonEntree.getInt(ATTR_SCORE_PACMAN));
		    if(jsonEntree.has(ATTR_SCORE_FANTOME)) partie.setScoreFantomes(jsonEntree.getInt(ATTR_SCORE_FANTOME));
		    if(jsonEntree.has(ATTR_ID_PACMANS)) {
		    	JSONArray jsonIdPacmans = jsonEntree.getJSONArray(ATTR_ID_PACMANS);
		    	List<Integer> idPacmans = new ArrayList<Integer>();
		    	for(int i=0;i<jsonIdPacmans.length();++i) {
		    		idPacmans.add(jsonIdPacmans.getInt(i));
		    	}
		    	partie.setIdPacmans(idPacmans);
		    }
		    
		    if(jsonEntree.has(ATTR_ID_FANTOMES)) {
		    	JSONArray jsonIdFantomes= jsonEntree.getJSONArray(ATTR_ID_FANTOMES);
		    	List<Integer> idFantomes = new ArrayList<Integer>();
		    	for(int i=0;i<jsonIdFantomes.length();++i) {
		    		idFantomes.add(jsonIdFantomes.getInt(i));
		    	}
		    	partie.setIdFantomes(idFantomes);
		    }
		    
		    ReceptionScore recepScore = new ReceptionScore(this.partieDao);
		    Integer idPartie = recepScore.enregistrerPartie(partie);
		    
		    if(!recepScore.getErreurs().isEmpty()) {
		    	jsonRep = new JSONObject();
		    	jsonRep.put(ATTR_ERREUR, new JSONObject(recepScore.getErreurs()));
		    	response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		    }else {
		    	jsonRep = new JSONObject();
		    	jsonRep.put(ATTR_ID_PARTIE, idPartie);
		    	response.setStatus(HttpServletResponse.SC_OK);
		    }
		    
		}catch(JSONException e){
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	    
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    if(jsonRep != null) {
		    PrintWriter out = response.getWriter();
		    out.print(jsonRep.toString());
		    out.flush();
	    }
	    
	    
	}
}
