package pl.jaz.jazapp.webapp.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.jaz.jazapp.webapp.register.RegisterRequest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@ApplicationScoped
public class UserCreatorService {
	@PersistenceContext
	private EntityManager entityManager;

	@Inject
	private UserSearchService userSearchService;

	private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Transactional
	public void createUser(RegisterRequest registerRequest) throws UserAlreadyExistsException {
		if (userSearchService.findUser(registerRequest.getUsername()).isPresent()) {
			throw new UserAlreadyExistsException();
		}
		final var encodedPassword = passwordEncoder.encode(registerRequest.getPassword());
		var user = new UserEntity(registerRequest.getUsername(), encodedPassword,
				registerRequest.getFirstName(), registerRequest.getLastName(),
				LocalDate.parse(registerRequest.getBirthdate(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		entityManager.persist(user);
	}
}
