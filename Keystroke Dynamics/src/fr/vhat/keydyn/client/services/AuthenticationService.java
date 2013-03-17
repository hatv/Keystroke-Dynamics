package fr.vhat.keydyn.client.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import fr.vhat.keydyn.shared.KeystrokeSequence;

/**
 * Authentication and sessions management.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
@RemoteServiceRelativePath("authentication")
public interface AuthenticationService extends RemoteService {
	boolean authenticateUser(String login, String password);
	Float[] authenticateUser(String login, int saveData,
			KeystrokeSequence keystrokeSequence, boolean giveInfos);
	void logout();
	String validateSession();
}
