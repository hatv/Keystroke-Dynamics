package fr.vhat.keydyn.client.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import fr.vhat.keydyn.shared.AuthenticationMode;
import fr.vhat.keydyn.shared.AuthenticationReturn;

/**
 * Authentication and sessions management.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
@RemoteServiceRelativePath("authentication")
public interface AuthenticationService extends RemoteService {
	AuthenticationReturn authenticateUser(String login, AuthenticationMode mode,
			String kdPassword, boolean giveInfo);
	void logout();
	String validateSession();
	String[] getUsersLogin();
	String getUserPassword(String login);
	int getUserStrokesNumber();
}
