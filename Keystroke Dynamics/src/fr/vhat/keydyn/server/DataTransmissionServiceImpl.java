package fr.vhat.keydyn.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import fr.vhat.keydyn.client.DataTransmissionService;
import fr.vhat.keydyn.client.entities.KDPassword;
import fr.vhat.keydyn.client.entities.User;
import fr.vhat.keydyn.shared.KeystrokeSequence;
import fr.vhat.keydyn.shared.StatisticsUnit;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * The Data Transmission Service provides the necessary functions to ensure the
 * persistence of the Keystroke Dynamics Data.
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
	 * Save the given Keystroke Dynamics Data in the data store.
	 * @param password String to store with the keystroke dynamics data.
	 * @param pressTimes Press times.
	 * @param releaseTimes Release times.
	 * @return True if the data have been stored, false otherwise.
	 */
	@Override
	public boolean saveKDData(String kdString) {
		// Check whether an user is logged or not
		String sessionLogin = (String)getThreadLocalRequest().getSession()
        		.getAttribute("login");
		if (sessionLogin != null) {
			// Then, check if the password matches the one stored in the data
			// store
			User u = ObjectifyService.ofy().load().type(User.class)
					.filter("login", sessionLogin).first().get();
			if (u != null) {
				KeystrokeSequence keystrokeSequence =
						new KeystrokeSequence(kdString);
				String password = keystrokeSequence.getPhrase();
				String hashedPassword = u.getHashedPassword();
				if (Password.check(password, hashedPassword)) {
					// KDData can be saved in the data store
					Date typingDate = new Date();
			        KDPassword kdData = new KDPassword(password, sessionLogin,
			        		keystrokeSequence, typingDate);
			        u.addKDDataKey(
			        		ObjectifyService.ofy().save().entity(kdData).now());
			        // Compute or update the means vectors
			        StatisticsUnit means = u.getMeans();
			        if (means != null) {
			        	int dataNumber = u.getKDDataSize();
				        means.addToMeans(keystrokeSequence, dataNumber);
			        } else {
			        	means = new StatisticsUnit();
			        	means.set(0,
			        			keystrokeSequence.getPressToPressSequence());
			        	means.set(1,
			        			keystrokeSequence
			        			.getReleaseToReleaseSequence());
			        	means.set(2,
			        			keystrokeSequence.getPressToReleaseSequence());
			        	means.set(3,
			        			keystrokeSequence.getReleaseToPressSequence());
			        }
			        u.setMeans(means);
			        // Compute or update the standard deviation vectors
			        StatisticsUnit sd = u.getSd();
			        if (sd != null) {
				        sd = ComputationServiceImpl.computeSd(sessionLogin);
			        } else {
			        	sd = new StatisticsUnit();
			        	int[] sdTimes = new int[kdData.getLength() - 1];
			        	for (int i = 0 ; i < sdTimes.length ; ++i)
			        		sdTimes[i] = 0;
			        	int[] sdTimesMore = new int[kdData.getLength()];
			        	for (int i = 0 ; i < sdTimesMore.length ; ++i)
			        		sdTimesMore[i] = 0;
			        	sd.set(0, sdTimes);
			        	sd.set(1, sdTimes);
			        	sd.set(2, sdTimesMore);
			        	sd.set(3, sdTimes);
			        }
			        u.setSd(sd);
			        ObjectifyService.ofy().save().entity(u).now();
			        log.info("User <" + sessionLogin + "> : new data saved: " +
			        		"WORD=" + keystrokeSequence.getPhrase() +
			        		" ; PRESS=" +
			        		keystrokeSequence.getPressSequence().toString() +
			        		" ; RELEASE=" +
			        		keystrokeSequence.getReleaseSequence().toString());
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
		User u = ObjectifyService.ofy().load().type(User.class)
				.filter("login", user).first().get();
		if (u != null) {
			List<Key<KDPassword>> kdDataKeys = u.getKDDataKeys();
			List<KDPassword> kdData = new ArrayList<KDPassword>();
			for (Key<KDPassword> kdDataKey : kdDataKeys) {
				KDPassword kdDataElement = ObjectifyService.ofy().load()
						.type(KDPassword.class).id(kdDataKey.getId()).get();
				kdData.add(kdDataElement);
			}
			return kdData;
		} else {
			log.warning("User <" + user + "> tried to get KD data" +
					" but this user doesn't exist.");
			return null;
		}
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
		User u = ObjectifyService.ofy().load().type(User.class)
				.filter("login", user).first().get();
		if (u != null) {
			StatisticsUnit meansUnit = u.getMeans();
			if (meansUnit != null) {
				return meansUnit;
			} else {
				log.info("No means to return to user <" + user +
						">.");
				return null;
			}
		} else {
			log.warning("User <" + user + "> tried to get means " +
					"but this user doesn't exist.");
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
			log.info("An user tried to get standard deviations but was not" +
					" logged.");
			return null;
		}
	}

	/**
	 * Static function which retrieve the standard deviations of a given user.
	 * @param user Login of the user.
	 * @return See getSd function description.
	 */
	public static StatisticsUnit getUserSd(String user) {
		User u = ObjectifyService.ofy().load().type(User.class)
				.filter("login", user).first().get();
		if (u != null) {
			StatisticsUnit sdUnit = u.getSd();
			if (sdUnit != null) {
				return sdUnit;
			} else {
				log.info("No standard deviations to return to user <" + user +
						">.");
				return null;
			}
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
		User u = ObjectifyService.ofy().load().type(User.class)
				.filter("login", user).first().get();
		if (u != null) {
			StatisticsUnit sdUnit = u.getSd();
			StatisticsUnit meansUnit = u.getMeans();
			if (sdUnit != null && meansUnit != null) {
				return ComputationServiceImpl.distance(keystrokeSequence,
						meansUnit, sdUnit);
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

	public static Float getUserThreshold(String user) {
		User u = ObjectifyService.ofy().load().type(User.class)
				.filter("login", user).first().get();
		if (u != null) {
			StatisticsUnit sdUnit = u.getSd();
			StatisticsUnit meansUnit = u.getMeans();
			List<KDPassword> kdPassword = getUserKDData(user);
			if (sdUnit != null && meansUnit != null) {
				return ComputationServiceImpl.threshold(kdPassword, meansUnit,
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
}
