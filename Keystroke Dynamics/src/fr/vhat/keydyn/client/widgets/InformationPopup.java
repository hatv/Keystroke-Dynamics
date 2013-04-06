package fr.vhat.keydyn.client.widgets;

import com.github.gwtbootstrap.client.ui.AlertBlock;
import com.github.gwtbootstrap.client.ui.Modal;
import com.github.gwtbootstrap.client.ui.Paragraph;
import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.github.gwtbootstrap.client.ui.constants.BackdropType;
import com.google.gwt.user.client.Timer;

/**
 * Build and display a popup with a layer on the screen.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
public class InformationPopup extends Modal {

	private AlertBlock alert;
	private Paragraph paragraph;

	/**
	 * Constructor of a popup.
	 * @param title Title of the popup.
	 * @param closable If true, the popup is closable with a button and ESC.
	 */
	public InformationPopup(String title, boolean closable) {

		super(true, true);

		this.setBackdrop(BackdropType.STATIC);
		this.setCloseVisible(closable);
		this.setHideOthers(true);
		this.setKeyboard(closable);
		this.setWidth(500);
		this.setTitle(title);

		paragraph = new Paragraph();
		alert = new AlertBlock(false);
	}

	/**
	 * Constructor of a not closable popup.
	 * @param title Title of the popup.
	 */
	public InformationPopup(String title) {
		this(title, false);
	}

	/**
	 * Hide the popup after a given delay.
	 * @param delay Delay in milliseconds
	 */
	public void hideWithDelay(int delay) {
		Timer hideTimer = new Timer() {
			@Override
			public void run() {
				hide();
			}
		};
		hideTimer.schedule(delay);
	}

	/**
	 * Default value is 4 seconds.
	 */
	public void hideWithDelay() {
		this.hideWithDelay(4000);
	}

	/**
	 * Set the type of the embedded alert.
	 * @param alertType AlertType of the embedded alert.
	 */
	public void setAlertType(AlertType alertType) {
		this.alert.setType(alertType);
	}

	/**
	 * Set the content of the embedded alert.
	 * @param content HTML content of the embedded alert.
	 */
	public void setAlertContent(String content) {
		this.alert.setHTML(content);
	}

	/**
	 * Set the title of the embedded alert.
	 * @param title Title of the embedded alert.
	 */
	public void setAlertTitle(String title) {
		this.alert.setHeading(title);
	}

	/**
	 * Set the content of the embedded paragraph.
	 * @param content HTML content of the embedded paragraph.
	 */
	public void setParagraphContent(String content) {
		this.paragraph.setText(content);
	}

	/**
	 * Show the alert.
	 */
	public void showAlert() {
		this.add(alert);
	}

	/**
	 * Hide the alert.
	 */
	public void hideAlert() {
		this.remove(alert);
	}

	/**
	 * Show the paragraph.
	 */
	public void showParagraph() {
		this.insert(paragraph, 0);
	}

	/**
	 * Hide the paragraph.
	 */
	public void hideParagraph() {
		this.remove(paragraph);
	}
}
