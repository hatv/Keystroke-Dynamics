package fr.vhat.keydyn.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import fr.vhat.keydyn.client.AuthenticationService;
import fr.vhat.keydyn.client.entities.User;
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
		User u = ObjectifyService.ofy().load().type(User.class)
				.filter("login", login).first().get();
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
					"in the data store.");
			return false;
		}
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
        	User u = ObjectifyService.ofy().load().type(User.class)
    				.filter("login", login).first().get();
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
	 * Check whether a login is available or not for a new registration.
	 * @return Login availability.
	 */
	@Override
    public boolean checkLoginAvailability(String login) {
    	User u = ObjectifyService.ofy().load().type(User.class)
				.filter("login", login).first().get();
    	if (u != null) {
    		// The specified login is already stored in the data store
    		return false;
    	}
    	else {
    		return true;
    	}
    }
}
