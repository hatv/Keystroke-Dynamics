package fr.vhat.keydyn.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Registration checks and management.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
public interface RegistrationServiceAsync {
	void registerUser(String login, String email, int age, String gender,
			String country, int computerExperience, int computerUsage, 
			AsyncCallback<Boolean> callback)
					throws IllegalArgumentException;
}
