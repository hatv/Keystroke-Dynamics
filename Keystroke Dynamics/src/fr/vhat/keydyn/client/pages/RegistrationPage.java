package fr.vhat.keydyn.client.pages;

import com.github.gwtbootstrap.client.ui.Alert;
import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.ButtonGroup;
import com.github.gwtbootstrap.client.ui.ControlGroup;
import com.github.gwtbootstrap.client.ui.ControlLabel;
import com.github.gwtbootstrap.client.ui.Controls;
import com.github.gwtbootstrap.client.ui.Fieldset;
import com.github.gwtbootstrap.client.ui.HelpBlock;
import com.github.gwtbootstrap.client.ui.Legend;
import com.github.gwtbootstrap.client.ui.ListBox;
import com.github.gwtbootstrap.client.ui.Paragraph;
import com.github.gwtbootstrap.client.ui.SubmitButton;
import com.github.gwtbootstrap.client.ui.TextBox;
import com.github.gwtbootstrap.client.ui.Tooltip;
import com.github.gwtbootstrap.client.ui.WellForm;
import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.github.gwtbootstrap.client.ui.constants.ButtonType;
import com.github.gwtbootstrap.client.ui.constants.ControlGroupType;
import com.github.gwtbootstrap.client.ui.constants.FormType;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.github.gwtbootstrap.client.ui.constants.Placement;
import com.github.gwtbootstrap.client.ui.constants.ToggleType;
import com.github.gwtbootstrap.client.ui.constants.Trigger;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import fr.vhat.keydyn.client.services.RegistrationService;
import fr.vhat.keydyn.client.services.RegistrationServiceAsync;
import fr.vhat.keydyn.client.widgets.Page;
import fr.vhat.keydyn.shared.FieldVerifier;

public class RegistrationPage extends Page {

	private VerticalPanel panel;
	private ControlGroup loginControlGroup;
	private TextBox loginTextBox;
	private HelpBlock loginHelpBlock;
	private ControlGroup emailControlGroup;
	private TextBox emailTextBox;
	private ControlGroup birthYearControlGroup;
	private TextBox birthYearTextBox;
	private ControlGroup genderControlGroup;
	private Button maleButton;
	private Button femaleButton;
	private ControlGroup countryControlGroup;
	private ListBox countryList;
	private ControlGroup computerExperienceControlGroup;
	private ListBox computerExperienceList;
	private ControlGroup typingUsageControlGroup;
	private ListBox typingUsageList;
	private VerticalPanel alertPanel;

	// Services creation for RPC communication between client and server sides.
	private static RegistrationServiceAsync registrationService =
			GWT.create(RegistrationService.class);

	public RegistrationPage() {
		super("Inscription", IconType.OK);
	}

	protected Widget getContent() {
		this.generatePage();
		this.generateHandlers();
		return panel;
	}

