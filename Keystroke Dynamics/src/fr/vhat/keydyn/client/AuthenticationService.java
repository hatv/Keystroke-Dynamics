package fr.vhat.keydyn.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * Authentication and sessions management.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
@RemoteServiceRelativePath("authentication")
public interface AuthenticationService extends RemoteService {
	boolean authenticateUser(String login, String password)
					throws IllegalArgumentException;
	void logout();
	String validateSession();
	boolean checkLoginAvailability(String login);
}
