package fr.vhat.keydyn.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HTML;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class KeyDyn implements EntryPoint {
	
	static VerticalPanel container = new VerticalPanel();

	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 *//*
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);
	*/
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		this.JSNI();

		Label registeringExplanation = new Label();
		String explanationText = "To sign up, please fill out the following "
				+ "form and send it. You'll receive an email soon.";
		registeringExplanation.setText(explanationText);
		container.add(registeringExplanation);

		Grid userDataGrid = new Grid(6, 2);
		
		HorizontalPanel loginPanel = new HorizontalPanel();
		Label loginLabel = new Label("Login");
		loginPanel.add(loginLabel);
		Image loginInfo = new Image("resources/info.png");
		loginPanel.add(loginInfo);
		TextBox loginUser = new TextBox();
		userDataGrid.setWidget(0, 0, loginPanel);
		userDataGrid.setWidget(0, 1, loginUser);
		
		HorizontalPanel emailPanel = new HorizontalPanel();
		Label emailLabel = new Label("E-mail");
		emailPanel.add(emailLabel);
		Image emailInfo = new Image("resources/info.png");
		emailPanel.add(emailInfo);
		TextBox emailUser = new TextBox();
		userDataGrid.setWidget(0, 0, emailPanel);
		userDataGrid.setWidget(0, 1, emailUser);
		
		container.add(userDataGrid);

		HTML applet = new HTML();
		// TODO hide applet ?
		String HTML5Applet = new String("<object name=\"Keyboard Applet\" " +
			"type=\"application/x-java-applet\" height=\"387\" width=\"482\">" +
			"<param name=\"mayscript\" value=\"yes\">" +
			"<param name=\"scriptable\" value=\"true\">" +
	        "<param name=\"codebase\" value=\"resources\">" +
	        "<param name=\"code\" value=\"KeyboardApplet\">" +
	        "<param name=\"id\" value=\"KeyboardApplet\">" +
	        "<!--  <param name=\"archive\" value=\"KeyboardApplet.jar\"> -->" +
	        "Please accept the Java Applet or install JRE 6 at least." +
	        "</object>");
	    applet.setHTML(HTML5Applet);
	    container.add(applet);

	    HTML focusButton = new HTML();
		focusButton.setHTML("<button " +
				"onClick=\"startFocus('keyboardApplet')\">" +
				"Permanent focus</button>");
	    container.add(focusButton);

		container.addStyleName("container");

		RootPanel.get("registerContent").add(container);
	};

	public static void appletCallback(String msg) {
	    Label test = new Label(msg);
		container.add(test);
	};

	public native void JSNI() /*-{
    $wnd.appletCallback = function(x) {
        @fr.vhat.keydyn.client.KeyDyn::appletCallback(Ljava/lang/String;)(x);
    	}
    }-*/;
}


