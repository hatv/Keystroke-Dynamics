package fr.vhat.keydyn.server.services;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import fr.vhat.keydyn.client.services.RegistrationService;
import fr.vhat.keydyn.server.DataStore;
import fr.vhat.keydyn.server.Mail;
import fr.vhat.keydyn.server.Password;
import fr.vhat.keydyn.shared.FieldVerifier;
import fr.vhat.keydyn.shared.entities.User;

import com.googlecode.objectify.ObjectifyService;

import java.util.Date;
import java.util.logging.Logger;

/**
 * The Registration Service provides the necessary functions to ensure the
 * registration of a new user.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
@SuppressWarnings("serial")
public class RegistrationServiceImpl extends RemoteServiceServlet implements
		RegistrationService {

	private static final Logger log = Logger.getLogger(
			RegistrationServiceImpl.class.getName());
	
	static {
		ObjectifyService.register(User.class);
	}

	/**
	 * Register a new user according to the given information.
	 * @param login An available valid login for the user to register.
	 * @param email A valid email address.
	 * @param birthYear Birth year of the user.
	 * @param gender Gender of the user among "Male" or "Female".
	 * @param country Living country of the user.
	 * @param computerExperience Experience of the user with computers.
	 * @param typingUsage Estimation of the time spending typing on a keyboard.
	 * @return Success of the registration.
	 */
	public boolean registerUser(String login, String email, int birthYear,
			String gender, String country, int computerExperience,
			int typingUsage) {

		log.info("Registration attempt : <" + login + " ; " + email + " ; "
				+ birthYear + " ; " + gender + " ; " + country + " ; "
				+ computerExperience + " ; " + typingUsage + ">");

		if (FieldVerifier.isValidLogin(login)
				&& FieldVerifier.isValidEmail(email)
				&& FieldVerifier.isValidBirthYear(birthYear)
				&& FieldVerifier.isValidGender(gender)
				&& FieldVerifier.isValidCountry(country)
				&& FieldVerifier.isValidExperience(computerExperience)
				&& FieldVerifier.isValidUsage(typingUsage)
				&& (DataStore.retrieveUser(login) == null)) {

			String password = Password.generatePassword();
			String hashedPassword = Password.hash(password);
			Date date = new Date();
	        User user = new User(login, password, hashedPassword, email,
	        		birthYear, gender, country, computerExperience, typingUsage,
	        		date);
	        DataStore.saveUser(user);

			Mail.sendWelcomeMail(email, login, password);
			return true;
		} else
			return false;
	}

	/**
	 * Check whether a login is available or not for a new registration.
	 * @return Login availability.
	 */
	@Override
    public boolean checkLoginAvailability(String login) {
    	User u = DataStore.retrieveUser(login);
    	if (u != null) {
    		// The specified login is already stored in the data store
    		return false;
    	}
    	else {
    		return true;
    	}
    }
}
