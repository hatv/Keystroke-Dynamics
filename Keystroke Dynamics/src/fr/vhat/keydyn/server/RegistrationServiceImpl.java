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
			String password = Password.generatePassword();
			String hashedPassword = Password.hash(password);
			Date date = new Date();
	        User user = new User(login, password, hashedPassword, email, age,
	        		gender, country, computerExperience, typingUsage, date);
	        ObjectifyService.ofy().save().entity(user).now();
			log.info("User <" + login + "> created with password <" + password
					+ ">.");
			// TODO : envoyer le mdp par mail
			// Following line for test
			/*
			String tmpLogin = "";
			for (int i = 0 ; i < 1000 ; ++i) {
				tmpLogin = login + i;
				String password = Password.generatePassword();
				String hashedPassword = Password.hash(password);
				Date date = new Date();
		        User user = new User(tmpLogin, password, hashedPassword, email, age,
		        		gender, country, computerExperience, computerUsage, date);
		        ObjectifyService.ofy().save().entity(user).now();
				log.info("User <" + login + "> created with password <" + password
						+ ">.");
				// TODO : envoyer le mdp par mail
			}
			*/
			return true;
		} else
			return false;
	}
}
