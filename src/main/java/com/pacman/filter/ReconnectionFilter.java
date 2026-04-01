package com.pacman.filter;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(urlPatterns = {"/connexion", "/inscription"})
public class ReconnectionFilter implements Filter{
    public static final String ACCES_CONNEXION     = "/connexion";
    public static final String ACCES_INSCRIPTION     = "/inscription";
    public static final String ACCES_API = "/api";
    public static final String ACCES_MENU     = "/menu";
	public static final String ATTR_JOUEUR_SESSION = "sessionJoueur";
	
	
	@Override
	public void destroy() {}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String chemin = request.getRequestURI().substring( request.getContextPath().length() );
        if(chemin.startsWith(ACCES_API)) {//Ne bloque pas les requêtes api
        	chain.doFilter(request, response);
        	return;
        }

        HttpSession session = request.getSession();
        boolean estConnecte = session.getAttribute(ATTR_JOUEUR_SESSION)!=null;
        if(!estConnecte) {
        	chain.doFilter(request, response);
        	return;
        }else {
        	response.sendRedirect(request.getContextPath() + ACCES_MENU);
        }
	}
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {}
}
