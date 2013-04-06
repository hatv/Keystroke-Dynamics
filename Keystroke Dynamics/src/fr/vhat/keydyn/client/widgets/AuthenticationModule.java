package fr.vhat.keydyn.client.widgets;

import java.util.LinkedList;
import java.util.List;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.ControlGroup;
import com.github.gwtbootstrap.client.ui.ControlLabel;
import com.github.gwtbootstrap.client.ui.Controls;
import com.github.gwtbootstrap.client.ui.Fieldset;
import com.github.gwtbootstrap.client.ui.HelpBlock;
import com.github.gwtbootstrap.client.ui.Legend;
import com.github.gwtbootstrap.client.ui.ListBox;
import com.github.gwtbootstrap.client.ui.PasswordTextBox;
import com.github.gwtbootstrap.client.ui.TextBox;
import com.github.gwtbootstrap.client.ui.Tooltip;
import com.github.gwtbootstrap.client.ui.WellForm;
import com.github.gwtbootstrap.client.ui.constants.ButtonType;
import com.github.gwtbootstrap.client.ui.constants.FormType;
import com.github.gwtbootstrap.client.ui.constants.Placement;
import com.github.gwtbootstrap.client.ui.constants.Trigger;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsDate;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import fr.vhat.keydyn.client.services.AuthenticationService;
import fr.vhat.keydyn.client.services.AuthenticationServiceAsync;
import fr.vhat.keydyn.shared.AuthenticationMode;
import fr.vhat.keydyn.shared.AuthenticationReturn;

public class AuthenticationModule extends VerticalPanel {

	private PageAuthentication owner;
	private WellForm authenticationForm;
	private TextBox loginTextBox;
	private ListBox loginListBox;
	private Button submitButton;
	private PasswordTextBox passwordTextBox;
	private AuthenticationMode authenticationMode;

	// JavaScript parameters
	private double firstTimestamp = 0;
	private List<Integer> releasedTable = new LinkedList<Integer>();
	private List<Integer> pressedTable = new LinkedList<Integer>();
	private List<Character> characters = new LinkedList<Character>();
	private String string = new String();

	// Services creation for RPC communication between client and server sides.
	private static AuthenticationServiceAsync authenticationService =
			GWT.create(AuthenticationService.class);

	/**
	 * Constructor.
	 * @param authenticationMode Mode among the several AuthenticationMode
	 * available.
	 * @param applet True if we want to use a Java applet, false to use simple
	 * JavaScript code.
	 * @param legend LEgend to display above the module.
	 * @param owner Owner page of the authentication module.
	 */
	public AuthenticationModule(AuthenticationMode authenticationMode,
			boolean applet, String legend, PageAuthentication owner) {

		this.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

		this.authenticationMode = authenticationMode;
		this.authenticationForm = this.getAuthenticationForm(legend);
		this.owner = owner;

		if (applet) {
			this.add(this.getApplet());
		} else {
			this.displayAuthenticationForm();
			this.addJSHandlers();
		}

		VerticalPanel alertPanel = new VerticalPanel();
		this.add(alertPanel);
	}

	/**
	 * By default, the authentication module use JavaScript rather than a Java
	 * applet.
	 * @param authenticationMode Mode to use : see the main constructor for
	 * more information.
	 * @param legend Legend to display above the module.
	 * @param owner Owner page of the authentication module.
	 */
	public AuthenticationModule(AuthenticationMode authenticationMode,
			String legend, PageAuthentication owner) {
		this(authenticationMode, false, legend, owner);
	}

	/**
	 * Build the HTML object which contain the Java Applet.
	 * @return HTML Applet object.
	 */
	private HTML getApplet() {
		HTML applet = new HTML();
		String installJava = "<a href=\"http://www.java.com/fr/download/help/" +
				"download_options.xml\">Installer Java.</a>";
		String testJava = "<a href=\"http://www.java.com/en/download/" +
				"testjava.jsp\">Tester Java.</a>";
		String HTML5Applet = new String("<object id=\"KeyboardApplet\" " +
			"type=\"application/x-java-applet\" height=\"10\" width=\"10\">" +
			"<param name=\"mayscript\" value=\"true\">" +
			"<param name=\"scriptable\" value=\"true\">" +
	        "<param name=\"codebase\" value=\"resources/\">" +
	        "<param name=\"code\" value=\"KeyboardApplet.class\">" +
	        "<!--  <param name=\"archive\" value=\"KeyboardApplet.jar\"> -->" +
	        "Pour utiliser cette application d'authentification sécurisée, " +
	        "vous devez installer Java et accepter l'Applet. " + installJava +
	        " " + testJava + "</object>");
	    applet.setHTML(HTML5Applet);
	    return applet;
	}

