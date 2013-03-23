package fr.vhat.keydyn.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

import fr.vhat.keydyn.client.events.ChangeGroupRequestedEvent;
import fr.vhat.keydyn.client.events.ChangeGroupRequestedEventHandler;
import fr.vhat.keydyn.client.services.AuthenticationService;
import fr.vhat.keydyn.client.services.AuthenticationServiceAsync;
import fr.vhat.keydyn.client.widgets.GroupTabPanel;

/**
 * The Application class is the main class of the GUI.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
public class Application implements ChangeGroupRequestedEventHandler {

	// Services creation for RPC communication between client and server sides.
	private static AuthenticationServiceAsync authenticationService =
			GWT.create(AuthenticationService.class);

	private GroupTabPanel notConnectedGroupTabPanel;
	private GroupTabPanel connectedGroupTabPanel;
	private String divId;
	private int displayedGroupTabPanelIndex;

	public Application(String divId) {
		// Load the JSNI (JavaScript Native Interface) functions.
		this.JSNI();
		// Set the two GroupTabPanel.
		this.setConnectedGroupTabPanel(new GroupTabPanel());
		this.setNotConnectedGroupTabPanel(new GroupTabPanel());
		this.setDivId(divId);
		// Check whether the user is already connected or not.
		if (this.isValidSession()) {
			this.setDisplayedGroupTabPanelIndex(1);
		} else {
			this.setDisplayedGroupTabPanelIndex(0);
		}
	}

	private GroupTabPanel getNotConnectedGroupTabPanel() {
		return notConnectedGroupTabPanel;
	}

	private void setNotConnectedGroupTabPanel(
			GroupTabPanel notConnectedGroupTabPanel) {
		notConnectedGroupTabPanel.addChangeGroupRequestedEventHandler(this);
		this.notConnectedGroupTabPanel = notConnectedGroupTabPanel;
	}

	private GroupTabPanel getConnectedGroupTabPanel() {
		return connectedGroupTabPanel;
	}

	private void setConnectedGroupTabPanel(
			GroupTabPanel connectedGroupTabPanel) {
		connectedGroupTabPanel.addChangeGroupRequestedEventHandler(this);
		this.connectedGroupTabPanel = connectedGroupTabPanel;
	}

	private String getDivId() {
		return divId;
	}

	public void setDivId(String divId) {
		this.divId = divId;
	}

	private int getDisplayedGroupTabPanelIndex() {
		return displayedGroupTabPanelIndex;
	}

	private void setDisplayedGroupTabPanelIndex(
			int displayedGroupTabPanelIndex) {
		this.displayedGroupTabPanelIndex = displayedGroupTabPanelIndex;
		this.show();
	}

	public void show() {
		RootPanel.get(this.getDivId()).clear();
		GroupTabPanel displayedGroupTabPanel;
		if (this.getDisplayedGroupTabPanelIndex() == 0) {
			displayedGroupTabPanel = this.getNotConnectedGroupTabPanel();
		} else {
			displayedGroupTabPanel = this.getConnectedGroupTabPanel();
		}
		RootPanel.get(this.getDivId()).add(displayedGroupTabPanel);
	}

	@Override
	public void onChangeGroupRequested(ChangeGroupRequestedEvent event) {
		this.setDisplayedGroupTabPanelIndex(event.getGroupIndex());
	}

	/**
	 * Check whether a session is opened with the server.
	 * @return Session validity.
	 */
	public boolean isValidSession() {
		authenticationService.validateSession(new AsyncCallback<String>() {
			@Override
			public void onFailure(Throwable caught) {
				new ServiceFailurePopup("InitSessionValidation: " + 
						caught.getMessage()).show();
			}
			@Override
			public void onSuccess(String login) {
				/*if (login != null)
					loadUserPage(login);
				else
					loadHomePage();*/
				new ServiceFailurePopup("Le service s'est bien exécuté").show();
			}
		});
		return false;
		//loadHomePage();
	}

	/**
	 * Define the JavaScript Native functions to be used in the web application.
	 * appletCallback:
	 *  	Called from Keyboard Applet to send Keystroke Dynamics data.
	 * appletCallbackChar:
	 * 		Called from Keyboard Applet to send last typed character.
	 */
	public native void JSNI() /*-{
		$wnd.requestFocus = function() {
			$wnd.startFocus('KeyboardApplet');
		}
    	//$wnd.appletCallback = function(x) {
        //   @fr.vhat.keydyn.client.KeyDyn::appletCallback(Ljava/lang/String;)(x);
    	//}
    	//$wnd.appletCallbackChar = function(x) {
        //   @fr.vhat.keydyn.client.KeyDyn::appletCallbackChar(Ljava/lang/String;)(x);
    	//}
    }-*/;
}
