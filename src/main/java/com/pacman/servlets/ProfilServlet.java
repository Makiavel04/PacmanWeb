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
import com.pacman.dao.PartieDao;
import com.pacman.metier.*;

@WebServlet("/profil")
public class ProfilServlet extends HttpServlet {
    public static final String VUE = "/WEB-INF/profil.jsp";
    private PartieDao partieDao;
    public void init() throws ServletException {
        DAOFactory mysqlFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
        this.partieDao = mysqlFactory.getPartieDao();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        if (session.getAttribute("sessionJoueur") == null) {
            response.sendRedirect(request.getContextPath() + "/connexion");
            return;
        }
        Joueur joueurConnecte = (Joueur) session.getAttribute("sessionJoueur");
        MetierProfil ME = new MetierProfil(this.partieDao);
        List<Partie> historique = ME.recupererHistorique(joueurConnecte.getId());
        request.setAttribute("historique", historique);

        this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
    }
}