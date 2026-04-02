package com.pacman.servlets;

import java.io.IOException;

import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pacman.beans.Joueur;
import com.pacman.beans.Partie;
import com.pacman.dao.DAOFactory;
import com.pacman.dao.JoueurDao;
import com.pacman.dao.PartieDao;
import com.pacman.metier.*;

@WebServlet("/profil")
public class ProfilServlet extends HttpServlet {
	private static final String CONFIG_DAO_FACTORY = "daofactory";
	public static final String VERS_CONNEXION = "/connexion";
    public static final String VUE = "/WEB-INF/profil.jsp";
    public static final String ATTR_JOUEUR_SESSION = "sessionJoueur"; 
    private PartieDao partieDao;
    private JoueurDao joueurDao;
    public void init() throws ServletException {
        this.partieDao = ( (DAOFactory) getServletContext().getAttribute( CONFIG_DAO_FACTORY ) ).getPartieDao();
        this.joueurDao = ( (DAOFactory) getServletContext().getAttribute( CONFIG_DAO_FACTORY ) ).getJoueurDao();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        MetierProfil ME = new MetierProfil(this.partieDao, this.joueurDao);
        Joueur joueurConnecte = (Joueur) session.getAttribute(ATTR_JOUEUR_SESSION);
        if(joueurConnecte == null) {//Au cas où le filter ne fait pas son travail
        	response.sendRedirect(request.getContextPath() + VERS_CONNEXION);
        	return;
        }else {
        	Joueur j = ME.majJoueur(joueurConnecte);
        	if(j!=null) {
        		joueurConnecte = j;
        		session.setAttribute(ATTR_JOUEUR_SESSION, j);
        	}
        }
        
	    List<Partie> historique = ME.recupererHistorique(joueurConnecte.getId());
	    request.setAttribute("historique", historique);
	
	    this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
    }
}