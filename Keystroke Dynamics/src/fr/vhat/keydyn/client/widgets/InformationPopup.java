package fr.vhat.keydyn.client.widgets;

import com.github.gwtbootstrap.client.ui.Modal;
import com.github.gwtbootstrap.client.ui.constants.BackdropType;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Widget;

/**
 * Build and display a popup with a layer on the screen.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
public class InformationPopup extends Modal {

	/**
	 * Constructor.
	 * @param closable If true, the popup is closable with a button and ESC.
	 * @param title Title of the popup.
	 * @param content Content of the popup.
	 */
	public InformationPopup(String title, Widget content, boolean closable) {
		super(true, true);
		this.setBackdrop(BackdropType.STATIC);
		this.setCloseVisible(closable);
		this.setHideOthers(true);
		this.setKeyboard(closable);
		this.setWidth(400);
		this.setTitle(title);
		this.add(content);
	}

	/**
	 * Constructor : not closable.
	 * @param title Title of the popup.
	 * @param content Content of the popup.
	 */
	public InformationPopup(String title, Widget content) {
		this(title, content, false);
	}

	/**
	 * Hide the popup after a given delay.
	 * @param delay Delay in milliseconds
	 */
	public void hidePopupWithDelay(int delay) {
		Timer hideTimer = new Timer() {
			@Override
			public void run() {
				hide();
			}
		};
		hideTimer.schedule(delay);
	}
}
