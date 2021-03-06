package fr.vhat.keydyn.client.pages;

import com.github.gwtbootstrap.client.ui.Paragraph;
import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import fr.vhat.keydyn.client.widgets.AuthenticationModule;
import fr.vhat.keydyn.client.widgets.GroupTabPanel;
import fr.vhat.keydyn.client.widgets.InformationPopup;
import fr.vhat.keydyn.client.widgets.PageAuthentication;
import fr.vhat.keydyn.client.widgets.TrainingProgressBar;
import fr.vhat.keydyn.shared.AuthenticationMode;
import fr.vhat.keydyn.shared.AuthenticationReturn;

/**
 * Represent the training page.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
public class TrainingPage extends PageAuthentication {

	private static TrainingPage instance;

	private static boolean applet;
	private static GroupTabPanel owner;
	private static AuthenticationModule trainingModule;
	private static AuthenticationReturn trainingReturn;
	private static InformationPopup trainingPopup;
	private static TrainingProgressBar trainingBar;

	/**
	 * Constructor of the training page.
	 * @param applet True if we want to use a Java applet, false to use simple
	 * JavaScript code.
	 */
	private TrainingPage(boolean applet) {

		super("Entraînement", IconType.BOOK, owner);

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

		this.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				trainingBar.refresh();
			}
		});
	}

	/**
	 * Initialize the training page singleton.
	 * @param owner GroupTabPanel owner of the page.
	 * @param applet True to use an applet, false to use JavaScript.
	 */
	public static void init(GroupTabPanel owner, boolean applet) {
		TrainingPage.owner = owner;
		TrainingPage.applet = applet;
	}

	/**
	 * Get the instance of the training page.
	 * @return Page object.
	 */
	public static TrainingPage getInstance() {
		if (instance == null) {
			instance = new TrainingPage(TrainingPage.applet);
		}
		return instance;
	}

	@Override
	protected Widget getContent() {

		VerticalPanel panel = new VerticalPanel();
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		panel.setWidth("800px");

		Paragraph introduction = new Paragraph();
		String introductionText = new String("À l'aide du module ci-dessous," +
				"vous pouvez entraîner le système. Toutes les frappes " +
				"comportant le bon mot de passe seront automatiquement " +
				"enregistrées dans le système d'apprentissage. " +
				"Il est donc très IMPORTANT que seul le propriétaire " +
				"de CE compte n'utilise le module ci-dessous, sans " +
				"quoi le système sera mal calibré. Pour réaliser des " +
				"tests avec votre entourage, rendez-vous sur la page " +
				"approppriée.");
		if (TrainingPage.applet) {
			introductionText += " Si le module n'apparaît pas, vérifiez votre" +
					" installation de Java et assurez-vous d'avoir accepté " +
					"l'exécution de l'Applet.";
		}
		introduction.setText(introductionText);
		introduction.addStyleName("indent");
		panel.add(introduction);
		
		Paragraph help = new Paragraph();
		help.setText("Pendant la frappe du mot de passe, votre dynamique de" +
				" frappe est analysée et toute faute de frappe entraîne donc" +
				" la réinitialisation de la séquence tapée. La touche " +
				"<Entrée> de votre clavier vous permet de valider.");
		help.addStyleName("indent");
		panel.add(help);

		Paragraph more = new Paragraph();
		more.setText("Au cours de l'apprentissage, certaines de vos entrées " +
				"seront refusées par le système. Pas d'inquiète cependant : " +
				"cela signifie simplement que l'authentification vous aurait " +
				"été refusée en temps normal. Mais cette page " +
				"d'entraînement vous permet justement de faire " +
				"connaître au système votre dynamique de frappe et " +
				"vous devriez peu à peu voir toutes vos saisies acceptées.");
		more.addStyleName("indent");
		panel.add(more);

		trainingModule =
				new AuthenticationModule(AuthenticationMode.TRAIN_MODE,
						TrainingPage.applet, "Module d'entraînement", this);
		if (TrainingPage.applet) {
			addAppletHandlers();
		}
		panel.add(trainingModule);

		Paragraph statistics = new Paragraph();
		statistics.setText("La distance qui s'affiche est un critère " +
				"calculé par le système pour déterminer la proximité " +
				"entre votre dernière saisie et vos habitudes de frappe " +
				"habituelles. Si cette distance est faible (inférieure " +
				"au seuil), le système considèrera que vous êtes bien à " +
				"l'origine de la saisie. Attention : ce critère de distance " +
				"n'est pas lié à votre vitesse de frappe !");
		statistics.addStyleName("indent");
		panel.add(more);

		Paragraph progressBar = new Paragraph();
		progressBar.setText("La fiabilité du système augmente au fur et à " +
				"mesure que vous l'entraînez :");
		progressBar.addStyleName("indent");
		panel.add(progressBar);

		trainingBar = new TrainingProgressBar();
		panel.add(trainingBar);

		Paragraph progressBarExplain = new Paragraph();
		progressBarExplain.setText("Une fois cette barre remplie, " +
				"vous pouvez considérer que le système connait suffisamment " +
				"votre dynamique de frappe. Vous pouvez cependant " +
				"l'entrainer davantage afin d'accroitre encore sa sécurité.");
		progressBarExplain.addStyleName("indent");
		panel.add(progressBar);

		return panel;
	}

	/**
	 * Add elements handlers in case of Java applet.
	 */
	private static void addAppletHandlers() {
		trainingModule.getPasswordTextBox().addFocusHandler(
			new FocusHandler() {
				@Override
				public native void onFocus(FocusEvent event) /*-{
				    $wnd.startFocus('KeyboardApplet');
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
			@fr.vhat.keydyn.client.pages.TrainingPage::appletLoaded()();
		}
    	$wnd.appletCallback = function(x) {
            @fr.vhat.keydyn.client.pages.TrainingPage::authenticationCallback(Ljava/lang/String;)(x);
    	}
    	$wnd.appletCallbackChar = function(x) {
            @fr.vhat.keydyn.client.pages.TrainingPage::authenticationCallbackChar(Ljava/lang/String;)(x);
    	}
    }-*/;

	/**
	 * When the applet is loaded : display the authentication form.
	 */
	private static void appletLoaded() {
		TrainingPage.trainingModule.displayAuthenticationForm();
	}

	/**
	 * Implement the function of the parent in order to use polymorphism.
	 * @param string Callback keystroke sequence.
	 */
	@Override
	public void callback(String string) {
		TrainingPage.authenticationCallback(string);
	}

	/**
	 * When an authentication callback is thrown : clear the password field and
	 * send the keystroke sequence to the server.
	 * @param string Callback keystroke sequence.
	 */
	private static void authenticationCallback(String string) {
		if (TrainingPage.applet) {
			TrainingPage.trainingModule.clearPassword();
		}
		TrainingPage.trainingModule.authenticateUser("",
				AuthenticationMode.TRAIN_MODE, string, true);

		trainingPopup =
				new InformationPopup("Vérification en cours", true);
		trainingPopup.setParagraphContent(
				"Veuillez patienter pendant que le serveur vérifie les " +
				"informations fournies.");
		trainingPopup.showParagraph();
		trainingPopup.show();
	}

	/**
	 * Implement the function of the parent in order to use polymorphism.
	 * @param string Callback character.
	 */
	@Override
	public void callbackChar(String string) {
		TrainingPage.authenticationCallbackChar(string);
	}

	/**
	 * When an authentication callback is thrown for a character : add a
	 * character to the password field.
	 */
	private static void authenticationCallbackChar(String string) {
		if (TrainingPage.applet) {
			TrainingPage.trainingModule.addCharacterToPassword();
		}
	}

	/**
	 * Function which is executed when the application receive an answer from
	 * the server.
	 */
	public void execReturn(AuthenticationReturn authenticationReturn) {
		if (authenticationReturn == null) {
			trainingPopup.setAlertTitle("Échec de connexion au serveur.");
			trainingPopup.setAlertContent(
					"Vérifiez votre connexion internet.");
			trainingPopup.setAlertType(AlertType.WARNING);
		} else {
			AlertType alertType = (authenticationReturn.isAuthenticated())
					?AlertType.SUCCESS
							:AlertType.ERROR;
			trainingPopup.setAlertTitle(
					authenticationReturn.getStringTitle());
			trainingPopup.setAlertContent(
					authenticationReturn.getStringContent());
			trainingPopup.setAlertType(alertType);
		}
		trainingPopup.showAlert();
		trainingPopup.hideWithDelay(3000);

		TrainingPage.owner.refreshTabs();
	}

	public void refresh() {
		trainingBar.refresh();
	}
}
