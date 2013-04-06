package fr.vhat.keydyn.client.pages;

import com.github.gwtbootstrap.client.ui.Alert;
import com.github.gwtbootstrap.client.ui.Modal;
import com.github.gwtbootstrap.client.ui.Paragraph;
import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.github.gwtbootstrap.client.ui.constants.BackdropType;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import fr.vhat.keydyn.client.widgets.AuthenticationModule;
import fr.vhat.keydyn.client.widgets.GroupTabPanel;
import fr.vhat.keydyn.client.widgets.PageAuthentication;
import fr.vhat.keydyn.shared.AuthenticationMode;
import fr.vhat.keydyn.shared.AuthenticationReturn;

/**
 * Represent the registration page.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
public class LoginPage extends PageAuthentication {

	private static LoginPage instance;

	private static boolean applet;
	private static GroupTabPanel owner;
	private static AuthenticationModule authenticationModule;
	private static VerticalPanel alertPanel;
	private static AuthenticationReturn authenticationReturn;
	private static Modal authenticationPopup;

	/**
	 * Constructor of the login page.
	 * @param applet True if we want to use a Java applet, false to use simple
	 * JavaScript code.
	 */
	private LoginPage(boolean applet) {

		super("Connexion", IconType.SIGNIN, owner);

		// Specific code to execute at tab loading if we want to use the Java
		// applet : JSNI method.
		if (applet) {
			this.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					JSNI();
				}
			});
		}
	}

	/**
	 * Initialize the login page singleton.
	 * @param owner GroupTabPanel owner of the page.
	 * @param applet True to use an applet, false to use JavaScript.
	 */
	public static void init(GroupTabPanel owner, boolean applet) {
		LoginPage.owner = owner;
		LoginPage.applet = applet;
	}

	/**
	 * Get the instance of the login page.
	 * @return Page object.
	 */
	public static LoginPage getInstance() {
		if (instance == null) {
			instance = new LoginPage(LoginPage.applet);
		}
		return instance;
	}

	@Override
	protected Widget getContent() {

		VerticalPanel panel = new VerticalPanel();
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		panel.setWidth("800px");

		Paragraph introduction = new Paragraph();
		String introductionText = new String("Vous pouvez vous authentifier " +
				"de façon sécurisée grâce au module d'authentification " +
				"ci-dessous.");
		if (LoginPage.applet) {
			introductionText += " Si celui-ci n'apparaît pas, vérifiez votre" +
					" installation de Java et assurez-vous d'avoir accepté " +
					"l'exécution de l'Applet.";
		}
		introduction.setText(introductionText);
		introduction.addStyleName("indent");
		panel.add(introduction);
		
		Paragraph help = new Paragraph();
		help.setText("Pendant la frappe du mot de passe, votre dynamique de" +
				" frappe est analysée et toute faute de frappe entraîne donc" +
				" la réinitialisation de la séquence tapée.");
		help.addStyleName("indent");
		panel.add(help);

		authenticationModule =
				new AuthenticationModule(2, LoginPage.applet, this);
		addAppletHandlers();
		panel.add(authenticationModule);

		alertPanel = new VerticalPanel();
		panel.add(alertPanel);

		return panel;
	}

	/**
	 * Add elements handlers in case of Java applet.
	 */
	private static void addAppletHandlers() {
		authenticationModule.getPasswordTextBox().addFocusHandler(
			new FocusHandler() {
				@Override
				public native void onFocus(FocusEvent event) /*-{
				    $wnd.startFocus('KeyboardApplet');
				}-*/;
			});
		authenticationModule.getLoginTextBox().addFocusHandler(
			new FocusHandler() {
				@Override
				public native void onFocus(FocusEvent event) /*-{
				    $wnd.stopFocus();
				}-*/;
			});
	}

	/**
	 * Define the JavaScript Native functions used with the Applet.
	 * requestFocus:
	 *  	Give focus to the Applet.
	 * leaveFocus:
	 *  	Free focus from the Applet.
	 * appletLoaded:
	 *  	Called when the Applet is loaded in the browser.
	 * appletCallback:
	 * 		Called from Keyboard Applet to send the keystroke sequence.
	 * appletCallbackChar:
	 * 		Called from Keyboard Applet to send last typed character.
	 */
	private native void JSNI() /*-{
		$wnd.requestFocus = function() {
			$wnd.startFocus('KeyboardApplet');
		}
		$wnd.leaveFocus = function() {
			$wnd.stopFocus();
		}
		$wnd.appletLoaded = function() {
			@fr.vhat.keydyn.client.pages.LoginPage::appletLoaded()();
		}
    	$wnd.appletCallback = function(x) {
            @fr.vhat.keydyn.client.pages.LoginPage::authenticationCallback(Ljava/lang/String;)(x);
    	}
    	$wnd.appletCallbackChar = function(x) {
            @fr.vhat.keydyn.client.pages.LoginPage::authenticationCallbackChar(Ljava/lang/String;)(x);
    	}
    }-*/;

	/**
	 * When the applet is loaded : display the authentication form.
	 */
	private static void appletLoaded() {
		LoginPage.authenticationModule.displayAuthenticationForm();
	}

	/**
	 * Implement the function of the parent in order to use polymorphism.
	 * @param string Callback keystroke sequence.
	 */
	@Override
	public void callback(String string) {
		LoginPage.authenticationCallback(string);
	}

	/**
	 * When an authentication callback is thrown : clear the password field and
	 * send the keystroke sequence to the server.
	 * @param string Callback keystroke sequence.
	 */
	private static void authenticationCallback(String string) {
		if (LoginPage.applet) {
			LoginPage.authenticationModule.clearPassword();
		}
		LoginPage.authenticationModule.authenticateUser(
				LoginPage.authenticationModule.getLogin(),
				AuthenticationMode.PRODUCTION_MODE, string, false);

		authenticationPopup = new Modal(true, true);
		authenticationPopup.setBackdrop(BackdropType.STATIC);
		authenticationPopup.setCloseVisible(false);
		authenticationPopup.setHideOthers(true);
		authenticationPopup.setKeyboard(false);
		authenticationPopup.setWidth(400);
		authenticationPopup.setTitle("Vérification en cours");
		authenticationPopup.add(new Paragraph("Veuillez patienter pendant " +
				"que le serveur vérifie les informations fournies."));
		authenticationPopup.show();
	}

	/**
	 * Implement the function of the parent in order to use polymorphism.
	 * @param string Callback character.
	 */
	@Override
	public void callbackChar(String string) {
		LoginPage.authenticationCallbackChar(string);
	}

	/**
	 * When an authentication callback is thrown for a character : add a
	 * character to the password field.
	 */
	private static void authenticationCallbackChar(String string) {
		if (LoginPage.applet) {
			LoginPage.authenticationModule.addCharacterToPassword();
		}
	}

	/**
	 * Function which is executed when the application receive an answer from
	 * the server.
	 */
	public void execReturn(AuthenticationReturn authenticationReturn) {
		AlertType alertType = (authenticationReturn.isAuthenticated())
				?AlertType.SUCCESS
						:AlertType.ERROR;
		Alert alert = new Alert(authenticationReturn.toString(), alertType,
				false);
		authenticationPopup.add(alert);
		hidePopupWithDelay(2500);
	}

	/**
	 * Hide the popup after a given delay.
	 * @param delay Delay in milliseconds
	 */
	private void hidePopupWithDelay(int delay) {
		Timer hideTimer = new Timer() {
			@Override
			public void run() {
				authenticationPopup.hide();
			}
		};
		hideTimer.schedule(delay);
	}
}
