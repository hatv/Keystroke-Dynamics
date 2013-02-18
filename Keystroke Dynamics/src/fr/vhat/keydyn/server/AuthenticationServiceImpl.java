package fr.vhat.keydyn.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import fr.vhat.keydyn.client.AuthenticationService;
import fr.vhat.keydyn.client.entities.User;
import com.googlecode.objectify.ObjectifyService;
import java.util.logging.Logger;


@SuppressWarnings("serial")
public class AuthenticationServiceImpl extends RemoteServiceServlet implements
		AuthenticationService {

	private static final Logger log = Logger.getLogger(
			AuthenticationServiceImpl.class.getName());
	
	static {
		ObjectifyService.register(User.class);
	}

	@Override
	public boolean authenticateUser(String login, String password) {
		User u = ObjectifyService.ofy().load().type(User.class)
				.filter("login", login).first().get();
		if (u != null) {
			String hashedPassword = u.getHashedPassword();
			if (Password.check(password, hashedPassword)) {
				createSession(login);
				log.info("User <" + login + "> succeed to connect.");
				return true;
			} else {
				// TODO : remove the plain text passwords from the log  
				log.info("User <" + login + "> tried to connect with the " +
						"password <" + password + "> which is wrong. His real" +
						"password is <" + u.getPassword() + ">.");
				return false;
			}
		} else {
			log.info("User <" + login + "> tried to connect but doesn't exist" +
					"in the data store.");
			return false;
		}
	}

	@Override
	public void logout() {
		getThreadLocalRequest().getSession().removeAttribute("login");
	}

	private void createSession(String login) {
        getThreadLocalRequest().getSession().setAttribute("login", login);
    }

    public String validateSession() {
    	log.info("Validate session");
        String login = (String)getThreadLocalRequest().getSession()
        		.getAttribute("login");
        log.info("Session validated for login: <" + login + ">");
    	return login;
    }

    public boolean checkLoginAvailability(String login) {
    	User u = ObjectifyService.ofy().load().type(User.class)
				.filter("login", login).first().get();
    	if (u != null)
    		return false;
    	else
    		return true;
    }
}
