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
import fr.vhat.keydyn.client.entities.KDPassword;
import fr.vhat.keydyn.shared.FieldVerifier;
import fr.vhat.keydyn.shared.KDData;
import fr.vhat.keydyn.shared.StatisticsUnit;

/**
 * Main class of the Keystroke Dynamics Authentication system.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
public class KeyDyn implements EntryPoint {

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
	static VerticalPanel chartsPanel = new VerticalPanel();

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

		/**
		 * Check whether a session is opened with the server.
		 */
		authenticationService.validateSession(new AsyncCallback<String>() {
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
		loadHomePage();
	};

	/**
	 * Load the home page: a simple login form.
	 */
	private void loadHomePage () {
		// TODO: replace with the Java applet when ready to use.
		// Java Applet focus when password, unfocus after
		RootPanel.get("content").clear();
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
		String explanationText = "To sign up, please fill out the following " +
			    "form and send it.<br />You'll receive an email with your " +
			    "password.";
		registeringExplanation.setHTML(explanationText);
		container.add(registeringExplanation);

		Grid userDataGrid = new Grid(7, 2);
		userDataGrid.setCellPadding(7);

		Label loginLabel = new Label("Login");
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

		Label emailLabel = new Label("E-mail");
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

		Label ageLabel = new Label("Age");
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

		Label genderLabel = new Label("Gender");
		genderLabel.addStyleName("registrationLabel");
		userDataGrid.setWidget(3, 0, genderLabel);
		HorizontalPanel genderPanel = new HorizontalPanel();
		genderPanel.setSpacing(5);
		VerticalPanel genderRadioPanel = new VerticalPanel();
		genderPanel.add(genderRadioPanel);
		genderUserFemale = new RadioButton("gender", "Female");
		genderRadioPanel.add(genderUserFemale);
		genderUserMale = new RadioButton("gender", "Male");
		genderRadioPanel.add(genderUserMale);
		final Image genderValidity = new Image();
		genderValidity.setVisible(false);
		genderPanel.add(genderValidity);
		userDataGrid.setWidget(3, 1, genderPanel);

		Label countryLabel = new Label("Country");
		countryLabel.addStyleName("registrationLabel");
		userDataGrid.setWidget(4, 0, countryLabel);
		HorizontalPanel countryPanel = new HorizontalPanel();
		countryList = new ListBox();
		countryList.addStyleName("registrationLabel");
		countryList.addItem("");
		countryList.addItem("Canada");
		countryList.addItem("France");
		countryList.addItem("U.S.A.");
		countryList.addItem("United Kingdom");
		countryList.addItem("Spain");
		countryList.addItem("Belgium");
		countryList.addItem("Other");
		countryPanel.add(countryList);
		final Image countryValidity = new Image();
		countryValidity.setVisible(false);
		countryPanel.add(countryValidity);
		userDataGrid.setWidget(4, 1, countryPanel);

		Label experienceLabel = new Label("Computer experience");
		experienceLabel.addStyleName("registrationLabel");
		userDataGrid.setWidget(5, 0, experienceLabel);
		HorizontalPanel experiencePanel = new HorizontalPanel();
		experienceList = new ListBox();
		experienceList.addStyleName("registrationLabel");
		experienceList.addItem("");
		experienceList.addItem("< 2 years");
		experienceList.addItem("~ 4 years");
		experienceList.addItem("~ 7 years");
		experienceList.addItem("~ 10 years");
		experienceList.addItem("> 13 years");
		experiencePanel.add(experienceList);
		final Image experienceValidity = new Image();
		experienceValidity.setVisible(false);
		experiencePanel.add(experienceValidity);
		userDataGrid.setWidget(5, 1, experiencePanel);

		Label usageLabel = new Label("Typing per week");
		usageLabel.addStyleName("registrationLabel");
		userDataGrid.setWidget(6, 0, usageLabel);
		HorizontalPanel usagePanel = new HorizontalPanel();
		usageList = new ListBox();
		usageList.addStyleName("registrationLabel");
		usageList.addItem("");
		usageList.addItem("< 30 minutes a day");
		usageList.addItem("~ 1 hour a day");
		usageList.addItem("~ 2 hours a day");
		usageList.addItem("~ 4 hours a day");
		usageList.addItem("> 5 hours a day");
		usagePanel.add(usageList);
		final Image usageValidity = new Image();
		usageValidity.setVisible(false);
		usagePanel.add(usageValidity);
		userDataGrid.setWidget(6, 1, usagePanel);

		container.add(userDataGrid);

		signUpButton = new Button("Sign up");
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
					authenticationService.checkLoginAvailability(login,
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
					gender = "Male";
				} else if (!genderUserMale.getValue() &&
						genderUserFemale.getValue()) {
					gender = "Female";
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
	private void loadUserPage (String login) {

		RootPanel.get("content").clear();
		RootPanel.get("infos").clear();
		
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
				"Welcome to your member area " + login + "!");
		container.add(welcomeLabel);
	
		// TODO: Explanations on how to register: process, mails, password, data
		HTML explanations = new HTML();
		explanations.setHTML("Type your password and press Enter.");
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
							int passwordLength = kdData.get(0).getLength();
							int[][] pressedData =
									new int[kdDataNumber][passwordLength];
							int[][] releasedData =
									new int[kdDataNumber][passwordLength];
							for (int i = 0 ; i < kdDataNumber ; ++i) {
								pressedData[i] = KDData.typingTime(
										kdData.get(i).getPressTimes());
								releasedData[i] = KDData.typingTime(
										kdData.get(i).getReleaseTimes());
							}
							LineChart pressedChart =
									MemberAreaCharts.getChart("pressed",
											passwordLength, pressedData);
							chartsPanel.add(pressedChart);
							LineChart releasedChart =
									MemberAreaCharts.getChart("released",
											passwordLength, releasedData);
							chartsPanel.add(releasedChart);
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
						Label pressedMeans = new Label(
								means.getPressedStatistics().toString());
						chartsPanel.insert(pressedMeans, 0);
						//Label releasedMeans = new Label(means
							//	.getReleasedStatistics().toString());
						//chartsPanel.insert(releasedMeans, 2);
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
    	$wnd.appletCallback = function(x) {
           @fr.vhat.keydyn.client.KeyDyn::appletCallback(Ljava/lang/String;)(x);
    	}
    	$wnd.appletCallbackChar = function(x) {
           @fr.vhat.keydyn.client.KeyDyn::appletCallbackChar(Ljava/lang/String;)(x);
    	}
    }-*/;

	/**
	 * Check Keystroke Dynamics data and store them in the data store.
	 * This function is called from the Keyboard Applet via JSNI.
	 * @param kdData Keystroke Dynamics data.
	 */
	public static void appletCallback(final String kdData) {
		String[] data = KDData.strings(kdData);

		transmissionService.saveKDData(data[0], data[1], data[2],
				new AsyncCallback<Boolean>() {
			@Override
            public void onFailure(Throwable caught) {
				displayErrorMessage("SaveNewKDData", caught.getMessage());
            }
            @Override
            public void onSuccess(Boolean KDDataSaved) {
            	if (KDDataSaved) {
            		updateKDData();
            		RootPanel.get("ingos").clear();
            	}
            	else {
            		displayInfoMessage("Wrong data: not saved.", true);
            	}
            }
		});
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