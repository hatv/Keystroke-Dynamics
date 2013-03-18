package fr.vhat.keydyn.server.services;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import fr.vhat.keydyn.client.services.AuthenticationService;
import fr.vhat.keydyn.server.DataStore;
import fr.vhat.keydyn.server.Password;
import fr.vhat.keydyn.shared.KeystrokeSequence;
import fr.vhat.keydyn.shared.entities.User;

import com.googlecode.objectify.ObjectifyService;

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
	 * Authenticate an user from his login and password according to the
	 * information stored in the data store.
	 * @param login User's login.
	 * @param password User's candidate password.
	 * @return Authentication success.
	 */
	@Override
	public boolean authenticateUser(String login, String password) {
		User u = DataStore.retrieveUser(login);
		if (u != null) {
			// User u found in the data store
			String hashedPassword = u.getHashedPassword();
			if (Password.check(password, hashedPassword)) {
				createSession(login);
				log.info("User <" + login + "> succeed to connect.");
				return true;
			} else {
				// TODO : remove the plain text passwords from the log  
				log.info("User <" + login + "> tried to connect with the " +
						"password <" + password + "> which is wrong. His " +
						"right password is <" + u.getPassword() + ">.");
				return false;
			}
		} else {
			log.info("User <" + login + "> tried to connect but doesn't exist" +
					" in the data store.");
			return false;
		}
	}

	/**
	 * Authenticate an user from his login and the keystroke sequence of his
	 * password according to the information stored in the data store.
	 * @param login User's login.
	 * @param mode Save data behavior : 0 means "never save data" (test),
	 * 1 means "always save data" (train) and 2 means "save data only if
	 * authenticated" (production). Furthermore, in train mode the login is
	 * unused as the session login is used.
	 * @param keystrokeSequence User's keystroke sequence password.
	 * @param giveInfo True to get distance and threshold, false otherwise.
	 * @return A Float table where the first element represents the success or
	 * failure of the authentication (1, -1, -2 or -3), the second one tells if
	 * the data have been saved or not in the data store (1 or -1) and then
	 * follows the distance and the threshold if giveInfos was set to true.
	 */
	@Override
	public Float[] authenticateUser(String login, int mode,
			String kdPassword, boolean giveInfo) {
		if (mode == 1 || (mode == 0 && login.equals(""))) {
			login = (String)getThreadLocalRequest().getSession()
					.getAttribute("login");
		}
		User user = DataStore.retrieveUser(login);
		Float[] result;
		if (giveInfo) {
			result = new Float[4];
			result[2] = (float)-1;
			result[3] = (float)-1;
		} else {
			result = new Float[2];
		}
		result[0] = (float)-1;
		result[1] = (float)-1;
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
							"usage of his keystroke dynamics.");
					// TODO: à ce moment, quand on load la memberAreaPage, on appelle getTempPW
					// qui fait le ménage et éventuellement affiche une popup si des mots de passe
					// sont proposés, l'utilisateur doit obligatoirement accepter ou refuser
					result[0] = (float)1;
				} else {
					log.info("User <" + login + "> tried to connect with the " +
							"correct password but didn't succeed according " +
							"to his keystroke dynamics.");
				}
				if (giveInfo) {
					result[2] = distance;
					result[3] = threshold;
				}
				if (mode == 1 || (mode == 2 && result[0] == 1)) {
				// Train mode or production mode with success
					DataStore.saveKDData(user, keystrokeSequence, false, null);
					result[1] = (float)1;
					//+ if(compteReady)
					//		if result[0] == 1 -> ajouter un succes
					// 		else if result[0] == 0 && saveData == 1 -> ajouter un echec
				} else if (mode == 0) {
				// Test mode
					// compléter authenticationAttempts car on est en test -> succes ou echec
				} else if (mode == 2 && result[0] == 0) {
				// Production mode with fail
					// if compte ready, store dans TempPassword avec des
					// infos supplémentaires issues du javascript et de l'applet + IP
				}
			} else {
				log.info("User <" + login + "> tried to connect with the " +
						"password <" + password + "> which is wrong. His " +
						"right password is <" + user.getPassword() + ">. His " +
						"keystroke dynamics will not be analyzed.");
				result[0] = (float)-2;
			}
		} else {
			log.info("User <" + login + "> tried to connect but doesn't exist" +
					" in the data store.");
			result[0] = (float)-3;
		}
		return result;
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
}
