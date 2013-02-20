package fr.vhat.keydyn.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import fr.vhat.keydyn.client.RegistrationService;
import fr.vhat.keydyn.client.entities.User;
import fr.vhat.keydyn.shared.FieldVerifier;
import com.googlecode.objectify.ObjectifyService;
import java.util.Date;
import java.util.logging.Logger;

@SuppressWarnings("serial")
public class RegistrationServiceImpl extends RemoteServiceServlet implements
		RegistrationService {

	private static final Logger log = Logger.getLogger(
			RegistrationServiceImpl.class.getName());
	
	static {
		ObjectifyService.register(User.class);
	}

	public boolean registerUser(String login, String email, int age,
			String gender, String country, int computerExperience,
			int typingUsage) {

		if (FieldVerifier.isValidLogin(login)
				&& FieldVerifier.isValidEmail(email)
				&& FieldVerifier.isValidAge(age)
				&& FieldVerifier.isValidGender(gender)
				&& FieldVerifier.isValidCountry(country)
				&& FieldVerifier.isValidExperience(computerExperience)
				&& FieldVerifier.isValidUsage(typingUsage)) {

			// TODO : vérifier que le login est disponible
			String password = Password.generatePassword();
			String hashedPassword = Password.hash(password);
			Date date = new Date();
	        User user = new User(login, password, hashedPassword, email, age,
	        		gender, country, computerExperience, typingUsage, date);
	        ObjectifyService.ofy().save().entity(user).now();
			log.info("User <" + login + "> created with password <" + password
					+ ">.");

			Mail.sendPasswordMail("vh7utc@gmail.com", login, password);
			return true;
		} else
			return false;
	}
}
