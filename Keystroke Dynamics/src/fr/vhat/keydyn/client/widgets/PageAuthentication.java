package fr.vhat.keydyn.client.widgets;

import com.github.gwtbootstrap.client.ui.constants.IconType;

/**
 * Abstract class from which inherit the pages of the application that contain
 * an AuthenticationModule : they must implement the callback methods.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
public abstract class PageAuthentication extends Page {

	/**
	 * Constructor.
	 * @param heading Title of the tab.
	 * @param icon Icon to add to the tab.
	 * @param owner GroupTabPanel containing this tab.
	 */
	protected PageAuthentication(String heading, IconType icon,
				GroupTabPanel owner) {
		super(heading, icon, owner);
	}

	/**
	 * Called at Java applet or JavaScript callback with the given string.
	 * @param string Keystroke sequence.
	 */
	protected static void appletCallback(String string) {}

	/**
	 * Called at Java applet or JavaScript callback with the given character.
	 * @param string Last type character.
	 */
	protected static void appletCallbackChar(String string) {}
}
