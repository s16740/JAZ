package pl.jaz.jazapp;

import javax.faces.application.ResourceHandler;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("*")
public class AuthenticationFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (isUserLogged(request.getSession()) || isSiteAllowed(request) || isResourceRequest(request)) {
            chain.doFilter(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/login.xhtml");
        }
    }

    private boolean isSiteAllowed(HttpServletRequest request) {
        return "/login.xhtml".equals(request.getServletPath())
                || "/register.xhtml".equals(request.getServletPath());
    }

    private boolean isResourceRequest(HttpServletRequest request) {
        return request.getRequestURI().startsWith(request.getContextPath() + ResourceHandler.RESOURCE_IDENTIFIER + "/");
    }

    private boolean isUserLogged(HttpSession session) {
		return session.getAttribute("user") != null;
    }


}
