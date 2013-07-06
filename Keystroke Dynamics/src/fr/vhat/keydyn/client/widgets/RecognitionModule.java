package fr.vhat.keydyn.client.widgets;

import java.util.LinkedList;
import java.util.List;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.ControlGroup;
import com.github.gwtbootstrap.client.ui.ControlLabel;
import com.github.gwtbootstrap.client.ui.Controls;
import com.github.gwtbootstrap.client.ui.Fieldset;
import com.github.gwtbootstrap.client.ui.HelpBlock;
import com.github.gwtbootstrap.client.ui.Label;
import com.github.gwtbootstrap.client.ui.Legend;
import com.github.gwtbootstrap.client.ui.TextBox;
import com.github.gwtbootstrap.client.ui.WellForm;
import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.github.gwtbootstrap.client.ui.constants.ButtonType;
import com.github.gwtbootstrap.client.ui.constants.FormType;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsDate;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import fr.vhat.keydyn.client.services.RecognitionService;
import fr.vhat.keydyn.client.services.RecognitionServiceAsync;

/**
 * Recognition module is a form which can recognize an user according to
 * his keystroke dynamics.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
public class RecognitionModule extends VerticalPanel {

	private PageRecognition owner;
	private WellForm recognitionForm;
	private Label sourceSentence;
	private TextBox answerTextBox;
	private Button submitButton;

	// JavaScript parameters
	private double firstTimestamp = 0;
	private List<Integer> releasedTable = new LinkedList<Integer>();
	private List<Integer> pressedTable = new LinkedList<Integer>();
	private List<Character> characters = new LinkedList<Character>();
	private String string = new String();

	// Services creation for RPC communication between client and server sides.
	private static RecognitionServiceAsync recognitionService =
			GWT.create(RecognitionService.class);

	/**
	 * Constructor.
	 * @param recognitionMode Mode among the two RecognitionMode
	 * available.
	 * @param legend Legend to display above the module.
	 * @param owner Owner page of the recognition module.
	 */
	public RecognitionModule(String legend, PageRecognition owner) {

		this.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

		this.recognitionForm = this.getRecognitionForm(legend);
		this.owner = owner;

		this.changeSentence();
		this.displayRecognitionForm();
		this.addJSHandlers();

		VerticalPanel alertPanel = new VerticalPanel();
		this.add(alertPanel);
	}

	/**
	 * Build the recognition form on demand.
	 * @param legend Legend to display above the module.
	 * @return Recognition form.
	 */
	private WellForm getRecognitionForm(String legend) {

		WellForm recognitionForm = new WellForm();
		recognitionForm.setType(FormType.HORIZONTAL);
		Fieldset fieldset = new Fieldset();
		recognitionForm.add(fieldset);
		Legend recognitionLegend = new Legend(legend);
		fieldset.add(recognitionLegend);

		ControlGroup sourceSentenceControlGroup;
		ControlLabel sourceSentenceLabel;
		Controls sourceSentenceControl;
		sourceSentenceControlGroup = new ControlGroup();
		fieldset.add(sourceSentenceControlGroup);
		sourceSentenceLabel = new ControlLabel("Phrase");
		sourceSentenceControlGroup.add(sourceSentenceLabel);
		sourceSentenceControl = new Controls();
		sourceSentenceControlGroup.add(sourceSentenceControl);
		sourceSentence = new Label("En cours de chargement");
		sourceSentenceControl.add(sourceSentence);

		ControlGroup answerControlGroup = new ControlGroup();
		fieldset.add(answerControlGroup);
		ControlLabel answerLabel = new ControlLabel("Votre saisie");
		answerControlGroup.add(answerLabel);
		Controls answerControl = new Controls();
		answerControlGroup.add(answerControl);
		answerTextBox = new TextBox();
		answerControl.add(answerTextBox);
		HelpBlock answerHelpBlock = new HelpBlock();
		answerHelpBlock.setVisible(false);
		answerControl.add(answerHelpBlock);

		HorizontalPanel buttonsPanel = new HorizontalPanel();
		submitButton = new Button("Valider");
		submitButton.setType(ButtonType.SUCCESS);
		submitButton.addStyleName("buttonsPanel");
		buttonsPanel.add(submitButton);
		
		recognitionForm.add(buttonsPanel);

		return recognitionForm;
	}

	/**
	 * Display the recognition form.
	 */
	public void displayRecognitionForm() {
		this.insert(this.recognitionForm, 0);
	}

	/**
	 * Clear the answer field.
	 */
	public void clearAnswer() {
		answerTextBox.setText("");
	}

	/**
	 * Give the answer text box object in order to handle events on it.
	 * @return Answer text box.
	 */
	public TextBox getAnswerTextBox() {
		return this.answerTextBox;
	}

	/**
	 * Handlers added in case of JavaScript use.
	 */
	private void addJSHandlers() {

		this.answerTextBox.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				char c = event.getCharCode();
				if ((c >= 'a' && c <= 'z') || c == ' ') {
					JsDate date = JsDate.create();
					double time = date.getTime();
					if (firstTimestamp == 0) {
						firstTimestamp = time;
					}
					pressedTable.add((int)(time - firstTimestamp));
					characters.add(c);
					string = string + c;
					owner.callbackChar(Character.toString(c));
				}
			}
		});

		this.answerTextBox.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (pressedTable.size() != 0) {
					int code = event.getNativeKeyCode();
					if ((code >= 65 && code <= 90) || code == 32) {
						JsDate date = JsDate.create();
						double time = date.getTime();
						releasedTable.add((int)(time - firstTimestamp));
					}
					else if (code == 13) {
						sendDataAndReset();
					} else {
						reset();
					}
				}
			}
		});

		submitButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				sendDataAndReset();
			}
		});
	}

	/**
	 * Send the keystroke dynamics data to the owner page and reset fields.
	 */
	void sendDataAndReset() {
		// System.out.println("chars    " + characters.toString());
		// System.out.println("pressed  " + pressedTable.toString());
		// System.out.println("released " + releasedTable.toString());
		owner.callback(string + ";" + pressedTable.toString()
				+ ";" + releasedTable.toString());
		// If the string is correct, we purpose a new string to the user
		if (string == sourceSentence.getText()) {
			this.changeSentence();
		}
		reset();
	}

	/**
	 * Reset each field to the initial state.
	 */
	void reset() {
		firstTimestamp = 0;
		characters.clear();
		string = "";
		pressedTable.clear();
		releasedTable.clear();
		answerTextBox.setText("");
	}

	/**
	 * Get a new sentence from the server to display it.
	 */
	void changeSentence() {
		recognitionService.getRandomSentence(new AsyncCallback<String>() {
			@Override
            public void onFailure(Throwable caught) {
				InformationPopup popup = new InformationPopup(
						"Récupération d'une phrase", true);
				popup.setAlertType(AlertType.WARNING);
				popup.setAlertTitle("Échec de connexion au serveur.");
				popup.setAlertContent("Vérifiez votre connexion internet.");
				popup.showAlert();
				popup.show();
				popup.hideWithDelay();
            }
            @Override
            public void onSuccess(String sentence) {
            	sourceSentence.setText(sentence);
            }
		});
	}
}
