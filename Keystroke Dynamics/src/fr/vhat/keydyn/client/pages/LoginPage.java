package fr.vhat.keydyn.client.pages;

import com.github.gwtbootstrap.client.ui.Paragraph;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import fr.vhat.keydyn.client.widgets.AuthenticationModule;
import fr.vhat.keydyn.client.widgets.GroupTabPanel;
import fr.vhat.keydyn.client.widgets.Page;

/**
 * Represent the registration page.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
public class LoginPage extends Page {

	private static LoginPage instance;

	private static GroupTabPanel owner;
	private static AuthenticationModule authenticationModule;
	private VerticalPanel panel;

	/**
	 * Constructor of the login page.
	 */
	private LoginPage() {

		super("Connexion", IconType.SIGNIN, owner);

		// Specific code to execute at tab loading : JSNI method.
		this.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				JSNI();
			}
		});
	}

	/**
	 * Initialize the login page singleton.
	 * @param owner GroupTabPanel owner of the page.
	 */
	public static void init(GroupTabPanel owner) {
		LoginPage.owner = owner;
	}

	/**
	 * Get the instance of the login page.
	 * @return Page object.
	 */
	public static LoginPage getInstance() {
		if (instance == null) {
			instance = new LoginPage();
		}
		return instance;
	}

	@Override
	protected Widget getContent() {

		panel = new VerticalPanel();
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		panel.setWidth("800px");

		Paragraph introduction = new Paragraph();
		introduction.setText("Vous pouvez vous authentifier de façon " +
				"sécurisée grâce au module d'authentification ci-dessous." +
				" Si celui-ci n'apparaît pas, vérifiez votre installation" +
				" de Java et assurez-vous d'avoir accepté l'exécution de " +
				"l'Applet.");
		introduction.addStyleName("indent");
		panel.add(introduction);
		
		Paragraph help = new Paragraph();
		help.setText("Pendant la frappe du mot de passe, votre dynamique de" +
				" frappe est analysée et toute faute de frappe entraîne donc" +
				" la réinitialisation de la séquence tapée.");
		help.addStyleName("indent");
		panel.add(help);

		authenticationModule = new AuthenticationModule(2);
		authenticationModule.getPasswordTextBox().addFocusHandler(
			new FocusHandler() {
				@Override
				public native void onFocus(FocusEvent event) /*-{
				    $wnd.startFocus('KeyboardApplet');
				}-*/;
			});
		authenticationModule.getPasswordTextBox().addFocusHandler(
			new FocusHandler() {
				@Override
				public void onFocus(FocusEvent event) {
				    System.out.println("focus !!");
				}
			});
		authenticationModule.getPasswordTextBox().addBlurHandler(
			new BlurHandler() {
				@Override
				public native void onBlur(BlurEvent event) /*-{
				    $wnd.stopFocus();
				}-*/;
			});
		panel.add(authenticationModule);

		return panel;
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
            @fr.vhat.keydyn.client.pages.LoginPage::appletCallback(Ljava/lang/String;)(x);
    	}
    	$wnd.appletCallbackChar = function(x) {
            @fr.vhat.keydyn.client.pages.LoginPage::appletCallbackChar(Ljava/lang/String;)(x);
    	}
    }-*/;

	/**
	 * When the applet is loaded : display the authentication form.
	 */
	private static void appletLoaded() {
		System.out.println("appletLoaded");
		LoginPage.authenticationModule.displayAuthenticationForm();
	}

	/**
	 * When an applet callback is thrown : clear the password field.
	 */
	private static void appletCallback(String string) {
		System.out.println("callback " + string);
		LoginPage.authenticationModule.clearPassword();
	}

	/**
	 * When an applet callback is thrown for a character : add a character to
	 * the password field.
	 */
	private static void appletCallbackChar(String string) {
		System.out.println("callbackCHAR " + string);
		LoginPage.authenticationModule.addCharacterToPassword();
	}
}
