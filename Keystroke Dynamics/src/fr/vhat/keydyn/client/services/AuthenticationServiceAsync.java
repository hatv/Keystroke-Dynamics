package fr.vhat.keydyn.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;

import fr.vhat.keydyn.shared.KeystrokeSequence;

/**
 * Authentication and sessions management.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
public interface AuthenticationServiceAsync {
	void authenticateUser(String login, String password, 
			AsyncCallback<Boolean> callback);
	void authenticateUser(String login, int saveData,
			KeystrokeSequence keystrokeSequence, boolean giveInfos,
			AsyncCallback<Float[]> callback);
	void logout(AsyncCallback<Void> callback);
	void validateSession(AsyncCallback<String> callback);
}