	private void generatePage() {
		panel = new VerticalPanel();
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

		Paragraph registrationDirections = new Paragraph("Pour vous inscrire," +
				"veuillez remplir le formulaire ci-dessous : vous recevrez " +
				"votre mot de passe par courriel dans les plus brefs délais. " +
				"N'oubliez pas de vérifier votre boîte de courrier " +
				"indésirable.");
		panel.add(registrationDirections);
		panel.setCellHorizontalAlignment(
				registrationDirections,HasHorizontalAlignment.ALIGN_JUSTIFY);

		WellForm registrationForm = new WellForm();
		registrationForm.setType(FormType.HORIZONTAL);
		panel.add(registrationForm);
		Fieldset fieldset = new Fieldset();
		registrationForm.add(fieldset);
		Legend registrationLegend = new Legend("Formulaire d'inscription");
		fieldset.add(registrationLegend);

		loginControlGroup = new ControlGroup();
		fieldset.add(loginControlGroup);
		ControlLabel loginLabel = new ControlLabel("Identifiant");
		loginControlGroup.add(loginLabel);
		Controls loginControl = new Controls();
		loginControlGroup.add(loginControl);
		loginTextBox = new TextBox();
		loginTextBox.setMaxLength(13);
		loginControl.add(loginTextBox);
		loginHelpBlock = new HelpBlock();
		loginHelpBlock.setVisible(false);
		loginControl.add(loginHelpBlock);
		Tooltip loginTooltip =
				new Tooltip("Entre 5 et 13 caractères minuscules uniquement.");
		loginTooltip.setWidget(loginTextBox);
		loginTooltip.setTrigger(Trigger.FOCUS);
		loginTooltip.setPlacement(Placement.RIGHT);
		loginTooltip.reconfigure();

		emailControlGroup = new ControlGroup();
		fieldset.add(emailControlGroup);
		ControlLabel emailLabel = new ControlLabel("Courriel");
		emailControlGroup.add(emailLabel);
		Controls emailControl = new Controls();
		emailControlGroup.add(emailControl);
		emailTextBox = new TextBox();
		emailControl.add(emailTextBox);
		Tooltip emailTooltip =
				new Tooltip("Adresse courriel valide pour la réception du mot" +
						" de passe. Vous ne recevrez aucun courriel " +
						"indésirable.");
		emailTooltip.setWidget(emailTextBox);
		emailTooltip.setTrigger(Trigger.FOCUS);
		emailTooltip.setPlacement(Placement.RIGHT);
		emailTooltip.reconfigure();

		birthYearControlGroup = new ControlGroup();
		fieldset.add(birthYearControlGroup);
		ControlLabel birthYearLabel = new ControlLabel("Année de naissance");
		birthYearControlGroup.add(birthYearLabel);
		Controls birthYearControl = new Controls();
		birthYearControlGroup.add(birthYearControl);
		birthYearTextBox = new TextBox();
		birthYearTextBox.setMaxLength(4);
		birthYearControl.add(birthYearTextBox);

		genderControlGroup = new ControlGroup();
		fieldset.add(genderControlGroup);
		genderControlGroup.setType(ControlGroupType.ERROR);
		ControlLabel genderLabel = new ControlLabel("Sexe");
		genderControlGroup.add(genderLabel);
		Controls genderControl = new Controls();
		genderControlGroup.add(genderControl);
		maleButton = new Button("Homme");
		femaleButton = new Button("Femme");
		ButtonGroup genderRadio = new ButtonGroup(maleButton, femaleButton);
		genderRadio.setToggle(ToggleType.RADIO);
		genderControl.add(genderRadio);

		countryControlGroup = new ControlGroup();
		fieldset.add(countryControlGroup);
		ControlLabel countryLabel = new ControlLabel("Pays");
		countryControlGroup.add(countryLabel);
		Controls countryControl = new Controls();
		countryControlGroup.add(countryControl);
		countryList = new ListBox();
		countryList.addItem("Sélectionnez un pays...");
		countryList.addItem("France");
		countryList.addItem("Canada");
		countryList.addItem("Belgique");
		countryList.addItem("Suisse");
		countryList.addItem("États-Unis");
		countryList.addItem("Royaume-Uni");
		countryList.addItem("Autre pays d'Europe");
		countryList.addItem("Pays d'Afrique");
		countryList.addItem("Pays d'Asie");
		countryList.addItem("Autre pays");
		countryControl.add(countryList);
		Tooltip countryTooltip = new Tooltip("Pays dans lequel vous avez " + 
					"passé  la majeure partie de votre vie.");
		countryTooltip.setWidget(countryList);
		countryTooltip.setTrigger(Trigger.FOCUS);
		countryTooltip.setPlacement(Placement.RIGHT);
		countryTooltip.reconfigure();

		computerExperienceControlGroup = new ControlGroup();
		fieldset.add(computerExperienceControlGroup);
		ControlLabel computerExperienceLabel =
				new ControlLabel("Expérience informatique");
		computerExperienceControlGroup.add(computerExperienceLabel);
		Controls computerExperienceControl = new Controls();
		computerExperienceControlGroup.add(computerExperienceControl);
		computerExperienceList = new ListBox();
		computerExperienceList.addItem(
				"Sélectionnez une durée...");
		computerExperienceList.addItem("< 2 ans");
		computerExperienceList.addItem("~ 4 ans");
		computerExperienceList.addItem("~ 7 ans");
		computerExperienceList.addItem("~ 10 ans");
		computerExperienceList.addItem("> 13 ans");
		computerExperienceControl.add(computerExperienceList);
		Tooltip computerExperienceTooltip = new Tooltip("Années depuis " +
				"lesquelles vous utilisez régulièrement l'informatique.");
		computerExperienceTooltip.setWidget(computerExperienceList);
		computerExperienceTooltip.setTrigger(Trigger.FOCUS);
		computerExperienceTooltip.setPlacement(Placement.RIGHT);
		computerExperienceTooltip.reconfigure();

		typingUsageControlGroup = new ControlGroup();
		typingUsageControlGroup.setType(ControlGroupType.ERROR);
		fieldset.add(typingUsageControlGroup);
		ControlLabel typingUsageLabel =
				new ControlLabel("Utilisation du clavier");
		typingUsageControlGroup.add(typingUsageLabel);
		Controls typingUsageControl = new Controls();
		typingUsageControlGroup.add(typingUsageControl);
		typingUsageList = new ListBox();
		typingUsageList.addItem(
				"Sélectionnez une durée...");
		typingUsageList.addItem("< 30 minutes par jour");
		typingUsageList.addItem("~ 1 heure par jour");
		typingUsageList.addItem("~ 2 heures par jour");
		typingUsageList.addItem("~ 4 heures par jour");
		typingUsageList.addItem("> 5 heures par jour");
		typingUsageControl.add(typingUsageList);
		Tooltip typingUsageTooltip = new Tooltip("Nombre moyen d'heures " +
				"passées quotidiennement à faire de la saisie sur un clavier.");
		typingUsageTooltip.setWidget(typingUsageList);
		typingUsageTooltip.setTrigger(Trigger.FOCUS);
		typingUsageTooltip.setPlacement(Placement.RIGHT);
		typingUsageTooltip.reconfigure();

		SubmitButton submitButton = new SubmitButton("S'inscrire");
		submitButton.setType(ButtonType.SUCCESS);
		registrationForm.add(submitButton);

		alertPanel = new VerticalPanel();
		panel.add(alertPanel);
	}

