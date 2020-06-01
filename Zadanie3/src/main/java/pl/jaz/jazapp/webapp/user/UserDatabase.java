package pl.jaz.jazapp.webapp.user;

import pl.jaz.jazapp.webapp.login.LoginRequest;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.faces.context.FacesContext;
import java.util.*;

@Singleton
@Startup
public class UserDatabase {
    private Map<LoginRequest, User> users = new HashMap<>();

    public void addUser(User user) throws UserAlreadyExistsException {


        LoginRequest loginRequest = new LoginRequest(user.getUsername(), user.getPassword());
        Optional<User> x = getUser(loginRequest);
        if (x.isPresent()) {
            throw new UserAlreadyExistsException();
        }
        System.out.println("dodaje usera: " + user);
        users.put(loginRequest, user);
    }

    public Optional<User> getUser(LoginRequest loginRequest) {
        return Optional.ofNullable(users.get(loginRequest));
    }

}
