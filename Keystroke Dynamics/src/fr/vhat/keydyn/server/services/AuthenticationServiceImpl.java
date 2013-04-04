package fr.vhat.keydyn.server.services;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import fr.vhat.keydyn.client.services.AuthenticationService;
import fr.vhat.keydyn.server.DataStore;
import fr.vhat.keydyn.server.Password;
import fr.vhat.keydyn.shared.AuthenticationMode;
import fr.vhat.keydyn.shared.AuthenticationReturn;
import fr.vhat.keydyn.shared.KeystrokeSequence;
import fr.vhat.keydyn.shared.entities.User;

import com.googlecode.objectify.ObjectifyService;

import java.util.List;
import java.util.logging.Logger;

/**
 * The Authentication Service provides the necessary functions to ensure the
 * users authentication and session persistence.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
@SuppressWarnings("serial")
public class AuthenticationServiceImpl extends RemoteServiceServlet implements
		AuthenticationService {

	private static final Logger log = Logger.getLogger(
			AuthenticationServiceImpl.class.getName());

	static {
		ObjectifyService.register(User.class);
	}

	/**
	 * Authenticate an user from his login and the keystroke sequence of his
	 * password according to the information stored in the data store.
	 * @param login User's login.
	 * @param mode AuthenticationMode among test, train or production.
	 * @param keystrokeSequence User's keystroke sequence password.
	 * @param giveInfo True to get distance and threshold, false otherwise.
	 * @return AuthenticationReturn object giving requested information.
	 */
	@Override
	public AuthenticationReturn authenticateUser(String login,
			AuthenticationMode mode, String kdPassword, boolean giveInfo) {

		// Train mode or test mode with default login : get login from session
		if (mode == AuthenticationMode.TRAIN_MODE
				|| (mode == AuthenticationMode.TEST_MODE && login.equals(""))) {
			login = (String)getThreadLocalRequest().getSession()
					.getAttribute("login");
		}
		User user = DataStore.retrieveUser(login);

		AuthenticationReturn authenticationReturn = new AuthenticationReturn();

		if (user != null) {
			// User u found in the data store
			KeystrokeSequence keystrokeSequence =
					new KeystrokeSequence(kdPassword);
			String password = keystrokeSequence.getPhrase();
			String hashedPassword = user.getHashedPassword();
			if (Password.check(password, hashedPassword)) {
				Float distance =
						DataTransmissionServiceImpl.getUserDistance(
								keystrokeSequence, login);
				Float threshold =
						DataTransmissionServiceImpl.getUserThreshold(login);
				if (distance <= threshold) {
					createSession(login);
					log.info("User <" + login + "> succeed to connect with " +
							"usage of his keystroke dynamics : distance = " +
							distance + " and threshold was " + threshold);
					// TODO: à ce moment, quand on load la memberAreaPage, on appelle getTempPW
					// qui fait le ménage et éventuellement affiche une popup si des mots de passe
					// sont proposés, l'utilisateur doit obligatoirement accepter ou refuser
					authenticationReturn.setAuthenticated(true);
				} else {
					log.info("User <" + login + "> tried to connect with the " +
							"correct password but didn't succeed according " +
							"to his keystroke dynamics.");
					authenticationReturn.setAuthenticationErrorCode(-2);
				}
				if (giveInfo) {
					authenticationReturn.setDistance(distance);
					authenticationReturn.setThreshold(threshold);
				}
				if ((mode == AuthenticationMode.TRAIN_MODE)
					|| (mode == AuthenticationMode.PRODUCTION_MODE
							&& authenticationReturn.isAuthenticated())) {
					// Train mode or production mode with success
					DataStore.saveKDData(user, keystrokeSequence, false, null);
					authenticationReturn.setSaved(true);
					// TODO: Stats : ajouter les succès et échec dans table d'association
					// + if(compteReady)
					//		if result[0] == 1 -> ajouter un succes aux stats
					// 		else if result[0] == 0 && saveData == 1 -> ajouter un echec aux stats
				} else if (mode == AuthenticationMode.TEST_MODE) {
					// Test mode
					// compléter authenticationAttempts car on est en test -> succes ou echec aux stats
				} else if (mode == AuthenticationMode.PRODUCTION_MODE
						&& !authenticationReturn.isAuthenticated()) {
					// TODO: TempPassword functionality
					// Production mode with fail
					// if compte ready, store dans TempPassword avec des
					// infos supplémentaires issues du javascript et de l'applet + IP
				}
			} else {
				log.info("User <" + login + "> tried to connect with the " +
						"password <" + password + "> which is wrong. His " +
						"right password is <" + user.getPassword() + ">. His " +
						"keystroke dynamics will not be analyzed.");
				authenticationReturn.setAuthenticationErrorCode(-3);
			}
		} else {
			log.info("User <" + login + "> tried to connect but doesn't exist" +
					" in the data store.");
			authenticationReturn.setAuthenticationErrorCode(-4);
		}

		return authenticationReturn;
	}

	/**
	 * Delete a session.
	 */
	@Override
	public void logout() {
		getThreadLocalRequest().getSession().removeAttribute("login");
	}

	/**
	 * Create a session by setting the login attribute to the user's login.
	 * @param login User's login.
	 */
	private void createSession(String login) {
        getThreadLocalRequest().getSession().setAttribute("login", login);
    }

	/**
	 * Check if a session is still valid.
	 * @return Login of the current session or null.
	 */
	@Override
    public String validateSession() {
        String login = (String)getThreadLocalRequest().getSession()
        		.getAttribute("login");
        if (login != null) {
        	// A session exists
        	User u = DataStore.retrieveUser(login);
    		if (u != null) {
    			// User u found in the data store
    			log.fine("Session validated for login: <" + login + ">");
    			return login;
    		} else {
    			log.info("User <" + login + "> try to validate session but " +
    					"this user doesn't exist in the data store.");
    			return null;
    		}
        } else {
        	return login;
        }
    }

	/**
	 * Give the list of the enough trained accounts.
	 * @return Login list in a String table.
	 */
	@Override
	public String[] getUsersLogin() {
		List<User> userList = DataStore.getEnoughTrainedUsers();
		int size = userList.size();
		String[] result = new String[size];
		for (int i = 0 ; i < size ; ++i) {
			result[i] = userList.get(i).getLogin();
		}
		return result;
	}

	/**
	 * Give the password of an user only if this user account is enough trained.
	 * @return Password of the given user.
	 */
	@Override
	public String getUserPassword(String login) {
		User user = DataStore.retrieveUser(login);
		if (user.isEnoughTrained()) {
			return user.getPassword();
		} else {
			return null;
		}
	}
}
