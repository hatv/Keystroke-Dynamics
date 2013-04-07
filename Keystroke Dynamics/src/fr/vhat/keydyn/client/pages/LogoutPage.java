package fr.vhat.keydyn.client.pages;

import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

import fr.vhat.keydyn.client.services.AuthenticationService;
import fr.vhat.keydyn.client.services.AuthenticationServiceAsync;
import fr.vhat.keydyn.client.widgets.GroupTabPanel;
import fr.vhat.keydyn.client.widgets.InformationPopup;
import fr.vhat.keydyn.client.widgets.Page;

/**
 * Represent the logout page.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
public class LogoutPage extends Page {

	// Services creation for RPC communication between client and server sides.
	private static AuthenticationServiceAsync authenticationService =
			GWT.create(AuthenticationService.class);

	/**
	 * Constructor of the logout tab which delete the session cookies.
	 */
	public LogoutPage(final GroupTabPanel owner) {

		super("Déconnexion", IconType.SIGNOUT, owner);

		this.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				authenticationService.logout(new AsyncCallback<Void>() {
					@Override
                    public void onFailure(Throwable caught) {
						InformationPopup popup = new InformationPopup(
								"Déconnexion", true);
						popup.setAlertType(AlertType.WARNING);
						popup.setAlertTitle("Échec de connexion au serveur.");
						popup.setAlertContent("Vérifiez votre connexion " +
								"internet.");
						popup.showAlert();
						popup.show();
						popup.hideWithDelay();
                    }
                    @Override
                    public void onSuccess(Void result) {
                    	owner.changeGroupRequested(0);
                    }
				});
			}
		});
	}

	@Override
	protected Widget getContent() {
		return null;
	}
}
