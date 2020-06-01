package pl.jaz.jazapp.webapp.register;

import pl.jaz.jazapp.webapp.user.UserAlreadyExistsException;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Named
@RequestScoped
public class RegisterController {
	@Inject
	private RegisterService registerService;

	public String register(RegisterRequest registerRequest) {
		System.out.println(String.format("Tried to register with %s and password %s", registerRequest.getUsername(),
				registerRequest.getPassword()));

		if (!registerRequest.getPassword().equals(registerRequest.getPasswordCheck())) {
			FacesContext.getCurrentInstance().getExternalContext().getFlash()
					.put("error-message", "Passwords do not match.");
			return "";
		}

		if (!registerRequest.getPassword().matches("^(?=.*?[A-Z])(?=.*?[a-z])((?=.*?[0-9])|(?=.*?[#?!@$%^&*-])).{3,}$")) {
			FacesContext.getCurrentInstance().getExternalContext().getFlash()
					.put("error-message", "Password must contain small and large letter and at least one number or special character.");
			return "";
		}

		if (!registerRequest.getUsername().matches("[a-z0-9]*")) {
			FacesContext.getCurrentInstance().getExternalContext().getFlash()
					.put("error-message", "Username can contain only numbers and small characters.");
			return "";
		}

		if (!registerRequest.getFirstName().matches("[a-zA-Z]*")) {
			FacesContext.getCurrentInstance().getExternalContext().getFlash()
					.put("error-message", "First name can contain only letters.");
			return "";
		}

		if (!registerRequest.getLastName().matches("[a-zA-Z]*")) {
			FacesContext.getCurrentInstance().getExternalContext().getFlash()
					.put("error-message", " Last name can contain only letters.");
			return "";
		}

		final var birthdate = registerRequest.getBirthdate();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		try {
			LocalDate.parse(birthdate, formatter);
		} catch (DateTimeParseException dateTimeParseException) {
			FacesContext.getCurrentInstance().getExternalContext().getFlash()
					.put("error-message", "Wrong date format. Use DD/MM/YYYY.");
			return "";
		}

		try {
			registerService.registerUser(registerRequest);
		} catch (UserAlreadyExistsException e) {
			FacesContext.getCurrentInstance().getExternalContext().getFlash()
					.put("error-message", "Username is already taken");
			return "";
		}

		return "/login.xhtml?faces-redirect=true";
	}


}
