package fr.vhat.keydyn.client.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * Registration checks and management.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
@RemoteServiceRelativePath("registration")
public interface RegistrationService extends RemoteService {
	boolean checkLoginAvailability(String login);
	boolean registerUser(String login, String email, int age, String gender,
			String country, int computerExperience, int computerUsage);
}
