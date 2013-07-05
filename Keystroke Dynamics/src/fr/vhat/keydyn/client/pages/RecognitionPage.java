package fr.vhat.keydyn.client.pages;

import com.github.gwtbootstrap.client.ui.Paragraph;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import fr.vhat.keydyn.client.widgets.GroupTabPanel;
import fr.vhat.keydyn.client.widgets.PageRecognition;
import fr.vhat.keydyn.client.widgets.RecognitionModule;
import fr.vhat.keydyn.shared.RecognitionMode;
import fr.vhat.keydyn.shared.RecognitionReturn;

/**
 * Represent the recognition page.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
public class RecognitionPage extends PageRecognition {

	private static RecognitionPage instance;

	private static GroupTabPanel owner;
	private static RecognitionModule recognitionModule;

	/**
	 * Constructor of the recognition page.
	 */
	private RecognitionPage() {

		super("Identification", IconType.BARCODE, owner);
	}

	/**
	 * Initialize the recognition page singleton.
	 * @param owner GroupTabPanel owner of the page.
	 */
	public static void init(GroupTabPanel owner) {
		RecognitionPage.owner = owner;
	}

	/**
	 * Get the instance of the training page.
	 * @return Page object.
	 */
	public static RecognitionPage getInstance() {
		if (instance == null) {
			instance = new RecognitionPage();
		}
		return instance;
	}

	@Override
	protected Widget getContent() {

		VerticalPanel panel = new VerticalPanel();
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		panel.setWidth("800px");

		Paragraph introduction = new Paragraph();
		String introductionText = new String("Le module ci-dessous " +
				"vous permet de faire connaître au système les spécificités " +
				"de votre dynamique de frappe par la saisie successive " +
				"de courtes phrases qui vous seront dictées.");
		introduction.setText(introductionText);
		introduction.addStyleName("indent");
		panel.add(introduction);
		
		Paragraph help = new Paragraph();
		help.setText("Il est recommandé de retenir la phrase afin d'être en " +
				"mesure de la taper d'une seule traite, cela devrait " +
				"améliorer considérablement les performances du système.");
		help.addStyleName("indent");
		panel.add(help);

		recognitionModule =
				new RecognitionModule(RecognitionMode.TRAIN_MODE,
						"Module d'entraînement", this);
		panel.add(recognitionModule);

		return panel;
	}

	/**
	 * Implement the function of the parent in order to use polymorphism.
	 * @param string Callback keystroke sequence.
	 */
	@Override
	public void callback(String string) {
		RecognitionPage.recognitionCallback(string);
	}

	/**
	 * When a recognition callback is thrown : clear the text field and
	 * send the keystroke sequence to the server.
	 * @param string Callback keystroke sequence.
	 */
	private static void recognitionCallback(String string) {/*
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
	*/}

	/**
	 * Implement the function of the parent in order to use polymorphism.
	 * @param string Callback character.
	 */
	@Override
	public void callbackChar(String string) {
		RecognitionPage.recognitionCallbackChar(string);
	}

	/**
	 * When a recognition callback is thrown for a character : add this
	 * character to the text field.
	 */
	private static void recognitionCallbackChar(String string) {
		
	}

	/**
	 * Function which is executed when the application receive an answer from
	 * the server.
	 */
	public void execReturn(RecognitionReturn recognitionReturn) {/*
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

		trainingBar.refresh();
	*/}
}
