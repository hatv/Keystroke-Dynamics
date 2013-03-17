package fr.vhat.keydyn.server.services;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import fr.vhat.keydyn.client.entities.KDPassword;
import fr.vhat.keydyn.client.entities.User;
import fr.vhat.keydyn.client.services.DataTransmissionService;
import fr.vhat.keydyn.server.Computation;
import fr.vhat.keydyn.server.DataStore;
import fr.vhat.keydyn.shared.KeystrokeSequence;
import fr.vhat.keydyn.shared.StatisticsUnit;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * The Data Transmission Service provides the necessary functions to ensure the
 * persistence of the Keystroke Dynamics data.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
@SuppressWarnings("serial")
public class DataTransmissionServiceImpl extends RemoteServiceServlet implements
		DataTransmissionService {

	private static final Logger log = Logger.getLogger(
			DataTransmissionServiceImpl.class.getName());

	static {
		ObjectifyService.register(User.class);
		ObjectifyService.register(KDPassword.class);
	}

	/**
	 * Return a table of two tables (pressed and released data) of n tables (one
	 * for each KDData stored in the data store) of m integers (where m is the 
	 * number of characters in the password).
	 * @return See description.
	 */
	@Override
	public List<KDPassword> getKDData() {
		String sessionLogin = (String)getThreadLocalRequest().getSession()
        		.getAttribute("login");
		if (sessionLogin != null) {
			return getUserKDData(sessionLogin);
		} else {
			log.info("An user tried to get KD data but was not logged.");
			return null;
		}
	}

	/**
	 * Static function which retrieve the KDData of a given user.
	 * @param user Login of the user.
	 * @return See getKDData function description.
	 */
	public static List<KDPassword> getUserKDData(String user) {
		User u = DataStore.retrieveUser(user);
		if (u != null) {
			return getUserKDData(u);
		} else {
			log.warning("User <" + user + "> tried to access KD data" +
					" but this user doesn't exist.");
			return null;
		}
	}

	/**
	 * Static function which retrieve the KDData of a given user.
	 * @param user User entity.
	 * @return See getKDData function description.
	 */
	public static List<KDPassword> getUserKDData(User user) {
		List<Key<KDPassword>> kdDataKeys = user.getKDPasswordKeys();
		List<KDPassword> kdData = new ArrayList<KDPassword>();
		for (Key<KDPassword> kdDataKey : kdDataKeys) {
			kdData.add(DataStore.retrievePassword(kdDataKey));
		}
		return kdData;
	}

	/**
	 * Retrieve the means computed for the current user.
	 * @return Means of the user in a StatisticsUnit format or null.
	 */
	public StatisticsUnit getMeans() {
		String sessionLogin = (String)getThreadLocalRequest().getSession()
        		.getAttribute("login");
		if (sessionLogin != null) {
			return getUserMeans(sessionLogin);
		} else {
			log.info("An user tried to get means but was not logged.");
			return null;
		}
	}

	/**
	 * Static function which retrieve the means of a given user.
	 * @param user Login of the user.
	 * @return See getMeans function description.
	 */
	public static StatisticsUnit getUserMeans (String user) {
		User u = DataStore.retrieveUser(user);
		if (u != null) {
			return u.getMeans();
		} else {
			log.warning("User <" + user + "> tried to get means but this user" +
					" doesn't exist.");
			return null;
		}
	}

	/**
	 * Retrieve the standard deviations computed for the current user.
	 * @return Standard deviations of the user in a StatisticsUnit format or
	 * null.
	 */
	public StatisticsUnit getSd() {
		String sessionLogin = (String)getThreadLocalRequest().getSession()
        		.getAttribute("login");
		if (sessionLogin != null) {
			return getUserSd(sessionLogin);
		} else {
			log.info("An user tried to get standard deviations but was not " +
					"logged.");
			return null;
		}
	}

	/**
	 * Static function which retrieve the standard deviations of a given user.
	 * @param user Login of the user.
	 * @return See getSd function description.
	 */
	public static StatisticsUnit getUserSd(String user) {
		User u = DataStore.retrieveUser(user);
		if (u != null) {
			return u.getSd();
		} else {
			log.warning("User <" + user + "> tried to get means " +
					"but this user doesn't exist.");
			return null;
		}
	}

	/**
	 * Compute or retrieve the distance value between the KeystrokeSequence and
	 * the stored data.
	 * @param keystrokeSequence KeystrokeSequence to estimate distance from.
	 * @return Distance.
	 */
	public Float getDistance(KeystrokeSequence keystrokeSequence) {
		String sessionLogin = (String)getThreadLocalRequest().getSession()
        		.getAttribute("login");
		if (sessionLogin != null) {
			return getUserDistance(keystrokeSequence, sessionLogin);
		} else {
			log.info("An user tried to get distance but was not logged.");
			return null;
		}
	}

	/**
	 * Static function which compute or retrieve the distance between the
	 * KeystrokeSequence and the stored data of a given user.
	 * @param keystrokeSequence KeystrokeSequence to estimate distance from.
	 * @param user User login.
	 * @return Distance.
	 */
	public static Float getUserDistance(KeystrokeSequence keystrokeSequence,
			String user) {
		User u = DataStore.retrieveUser(user);
		if (u != null) {
			StatisticsUnit sdUnit = u.getSd();
			StatisticsUnit meansUnit = u.getMeans();
			if (sdUnit != null && meansUnit != null) {
				return Computation.distance(keystrokeSequence, meansUnit,
						sdUnit);
			} else {
				log.info("No distance to return to user <" + user + ">: " +
						"unable to compute it.");
				return null;
			}
		} else {
			log.warning("User <" + user + "> tried to get distance but this " +
					"user doesn't exist.");
			return null;
		}
	}

	/**
	 * Compute or retrieve the authentication threshold value of the current
	 * user.
	 * @return Threshold.
	 */
	public Float getThreshold() {
		String sessionLogin = (String)getThreadLocalRequest().getSession()
        		.getAttribute("login");
		if (sessionLogin != null) {
			return getUserThreshold(sessionLogin);
		} else {
			log.info("An user tried to get threshold but was not logged.");
			return null;
		}
	}

	/**
	 * Compute or retrieve the authentication threshold value of a given user.
	 * @param user User login.
	 * @return Threshold.
	 */
	public static Float getUserThreshold(String user) {
		User u = DataStore.retrieveUser(user);
		if (u != null) {
			return u.getThreshold();
		} else {
			log.warning("User <" + user + "> tried to get threshold but this " +
					"user doesn't exist.");
			return null;
		}
	}
}
