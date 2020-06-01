package pl.jaz.jazapp.webapp.login;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.jaz.jazapp.webapp.user.*;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class LoginService {
    @Inject
    private UserDatabase userDatabase;
    @Inject
	private UserSearchService userSearchService;
	private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserEntity loginUser(LoginRequest loginRequest) throws UserNotFoundException {
		final var user = userSearchService.findUser(loginRequest.getUsername()).orElseThrow(UserNotFoundException::new);
		if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
			throw new UserNotFoundException();
		}
		return user;
//        return userDatabase.getUser(loginRequest).orElseThrow(UserNotFoundException::new);
    }
}
