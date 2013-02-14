package fr.vhat.keydyn.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface AuthenticationServiceAsync {
	void authenticateUser(String login, String password, 
			AsyncCallback<Boolean> callback)
					throws IllegalArgumentException;
	void logout(AsyncCallback<Void> callback);
	void validateSession(AsyncCallback<String> callback);
}
