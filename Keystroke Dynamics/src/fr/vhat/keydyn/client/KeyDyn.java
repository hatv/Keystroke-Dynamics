package fr.vhat.keydyn.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HTML;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class KeyDyn implements EntryPoint {
	
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";
	
	
	// TODO : se place forcément ici ?
	private static RegistrationServiceAsync registrationService =
			GWT.create(RegistrationService.class);
	private static AuthenticationServiceAsync authenticationService =
			GWT.create(AuthenticationService.class);
	private static DataTransmissionServiceAsync transmissionService =
			GWT.create(DataTransmissionService.class);

	// TODO : meilleur placement possible ?
	static VerticalPanel kdDataPanel;
	
	public void onModuleLoad() {
		this.JSNI();
		
		authenticationService.validateSession(new AsyncCallback<String>() {
			@Override
			public void onFailure(Throwable caught) {
				System.out.println("FAILURE");
			}
			@Override
			public void onSuccess(String login) {
				System.out.println("SUCCESS");
				// TODO : Gérer le retour
				if (login != null)
					loadUserPage(login);
				else
					loadHomePage();
			}
		});
	};
	
	private void loadHomePage () {
		RootPanel.get("registerContent").clear();
		
		VerticalPanel container = new VerticalPanel();
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
		Button loginButton = new Button("Login");
		container.add(loginButton);
		Button forgottenPasswordButton = new Button("I forgot my password");
		container.add(forgottenPasswordButton);
		
		container.addStyleName("container");
		RootPanel.get("registerContent").add(container);
		
		registrationButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				loadRegistrationPage();
			}
		});
		
		loginButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				authenticationService.authenticateUser(loginUser.getText(),
						passwordUser.getText(), new AsyncCallback<Boolean>() {
					@Override
					public void onFailure(Throwable caught) {
						System.out.println("FAILURE");
					}
					@Override
					public void onSuccess(Boolean result) {
						System.out.println("SUCCESS");
						// TODO : Gérer le retour
						if (result)
							loginUser.setText("CONNECTEEEE");
						else
							loginUser.setText("WRONG");
					}
				});
			}
		});
	}
	
	private void loadRegistrationPage () {
		RootPanel.get("registerContent").clear();
		
		VerticalPanel container = new VerticalPanel();
		final TextBox loginUser;
		final TextBox emailUser;
		final TextBox ageUser;
		final ListBox countryList;
		Button signUpButton;
		final RadioButton genderUserFemale;
		final RadioButton genderUserMale;
		ListBox experienceList;
		ListBox usageList;
		
		HorizontalPanel header = new HorizontalPanel();
		Button homeButton = new Button("Home");
		Button aboutButton = new Button("About");
		header.setSpacing(5);
		header.add(homeButton);
		header.add(aboutButton);
		container.add(header);
		
		HTML introduction = new HTML();
		String introductionText = "Work in progress : to learn more about " +
			"this project, visit " +
			"<a href=\"http://www.victorhatinguais.fr/#/portfolio/portfolio/" +
			"keystroke-dynamics-analysis/\" target=\"_blank\"\">" +
			"victorhatinguais.fr</a>";
		introduction.setHTML(introductionText);
		container.add(introduction);
		
		Label registeringExplanation = new Label();
		String explanationText = "To sign up, please fill out the following "
				+ "form and send it. You'll receive an email soon.";
		registeringExplanation.setText(explanationText);
		container.add(registeringExplanation);

		Grid userDataGrid = new Grid(7, 2);
		userDataGrid.setCellPadding(7);

		Label loginLabel = new Label("Login");
		loginLabel.addStyleName("registrationLabel");
		HorizontalPanel loginPanel = new HorizontalPanel();
		loginUser = new TextBox();
		loginUser.setMaxLength(16);
		loginUser.setVisibleLength(23);
		loginPanel.add(loginUser);
		Image loginInfo = new Image("resources/img/information.png");
		loginInfo.setTitle("TODO : onMouseOverHandler");
		loginPanel.add(loginInfo);
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
		userDataGrid.setWidget(1, 0, emailLabel);
		userDataGrid.setWidget(1, 1, emailPanel);
		
		Label ageLabel = new Label("Age");
		ageLabel.addStyleName("registrationLabel");
		ageUser = new TextBox();
		ageUser.setMaxLength(2);
		ageUser.setVisibleLength(2);
		userDataGrid.setWidget(2, 0, ageLabel);
		userDataGrid.setWidget(2, 1, ageUser);
		
		Label genderLabel = new Label("Gender");
		genderLabel.addStyleName("registrationLabel");
		userDataGrid.setWidget(3, 0, genderLabel);
		HorizontalPanel genderPanel = new HorizontalPanel();
		genderUserFemale = new RadioButton("gender", "Female");
		genderPanel.add(genderUserFemale);
		genderUserMale = new RadioButton("gender", "Male");
		genderPanel.add(genderUserMale);
		userDataGrid.setWidget(3, 1, genderPanel);
		
		Label countryLabel = new Label("Country");
		countryLabel.addStyleName("registrationLabel");
		userDataGrid.setWidget(4, 0, countryLabel);
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
		userDataGrid.setWidget(4, 1, countryList);
		
		Label experienceLabel = new Label("Computer experience");
		experienceLabel.addStyleName("registrationLabel");
		userDataGrid.setWidget(5, 0, experienceLabel);
		experienceList = new ListBox();
		experienceList.addStyleName("registrationLabel");
		experienceList.addItem("");
		experienceList.addItem("< 2 years");
		experienceList.addItem("~ 4 years");
		experienceList.addItem("~ 7 years");
		experienceList.addItem("~ 10 years");
		experienceList.addItem("> 13 years");
		userDataGrid.setWidget(5, 1, experienceList);
		
		Label usageLabel = new Label("Typing per week");
		usageLabel.addStyleName("registrationLabel");
		userDataGrid.setWidget(6, 0, usageLabel);
		usageList = new ListBox();
		usageList.addStyleName("registrationLabel");
		usageList.addItem("");
		usageList.addItem("< 30 minutes a day");
		usageList.addItem("~ 1 hour a day");
		usageList.addItem("~ 2 hours a day");
		usageList.addItem("~ 4 hours a day");
		usageList.addItem("> 5 hours a day");
		userDataGrid.setWidget(6, 1, usageList);
		
		container.add(userDataGrid);

		signUpButton = new Button("Sign up");
		container.add(signUpButton);
		// TODO : when button pressed, check if the textboxes do not contain
		// some error texts

		container.addStyleName("container");

		RootPanel.get("registerContent").add(container);

		loginUser.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				// TODO : utiliser FieldVerifier
				// TODO : login -> in DB ? si oui, oubli de mot de passe ?
				String login = new String(loginUser.getText());
				if (!login.matches("^[a-z]{5,13}$") && !login.equals("")) {
					loginUser.setText("5 to 13 lowercase letters");
					loginUser.addStyleName("errorText");
				}
			}
		});
		loginUser.addFocusHandler(new FocusHandler() {
			@Override
			public void onFocus(FocusEvent event) {
				if (loginUser.getText().equals("5 to 13 lowercase letters")) {
					loginUser.setText("");
				}
				loginUser.removeStyleName("errorText");
			}
		});
		
		emailUser.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				String email = new String(emailUser.getText());
				if (!email.matches("^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\." +
						"[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z" +
						"0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$")
					&& !email.equals("")) {
					emailUser.setText("Please type a valid adress");
					emailUser.addStyleName("errorText");
				}
			}
		});
		emailUser.addFocusHandler(new FocusHandler() {
			@Override
			public void onFocus(FocusEvent event) {
				if (emailUser.getText().equals("Please type a valid adress")) {
					emailUser.setText("");
				}
				emailUser.removeStyleName("errorText");
			}
		});
		
		ageUser.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				String age = new String(ageUser.getText());
				if (!age.matches("^[0-9]{1,2}$") && !age.equals("")) {
					ageUser.setText("XX");
					ageUser.addStyleName("errorText");
				}
			}
		});
		ageUser.addFocusHandler(new FocusHandler() {
			@Override
			public void onFocus(FocusEvent event) {
				if (ageUser.getText().equals("XX")) {
					ageUser.setText("");
				}
				ageUser.removeStyleName("errorText");
			}
		});
		
		signUpButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO : utiliser FieldVerifier avant d'envoyer la sauce
				try {
					int age = Integer.parseInt(ageUser.getText());
					String gender = "Undefined";
					if (genderUserMale.getValue() &&
							!genderUserFemale.getValue()) {
						gender = "Male";
					} else if (!genderUserMale.getValue() &&
							genderUserFemale.getValue()) {
						gender = "Female";
					} else
						gender = "Undefined";
					registrationService.registerUser(loginUser.getText(), 
							emailUser.getText(), age, gender, 
							countryList.getValue(
									countryList.getSelectedIndex()),
							// TODO : computerExperience, computerUsage
							/*computerExperience, computerUsage*/1, 1,
							new AsyncCallback<Boolean>() {
						@Override
                        public void onFailure(Throwable caught) {
							System.out.println("FAILURE");
                        }
                        @Override
                        public void onSuccess(Boolean registered) {
                        	System.out.println("SUCCESS");
                        	// TODO : Gérer le retour
                        	if (registered)
                        		// TODO : bien inscrit
                        		loginUser.setText("OKKKKK.serverOk");
                        	else
                        		// TODO : highlight les problèmes
                        		loginUser.setText("FAIL.serverFAIL");
                        }
					});
				
				} catch (NumberFormatException e) {
					// TODO : l'âge n'est pas un entier
				}
			}
		});
		
		homeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				loadHomePage();
			}
		});
	}

	static boolean firstAdd = true;
	static int[] pressedSum;
	static int[] releasedSum;
	static int[] pressedMeans;
	static int[] releasedMeans;
	static Label pressedMeansLabel = new Label("pressedMeans");
	static Label releasedMeansLabel = new Label("releasedMeans");
	static int nb = 0;
	private void loadUserPage (String login) {
		firstAdd = true;
		RootPanel.get("registerContent").clear();
		
		VerticalPanel container = new VerticalPanel();
		container.addStyleName("container");
		
		HorizontalPanel header = new HorizontalPanel();
		Button logoutButton = new Button("Logout");
		// TODO : delete session
		Button aboutButton = new Button("About");
		header.setSpacing(5);
		header.add(logoutButton);
		header.add(aboutButton);
		container.add(header);
		
		Label l = new Label("Welcome to your member area " + login + "!");
		container.add(l);
		
		HTML applet = new HTML();
		// TODO hide applet ?
		String HTML5Applet = new String("<object id=\"KeyboardApplet\" " +
			"type=\"application/x-java-applet\" height=\"387\" width=\"482\">" +
			"<param name=\"mayscript\" value=\"yes\">" +
			"<param name=\"scriptable\" value=\"true\">" +
	        "<param name=\"codebase\" value=\"resources/\">" +
	        "<param name=\"code\" value=\"KeyboardApplet.class\">" +
	        "<!--  <param name=\"archive\" value=\"KeyboardApplet.jar\"> -->" +
	        "Please accept the Java Applet or install JRE 6 at least." +
	        // TODO : Message donnant les liens pour télécharger Java (JVM)
	        "</object>");
	    applet.setHTML(HTML5Applet);
	    container.add(applet);

	    // TODO : l'applet doit libérer le focus une fois terminé
	    HTML focusButton = new HTML();
		focusButton.setHTML("<button " +
				"onClick=\"startFocus('KeyboardApplet')\">" +
				"Permanent focus</button>");
	    container.add(focusButton);
	    
	    kdDataPanel = new VerticalPanel();
	    container.add(kdDataPanel);

	    transmissionService.getKDData(new AsyncCallback<String[][]>() {
			@Override
            public void onFailure(Throwable caught) {
				System.out.println("FAILURE");
            }
            @Override
            public void onSuccess(String[][] kdData) {
            	System.out.println("SUCCESS");
            	// TODO : noter les anciennes data et tracer le graphique (kdData)
            }
		});
	    
		RootPanel.get("registerContent").add(container);
		
		logoutButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				authenticationService.logout(new AsyncCallback<Void>() {
					@Override
                    public void onFailure(Throwable caught) {
						System.out.println("FAILURE");
                    }
                    @Override
                    public void onSuccess(Void result) {
                    	System.out.println("SUCCESS");
                    	loadHomePage();
                    }
				});
			}
		});
	}
	
	public native void JSNI() /*-{
    	$wnd.appletCallback = function(x) {
           @fr.vhat.keydyn.client.KeyDyn::appletCallback(Ljava/lang/String;)(x);
    	}
    }-*/;

	public static void appletCallback(final String kdData) {
		System.out.println("appletCallback");
		System.out.println(kdData);
		// TODO : send to the data store
		String[] data = kdData.split(";");
		transmissionService.saveKDData(data[0], data[1], data[2],
				new AsyncCallback<Boolean>() {
			@Override
            public void onFailure(Throwable caught) {
				System.out.println("FAILURE");
            }
            @Override
            public void onSuccess(Boolean KDDataSaved) {
            	System.out.println("SUCCESS");
            	if (KDDataSaved) {
            		addKDData(kdData);
            		// TODO : show user that 1 KDData saved, 1 less to train
            	}
            	else {
            		// TODO : show user that a problem did happen
            	}
            }
		});
	};

	/**
	 * Display information about KDData saved in the data store : data,
	 * statistics and chart
	 * @param kdData : a string retrieved from the Java applet
	 */
	private static void addKDData (String kdData) {
		String[] splitString = kdData.split(";");
		nb++;
		if (firstAdd) {
			System.out.println("firstAdd");
			kdDataPanel.add(pressedMeansLabel);
			kdDataPanel.add(releasedMeansLabel);
			pressedSum = new int[splitString[0].length()];
			releasedSum = new int[splitString[0].length()];
			pressedMeans = new int[splitString[0].length()];
			releasedMeans = new int[splitString[0].length()];
			Label dataLabelPassword = new Label(splitString[0]);
			kdDataPanel.add(dataLabelPassword);
			firstAdd = false;
		}
		Label dataLabelPress = new Label(splitString[1]);
		Label dataLabelRelease = new Label(splitString[2]);
		kdDataPanel.add(dataLabelPress);
		kdDataPanel.add(dataLabelRelease);
		String[] pressedTimes = splitString[1].substring(1, splitString[1].length()-2).split(",");
		int t = 0;
		String tempStr = "[";
		for (int i = 0 ; i < pressedTimes.length ; ++i) {
			t = Integer.parseInt(pressedTimes[i].trim());
			pressedSum[i] += t;
			pressedMeans[i] = pressedSum[i]/nb;
			tempStr += pressedMeans[i];
			if (i+1 != pressedTimes.length) {
				tempStr += ", ";
			}
		}
		tempStr += "]";
		pressedMeansLabel.setText(tempStr);
		tempStr = "[";
		String[] releasedTimes = splitString[2].substring(1, splitString[2].length()-2).split(",");
		for (int i = 0 ; i < pressedTimes.length ; ++i) {
			t = Integer.parseInt(releasedTimes[i].trim());
			releasedSum[i] += t;
			releasedMeans[i] = releasedSum[i]/nb;
			tempStr += releasedMeans[i];
			if (i+1 != pressedTimes.length) {
				tempStr += ", ";
			}
		}
		tempStr += "]";
		releasedMeansLabel.setText(tempStr);
	}

}