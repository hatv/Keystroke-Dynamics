package fr.vhat.keydyn.client;

import com.github.gwtbootstrap.client.ui.Container;
import com.github.gwtbootstrap.client.ui.FluidContainer;
import com.github.gwtbootstrap.client.ui.FluidRow;
import com.github.gwtbootstrap.client.ui.PageHeader;
import com.github.gwtbootstrap.client.ui.Row;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.TextAlign;
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
	private FluidRow contentRow;

	public Application(String divId) {
		// Load the JSNI (JavaScript Native Interface) functions.
		this.JSNI();
		// Init the two GroupTabPanel.
		this.setConnectedGroupTabPanel(new GroupTabPanel(true, 0));
		this.setNotConnectedGroupTabPanel(new GroupTabPanel(false, 0));
		this.setDivId(divId);
		// Check whether the user is already connected or not.
		if (this.isValidSession()) {
			this.setDisplayedGroupTabPanelIndex(1);
		} else {
			this.setDisplayedGroupTabPanelIndex(0);
		}
		RootPanel.get().clear();
		FluidContainer mainContainer = new FluidContainer();
		mainContainer.setStyleName("mainContainer");
		FluidRow headerRow = this.getHeaderRow();
		mainContainer.add(headerRow);
		contentRow = new FluidRow();
		mainContainer.add(contentRow);
		RootPanel.get().add(mainContainer);
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
	}

	public void show() {
		GroupTabPanel displayedGroupTabPanel;
		if (this.getDisplayedGroupTabPanelIndex() == 0) {
			displayedGroupTabPanel = this.getNotConnectedGroupTabPanel();
		} else {
			displayedGroupTabPanel = this.getConnectedGroupTabPanel();
		}
		contentRow.add(displayedGroupTabPanel);
	}

	private FluidRow getHeaderRow() {
		PageHeader title = new PageHeader();
		title.setText("Analyse de la Dynamique de Frappe");
		title.setSubtext("");
		title.getElement().getStyle().setTextAlign(TextAlign.CENTER);
		FluidRow headerRow = new FluidRow();
		headerRow.add(title);
		return headerRow;
	}

	@Override
	public void onChangeGroupRequested(ChangeGroupRequestedEvent event) {
		this.setDisplayedGroupTabPanelIndex(event.getGroupIndex());
		this.show();
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
						caught.getMessage()).showPopup();
			}
			@Override
			public void onSuccess(String login) {
				/*if (login != null)
					loadUserPage(login);
				else
					loadHomePage();*/
				new ServiceFailurePopup("Le service s'est bien exécuté")
						.showPopup();
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
