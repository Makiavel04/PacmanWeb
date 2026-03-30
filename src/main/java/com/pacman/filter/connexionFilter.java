
package com.pacman.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



public class connexionFilter implements Filter {

    public static final String ACCES_PUBLIC     = "/connexion";
	public static final String ATTR_JOUEUR_SESSION = "joueur_session";

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String uri = request.getRequestURI();
        HttpSession session = request.getSession();
        
        if (session.getAttribute(ATTR_JOUEUR_SESSION) == null && !uri.contains("/connexion")  && !uri.contains("/inscription")) 
            response.sendRedirect(request.getContextPath() + ACCES_PUBLIC);
        else 
        	chain.doFilter( request, response );
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
