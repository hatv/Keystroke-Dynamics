package fr.vhat.keydyn.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;

import fr.vhat.keydyn.shared.RecognitionReturn;

/**
 * Recognition management.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
public interface RecognitionServiceAsync {
	void recognize(String kdSentence, boolean giveInfo,
			AsyncCallback<RecognitionReturn> callback);
	void learn(String login, String kdSentence,
			AsyncCallback<Void> callback);
	void getRandomSentence(AsyncCallback<String> callback);
}
