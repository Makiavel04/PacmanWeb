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
	public static final String VERS_CONNEXION = "/connexion";
    public static final String VUE = "/WEB-INF/cosmetique.jsp";
    public static final String ATTR_JOUEUR_SESSION = "sessionJoueur"; 
    public static final String ATTR_ERREUR = "erreur";
    public static final String ATTR_MES_COSMETIQUES = "mycos";
    public static final String ATTR_COSMETIQUES = "cos";
    
    public static final String ATTR_METIER_TEMP = "metier_temp";
    
    private JoueurDao joueurDao;
    private CosmetiqueDao cosmetiqueDao;

    public void init() throws ServletException {
        this.joueurDao = ( (DAOFactory) getServletContext().getAttribute( CONFIG_DAO_FACTORY ) ).getJoueurDao();
        this.cosmetiqueDao = ( (DAOFactory) getServletContext().getAttribute( CONFIG_DAO_FACTORY ) ).getCosmetiqueDao();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession();
        Joueur joueurConnecte = (Joueur) session.getAttribute(ATTR_JOUEUR_SESSION);
        
        if(joueurConnecte == null) {//Au cas où le filter ne fait pas son travail
        	response.sendRedirect(request.getContextPath() + VERS_CONNEXION);
        	return;
        }
        
        
        MetierCosmetique metier = (MetierCosmetique) request.getAttribute(ATTR_METIER_TEMP); //Tente de récupérer le métier s'il existe.
        if(metier != null) {
        	request.removeAttribute(ATTR_METIER_TEMP);
        }else {
        	metier = new MetierCosmetique(joueurDao, cosmetiqueDao);
        }
        
        List<Cosmetique> mesCos = metier.recupererInventaire(joueurConnecte);
        List<Cosmetique> cosBoutique = metier.recupererBoutique();
	    request.setAttribute(ATTR_MES_COSMETIQUES, mesCos);
	    request.setAttribute(ATTR_COSMETIQUES, cosBoutique);
	    if (!metier.getErreurs().isEmpty()) {
	    	request.setAttribute(ATTR_ERREUR, metier.getErreurs());
	    }
	        
	    this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
        
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Joueur joueurConnecte = (Joueur) session.getAttribute(ATTR_JOUEUR_SESSION);

        if(joueurConnecte==null) {
        	response.sendRedirect(request.getContextPath() + VERS_CONNEXION);//Au cas où le filter ne fait pas son travail
        	return ;
        }

        String action = request.getParameter("action");
        Integer idCosmetique = null;
        try{
        	idCosmetique = Integer.parseInt(request.getParameter("idCosmetique"));
        }catch (NumberFormatException e) {
            idCosmetique = null;
        }
        	
        MetierCosmetique metier = new MetierCosmetique(joueurDao, cosmetiqueDao);
		metier.traiterAction(action, idCosmetique, joueurConnecte);
		
		if (metier.getErreurs().isEmpty()) {
			session.setAttribute(ATTR_JOUEUR_SESSION, joueurConnecte);            
		    response.sendRedirect(request.getContextPath() + "/cosmetique");
		} else {
		   	request.setAttribute(ATTR_METIER_TEMP, metier);
	        doGet(request, response); 
		}
        
        
    }
}