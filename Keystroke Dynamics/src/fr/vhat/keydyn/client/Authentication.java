package fr.vhat.keydyn.client;

import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

import fr.vhat.keydyn.client.services.AuthenticationService;
import fr.vhat.keydyn.client.services.AuthenticationServiceAsync;
import fr.vhat.keydyn.client.widgets.InformationPopup;
import fr.vhat.keydyn.shared.AuthenticationMode;
import fr.vhat.keydyn.shared.AuthenticationReturn;

/**
 * Authentication functionalities.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
public class Authentication {

	private static AuthenticationReturn authenticationReturn;

	// Services creation for RPC communication between client and server sides.
	private static AuthenticationServiceAsync authenticationService =
			GWT.create(AuthenticationService.class);

	/**
	 * Authentication function : send data to the server in order to check the
	 * validity of the authentication.
	 * @param login Login of the user.
	 * @param mode Mode (0 : test, 1 : train, 2 : production).
	 * @param kdData Keystroke Dynamics data.
	 * @param giveInfo True if the server has to return information.
	 * @return AuthenticationReturn object containing the requested information.
	 */
	public static AuthenticationReturn authenticateUser(String login,
			AuthenticationMode mode, String kdData, boolean giveInfo) {

		authenticationReturn = new AuthenticationReturn();

		authenticationService.authenticateUser(login, mode, kdData, giveInfo,
				new AsyncCallback<AuthenticationReturn>() {
			@Override
            public void onFailure(Throwable caught) {
				new InformationPopup("Erreur de connexion",
						"Impossible de contacter le serveur ; vérifiez votre " +
						"connexion.", AlertType.ERROR);
            }
            @Override
            public void onSuccess(AuthenticationReturn result) {
            	authenticationReturn = result;
            	// TODO
            	// NON : il faut lancer un event : resultat reçu (asynchrone)
            	// la page login doit se mettre en écoute de cet event
            	// à la réception elle doit le gérer
            }
		});
		return authenticationReturn;
	}
}