	/**
	 * Build the authentication form on demand.
	 * @param legend Legend to display above the module.
	 * @return Authentication form.
	 */
	private WellForm getAuthenticationForm(String legend) {

		WellForm authenticationForm = new WellForm();
		authenticationForm.setType(FormType.HORIZONTAL);
		Fieldset fieldset = new Fieldset();
		authenticationForm.add(fieldset);
		Legend authenticationLegend = new Legend(legend);
		fieldset.add(authenticationLegend);

		ControlGroup loginControlGroup;
		ControlLabel loginLabel;
		Controls loginControl;
		if (this.authenticationMode == AuthenticationMode.TEST_MODE) {
			loginControlGroup = new ControlGroup();
			fieldset.add(loginControlGroup);
			loginLabel = new ControlLabel("Identifiant");
			loginControlGroup.add(loginLabel);
			loginControl = new Controls();
			loginControlGroup.add(loginControl);
			loginListBox = new ListBox();
			loginControl.add(loginListBox);
			Tooltip loginTooltip =
					new Tooltip("Identifiant de la personne dont vous " +
							"voulez tenter d'usurper l'identité.");
			loginTooltip.setWidget(loginListBox);
			loginTooltip.setTrigger(Trigger.HOVER);
			loginTooltip.setPlacement(Placement.RIGHT);
			loginTooltip.reconfigure();
		} else if (
				this.authenticationMode == AuthenticationMode.PRODUCTION_MODE) {
			loginControlGroup = new ControlGroup();
			fieldset.add(loginControlGroup);
			loginLabel = new ControlLabel("Identifiant");
			loginControlGroup.add(loginLabel);
			loginControl = new Controls();
			loginControlGroup.add(loginControl);
			loginTextBox = new TextBox();
			loginTextBox.setMaxLength(13);
			loginControl.add(loginTextBox);
		}

		ControlGroup passwordControlGroup = new ControlGroup();
		fieldset.add(passwordControlGroup);
		ControlLabel passwordLabel = new ControlLabel("Mot de passe");
		passwordControlGroup.add(passwordLabel);
		Controls passwordControl = new Controls();
		passwordControlGroup.add(passwordControl);
		passwordTextBox = new PasswordTextBox();
		passwordControl.add(passwordTextBox);
		HelpBlock passwordHelpBlock = new HelpBlock();
		passwordHelpBlock.setVisible(false);
		passwordControl.add(passwordHelpBlock);

		HorizontalPanel buttonsPanel = new HorizontalPanel();
		submitButton = new Button("Valider");
		submitButton.setType(ButtonType.SUCCESS);
		submitButton.addStyleName("buttonsPanel");
		buttonsPanel.add(submitButton);
		if (authenticationMode == AuthenticationMode.PRODUCTION_MODE) {
			Button forgotPasswordButton = new Button("Mot de passe oublié");
			forgotPasswordButton.setType(ButtonType.INFO);
			forgotPasswordButton.addStyleName("buttonsPanel");
			buttonsPanel.add(forgotPasswordButton);
		}
		authenticationForm.add(buttonsPanel);

		return authenticationForm;
	}

	/**
	 * Display the authentication form once the Java Applet is ready.
	 */
	public void displayAuthenticationForm() {
		this.insert(this.authenticationForm, 0);
	}

	/**
	 * Give the currently selected or set login.
	 * @return Login.
	 */
	public String getLogin() {
		String login = new String();
		if (this.authenticationMode == AuthenticationMode.TEST_MODE) {
			login = loginListBox.getValue(loginListBox.getSelectedIndex());
		} else if (this.authenticationMode == AuthenticationMode.TRAIN_MODE) {
			login = "";
		} else if (
				this.authenticationMode == AuthenticationMode.PRODUCTION_MODE) {
			login = loginTextBox.getText();
		}
		return login;
	}

	/**
	 * Clear the password field.
	 */
	public void clearPassword() {
		passwordTextBox.setText("");
	}

	/**
	 * Add an unknown character to the password field.
	 */
	public void addCharacterToPassword() {
		passwordTextBox.setText(passwordTextBox.getText() + "x");
	}

	/**
	 * Give the password text box object in order to handle events on it.
	 * @return Password text box.
	 */
	public PasswordTextBox getPasswordTextBox() {
		return this.passwordTextBox;
	}

	/**
	 * Give the login text box object in order to handle events on it.
	 * @return Login text box.
	 */
	public TextBox getLoginTextBox() {
		return this.loginTextBox;
	}

	/**
	 * Handlers added in case of JavaScript use.
	 */
	private void addJSHandlers() {

		this.passwordTextBox.addKeyPressHandler(new KeyPressHandler() {
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

		this.passwordTextBox.addKeyUpHandler(new KeyUpHandler() {
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
		passwordTextBox.setText("");
	}

	/**
	 * Authentication function : send data to the server in order to check the
	 * validity of the authentication.
	 * @param login Login of the user.
	 * @param mode Mode (0 : test, 1 : train, 2 : production).
	 * @param kdData Keystroke Dynamics data.
	 * @param giveInfo True if the server has to return information.
	 * @return AuthenticationReturn object containing the requested information.
	 */
	public void authenticateUser(String login,
			AuthenticationMode mode, String kdData, boolean giveInfo) {

		authenticationService.authenticateUser(login, mode, kdData, giveInfo,
				new AsyncCallback<AuthenticationReturn>() {
			@Override
            public void onFailure(Throwable caught) {
				owner.execReturn(null);
            }
            @Override
            public void onSuccess(AuthenticationReturn authenticationReturn) {
            	owner.execReturn(authenticationReturn);
            }
		});
	}
}