	private void generateHandlers() {
		// Check login validity and availability on change event
		loginTextBox.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				checkLogin();
			}
		});
	}

	private void checkLogin() {
		String login = loginTextBox.getText();
		if (login.equals("")) {
			loginControlGroup.setType(ControlGroupType.WARNING);
			loginHelpBlock.setText(
					"Le choix d'un identifiant est obligatoire.");
			loginHelpBlock.setVisible(true);
		} else if (!FieldVerifier.isValidLogin(login)) {
			loginControlGroup.setType(ControlGroupType.ERROR);
			loginHelpBlock.setText(
					"L'identifiant doit être constitué de 5 à 13 " +
					"caractères minuscules uniquement.");
			loginHelpBlock.setVisible(true);
		} else {
			loginControlGroup.setType(ControlGroupType.WARNING);
			loginHelpBlock.setText(
					"Vérification de la disponibilité de " +
					"l'identifiant.");
			loginHelpBlock.setVisible(true);
			checkLoginAvailability(login);
		}
	}

	private void checkLoginAvailability(String login) {
		registrationService.checkLoginAvailability(login,
			new AsyncCallback<Boolean>() {
				@Override
				public void onFailure(Throwable caught) {
					Alert alert = new Alert(
							"CheckLoginAvailability" + caught.getMessage(),
							AlertType.WARNING,
							true);
					alert.setHeading("Échec de connexion");
					alertPanel.add(alert);
				}
				@Override
				public void onSuccess(Boolean available) {
					if (available) {
						loginControlGroup.setType(ControlGroupType.SUCCESS);
						loginHelpBlock.setText("L'identifiant est disponible.");
						loginHelpBlock.setVisible(false);
					} else {
						loginControlGroup.setType(ControlGroupType.ERROR);
						loginHelpBlock.setText(
								"Cet identifiant est déjà utilisé.");
						loginHelpBlock.setVisible(true);
					}
				}
			});
	}
}
