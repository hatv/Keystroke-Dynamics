package fr.vhat.keydyn.client.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * Authentication and sessions management.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
@RemoteServiceRelativePath("authentication")
public interface AuthenticationService extends RemoteService {
	//boolean authenticateUser(String login, String password);
	Float[] authenticateUser(String login, int mode, String kdPassword,
			boolean giveInfo);
	void logout();
	String validateSession();
	String[] getUsersLogin();
	String getUserPassword(String login);
}
