package fr.vhat.keydyn.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;

import fr.vhat.keydyn.shared.AuthenticationMode;
import fr.vhat.keydyn.shared.AuthenticationReturn;

/**
 * Authentication and sessions management.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
public interface AuthenticationServiceAsync {
	void authenticateUser(String login, AuthenticationMode mode,
			String kdPassword, boolean giveInfo,
			AsyncCallback<AuthenticationReturn> callback);
	void logout(AsyncCallback<Void> callback);
	void validateSession(AsyncCallback<String> callback);
	void getUsersLogin(AsyncCallback<String[]> callback);
	void getUserPassword(String login, AsyncCallback<String> callback);
	void getUserStrokesNumber(AsyncCallback<Integer> callback);
}
