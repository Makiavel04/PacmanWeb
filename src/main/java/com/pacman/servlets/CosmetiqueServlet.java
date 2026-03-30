package com.pacman.servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pacman.beans.*;
import com.pacman.dao.*;
import com.pacman.metier.MetierCosmetique;

@WebServlet("/cosmetique")
public class CosmetiqueServlet extends HttpServlet {
    private static final String CONFIG_DAO_FACTORY = "daofactory";
    public static final String VUE = "/WEB-INF/cosmetique.jsp";
    public static final String ATTR_JOUEUR_SESSION = "sessionJoueur"; 
    
    private JoueurDao joueurDao;
    private CosmetiqueDao cosmetiqueDao;

    public void init() throws ServletException {
        this.joueurDao = ( (DAOFactory) getServletContext().getAttribute( CONFIG_DAO_FACTORY ) ).getJoueurDao();
        this.cosmetiqueDao = ( (DAOFactory) getServletContext().getAttribute( CONFIG_DAO_FACTORY ) ).getCosmetiqueDao();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession();
        Joueur joueurConnecte = (Joueur) session.getAttribute(ATTR_JOUEUR_SESSION);
        
//        if (joueurConnecte == null) {
//            response.sendRedirect(request.getContextPath() + "/connexion");
//            return;
//        }
        MetierCosmetique metier = new MetierCosmetique(joueurDao, cosmetiqueDao);
        request.setAttribute("mycos", metier.recupererInventaire(joueurConnecte));
        request.setAttribute("cos", metier.recupererBoutique());
        if (metier.getErreur() != null) {
            request.setAttribute("erreur", metier.getErreur());
        }
        
        this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Joueur joueurConnecte = (Joueur) session.getAttribute(ATTR_JOUEUR_SESSION);
        if (joueurConnecte == null) {
            response.sendRedirect(request.getContextPath() + "/connexion");
            return;
        }

        MetierCosmetique metier = new MetierCosmetique(joueurDao, cosmetiqueDao);
        metier.traiterAction(request, joueurConnecte);

        if (metier.getErreur() == null) {
        	/*zebi*/
            session.setAttribute(ATTR_JOUEUR_SESSION, joueurConnecte);            
            response.sendRedirect(request.getContextPath() + "/cosmetique");
        } else {
            request.setAttribute("erreur", metier.getErreur());
            doGet(request, response); 
        }
    }
}