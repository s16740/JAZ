package pl.jaz.jazapp.webapp.register;

import pl.jaz.jazapp.webapp.user.User;
import pl.jaz.jazapp.webapp.user.UserAlreadyExistsException;
import pl.jaz.jazapp.webapp.user.UserDatabase;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class RegisterService {
    @Inject
    private UserDatabase userDatabase;

    public void registerUser(RegisterRequest registerRequest) throws UserAlreadyExistsException {
        User user = new User(registerRequest.getUsername(), registerRequest.getPassword(), registerRequest.getFirstName(),
				registerRequest.getLastName(), registerRequest.getBirthdate());

        userDatabase.addUser(user);
    }
}
