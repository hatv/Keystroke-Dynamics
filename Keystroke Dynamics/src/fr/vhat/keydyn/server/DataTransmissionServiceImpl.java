package fr.vhat.keydyn.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import fr.vhat.keydyn.client.DataTransmissionService;
import fr.vhat.keydyn.client.entities.KDPassword;
import fr.vhat.keydyn.client.entities.User;
import com.googlecode.objectify.ObjectifyService;

import java.util.Date;
import java.util.logging.Logger;


@SuppressWarnings("serial")
public class DataTransmissionServiceImpl extends RemoteServiceServlet implements
		DataTransmissionService {

	private static final Logger log = Logger.getLogger(
			DataTransmissionServiceImpl.class.getName());
	
	static {
		ObjectifyService.register(User.class);
		ObjectifyService.register(KDPassword.class);
	}

	@Override
	public boolean saveKDData(String password, String pressTimes,
			String releaseTimes) {
		// Check whether an user is logged or not
		String sessionLogin = (String)getThreadLocalRequest().getSession()
        		.getAttribute("login");
		if (sessionLogin != null) {
			// Then, check if the password matches the one stored in the data
			// store
			User u = ObjectifyService.ofy().load().type(User.class)
					.filter("login", sessionLogin).first().get();
			if (u != null) {
				String hashedPassword = u.getHashedPassword();
				if (Password.check(password, hashedPassword)) {
					// KDData can be saved in the data store
					Date typingDate = new Date();
			        KDPassword kdData = new KDPassword(password, pressTimes,
			        		releaseTimes, typingDate);
			        u.addKDDataKey(
			        		ObjectifyService.ofy().save().entity(kdData).now());
			        ObjectifyService.ofy().save().entity(u).now();
			        log.info("User <" + sessionLogin + "> : new data saved.");
					return true;
				}
				else {
					log.info("User <" + sessionLogin + "> tried to save new " +
							"data but the password is wrong.");
					return false;
				}
			} else {
				log.warning("User <" + sessionLogin + "> tried to save new " +
						"data but this user doesn't exist.");
				return false;
			}
		} else {
			log.warning("An user tried to save new data but was not logged.");
			return false;
		}
	}


	@Override
	public String[][] getKDData() {
		// Check whether an user is logged or not
		int kdDataSize;
		String sessionLogin = (String)getThreadLocalRequest().getSession()
        		.getAttribute("login");
		if (sessionLogin != null) {
			User u = ObjectifyService.ofy().load().type(User.class)
					.filter("login", sessionLogin).first().get();
			if (u != null) {
				kdDataSize = u.getKDDataSize();
				String[][] kdData = new String[kdDataSize][3];
				String[] tempStr = new String[3];
				KDPassword tempKDData;
				for (int i = 0 ; i < kdDataSize ; ++i) {
					tempKDData = u.getKDData(i);
					tempStr[0] = tempKDData.getWord();
					tempStr[1] = tempKDData.getPressTimes();
					tempStr[2] = tempKDData.getReleaseTimes();
					kdData[i] = tempStr;
				}
				return kdData;
			} else {
				log.info("User <" + sessionLogin + "> tried to get KD data " +
						"but this user doesn't exist.");
				return null;
			}
		} else {
			log.info("An user tried to get KD data but was not logged.");
			return null;
		}
	}
}
