package com.pacman.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.pacman.beans.Joueur;
import com.pacman.dao.DAOFactory;
import com.pacman.dao.JoueurDao;

public class ConnexionServlet extends HttpServlet {
    public static final String VUE_FORM = "/WEB-INF/connexion.jsp";
    private JoueurDao joueurDao;

    public void init() throws ServletException {
        this.joueurDao = DAOFactory.getInstance().getJoueurDao();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher(VUE_FORM).forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pseudo = request.getParameter("pseudo");
        String motDePasse = request.getParameter("motdepasse");

        try {
            Joueur joueur = joueurDao.trouver(pseudo);
            if (joueur != null && joueur.getMotDePasse().equals(motDePasse)) {
                HttpSession session = request.getSession();
                session.setAttribute("sessionJoueur", joueur);
                response.sendRedirect(request.getContextPath() + "/menu");
            } else {
                request.setAttribute("erreurConnexion", "Pseudo ou mot de passe incorrect.");
                this.getServletContext().getRequestDispatcher(VUE_FORM).forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("erreurConnexion", "Erreur système : " + e.getMessage());
            this.getServletContext().getRequestDispatcher(VUE_FORM).forward(request, response);
        }
    }
}