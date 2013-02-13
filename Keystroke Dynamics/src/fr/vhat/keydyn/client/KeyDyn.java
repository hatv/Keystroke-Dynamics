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
	
	static VerticalPanel container = new VerticalPanel();
	private TextBox loginUser;
	private TextBox emailUser;
	private TextBox ageUser;
	private ListBox countryList;
	private Button signUpButton;
	private RadioButton genderUserFemale;
	private RadioButton genderUserMale;
	private ListBox experienceList;
	private ListBox usageList;
	
	private RegistrationServiceAsync registrationService =
			GWT.create(RegistrationService.class);


	public void onModuleLoad() {

		this.JSNI();

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
/*
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
	        "</object>");
	    applet.setHTML(HTML5Applet);
	    container.add(applet);

	    HTML focusButton = new HTML();
		focusButton.setHTML("<button " +
				"onClick=\"startFocus('KeyboardApplet')\">" +
				"Permanent focus</button>");
	    container.add(focusButton);
*/
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
	};

	public static void appletCallback(String msg) {
	    Label test = new Label(msg);
		container.add(test);
	};

	public native void JSNI() /*-{
    $wnd.appletCallback = function(x) {
        @fr.vhat.keydyn.client.KeyDyn::appletCallback(Ljava/lang/String;)(x);
    	}
    }-*/;
}