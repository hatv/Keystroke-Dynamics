package fr.vhat.keydyn.server;

import java.util.Date;
import java.util.logging.Logger;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

import fr.vhat.keydyn.client.entities.KDPassword;
import fr.vhat.keydyn.client.entities.TempPassword;
import fr.vhat.keydyn.client.entities.User;
import fr.vhat.keydyn.shared.KeystrokeSequence;
import fr.vhat.keydyn.shared.StatisticsUnit;

/**
 * Data store functionalities.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
public class DataStore {

	private static final Logger log = Logger.getLogger(
			DataStore.class.getName());

	/**
	 * Retrieve an user from his login in the data store.
	 * @param login Login of the user to retrieve.
	 * @return User entity or null if the user does not exist.
	 */
	public static User retrieveUser(String login) {
		return ObjectifyService.ofy().load().type(User.class)
				.filter("login", login).first().get();
	}

	/**
	 * Save an user in the data store immediately.
	 * @param user User entity.
	 */
	public static void saveUser(User user) {
		ObjectifyService.ofy().save().entity(user).now();
	}

	/**
	 * Retrieve a Keystroke Dynamics Password from his key in the data store.
	 * @param key Key of the Keystroke Dynamics Password.
	 * @return The corresponding KDPassword.
	 */
	public static KDPassword retrievePassword(Key<KDPassword> key) {
		return ObjectifyService.ofy().load().type(KDPassword.class)
				.id(key.getId()).get();
	}

	/**
	 * Save the given Keystroke Dynamics data in the data store.
	 * @param user User entity of the user.
	 * @param keystrokeSequence Keystroke Dynamics data to store.
	 * @param temp True if the data must be stored in the temporary password
	 * data table.
	 * @param info String table of characteristics about the computer like IP
	 * address, OS or browser in order to help the user to validate the
	 * candidate keystroke record if temp is set to true.
	 */
	public static void saveKDData(User user,
			KeystrokeSequence keystrokeSequence, boolean temp, String[] info) {
		Date typingDate = new Date();
		String login = user.getLogin();
	    if (!temp) {
	    	KDPassword kdPassword = new KDPassword(
	    			keystrokeSequence.getPhrase(), login, keystrokeSequence,
	    			typingDate);
	        user.addKDPasswordKey(
	        		ObjectifyService.ofy().save().entity(kdPassword).now());
	        log.info("User <" + login + "> : new data saved: " +
	        		keystrokeSequence.toString());

		    // Update the means vectors
	        StatisticsUnit means = user.getMeans();
	        int dataNumber = user.getKDPasswordNumber();
		    means.addToMeans(keystrokeSequence, dataNumber - 1);
	        user.setMeans(means);
	        // Means must be updated before the standard deviations computation
	        DataStore.saveUser(user);
	        log.info("Means have been updated for user <" + login + ">.");

		    // Update the standard deviation vectors
	        user.setSd(Computation.computeSd(login));
	        // Standard deviations must be updated before the threshold
	        // computation
	        DataStore.saveUser(user);
	        log.info("Standard deviations have been updated for user <" + login
	        		+ ">.");

		    // Update the threshold
	        Float threshold = Computation.computeThreshold(user);
	        user.setThreshold(threshold);
		    DataStore.saveUser(user);
		    log.info("Threshold has been updated for user <" + login + ">: " +
	        		threshold);
	    } else {
	    	TempPassword tempPassword =
	    			new TempPassword(keystrokeSequence.getPhrase(), login,
	    					keystrokeSequence, typingDate, info);
	    	ObjectifyService.ofy().save().entity(tempPassword).now();
	    }
	}
}
