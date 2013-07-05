package fr.vhat.keydyn.client.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import fr.vhat.keydyn.shared.RecognitionReturn;

/**
 * Recognition management.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
@RemoteServiceRelativePath("recognition")
public interface RecognitionService extends RemoteService {
	RecognitionReturn recognize(String kdSentence, boolean giveInfo);
	void learn(String login, String kdSentence);
	String getRandomSentence();
}
