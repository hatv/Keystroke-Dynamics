package fr.vhat.keydyn.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Authentication and sessions management.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
public interface AuthenticationServiceAsync {
	void authenticateUser(String login, String password, 
			AsyncCallback<Boolean> callback);
	void logout(AsyncCallback<Void> callback);
	void validateSession(AsyncCallback<String> callback);
	void checkLoginAvailability(String login, AsyncCallback<Boolean> callback);
}
