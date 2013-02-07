package com.victorhatinguais.keystrokedynamics.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HTML;

/*
  var _this = this; 
	$wnd.appletCallback = function(pParam) { 
	   	alert(pParam);
	}	 
 */
//this@com.app.MyClass::appletCallback( Ljava/lang/String;){pParam);
	

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Keystroke_Dynamics implements EntryPoint {
	static VerticalPanel container = new VerticalPanel();
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 *//*
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";
*/
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
		this.export();
		Label userLoginText = new Label("Login :");
		TextBox userLogin = new TextBox();
		HorizontalPanel loginPanel = new HorizontalPanel();
		loginPanel.add(userLoginText);
		loginPanel.add(userLogin);

		Label userMailText = new Label("E-mail :");
		TextBox userMail = new TextBox();
		HorizontalPanel mailPanel = new HorizontalPanel();
		mailPanel.add(userMailText);
		mailPanel.add(userMail);
		
		Label userPasswordText = new Label("Password :");
		TextBox userPassword = new TextBox();
		HorizontalPanel passwordPanel = new HorizontalPanel();
		passwordPanel.add(userPasswordText);
		passwordPanel.add(userPassword);
		
		Button createButton = new Button("Create user");
		createButton.addStyleName("button");

		container.add(loginPanel);
		container.add(mailPanel);
		container.add(passwordPanel);
		container.add(createButton);
		HTML applet = new HTML();
	    applet.setHTML("<object classid=\"clsid:CAFEEFAC-0015-0000-0000-ABCDEFFEDCBA\"><param name=\"mayscript\" value=\"yes\"><param name=\"scriptable\" value=\"true\"><param name=\"code\" value=\"Keyboard1.class\"><comment><embed id=\"keyboard1\" code=\"Keyboard1.class\" type=\"application/x-java-applet;jpi-version=1.6.0\"><noembed>No Java Support.</noembed></embed></comment></object>");
	    // name, code, width, height to define (sizes : is this possible to hide totally ?)
	    container.add(applet);
	    HTML button1 = new HTML();
	    HTML button2 = new HTML();
	    button1.setHTML("<button onClick=\"document.getElementById('keyboard1').requestFocus()\">focus on applet</button>");
		button2.setHTML("<button onClick=\"startFocus('keyboard1')\">permanent focus</button>");
	    container.add(button1);
	    container.add(button2);
		container.addStyleName("container");

		RootPanel.get("content").add(container);
	};

	public static void appletCallback(String msg) {
	    Label test = new Label(msg);
		container.add(test);
	};

	public native void export() /*-{
    $wnd.appletCallback = function(x) {
        @com.victorhatinguais.keystrokedynamics.client.Keystroke_Dynamics::appletCallback(Ljava/lang/String;)(x);
    	}
    }-*/;

	/*
		final Button sendButton = new Button("Send");
		final TextBox nameField = new TextBox();
		nameField.setText("GWT User");
		final Label errorLabel = new Label();

		// We can add style names to widgets
		sendButton.addStyleName("sendButton");

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("nameFieldContainer").add(nameField);
		RootPanel.get("sendButtonContainer").add(sendButton);
		RootPanel.get("errorLabelContainer").add(errorLabel);

		// Focus the cursor on the name field when the app loads
		nameField.setFocus(true);
		nameField.selectAll();

		// Create the popup dialog box
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		final Button closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		final Label textToServerLabel = new Label();
		final HTML serverResponseLabel = new HTML();
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
		dialogVPanel.add(textToServerLabel);
		dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);
*//*
		// Add a handler to close the DialogBox
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
				sendButton.setEnabled(true);
				sendButton.setFocus(true);
			}
		});
*//*
		// Create a handler for the sendButton and nameField
		class MyHandler implements ClickHandler, KeyUpHandler {
			/**
			 * Fired when the user clicks on the sendButton.
			 */
	/*		public void onClick(ClickEvent event) {
				sendNameToServer();
			}*/

			/**
			 * Fired when the user types in the nameField.
			 */
		/*	public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					sendNameToServer();
				}
			}
*/
			/**
			 * Send the name from the nameField to the server and wait for a response.
			 */
/*			private void sendNameToServer() {
				// First, we validate the input.
				errorLabel.setText("");
				String textToServer = nameField.getText();
				if (!FieldVerifier.isValidName(textToServer)) {
					errorLabel.setText("Please enter at least four characters");
					return;
				}

				// Then, we send the input to the server.
				sendButton.setEnabled(false);
				textToServerLabel.setText(textToServer);
				serverResponseLabel.setText("");
				greetingService.greetServer(textToServer,
						new AsyncCallback<String>() {
							public void onFailure(Throwable caught) {
								// Show the RPC error message to the user
								dialogBox
										.setText("Remote Procedure Call - Failure");
								serverResponseLabel
										.addStyleName("serverResponseLabelError");
								serverResponseLabel.setHTML(SERVER_ERROR);
								dialogBox.center();
								closeButton.setFocus(true);
							}

							public void onSuccess(String result) {
								dialogBox.setText("Remote Procedure Call");
								serverResponseLabel
										.removeStyleName("serverResponseLabelError");
								serverResponseLabel.setHTML(result);
								dialogBox.center();
								closeButton.setFocus(true);
							}
						});
			}
		}
*//*
		// Add a handler to send the name to the server
		MyHandler handler = new MyHandler();
		sendButton.addClickHandler(handler);
		nameField.addKeyUpHandler(handler);
	}*/
}


