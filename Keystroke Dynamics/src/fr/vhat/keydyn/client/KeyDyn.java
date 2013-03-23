package fr.vhat.keydyn.client;

import com.google.gwt.core.client.EntryPoint;

/**
 * Entry class of the Keystroke Dynamics Analysis system.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
public class KeyDyn implements EntryPoint {

	/**
	 * This method is called when the browser want to access to the page.
	 */
	public void onModuleLoad() {
		Application app = new Application("content");
		app.show();
	};
}