package pl.jaz.jazapp.webapp.login;

import pl.jaz.jazapp.webapp.user.User;
import pl.jaz.jazapp.webapp.user.UserDatabase;
import pl.jaz.jazapp.webapp.user.UserNotFoundException;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class LoginService {
    @Inject
    private UserDatabase userDatabase;

    public User loginUser(LoginRequest loginRequest) throws UserNotFoundException {
        return userDatabase.getUser(loginRequest).orElseThrow(UserNotFoundException::new);

    }
}
