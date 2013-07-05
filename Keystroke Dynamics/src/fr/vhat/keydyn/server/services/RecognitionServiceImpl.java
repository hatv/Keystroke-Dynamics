package fr.vhat.keydyn.server.services;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import fr.vhat.keydyn.client.services.RecognitionService;
import fr.vhat.keydyn.shared.RecognitionReturn;
import fr.vhat.keydyn.shared.entities.User;

import com.googlecode.objectify.ObjectifyService;

import java.util.logging.Logger;

/**
 * The Recognition Service provides the necessary functions to ensure the
 * users recognition.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
@SuppressWarnings("serial")
public class RecognitionServiceImpl extends RemoteServiceServlet implements
		RecognitionService {

	private static final Logger log = Logger.getLogger(
			RecognitionServiceImpl.class.getName());

	static {
		ObjectifyService.register(User.class);
	}

	/**
	 * Recognize an user from the keystroke sequence of a given sentence
	 * according to the information stored in the data store.
	 * @param keystrokeSequence User's keystroke sequence sentence.
	 * @param giveInfo True to get information, false otherwise.
	 * @return RecognitionReturn object giving requested information.
	 */
	@Override
	public RecognitionReturn recognize(String kdSentence, boolean giveInfo) {

		RecognitionReturn recognitionReturn = new RecognitionReturn();

		return recognitionReturn;
	}

	/**
	 * Learn the user's keystroke dynamics to the system by giving him a
	 * keystroke dynamics sequence for a specific sentence.
	 * @param login Login of the user.
	 * @param kdSentence Keystroke dynamics sentence.
	 */
	@Override
	public void learn(String login, String kdSentence) {
		log.info("User <" + login + "> is registering a new keystroke " +
				"dynamics sequence in order to be recognized.");
	}

	@Override
	public String getRandomSentence() {
		return "ceci est une phrase de test";
	}
}
