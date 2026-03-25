package com.pacman.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pacman.beans.Joueur;
import com.pacman.dao.DAOFactory;
import com.pacman.dao.JoueurDao;
import com.pacman.metier.InscriptionForm;

@WebServlet("/inscription")
public class InscriptionServlet extends HttpServlet {
	private static final String CONFIG_DAO_FACTORY = "daofactory";
	public static final String VUE = "/WEB-INF/inscription.jsp";
	public static final String ATTR_JOUEUR_SESSION = "joueur_session";
	public static final String ATTR_JOUEUR= "joueur";
	public static final String ATTR_FORM = "form";
    private JoueurDao joueurDao;

    public void init() throws ServletException {
        this.joueurDao = ( (DAOFactory) getServletContext().getAttribute( CONFIG_DAO_FACTORY ) ).getJoueurDao();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InscriptionForm form_inscription = new InscriptionForm(this.joueurDao);
        Joueur joueur = form_inscription.inscrireUtilisateur(request);
        
        HttpSession session = request.getSession();
        if(form_inscription.getErreurs().isEmpty() && joueur!=null) {//Si pas d'erreur dans le formulaire de connexion et qu'on a un joueur en base
            session.setAttribute(ATTR_JOUEUR_SESSION, joueur);
            response.sendRedirect(request.getContextPath() + "/menu");
        }else {
        	session.setAttribute(ATTR_JOUEUR_SESSION, null);
	        request.setAttribute(ATTR_JOUEUR, joueur);
	        request.setAttribute(ATTR_FORM, form_inscription);
	        this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
        }
    	
    }
}