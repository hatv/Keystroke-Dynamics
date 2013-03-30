package fr.vhat.keydyn.client;

import java.util.List;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.LineChart;
import fr.vhat.keydyn.client.services.AuthenticationService;
import fr.vhat.keydyn.client.services.AuthenticationServiceAsync;
import fr.vhat.keydyn.client.services.DataTransmissionService;
import fr.vhat.keydyn.client.services.DataTransmissionServiceAsync;
import fr.vhat.keydyn.client.services.RegistrationService;
import fr.vhat.keydyn.client.services.RegistrationServiceAsync;
import fr.vhat.keydyn.client.widgets.GroupTabPanel;
import fr.vhat.keydyn.shared.FieldVerifier;
import fr.vhat.keydyn.shared.KeystrokeSequence;
import fr.vhat.keydyn.shared.StatisticsUnit;
import fr.vhat.keydyn.shared.TimeSequence;
import fr.vhat.keydyn.shared.entities.KDPassword;

/**
 * Main class of the Keystroke Dynamics Authentication system.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
public class KeyDynSave implements EntryPoint {

	/**
	 * Services creation for RPC communication between client and server sides.
	 */
	private static RegistrationServiceAsync registrationService =
			GWT.create(RegistrationService.class);
	private static AuthenticationServiceAsync authenticationService =
			GWT.create(AuthenticationService.class);
	private static DataTransmissionServiceAsync transmissionService =
			GWT.create(DataTransmissionService.class);

	/**
	 * Panel to store the member area charts and statistics.
	 */
	static Grid chartsPanel = new Grid(13, 1);

	/**
	 * Generate an error message to display to the user.
	 * @param function Action or feature which needed the RPC request. 
	 * @param caughtMessage Message caught from the exception.
	 * @return Message to display to the user.
	 */
	private static String errorMessage(String function, String caughtMessage) {
		String message = function + ": an error occurred while attempting to " +
				"contact the server. Please check your network connection and" +
				" try again.\nDetails: " + caughtMessage;
		return message;
	}
	
	/**
	 * Display a communication error message on the HTML web page.
	 * @param function Action or feature which needed the RPC request. 
	 * @param caughtMessage Message caught from the exception.
	 */
	private static void displayErrorMessage(String function,
			String caughtMessage) {
		// TODO : gérer le CSS #errors .gwt-Label en rouge
		RootPanel.get("errors").clear();
		String errorMessage = errorMessage(function, caughtMessage);
		Label errorLabel = new Label(errorMessage);
		RootPanel.get("errors").add(errorLabel);
	}

	/**
	 * Display an information message on the HTML web page.
	 * @param message Message to display.
	 * @param isError Displays a red message if true, green otherwise.
	 */
	private static void displayInfoMessage(String message, boolean isError) {
		// TODO : gérer le CSS en fonction de isError (red) ou non (vert)
		RootPanel.get("infos").clear();
		Label infoLabel = new Label(message);
		RootPanel.get("infos").add(infoLabel);
	}

	/**
	 * This method is called when the browser want to access to the page.
	 */
	public void onModuleLoad() {

		/**
		 * Load the JSNI (Native JavaScript) functions.
		 */
		this.JSNI();

		Application app = new Application();
		GroupTabPanel test = new GroupTabPanel();
		test.addChangeGroupRequestedEventHandler(app);
		
		/**
		 * Check whether a session is opened with the server.
		 */
		/*authenticationService.validateSession(new AsyncCallback<String>() {
			@Override
			public void onFailure(Throwable caught) {
				displayErrorMessage("InitSessionValidation",
						caught.getMessage());
			}
			@Override
			public void onSuccess(String login) {
				if (login != null)
					loadUserPage(login);
				else
					loadHomePage();
			}
		});
		loadHomePage();*/
	};

	/**
	 * Load the home page: a simple login form.
	 */
	private void loadHomePage () {
		// TODO: replace with the Java applet when ready to use.
		// Java Applet focus when password, unfocus after
		RootPanel.get("content").clear();
		RootPanel.get("errors").clear();
		RootPanel.get("infos").clear();

		VerticalPanel container = new VerticalPanel();
		container.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		HorizontalPanel header = new HorizontalPanel();
		Button registrationButton = new Button("Registration");
		Button aboutButton = new Button("About");
		header.setSpacing(5);
		header.add(registrationButton);
		header.add(aboutButton);
		container.add(header);
		Grid loginGrid = new Grid(2, 2);
		Label login = new Label("Login");
		Label password = new Label("Password");
		final TextBox loginUser = new TextBox();
		final PasswordTextBox passwordUser = new PasswordTextBox();
		loginGrid.setWidget(0, 0, login);
		loginGrid.setWidget(1, 0, password);
		loginGrid.setWidget(0, 1, loginUser);
		loginGrid.setWidget(1, 1, passwordUser);
		loginGrid.setCellPadding(7);
		container.add(loginGrid);
		final Label loginStateLabel = new Label();
		container.add(loginStateLabel);
		HorizontalPanel buttonsPanel = new HorizontalPanel();
		buttonsPanel.setSpacing(5);
		Button loginButton = new Button("Login");
		buttonsPanel.add(loginButton);
		Button forgottenPasswordButton = new Button("I forgot my password");
		buttonsPanel.add(forgottenPasswordButton);
		container.add(buttonsPanel);

		container.addStyleName("container");
		RootPanel.get("content").add(container);

		registrationButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				loadRegistrationPage();
			}
		});

		aboutButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				loadAboutPage();
			}
		});
		
		loginButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				loginAttempt(loginUser, passwordUser, loginStateLabel);
			}
		});
		
		passwordUser.addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER)
					loginAttempt(loginUser, passwordUser, loginStateLabel);
			}
		});
		
		loginUser.addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER)
					loginAttempt(loginUser, passwordUser, loginStateLabel);
			}
		});
	}
	
	/**
	 * Try to authenticate an user according to the given login and password.
	 * @param loginBox Login TextBox.
	 * @param passwordBox Password TextBox.
	 * @param loginStateLabel Label to display a message.
	 */
	private void loginAttempt(final TextBox loginBox,
			final PasswordTextBox passwordBox,
			final Label loginStateLabel) {

		// TODO: afficher une image comme quoi la vérification est en cours
		final String login = loginBox.getText();
		final String password = passwordBox.getText();

		authenticationService.authenticateUser(login, password,
				new AsyncCallback<Boolean>() {
			@Override
			public void onFailure(Throwable caught) {
				displayErrorMessage("userAuthentication", caught.getMessage());
			}
			@Override
			public void onSuccess(Boolean result) {
				// TODO: CSS success failure
				// TODO: HTML messages
				if (result) {
					loginStateLabel.setText("Successfully authenticated by " +
							"the server.");
					loginStateLabel.setStylePrimaryName("success");
					loadUserPage(login);
				}
				else {
					loginStateLabel.setText("Authentication rejected. Please" +
							" try again." + " If you forgot your " +
							"password, you can click the eponymous button.");
					loginStateLabel.setStylePrimaryName("failure");
					loginBox.setText("");
					passwordBox.setText("");
				}
			}
		});
	}

	/**
	 * Load the about page: information about the project.
	 */
	private void loadAboutPage () {
		// TODO: content
		RootPanel.get("content").clear();
		RootPanel.get("errors").clear();
		RootPanel.get("infos").clear();
		VerticalPanel container = new VerticalPanel();
		container.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		container.addStyleName("container");

		HorizontalPanel buttonsPanel = new HorizontalPanel();
		buttonsPanel.setSpacing(5);
		container.add(buttonsPanel);
		Button homeButton = new Button("Home");
		buttonsPanel.add(homeButton);
		Button registerButton = new Button("Register");
		buttonsPanel.add(registerButton);
		HTML introduction = new HTML();
		String introductionText = "Work in progress : to learn more about " +
			"this project, visit " +
			"<a href=\"http://www.victorhatinguais.fr/#/portfolio/portfolio/" +
			"keystroke-dynamics-analysis/\" target=\"_blank\"\">" +
			"victorhatinguais.fr</a>";
		introduction.setHTML(introductionText);
		container.add(introduction);

		RootPanel.get("content").add(container);

		homeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				loadHomePage();
			}
		});

		registerButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				loadRegistrationPage();
			}
		});
	}

	/**
	 * Load the registration page: a dynamic form.
	 */
	private void loadRegistrationPage () {
		/**
		 * Build the registration page
		 */
		RootPanel.get("content").clear();
		RootPanel.get("errors").clear();
		RootPanel.get("infos").clear();

		VerticalPanel container = new VerticalPanel();
		container.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		final TextBox loginUser;
		final TextBox emailUser;
		final TextBox ageUser;
		final ListBox countryList;
		Button signUpButton;
		final RadioButton genderUserFemale;
		final RadioButton genderUserMale;
		final ListBox experienceList;
		final ListBox usageList;

		HorizontalPanel header = new HorizontalPanel();
		Button homeButton = new Button("Home");
		Button aboutButton = new Button("About");
		header.setSpacing(5);
		header.add(homeButton);
		header.add(aboutButton);
		container.add(header);

		HTML registeringExplanation = new HTML();
		String explanationText = "Pour vous inscrire, merci de compléter ce " +
			    "formulaire.<br />Vous recevrez un courriel contenant votre " +
			    "mot de passe dans les minutes qui suivent.";
		registeringExplanation.setHTML(explanationText);
		container.add(registeringExplanation);

		Grid userDataGrid = new Grid(7, 2);
		userDataGrid.setCellPadding(7);

		Label loginLabel = new Label("Identifiant");
		loginLabel.addStyleName("registrationLabel");
		HorizontalPanel loginPanel = new HorizontalPanel();
		loginUser = new TextBox();
		loginUser.setMaxLength(13);
		loginUser.setVisibleLength(23);
		loginPanel.add(loginUser);
		final Image loginInfo = new Image("resources/img/information.png");
		loginInfo.setTitle(
				"The login must be 5 to 13 lowercase only characters long.");
		loginPanel.add(loginInfo);
		final Image loginAvailability = new Image();
		loginAvailability.setVisible(false);
		loginPanel.add(loginAvailability);
		userDataGrid.setWidget(0, 0, loginLabel);
		userDataGrid.setWidget(0, 1, loginPanel);

		Label emailLabel = new Label("Courriel");
		emailLabel.addStyleName("registrationLabel");
		HorizontalPanel emailPanel = new HorizontalPanel();
		emailUser = new TextBox();
		emailUser.setVisibleLength(23);
		emailPanel.add(emailUser);
		Image emailInfo = new Image("resources/img/information.png");
		emailPanel.add(emailInfo);
		final Image emailValidity = new Image();
		emailValidity.setVisible(false);
		emailPanel.add(emailValidity);
		userDataGrid.setWidget(1, 0, emailLabel);
		userDataGrid.setWidget(1, 1, emailPanel);

		Label ageLabel = new Label("Âge");
		ageLabel.addStyleName("registrationLabel");
		HorizontalPanel agePanel = new HorizontalPanel();
		ageUser = new TextBox();
		ageUser.setMaxLength(2);
		ageUser.setVisibleLength(2);
		agePanel.add(ageUser);
		final Image ageValidity = new Image();
		ageValidity.setVisible(false);
		agePanel.add(ageValidity);
		userDataGrid.setWidget(2, 0, ageLabel);
		userDataGrid.setWidget(2, 1, agePanel);

		Label genderLabel = new Label("Sexe");
		genderLabel.addStyleName("registrationLabel");
		userDataGrid.setWidget(3, 0, genderLabel);
		HorizontalPanel genderPanel = new HorizontalPanel();
		genderPanel.setSpacing(5);
		VerticalPanel genderRadioPanel = new VerticalPanel();
		genderPanel.add(genderRadioPanel);
		genderUserFemale = new RadioButton("gender", "Féminin");
		genderRadioPanel.add(genderUserFemale);
		genderUserMale = new RadioButton("gender", "Masculin");
		genderRadioPanel.add(genderUserMale);
		final Image genderValidity = new Image();
		genderValidity.setVisible(false);
		genderPanel.add(genderValidity);
		userDataGrid.setWidget(3, 1, genderPanel);

		Label countryLabel = new Label("Pays");
		countryLabel.addStyleName("registrationLabel");
		userDataGrid.setWidget(4, 0, countryLabel);
		HorizontalPanel countryPanel = new HorizontalPanel();
		countryList = new ListBox();
		countryList.addStyleName("registrationLabel");
		countryList.addItem("");
		countryList.addItem("Canada");
		countryList.addItem("France");
		countryList.addItem("U.S.A.");
		countryList.addItem("Royaume-Uni");
		countryList.addItem("Espagne");
		countryList.addItem("Belgique");
		countryList.addItem("Suisse");
		countryList.addItem("Autre");
		countryPanel.add(countryList);
		final Image countryValidity = new Image();
		countryValidity.setVisible(false);
		countryPanel.add(countryValidity);
		userDataGrid.setWidget(4, 1, countryPanel);

		Label experienceLabel = new Label("Expérience informatique");
		experienceLabel.addStyleName("registrationLabel");
		userDataGrid.setWidget(5, 0, experienceLabel);
		HorizontalPanel experiencePanel = new HorizontalPanel();
		experienceList = new ListBox();
		experienceList.addStyleName("registrationLabel");
		experienceList.addItem("");
		experienceList.addItem("< 2 ans");
		experienceList.addItem("~ 4 ans");
		experienceList.addItem("~ 7 ans");
		experienceList.addItem("~ 10 ans");
		experienceList.addItem("> 13 ans");
		experiencePanel.add(experienceList);
		final Image experienceValidity = new Image();
		experienceValidity.setVisible(false);
		experiencePanel.add(experienceValidity);
		userDataGrid.setWidget(5, 1, experiencePanel);

		Label usageLabel = new Label("Usage quotidien du clavier");
		usageLabel.addStyleName("registrationLabel");
		userDataGrid.setWidget(6, 0, usageLabel);
		HorizontalPanel usagePanel = new HorizontalPanel();
		usageList = new ListBox();
		usageList.addStyleName("registrationLabel");
		usageList.addItem("");
		usageList.addItem("< 30 minutes");
		usageList.addItem("~ 1 heure");
		usageList.addItem("~ 2 heures");
		usageList.addItem("~ 4 heures");
		usageList.addItem("> 5 heures");
		usagePanel.add(usageList);
		final Image usageValidity = new Image();
		usageValidity.setVisible(false);
		usagePanel.add(usageValidity);
		userDataGrid.setWidget(6, 1, usagePanel);

		container.add(userDataGrid);

		signUpButton = new Button("S'inscrire");
		container.add(signUpButton);

		container.addStyleName("container");

		RootPanel.get("content").add(container);

		/**
		 * Check login validity on blur event
		 */
		loginUser.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				String login = new String(loginUser.getText());
				if (!FieldVerifier.isValidLogin(login) && !login.equals("")) {
					loginUser.setText("5 to 13 lowercase letters");
					loginUser.addStyleName("errorText");
				}
			}
		});

		/**
		 * Check login availability on key up event
		 */
		loginUser.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				String login = loginUser.getText();
				if (FieldVerifier.isValidLogin(login)) {
					loginAvailability.setVisible(true);
					loginAvailability.setUrl("resources/img/working.png");
					loginAvailability.setTitle(
							"Checking login availability...");
					registrationService.checkLoginAvailability(login,
							new AsyncCallback<Boolean>() {
						@Override
						public void onFailure(Throwable caught) {
							displayErrorMessage("CheckLoginAvailability",
									caught.getMessage());
						}
						@Override
						public void onSuccess(Boolean available) {
							if (available) {
								loginAvailability.setUrl(
										"resources/img/validated.png");
								loginAvailability.setTitle(
										"This login is available.");
							}
							else {
								loginAvailability.setUrl(
										"resources/img/error.png");
								loginAvailability.setTitle(
										"This login is not available.");
							}
						}
					});
				} else {
					loginAvailability.setVisible(false);
				}
			}
		});

		/**
		 * Clear login field on focus event
		 */
		loginUser.addFocusHandler(new FocusHandler() {
			@Override
			public void onFocus(FocusEvent event) {
				if (loginUser.getText().equals("5 to 13 lowercase letters")) {
					loginUser.setText("");
					loginUser.removeStyleName("errorText");
				}
			}
		});

		/**
		 * Check email validity on key up event
		 */
		emailUser.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				String email = emailUser.getText();
				if (!email.equals("")) {
					if (FieldVerifier.isValidEmail(email)) {
						emailValidity.setUrl("resources/img/validated.png");
						emailValidity.setTitle(
								"Your password will be sent to this e-mail.");
						emailValidity.setVisible(true);
					} else {
						emailValidity.setUrl("resources/img/error.png");
						emailValidity.setTitle(
								"Please provide a valid e-mail to receive your " +
								"password.");
						emailValidity.setVisible(true);
					}
				} else {
					emailValidity.setVisible(false);
				}
			}
		});

		/**
		 * Check email validity on blur event
		 */
		emailUser.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				String email = new String(emailUser.getText());
				if (!FieldVerifier.isValidEmail(email) && !email.equals("")) {
					emailUser.setText("Please provide a valid e-mail.");
					emailUser.addStyleName("errorText");
				}
			}
		});

		/**
		 * Clean email field on focus event
		 */
		emailUser.addFocusHandler(new FocusHandler() {
			@Override
			public void onFocus(FocusEvent event) {
				if (emailUser.getText()
						.equals("Please provide a valid e-mail.")) {
					emailUser.setText("");
					emailUser.removeStyleName("errorText");
				}
			}
		});

		/**
		 * Check age validity on blur event
		 */
		ageUser.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				String age = new String(ageUser.getText());
				if (!FieldVerifier.isValidAge(age) && !age.equals("")) {
					ageUser.setText("XX");
					ageUser.addStyleName("errorText");
				}
			}
		});

		/**
		 * Clean age field on focus event
		 */
		ageUser.addFocusHandler(new FocusHandler() {
			@Override
			public void onFocus(FocusEvent event) {
				if (ageUser.getText().equals("XX")) {
					ageUser.setText("");
					ageUser.removeStyleName("errorText");
				}
			}
		});

		/**
		 * Check age validity on key up event
		 */
		ageUser.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				String age = ageUser.getText();
				if (!age.equals("")) {
					if (FieldVerifier.isValidAge(age)) {
						ageValidity.setUrl("resources/img/validated.png");
						ageValidity.setTitle("");
						ageValidity.setVisible(true);
					} else {
						ageValidity.setUrl("resources/img/error.png");
						ageValidity.setTitle("Please provide a valid age.");
						ageValidity.setVisible(true);
					}
				} else {
					ageValidity.setVisible(false);
				}
			}
		});

		/**
		 * Check that one of the gender fields is selected.
		 */
		genderUserMale.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				genderValidity.setUrl("resources/img/validated.png");
				genderValidity.setTitle("");
				genderValidity.setVisible(true);
			}
		});

		/**
		 * Check that one of the gender fields is selected.
		 */
		genderUserFemale.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				genderValidity.setUrl("resources/img/validated.png");
				genderValidity.setTitle("");
				genderValidity.setVisible(true);
			}
		});

		/**
		 * Check that a country is selected.
		 */
		countryList.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				if (!countryList.getValue(countryList.getSelectedIndex())
						.equals("")) {
					countryValidity.setUrl("resources/img/validated.png");
					countryValidity.setTitle("");
					countryValidity.setVisible(true);
				} else {
					countryValidity.setUrl("resources/img/error.png");
					countryValidity.setTitle("Please select a value.");
					countryValidity.setVisible(true);
				}
			}
		});

		/**
		 * Check that a computer experience is selected.
		 */
		experienceList.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				if (!experienceList.getValue(experienceList.getSelectedIndex())
						.equals("")) {
					experienceValidity.setUrl("resources/img/validated.png");
					experienceValidity.setTitle("");
					experienceValidity.setVisible(true);
				} else {
					experienceValidity.setUrl("resources/img/error.png");
					experienceValidity.setTitle("Please select a value.");
					experienceValidity.setVisible(true);
				}
			}
		});

		/**
		 * Check that a typing usage is selected.
		 */
		usageList.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				if (!usageList.getValue(usageList.getSelectedIndex())
						.equals("")) {
					usageValidity.setUrl("resources/img/validated.png");
					usageValidity.setTitle("");
					usageValidity.setVisible(true);
				} else {
					usageValidity.setUrl("resources/img/error.png");
					usageValidity.setTitle("Please select a value.");
					usageValidity.setVisible(true);
				}
			}
		});

		/**
		 * Check the validity of the given information and register the user.
		 */
		signUpButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String login = loginUser.getText();
				String email = emailUser.getText();
				String age = ageUser.getText();
				String gender = "Undefined";
				if (genderUserMale.getValue() && !genderUserFemale.getValue()) {
					gender = "Masculin";
				} else if (!genderUserMale.getValue() &&
						genderUserFemale.getValue()) {
					gender = "Féminin";
				}
				String country =
						countryList.getValue(countryList.getSelectedIndex());
				int experienceIndex = experienceList.getSelectedIndex();
				int usageIndex = usageList.getSelectedIndex();
				if (FieldVerifier.isValidLogin(login)
						&& FieldVerifier.isValidEmail(email)
						&& FieldVerifier.isValidAge(age)
						&& FieldVerifier.isValidGender(gender)
						&& FieldVerifier.isValidCountry(country)
						&& FieldVerifier.isValidExperience(
								experienceList.getValue(experienceIndex))
						&& FieldVerifier.isValidUsage(
								usageList.getValue(usageIndex))) {
					int ageValue = Integer.parseInt(age);
					registrationService.registerUser(login, email, ageValue,
							gender, country, experienceIndex, usageIndex,
							new AsyncCallback<Boolean>() {
						@Override
                        public void onFailure(Throwable caught) {
							displayErrorMessage("SignUp", caught.getMessage());
                        }
                        @Override
                        public void onSuccess(Boolean registered) {
                        	if (registered) {
                        		// TODO : Gérer le retour : vous allez recevoir
                            	// un mail avec votre mot de passe
                        		loadHomePage();
                        	} else {
                        		// TODO : highlight les problèmes
                        		displayInfoMessage("Please fill out all the " +
                        				"requested information.", true);
                        	}
                        }
					});
				} else {
					// TODO: highlight les champs problématiques
					displayInfoMessage("Please fill out all the " +
            				"requested information.", true);
				}
			}
		});

		/**
		 * Load home page.
		 */
		homeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				loadHomePage();
			}
		});

		/**
		 * Load about page.
		 */
		aboutButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				loadAboutPage();
			}
		});
	}

	/**
	 * Load the member area page which contain the training module and
	 * information about the account.
	 * @param login Login of the current user.
	 */
	private void loadUserPage (final String login) {

		this.trainJSNI();

		RootPanel.get("content").clear();
		RootPanel.get("errors").clear();
		RootPanel.get("infos").clear();
		chartsPanel.clear();
		
		final VerticalPanel container = new VerticalPanel();
		container.addStyleName("container");
		container.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		HorizontalPanel header = new HorizontalPanel();
		Button logoutButton = new Button("Logout");
		Button aboutButton = new Button("About");
		header.setSpacing(5);
		header.add(logoutButton);
		header.add(aboutButton);
		container.add(header);
		
		Label welcomeLabel = new Label(
				"Welcome to your member area " + login + "! " +
			    "You can train the system on this page.");
		container.add(welcomeLabel);
		container.setSpacing(5);

		Button testButton = new Button("Test authentication");
		container.add(testButton);

		HTML explanations = new HTML();
		explanations.setHTML("Type your password and press Enter.<br/>" +
				"If you typed the correct password, your keystroke dynamics " +
				"will be saved and the authentication system will learn.<br/>" +
				"<br/>At any time, if you consider the current keystroke " +
				"dynamics does not match your usual,<br/>feel free to press " +
				"Enter or Backspace to automatically reset the application.");
		container.add(explanations);

		// TODO: A box which contain remaining passwords to enter in order to
		// reach the next step

		// TODO: A PasswordTextBox which display the password

		HTML applet = new HTML();
		String installJava = "<a href=\"http://www.java.com/en/download/help" +
				"/windows_manual_download.xml\">Install Java now.</a>";
		String testJava = "<a href=\"http://www.java.com/en/download/" +
				"testjava.jsp\">Test Java.</a>";
		// TODO hide applet + auto focus
		String HTML5Applet = new String("<object id=\"KeyboardApplet\" " +
			"type=\"application/x-java-applet\" height=\"100\" width=\"650\">" +
			"<param name=\"mayscript\" value=\"yes\">" +
			"<param name=\"scriptable\" value=\"true\">" +
	        "<param name=\"codebase\" value=\"resources/\">" +
	        "<param name=\"code\" value=\"KeyboardApplet.class\">" +
	        "<!--  <param name=\"archive\" value=\"KeyboardApplet.jar\"> -->" +
	        "This application is designed to securely authenticate users " +
	        "according to their keystroke dynamics. In order to do that, " +
	        "Java must be installed on your computer. " + installJava + " " +
	        testJava + "</object>");
	    applet.setHTML(HTML5Applet);
	    container.add(applet);

	    // TODO: l'applet doit libérer le focus une fois terminé
	    HTML focusButton = new HTML();
		focusButton.setHTML("<button " +
				"onClick=\"startFocus('KeyboardApplet')\">" +
				"Permanent focus</button>");
	    container.add(focusButton);
	    container.add(chartsPanel);

		// Load the Google Visualization API which draw the line charts.
		VisualizationUtils.loadVisualizationApi(onLoadCallback,
				LineChart.PACKAGE);

		RootPanel.get("content").add(container);

		logoutButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				authenticationService.logout(new AsyncCallback<Void>() {
					@Override
                    public void onFailure(Throwable caught) {
						displayErrorMessage("Logout", caught.getMessage());
                    }
                    @Override
                    public void onSuccess(Void result) {
                    	loadHomePage();
                    }
				});
			}
		});

		testButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				loadTestPage(login);
			}
		});
	}

	/**
	 * Load the member area page which contain the authentication test module.
	 * @param login Login of the current user.
	 */
	private void loadTestPage (final String login) {

		this.testJSNI();

		RootPanel.get("content").clear();
		RootPanel.get("errors").clear();
		RootPanel.get("infos").clear();
		chartsPanel.clear();
		
		final VerticalPanel container = new VerticalPanel();
		container.addStyleName("container");
		container.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		HorizontalPanel header = new HorizontalPanel();
		Button logoutButton = new Button("Logout");
		Button aboutButton = new Button("About");
		header.setSpacing(5);
		header.add(logoutButton);
		header.add(aboutButton);
		container.add(header);
		
		Label welcomeLabel = new Label(
				"Welcome to your member area " + login + "! " +
			    "You can test the system on this page.");
		container.add(welcomeLabel);
		container.setSpacing(5);

		Button trainButton = new Button("Train the system");
		container.add(trainButton);

		HTML explanations = new HTML();
		explanations.setHTML("Type your password and press Enter.<br/>" +
				"If you typed the correct password, your keystroke dynamics " +
				"will be analyzed<br/> and the authentication system will " +
				"tell you if it matches your usual one.<br/><br/>" +
				"You can also try to log in as an other user by entering a " +
				"login in the textbox.<br/>A list of existing login and " +
				"pasword is given so you can try.");
		container.add(explanations);

		// TODO: A PasswordTextBox which display the password

		HTML applet = new HTML();
		String installJava = "<a href=\"http://www.java.com/en/download/help" +
				"/windows_manual_download.xml\">Install Java now.</a>";
		String testJava = "<a href=\"http://www.java.com/en/download/" +
				"testjava.jsp\">Test Java.</a>";
		// TODO hide applet + auto focus
		String HTML5Applet = new String("<object id=\"KeyboardApplet\" " +
			"type=\"application/x-java-applet\" height=\"100\" width=\"650\">" +
			"<param name=\"mayscript\" value=\"yes\">" +
			"<param name=\"scriptable\" value=\"true\">" +
	        "<param name=\"codebase\" value=\"resources/\">" +
	        "<param name=\"code\" value=\"KeyboardApplet.class\">" +
	        "<!--  <param name=\"archive\" value=\"KeyboardApplet.jar\"> -->" +
	        "This application is designed to securely authenticate users " +
	        "according to their keystroke dynamics. In order to do that, " +
	        "Java must be installed on your computer. " + installJava + " " +
	        testJava + "</object>");
	    applet.setHTML(HTML5Applet);
	    container.add(applet);

	    // TODO: l'applet doit libérer le focus une fois terminé
	    /*HTML focusButton = new HTML();
		focusButton.setHTML("<button " +
				"onClick=\"startFocus('KeyboardApplet')\">" +
				"Permanent focus</button>");
	    container.add(focusButton);*/
	    container.add(chartsPanel);

	    HorizontalPanel testLoginPanel = new HorizontalPanel();
	    Label testLoginLabel = new Label("Login");
	    TextBox testLogin = new TextBox();
	    testLogin.setText(login);
	    testLoginPanel.add(testLoginLabel);
	    testLoginPanel.add(testLogin);
	    HorizontalPanel testPasswordPanel = new HorizontalPanel();
	    Label testPasswordLabel = new Label("Password");
	    PasswordTextBox testPassword = new PasswordTextBox();
	    testPasswordPanel.add(testPasswordLabel);
	    testPasswordPanel.add(testPassword);
	    container.add(testLoginPanel);
	    container.add(testPassword);

	    testLogin.addFocusHandler(new FocusHandler() {
			@Override
			public native void onFocus(FocusEvent event) /*-{
			    $wnd.stopFocus();
			}-*/;
		});

	    testLogin.addBlurHandler(new BlurHandler() {
			@Override
			public native void onBlur(BlurEvent event) /*-{
			    $wnd.startFocus('KeyboardApplet');
			}-*/;
		});

	    testPassword.addFocusHandler(new FocusHandler() {
			@Override
			public native void onFocus(FocusEvent event) /*-{
			    $wnd.startFocus('KeyboardApplet');
			}-*/;
		});

		RootPanel.get("content").add(container);

		logoutButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				authenticationService.logout(new AsyncCallback<Void>() {
					@Override
                    public void onFailure(Throwable caught) {
						displayErrorMessage("Logout", caught.getMessage());
                    }
                    @Override
                    public void onSuccess(Void result) {
                    	loadHomePage();
                    }
				});
			}
		});

		trainButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				loadUserPage(login);
			}
		});
	}

	// Create a callback to be called when the visualization API has been
	// loaded. Retrieve KDData information from the data store and display
	// them on a chart.
	private static Runnable onLoadCallback = new Runnable() {
		public void run() {
			// TODO: if null, display nothing.
			transmissionService.getKDData(
					new AsyncCallback<List<KDPassword>>() {
						@Override
				public void onFailure(Throwable caught) {
					displayErrorMessage("KDDataRetrieval", caught.getMessage());
				}
				@Override
				public void onSuccess(List<KDPassword> kdData) {
					if (kdData != null) {
						int kdDataNumber = kdData.size();
						if (kdDataNumber > 0) {
							TimeSequence[] pressedData =
									new TimeSequence[kdDataNumber];
							TimeSequence[] releasedData =
									new TimeSequence[kdDataNumber];
							TimeSequence[] pressedToReleasedData =
									new TimeSequence[kdDataNumber];
							TimeSequence[] releasedToPressedData =
									new TimeSequence[kdDataNumber];
							KeystrokeSequence keystrokeSequence;
							for (int i = 0 ; i < kdDataNumber ; ++i) {
								keystrokeSequence =
										kdData.get(i).getKeystrokeSequence();
								pressedData[i] =
										keystrokeSequence
										.getPressedToPressedSequence();
								releasedData[i] =
										keystrokeSequence
										.getReleasedToReleasedSequence();
								pressedToReleasedData[i] =
										keystrokeSequence
										.getPressedToReleasedSequence();
								releasedToPressedData[i] =
										keystrokeSequence
										.getReleasedToPressedSequence();
							}
							LineChart pressedChart =
									MemberAreaCharts.getChart(
											"pressed", pressedData);
							chartsPanel.setWidget(3, 0, pressedChart);
							LineChart releasedChart =
									MemberAreaCharts.getChart(
											"released", releasedData);
							chartsPanel.setWidget(6, 0, releasedChart);
							LineChart pressedToReleasedChart =
									MemberAreaCharts.getChart(
											"pressedToReleased",
											pressedToReleasedData);
							chartsPanel.setWidget(9, 0, pressedToReleasedChart);
							LineChart releasedToPressedChart =
									MemberAreaCharts.getChart(
											"releasedToPressed",
											releasedToPressedData);
							chartsPanel.setWidget(
									12, 0, releasedToPressedChart);
						}
					}
				}
			});

			transmissionService.getMeans(
					new AsyncCallback<StatisticsUnit>() {
						@Override
				public void onFailure(Throwable caught) {
					displayErrorMessage("MeansRetrieval", caught.getMessage());
				}
				@Override
				public void onSuccess(StatisticsUnit means) {
					if (means != null) {
						Label pressedMeans = new Label("Means: " +
								means.getPressedToPressedStatistics()
								.toString());
						chartsPanel.setWidget(1, 0, pressedMeans);
						Label releasedMeans = new Label("Means: " +
								means.getReleasedToReleasedStatistics()
								.toString());
						chartsPanel.setWidget(4, 0, releasedMeans);
						Label pressedToReleasedMeans = new Label("Means: " +
								means.getPressedToReleasedStatistics()
								.toString());
						chartsPanel.setWidget(7, 0, pressedToReleasedMeans);
						Label releasedToPressedMeans = new Label("Means: " +
								means.getReleasedToPressedStatistics()
								.toString());
						chartsPanel.setWidget(10, 0, releasedToPressedMeans);
					}
				}
			});

			transmissionService.getSd(
					new AsyncCallback<StatisticsUnit>() {
						@Override
				public void onFailure(Throwable caught) {
					displayErrorMessage("SdRetrieval", caught.getMessage());
				}
				@Override
				public void onSuccess(StatisticsUnit sd) {
					if (sd != null) {
						Label pressedSd = new Label("Sd: " +
								sd.getPressedToPressedStatistics().toString());
						chartsPanel.setWidget(2, 0, pressedSd);
						Label releasedSd = new Label("Sd: " +
								sd.getReleasedToReleasedStatistics().toString());
						chartsPanel.setWidget(5, 0, releasedSd);
						Label pressedToReleasedSd = new Label("Sd: " +
								sd.getPressedToReleasedStatistics()
								.toString());
						chartsPanel.setWidget(8, 0, pressedToReleasedSd);
						Label releasedToPressedSd = new Label("Sd: " +
								sd.getReleasedToPressedStatistics()
								.toString());
						chartsPanel.setWidget(11, 0, releasedToPressedSd);
					}
				}
			});
		}
	};

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
    	$wnd.appletCallback = function(x) {
           @fr.vhat.keydyn.client.KeyDyn::appletCallback(Ljava/lang/String;)(x);
    	}
    	$wnd.appletCallbackChar = function(x) {
           @fr.vhat.keydyn.client.KeyDyn::appletCallbackChar(Ljava/lang/String;)(x);
    	}
    }-*/;

	public native void testJSNI() /*-{
		$wnd.appletCallback = function(x) {
	    @fr.vhat.keydyn.client.KeyDyn::appletCallbackTest(Ljava/lang/String;)(x);
		}
	}-*/;

	public native void trainJSNI() /*-{
		$wnd.appletCallback = function(x) {
	    @fr.vhat.keydyn.client.KeyDyn::appletCallback(Ljava/lang/String;)(x);
		}
	}-*/;

	private static void authenticateUser(String login, int mode, String kdData,
			boolean giveInfo) {
		authenticationService.authenticateUser(login, mode, kdData, giveInfo,
				new AsyncCallback<Float[]>() {
			@Override
            public void onFailure(Throwable caught) {
				displayErrorMessage("authenticateUserTrainMode",
						caught.getMessage());
            }
            @Override
            public void onSuccess(Float[] info) {
            	String information = new String();
            	if (info[0] == -2) {
            		information = "Le mot de passe saisi n'est pas valide.";
            	} else if (info[0] == -3) {
            		information = "Cet utilisateur n'est pas reconnu.";
            	} else {
	            	if (info[1] == 1) {
	            		// New data have been saved
	            		updateKDData();
	            	}
	            	if (info[2] != -1) {
	            		String distance = info[2].toString();
	            		information += "Distance : " + distance + " ; ";
	            	}
	            	String authenticationSuccess;
	            	if (info[0] == 1) {
	            		authenticationSuccess = "AUTHENTICATED";
	            	} else {
	            		authenticationSuccess = "FAILED";
	            	}
	            	information += "Authentification : "
	            			+ authenticationSuccess;
	            	if (info[3] != -1) {
	            		String threshold = info[3].toString();
	            		information += " (Seuil = " + threshold.toString()
	            				+ ")";
	            	}
            	}
            	final Label informationLabel = new Label(information);
            	chartsPanel.setWidget(0, 0, informationLabel);
            	RootPanel.get("infos").clear();
        		RootPanel.get("errors").clear();
            }
		});
	}
	/**
	 * Check Keystroke Dynamics data and store them in the data store.
	 * This function is called from the Keyboard Applet via JSNI.
	 * @param kdData Keystroke Dynamics data.
	 */
	public static void appletCallback(final String kdData) {
		authenticateUser("", 1, kdData, true);
	};

	/**
	 * Check Keystroke Dynamics data and test them.
	 * This function is called from the Keyboard Applet via JSNI.
	 * @param kdData Keystroke Dynamics data.
	 */
	public static void appletCallbackTest(final String kdData) {
		authenticateUser("", 0, kdData, true);
	};

	/**
	 * Display last typed character on screen.
	 * This function is called from the Keyboard Applet via JSNI.
	 * @param char Last typed character.
	 */
	public static void appletCallbackChar(final String c) {
		// TODO: display in a field
		// TODO: when appletCallback is called, clear the field
		
	}

	/**
	 * Display information about KDData saved in the data store : data,
	 * statistics and chart
	 */
	private static void updateKDData () {
		// Draw or refresh charts
		chartsPanel.clear();
		onLoadCallback.run();
		// TODO: show user that 1 KDData saved, 1 less to train
	}
}