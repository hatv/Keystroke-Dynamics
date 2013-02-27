package fr.vhat.keydyn.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * Registration checks and management.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
@RemoteServiceRelativePath("registration")
public interface RegistrationService extends RemoteService {
	boolean registerUser(String login, String email, int age, String gender,
			String country, int computerExperience, int computerUsage)
					throws IllegalArgumentException;
}
