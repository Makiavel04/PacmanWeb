
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


@WebFilter("/*")
public class GuestFilter implements Filter {

    public static final String ACCES_MENU     = "/menu";
    public static final String ACCES_CONNEXION     = "/connexion";
    public static final String ACCES_INSCRIPTION     = "/inscription";
	public static final String ATTR_JOUEUR_SESSION = "sessionJoueur";
    public static final String ACCES_API = "/api";
    public static final String ACCES_CSS = "/css";

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String chemin = request.getRequestURI().substring( request.getContextPath().length() );
        
        if(chemin.startsWith(ACCES_API) || chemin.startsWith(ACCES_CSS)) {//Ne bloque pas les requêtes api
        	chain.doFilter(request, response);
        	return;
        }
        boolean estZonePublique = chemin.equals(ACCES_MENU) 
                || chemin.equals(ACCES_CONNEXION) 
                || chemin.equals(ACCES_INSCRIPTION);
        
        HttpSession session = request.getSession();
        boolean estConnecte = session.getAttribute(ATTR_JOUEUR_SESSION)!=null;
                
        if( estConnecte || estZonePublique) {//Si connecté ou accès à une page autorisée, on laisse passer
        	chain.doFilter(request, response);
        }else {// Si on est pas connecté et qu'on veut autre chose que les pages publiques, on est recalé
        	response.sendRedirect(request.getContextPath() + ACCES_CONNEXION);
        }
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
