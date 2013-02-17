package fr.vhat.keydyn.shared;

import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Store the web page management functions and data.
 * Implements the singleton design pattern.
 */
public class WebPage {

	private static WebPage instance;
	
	public static WebPage getInstance() {
		if (instance == null) {
			instance = new WebPage();
		}
		return instance;
	}

	VerticalPanel memberAreaKDDataPanel;
	
	private WebPage() {
		memberAreaKDDataPanel = new VerticalPanel();
	}
}
