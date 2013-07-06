package fr.vhat.keydyn.client.pages;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.ControlGroup;
import com.github.gwtbootstrap.client.ui.ControlLabel;
import com.github.gwtbootstrap.client.ui.Controls;
import com.github.gwtbootstrap.client.ui.Fieldset;
import com.github.gwtbootstrap.client.ui.HelpBlock;
import com.github.gwtbootstrap.client.ui.Legend;
import com.github.gwtbootstrap.client.ui.ListBox;
import com.github.gwtbootstrap.client.ui.Paragraph;
import com.github.gwtbootstrap.client.ui.TextBox;
import com.github.gwtbootstrap.client.ui.Tooltip;
import com.github.gwtbootstrap.client.ui.WellForm;
import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.github.gwtbootstrap.client.ui.constants.ButtonType;
import com.github.gwtbootstrap.client.ui.constants.ControlGroupType;
import com.github.gwtbootstrap.client.ui.constants.FormType;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.github.gwtbootstrap.client.ui.constants.Placement;
import com.github.gwtbootstrap.client.ui.constants.Trigger;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import fr.vhat.keydyn.client.services.RegistrationService;
import fr.vhat.keydyn.client.services.RegistrationServiceAsync;
import fr.vhat.keydyn.client.widgets.GroupTabPanel;
import fr.vhat.keydyn.client.widgets.InformationPopup;
import fr.vhat.keydyn.client.widgets.Page;
import fr.vhat.keydyn.shared.FieldVerifier;

