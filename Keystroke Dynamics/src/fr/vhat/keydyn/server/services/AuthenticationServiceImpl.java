package fr.vhat.keydyn.server.services;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import fr.vhat.keydyn.client.services.AuthenticationService;
import fr.vhat.keydyn.server.DataStore;
import fr.vhat.keydyn.server.Password;
import fr.vhat.keydyn.shared.AuthenticationMode;
import fr.vhat.keydyn.shared.AuthenticationReturn;
import fr.vhat.keydyn.shared.KeystrokeSequence;
import fr.vhat.keydyn.shared.entities.AuthenticationAttempt;
import fr.vhat.keydyn.shared.entities.User;

import com.googlecode.objectify.ObjectifyService;

import java.util.Date;
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
		ObjectifyService.register(AuthenticationAttempt.class);
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

		AuthenticationReturn authenticationReturn = new AuthenticationReturn();

		if (kdPassword.equals(";[];[]")) {
			authenticationReturn.setAuthenticationErrorCode(-3);
			return authenticationReturn;
		}

		// Train mode or test mode with default login : get login from session
		if (mode == AuthenticationMode.TRAIN_MODE
				|| (mode == AuthenticationMode.TEST_MODE && login.equals(""))) {
			login = (String)getThreadLocalRequest().getSession()
					.getAttribute("login");
		}
		User user = DataStore.retrieveUser(login);

		if (user != null) {

			// User found in the data store
			KeystrokeSequence keystrokeSequence =
					new KeystrokeSequence(kdPassword);
			String password = keystrokeSequence.getPhrase();
			String hashedPassword = user.getHashedPassword();

			if (Password.check(password, hashedPassword)) {

				Date date = new Date();
				Float distance =
						DataTransmissionServiceImpl.getUserDistance(
								keystrokeSequence, login);
				Float threshold =
						DataTransmissionServiceImpl.getUserThreshold(login);

				if (distance <= threshold) {

					authenticationReturn.setAuthenticated(true);
					// Succeed to connect : +1 to the training value
					if (mode == AuthenticationMode.TRAIN_MODE) {
						user.setTrainingValue(
								user.getTrainingValue() + 1);
					}
					if (mode == AuthenticationMode.PRODUCTION_MODE) {
						long elapsedTime;
						if (user.getLastLoginAttempt() != null) {
							elapsedTime = date.getTime() 
									- user.getLastLoginAttempt().getTime();
						} else {
							elapsedTime = 10000;
						}
						if (elapsedTime >= 10000) {
							createSession(login);
							log.info("User <" + login + "> succeed to connect "
									+ "with usage of his keystroke dynamics :"
									+ " distance = " + distance + " and "
									+ "threshold was " + threshold);
							user.setTrainingValue(
									user.getTrainingValue() + 1);
						} else {
							log.info("User <" + login + "> unable to connect "
									+ "with usage of his keystroke dynamics :"
									+ " distance = " + distance + " and "
									+ "threshold was " + threshold);
							authenticationReturn.setAuthenticationErrorCode(-5);
							authenticationReturn.setTimeToWait(
									10000 - elapsedTime);
						}
					}

					// TODO: à ce moment, quand on load la memberAreaPage, on appelle getTempPW
					// qui fait le ménage et éventuellement affiche une popup si des mots de passe
					// sont proposés, l'utilisateur doit obligatoirement accepter ou refuser
					// et ses nouvelles tentatives comptent éventuellement comme des échecs

				} else {
					log.info("User <" + login + "> tried to connect with the " +
							"correct password but didn't succeed according " +
							"to his keystroke dynamics.");
					authenticationReturn.setAuthenticationErrorCode(-2);

					// Failed to log in : -4 to the training value
					if (mode == AuthenticationMode.TRAIN_MODE) {
						user.setTrainingValue(user.getTrainingValue() - 4);
					}
					
					if (mode == AuthenticationMode.PRODUCTION_MODE
							&& (user.getTrainingValue() <
									User.getMaxTrainingValue())
							&& (distance <=
							threshold * 
							(User.getMaxTrainingValue()
									- user.getTrainingValue()))) {
						// It's a fail but user is logged
						authenticationReturn.setAuthenticated(true);
						createSession(login);
						log.info("But as he's a new user not enough trained," +
								" we accept it anyway.");
					}
				}

				if (giveInfo) {
					authenticationReturn.setDistance(distance);
					authenticationReturn.setThreshold(threshold);
				}

				String attackerLogin = (String)getThreadLocalRequest()
						.getSession().getAttribute("login");
				if ((mode == AuthenticationMode.TRAIN_MODE)
					|| (mode == AuthenticationMode.PRODUCTION_MODE
							&& authenticationReturn.isAuthenticated())) {
					// Train mode or production mode with success
					DataStore.saveKDData(user, keystrokeSequence, false, null);
					authenticationReturn.setSaved(true);
					// TODO: ajouter les échecs sur TempPassword aux Stats

					// If his account is enough trained, log for stats
					if (user.isEnoughTrained()) {
						AuthenticationAttempt authenticationAttempt =
							new AuthenticationAttempt(
									login,
									login,
									keystrokeSequence, 0,
									user.getKDPasswordNumber(), date,
									authenticationReturn.isAuthenticated());
						DataStore.saveAuthenticationAttempt(
							authenticationAttempt);
					}
				} else if (mode == AuthenticationMode.TEST_MODE) {
					// Authentication Attempt except on his own account
					if (!attackerLogin.equals(login)
							&& user.isEnoughTrained()) {
						AuthenticationAttempt authenticationAttempt =
							new AuthenticationAttempt(
									attackerLogin,
									login,
									keystrokeSequence, 0,
									user.getKDPasswordNumber(), date,
									authenticationReturn.isAuthenticated());
						DataStore.saveAuthenticationAttempt(
							authenticationAttempt);
					}
				} else {
					// TODO: TempPassword functionality
					// Production mode with fail
					// if compte ready, store dans TempPassword avec des
					// infos supplémentaires issues du javascript et de l'applet + IP
				}

				if (mode == AuthenticationMode.PRODUCTION_MODE) {
					user.setLastLoginAttempt(date);
					DataStore.saveUser(user);
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

		if (!giveInfo) {
			authenticationReturn.setSaved(false);
			if (authenticationReturn.getAuthenticationErrorCode() != -5
					&& authenticationReturn.getAuthenticationErrorCode() != 0) {
				authenticationReturn.setAuthenticationErrorCode(-1);
			}
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
	 * Give the list of the enough trained accounts, plus the session login.
	 * @return Login list in a String table.
	 */
	@Override
	public String[] getUsersLogin() {
		List<User> userList = DataStore.getEnoughTrainedUsers();
		int size = userList.size();
		String[] result = new String[size + 1];
		result[0] = "";
		for (int i = 0 ; i < size ; ++i) {
			result[i+1] = userList.get(i).getLogin();
		}
		return result;
	}

	/**
	 * Give the password of an user only if this user account is enough trained.
	 * @return Password of the given user.
	 */
	@Override
	public String getUserPassword(String login) {
		String sessionLogin = (String)getThreadLocalRequest().getSession()
        		.getAttribute("login");
		if (login.equals("") || login.equals(sessionLogin)) {
			return "Votre mot de passe";
		}
		User user = DataStore.retrieveUser(login);
		if (user.isEnoughTrained()) {
			return user.getPassword();
		} else {
			return null;
		}
	}

	/**
	 * Get the number of keystroke dynamics data registered in the system for
	 * this user's password.
	 * @return Number of keystroke data stored in the system.
	 */
	@Override
	public int getUserStrokesNumber() {
		String sessionLogin = (String)getThreadLocalRequest().getSession()
        		.getAttribute("login");
		return DataStore.getUserStrokesNumber(sessionLogin);
	}
}
