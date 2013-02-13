package fr.vhat.keydyn.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("registration")
public interface RegistrationService extends RemoteService {
	boolean registerUser(String login, String email, int age, String gender,
			String country, int computerExperience, int computerUsage)
					throws IllegalArgumentException;
}