/**
 * Represent the registration page.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
public class RegistrationPage extends Page {

	private VerticalPanel panel;
	private ControlGroup loginControlGroup;
	private TextBox loginTextBox;
	private HelpBlock loginHelpBlock;
	private ControlGroup emailControlGroup;
	private TextBox emailTextBox;
	private HelpBlock emailHelpBlock;
	private ControlGroup birthYearControlGroup;
	private TextBox birthYearTextBox;
	private HelpBlock birthYearHelpBlock;
	private ControlGroup genderControlGroup;
	private ListBox genderList;
	private HelpBlock genderHelpBlock;
	private ControlGroup countryControlGroup;
	private ListBox countryList;
	private HelpBlock countryHelpBlock;
	private ControlGroup computerExperienceControlGroup;
	private ListBox computerExperienceList;
	private HelpBlock computerExperienceHelpBlock;
	private ControlGroup typingUsageControlGroup;
	private ListBox typingUsageList;
	private HelpBlock typingUsageHelpBlock;
	private Button submitButton;
	private VerticalPanel alertPanel;

	// Services creation for RPC communication between client and server sides.
	private static RegistrationServiceAsync registrationService =
			GWT.create(RegistrationService.class);

	/**
	 * Constructor of the registration page.
	 */
	public RegistrationPage(GroupTabPanel owner) {
		super("Inscription", IconType.OK, owner);
	}

	@Override
	protected Widget getContent() {
		this.generatePage();
		this.generateHandlers();
		return panel;
	}

	/**
	 * Build the form.
	 */
	private void generatePage() {

		panel = new VerticalPanel();
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

		Paragraph registrationDirections = new Paragraph("Pour vous inscrire," +
				" veuillez remplir le formulaire ci-dessous : vous recevrez " +
				"votre mot de passe par courriel dans les plus brefs délais. " +
				"N'oubliez pas de vérifier votre boîte de courrier " +
				"indésirable.");
		registrationDirections.addStyleName("indent");
		panel.add(registrationDirections);

		WellForm registrationForm = new WellForm();
		registrationForm.addStyleName("registrationForm");
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
		emailHelpBlock = new HelpBlock();
		emailHelpBlock.setVisible(false);
		emailControl.add(emailHelpBlock);
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
		birthYearHelpBlock = new HelpBlock();
		birthYearHelpBlock.setVisible(false);
		birthYearControl.add(birthYearHelpBlock);

		genderControlGroup = new ControlGroup();
		fieldset.add(genderControlGroup);
		ControlLabel genderLabel = new ControlLabel("Sexe");
		genderControlGroup.add(genderLabel);
		Controls genderControl = new Controls();
		genderControlGroup.add(genderControl);
		genderList = new ListBox();
		genderList.addItem("Sélectionnez votre sexe...");
		genderList.addItem("Homme");
		genderList.addItem("Femme");
		genderControl.add(genderList);
		genderHelpBlock = new HelpBlock();
		genderHelpBlock.setVisible(false);
		genderControl.add(genderHelpBlock);

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
		countryHelpBlock = new HelpBlock();
		countryHelpBlock.setVisible(false);
		countryControl.add(countryHelpBlock);
		Tooltip countryTooltip = new Tooltip("Pays dans lequel vous avez " + 
					"passé la majeure partie de votre vie.");
		countryTooltip.setWidget(countryList);
		countryTooltip.setTrigger(Trigger.HOVER);
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
		computerExperienceHelpBlock = new HelpBlock();
		computerExperienceHelpBlock.setVisible(false);
		computerExperienceControl.add(computerExperienceHelpBlock);
		Tooltip computerExperienceTooltip = new Tooltip("Années depuis " +
				"lesquelles vous utilisez régulièrement l'informatique.");
		computerExperienceTooltip.setWidget(computerExperienceList);
		computerExperienceTooltip.setTrigger(Trigger.HOVER);
		computerExperienceTooltip.setPlacement(Placement.RIGHT);
		computerExperienceTooltip.reconfigure();

		typingUsageControlGroup = new ControlGroup();
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
		typingUsageHelpBlock = new HelpBlock();
		typingUsageHelpBlock.setVisible(false);
		typingUsageControl.add(typingUsageHelpBlock);
		Tooltip typingUsageTooltip = new Tooltip("Nombre moyen d'heures " +
				"passées quotidiennement à faire de la saisie sur un clavier.");
		typingUsageTooltip.setWidget(typingUsageList);
		typingUsageTooltip.setTrigger(Trigger.HOVER);
		typingUsageTooltip.setPlacement(Placement.RIGHT);
		typingUsageTooltip.reconfigure();

		submitButton = new Button("S'inscrire");
		submitButton.setType(ButtonType.SUCCESS);
		registrationForm.add(submitButton);

		alertPanel = new VerticalPanel();
		panel.add(alertPanel);
	}

	/**
	 * Define the handlers of the form.
	 */
	private void generateHandlers() {
		// Check login validity and availability on key up event.
		loginTextBox.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				checkLogin();
			}
		});

		// Check email validity on blur event.
		emailTextBox.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				checkEmail();
			}
		});

		// Check birth year validity on blur event.
		birthYearTextBox.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				checkBirthYear();
			}
		});

		// Check that one of the gender has been selected.
		genderList.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				checkGender();
			}
		});

		// Check that a country has been selected.
		countryList.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				checkCountry();
			}
		});

		// Check that a computer experience has been selected.
		computerExperienceList.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				checkComputerExperience();
			}
		});

		// Check that a typing usage has been selected.
		typingUsageList.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				checkTypingUsage();
			}
		});

		// Check all the form on submit event.
		submitButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				checkAndSendForm();
			}
		});
	}

	/**
	 * Check that the provided information for the field <login> are valid.
	 */
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

	/**
	 * Check whether a given login is available or not.
	 * @param login Login to check.
	 */
	private void checkLoginAvailability(String login) {
		registrationService.checkLoginAvailability(login,
			new AsyncCallback<Boolean>() {
				@Override
				public void onFailure(Throwable caught) {
					loginControlGroup.setType(ControlGroupType.WARNING);
					loginHelpBlock.setText("La disponibilité n'a pas pu être" +
							" vérifiée.");
					loginHelpBlock.setVisible(true);
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

	/**
	 * Check that the provided information for the field <email> are valid.
	 */
	private void checkEmail() {
		String email = emailTextBox.getText();
		if (email.equals("")) {
			emailControlGroup.setType(ControlGroupType.WARNING);
			emailHelpBlock.setText(
					"Une adresse valide est nécessaire pour la " +
					"réception de votre mot de passe.");
			emailHelpBlock.setVisible(true);
		} else if (!FieldVerifier.isValidEmail(email)) {
			emailControlGroup.setType(ControlGroupType.ERROR);
			emailHelpBlock.setText(
					"Veuillez saisir un courriel valide afin de recevoir " +
					"votre mot de passe.");
			emailHelpBlock.setVisible(true);
		} else {
			emailControlGroup.setType(ControlGroupType.SUCCESS);
			emailHelpBlock.setText("");
			emailHelpBlock.setVisible(false);
		}
	}

	/**
	 * Check that the provided information for the field <birth year> are valid.
	 */
	private void checkBirthYear() {
		String birthYear = birthYearTextBox.getText();
		if (birthYear.equals("")) {
			birthYearControlGroup.setType(ControlGroupType.WARNING);
			birthYearHelpBlock.setText(
					"Veuillez entrer votre année de naissance.");
			birthYearHelpBlock.setVisible(true);
		} else if (!FieldVerifier.isValidBirthYear(birthYear)) {
			birthYearControlGroup.setType(ControlGroupType.ERROR);
			birthYearHelpBlock.setText(
					"Veuillez entrer votre véritable année de naissance à " +
					"quatre chiffres.");
			birthYearHelpBlock.setVisible(true);
		} else {
			birthYearControlGroup.setType(ControlGroupType.SUCCESS);
			birthYearHelpBlock.setText("");
			birthYearHelpBlock.setVisible(false);
		}
	}

	/**
	 * Check that the provided information for the field <gender> are valid.
	 */
	private void checkGender() {
		if (genderList.getSelectedIndex() == 0) {
			genderControlGroup.setType(ControlGroupType.WARNING);
			genderHelpBlock.setText("Veuillez renseigner votre sexe.");
			genderHelpBlock.setVisible(true);
		} else {
			genderControlGroup.setType(ControlGroupType.SUCCESS);
			genderHelpBlock.setText("");
			genderHelpBlock.setVisible(false);
		}
	}

	/**
	 * Check that the provided information for the field <country> are valid.
	 */
	private void checkCountry() {
		if (countryList.getSelectedIndex() == 0) {
			countryControlGroup.setType(ControlGroupType.WARNING);
			countryHelpBlock.setText("Veuillez sélectionner votre pays.");
			countryHelpBlock.setVisible(true);
		} else {
			countryControlGroup.setType(ControlGroupType.SUCCESS);
			countryHelpBlock.setText("");
			countryHelpBlock.setVisible(false);
		}
	}

	/**
	 * Check that the provided information for the field <computer experience>
	 * are valid.
	 */
	private void checkComputerExperience() {
		if (computerExperienceList.getSelectedIndex() == 0) {
			computerExperienceControlGroup.setType(ControlGroupType.WARNING);
			computerExperienceHelpBlock.setText("Veuillez faire un choix.");
			computerExperienceHelpBlock.setVisible(true);
		} else {
			computerExperienceControlGroup.setType(ControlGroupType.SUCCESS);
			computerExperienceHelpBlock.setText("");
			computerExperienceHelpBlock.setVisible(false);
		}
	}

	/**
	 * Check that the provided information for the field <typing usage> are
	 * valid.
	 */
	private void checkTypingUsage() {
		if (typingUsageList.getSelectedIndex() == 0) {
			typingUsageControlGroup.setType(ControlGroupType.WARNING);
			typingUsageHelpBlock.setText("Veuillez faire un choix.");
			typingUsageHelpBlock.setVisible(true);
		} else {
			typingUsageControlGroup.setType(ControlGroupType.SUCCESS);
			typingUsageHelpBlock.setText("");
			typingUsageHelpBlock.setVisible(false);
		}
	}

	/**
	 * Check that all the given information are valid and send the form to the
	 * server. Then display the information received by the server and refresh
	 * the page.
	 */
	private void checkAndSendForm() {

		String login = loginTextBox.getText();
		String email = emailTextBox.getText();
		String birthYear = birthYearTextBox.getText();
		String gender = genderList.getValue(genderList.getSelectedIndex());
		String country = countryList.getValue(countryList.getSelectedIndex());
		int computerExperienceIndex = computerExperienceList.getSelectedIndex();
		int typingUsageIndex = typingUsageList.getSelectedIndex();

		if (FieldVerifier.isValidLogin(login)
				&& FieldVerifier.isValidEmail(email)
				&& FieldVerifier.isValidBirthYear(birthYear)
				&& FieldVerifier.isValidGender(gender)
				&& FieldVerifier.isValidCountry(country)
				&& FieldVerifier.isValidComputerExperience(
						computerExperienceList.getValue(
								computerExperienceIndex))
				&& FieldVerifier.isValidTypingUsage(
						typingUsageList.getValue(typingUsageIndex))) {
			int birthYearValue = Integer.parseInt(birthYear);

			registrationService.registerUser(login, email, birthYearValue,
					gender, country, computerExperienceIndex, typingUsageIndex,
					new AsyncCallback<Boolean>() {

				@Override
                public void onFailure(Throwable caught) {
					InformationPopup popup = new InformationPopup(
							"Inscription", true);
					popup.setAlertType(AlertType.WARNING);
					popup.setAlertTitle("Échec de connexion au serveur.");
					popup.setAlertContent("Vérifiez votre connexion internet.");
					popup.showAlert();
					popup.show();
					popup.hideWithDelay();
                }

                @Override
                public void onSuccess(Boolean registered) {
                	if (registered) {
                		InformationPopup popup = new InformationPopup(
        						"Inscription", true);
        				popup.setAlertType(AlertType.SUCCESS);
        				popup.setAlertTitle("Inscription validée.");
        				popup.setAlertContent("Vous devriez recevoir votre " +
        						"mot de passe par mail dans les prochaines " +
        						"minutes. Pensez à vérifier vos courriers " +
        						"indésirables.");
        				popup.showAlert();
        				popup.show();
        				popup.hideWithDelay(6000);
                		clearForm();
                		getOwner().selectTab(1);
                	} else {
                		InformationPopup popup = new InformationPopup(
        						"Inscription", true);
        				popup.setAlertType(AlertType.ERROR);
        				popup.setAlertTitle("Inscription refusée.");
        				popup.setAlertContent("Assurez vous que tous les " +
        						"champs sont bien renseignés et valides.");
        				popup.showAlert();
        				popup.show();
        				popup.hideWithDelay(3000);
                	}
                }
			});
		} else {
			InformationPopup popup = new InformationPopup("Inscription", true);
			popup.setAlertType(AlertType.ERROR);
			popup.setAlertTitle("Informations manquantes.");
			popup.setAlertContent("Assurez vous que tous les " +
					"champs sont bien renseignés et valides.");
			popup.showAlert();
			popup.show();
			popup.hideWithDelay(3000);
		}
	}

	/**
	 * Reset the form to its initial state.
	 */
	private void clearForm() {
		loginTextBox.setText("");
		emailTextBox.setText("");
		birthYearTextBox.setText("");
		genderList.setSelectedIndex(0);
		countryList.setSelectedIndex(0);
		computerExperienceList.setSelectedIndex(0);
		typingUsageList.setSelectedIndex(0);
		loginControlGroup.setType(ControlGroupType.NONE);
		emailControlGroup.setType(ControlGroupType.NONE);
		birthYearControlGroup.setType(ControlGroupType.NONE);
		genderControlGroup.setType(ControlGroupType.NONE);
		countryControlGroup.setType(ControlGroupType.NONE);
		computerExperienceControlGroup.setType(ControlGroupType.NONE);
		typingUsageControlGroup.setType(ControlGroupType.NONE);
	}
}
