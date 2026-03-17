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

public class InscriptionServlet extends HttpServlet {
    
    public static final String VUE_FORM = "/WEB-INF/inscription.jsp";
    
    private JoueurDao joueurDao;

    public void init() throws ServletException {
        this.joueurDao = DAOFactory.getInstance().getJoueurDao();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher(VUE_FORM).forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pseudo = request.getParameter("nouveauPseudo");
        String motDePasse = request.getParameter("nouveauMdp");

        try {
            if (joueurDao.trouver(pseudo) != null) {
                request.setAttribute("erreurInscription", "Ce pseudo est déjà utilisé !");
                this.getServletContext().getRequestDispatcher(VUE_FORM).forward(request, response);
            } else {
                Joueur nouveauJoueur = new Joueur();
                nouveauJoueur.setPseudo(pseudo);
                nouveauJoueur.setMotDePasse(motDePasse);
                joueurDao.creer(nouveauJoueur);

                HttpSession session = request.getSession();
                session.setAttribute("sessionJoueur", nouveauJoueur);
                response.sendRedirect(request.getContextPath() + "/menu");
            }
        } catch (Exception e) {
            request.setAttribute("erreurInscription", "Erreur lors de la création : " + e.getMessage());
            this.getServletContext().getRequestDispatcher(VUE_FORM).forward(request, response);
        }
    }
}