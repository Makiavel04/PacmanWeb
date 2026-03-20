package com.pacman.servlets;

import java.io.IOException;

import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pacman.beans.*;
import com.pacman.dao.*;

public class CosmetiqueServlet extends HttpServlet {
	public static final String VUE = "/WEB-INF/cosmetique.jsp";
    private JoueurDao joueurDao;
    private CosmetiqueDao cosmetiqueDao;

    public void init() throws ServletException {
        DAOFactory mysqlFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
        this.joueurDao = mysqlFactory.getJoueurDao();
        this.cosmetiqueDao = mysqlFactory.getCosmetiqueDao();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        if (session.getAttribute("sessionJoueur") == null) {
            response.sendRedirect(request.getContextPath() + "/connexion");
            return;
        }

        try {
        	Joueur joueurConnecte = (Joueur) session.getAttribute("sessionJoueur");
        	int id = joueurConnecte.getId();           
        	List<Cosmetique> mylistcos = cosmetiqueDao.listerInventaireJoueur(id);
            List<Cosmetique> listcos = cosmetiqueDao.listerTous();
            request.setAttribute("mycos", mylistcos);
            request.setAttribute("cos", listcos);
        } catch (Exception e) {
            request.setAttribute("erreur", "Impossible de charger les cosmetique.");
        }
        
        this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Joueur joueurConnecte = (Joueur) session.getAttribute("sessionJoueur");

        // Sécurité anti-triche
        if (joueurConnecte == null) {
            response.sendRedirect(request.getContextPath() + "/connexion");
            return;
        }

        String action = request.getParameter("action");
        String idCosmetiqueStr = request.getParameter("idCosmetique");

        try {
            if (action != null && idCosmetiqueStr != null) {
                int idCosmetique = Integer.parseInt(idCosmetiqueStr);
                int idJoueur = joueurConnecte.getId();

                if ("acheter".equals(action)) {
                    joueurDao.ajouterCosmetique(idJoueur, idCosmetique);
                    
                } else if ("equiper".equals(action)) {
                    joueurDao.equiperCosmetique(idJoueur, idCosmetique);

                    joueurConnecte.setIdCosmetiqueActif(idCosmetique);
                    session.setAttribute("sessionJoueur", joueurConnecte);
                }
            }
            response.sendRedirect(request.getContextPath() + "/cosmetique");
            
        } catch (Exception e) {
            request.setAttribute("erreur", "Action impossible : Tu possèdes déjà cet objet !");
            doGet(request, response); 
        }
    }
}	