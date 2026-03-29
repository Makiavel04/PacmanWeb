package com.pacman.servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pacman.beans.Joueur;
import com.pacman.dao.DAOFactory;
import com.pacman.dao.JoueurDao;

@WebServlet("/menu")
public class MenuServlet extends HttpServlet {
    public static final String VUE = "/WEB-INF/menu.jsp";
    private JoueurDao joueurDao;

    public void init() throws ServletException {
        DAOFactory mysqlFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
        this.joueurDao = mysqlFactory.getJoueurDao();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Joueur> leaderboard = joueurDao.listerMeilleursScores();
            request.setAttribute("scores", leaderboard);
        } catch (Exception e) {
            request.setAttribute("erreur", "Impossible de charger le classement.");
        }
        
        this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
    }
}