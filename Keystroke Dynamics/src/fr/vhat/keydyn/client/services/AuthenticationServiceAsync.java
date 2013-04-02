package fr.vhat.keydyn.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Authentication and sessions management.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
public interface AuthenticationServiceAsync {
	//void authenticateUser(String login, String password, 
	//		AsyncCallback<Boolean> callback);
	void authenticateUser(String login, int mode, String kdPassword,
			boolean giveInfo, AsyncCallback<Float[]> callback);
	void logout(AsyncCallback<Void> callback);
	void validateSession(AsyncCallback<String> callback);
	void getUsersLogin(AsyncCallback<String[]> callback);
	void getUserPassword(String login, AsyncCallback<String> callback);
}
