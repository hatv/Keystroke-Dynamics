package fr.vhat.keydyn.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import fr.vhat.keydyn.client.RegistrationService;
import fr.vhat.keydyn.client.entities.User;
import fr.vhat.keydyn.shared.FieldVerifier;
import com.googlecode.objectify.ObjectifyService;
import java.util.Date;

@SuppressWarnings("serial")
public class RegistrationServiceImpl extends RemoteServiceServlet implements
		RegistrationService {
	
	static {
		ObjectifyService.register(User.class);
	}
	public boolean registerUser(String login, String email, int age,
			String gender, String country, int computerExperience,
			int computerUsage) {
		if (FieldVerifier.isValidLogin(login) /*TODO : others*/) {
			// TODO : insert in DB
	        User user = new User();
	        user.setLogin(login);
	        user.setEmail(email);
	        user.setAge(age);
	        user.setGender(gender);
	        user.setCountry(country);
	        user.setComputerExperience(computerExperience);
	        user.setComputerUsage(computerUsage);
	        user.setDate(new java.util.Date());
	        ObjectifyService.ofy().save().entity(user).now();
			// TODO : envoyer le mdp par mail
			return true;
		} else
			return false;
	}
}
