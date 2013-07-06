package fr.vhat.keydyn.server;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

import fr.vhat.keydyn.shared.KeystrokeSequence;
import fr.vhat.keydyn.shared.StatisticsUnit;
import fr.vhat.keydyn.shared.entities.AuthenticationAttempt;
import fr.vhat.keydyn.shared.entities.KDPassword;
import fr.vhat.keydyn.shared.entities.TempPassword;
import fr.vhat.keydyn.shared.entities.User;

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
				.filter("login", login).first().now();
	}

	/**
	 * Save an user in the data store immediately.
	 * @param user User entity.
	 */
	public static void saveUser(User user) {
		ObjectifyService.ofy().save().entity(user).now();
		log.info("User <" + user.getLogin() + "> created or updated with " +
				"password <" + user.getPassword() + ">.");
	}

	/**
	 * Retrieve a Keystroke Dynamics Password from his key in the data store.
	 * @param key Key of the Keystroke Dynamics Password.
	 * @return The corresponding KDPassword.
	 */
	public static KDPassword retrievePassword(Key<KDPassword> key) {
		return ObjectifyService.ofy().load().type(KDPassword.class)
				.id(key.getId()).now();
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

	/**
	 * Retrieve the users whose account is enough trained.
	 * @return List of users enough trained.
	 */
	public static List<User> getEnoughTrainedUsers() {
		return ObjectifyService.ofy().load().type(User.class)
				.filter("isEnoughTrained", true).list();
	}

	/**
	 * Save an authentication attempt in the data store immediately.
	 * @param authenticationAttempt AuthenticationAttempt entity.
	 */
	public static void saveAuthenticationAttempt(
			AuthenticationAttempt authenticationAttempt) {
		ObjectifyService.ofy().save().entity(authenticationAttempt).now();
		String logString = new String("User <" +
				authenticationAttempt.getAttackerLogin() + "> " + "tried to " +
				"log in as <" + authenticationAttempt.getVictimLogin() + ">: ");
		String success = (authenticationAttempt.isSuccess())?"SUCCESS":"FAIL";
		logString += success;
		log.info(logString);
	}

	public static int getUserStrokesNumber(String login) {
		List<KDPassword> strokesList =
			ObjectifyService.ofy().load().type(KDPassword.class)
				.filter("author", login).list();
		return strokesList.size();
	}
}
