package pl.jaz.jazapp.webapp.login;

import pl.jaz.jazapp.webapp.user.User;
import pl.jaz.jazapp.webapp.user.UserNotFoundException;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;



@Named
@RequestScoped
public class LoginController {
    @Inject
    private LoginService loginService;

    private String originalURL;

	@PostConstruct
	public void init() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		originalURL = (String) externalContext.getRequestMap().get(RequestDispatcher.FORWARD_REQUEST_URI);

		if (originalURL == null) {
			originalURL = externalContext.getRequestContextPath() + "/index.xhtml";
		} else {
			String originalQuery = (String) externalContext.getRequestMap().get(RequestDispatcher.FORWARD_QUERY_STRING);

			if (originalQuery != null) {
				originalURL += "?" + originalQuery;
			}
		}
	}

    public String login(LoginRequest loginRequest) {
        System.out.println(String.format("Tried to login with %s and password %s", loginRequest.getUsername(), loginRequest.getPassword()));

		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();

		try {
			User user = loginService.loginUser(loginRequest);
			externalContext.getSessionMap().put("user", user);
			externalContext.redirect(originalURL);
		} catch (IOException | UserNotFoundException e) {
			externalContext.getFlash().put("error-message", "Username or password id incorrect");
			return "";
		}
        return "/index.xhtml?faces-redirect=true";
    }
}
