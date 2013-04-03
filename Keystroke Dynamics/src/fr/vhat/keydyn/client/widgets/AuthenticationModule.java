package fr.vhat.keydyn.client.widgets;

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
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class AuthenticationModule extends VerticalPanel {

	private WellForm authenticationForm;
	private TextBox loginTextBox;
	private ListBox loginListBox;
	private PasswordTextBox passwordTextBox;
	private int mode;

	/**
	 * Constructor.
	 * @param mode Save data behavior : 0 means "never save data" (test),
	 * 1 means "always save data" (train) and 2 means "save data only if
	 * authenticated" (production). Furthermore, in train mode the login is
	 * unused as the session login is used, in production mode, there is a
	 * field where to enter the login and in test mode, there is a ListBox
	 * with multiple logins and a field to display the matching password.
	 * @param applet True if we want to use a Java applet, false to use simple
	 * JavaScript code.
	 */
	public AuthenticationModule(int mode, boolean applet) {

		this.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

		this.mode = mode;
		this.authenticationForm = this.getAuthenticationForm();

		if (applet) {
			this.add(this.getApplet());
		} else {
			this.displayAuthenticationForm();
			// TODO : specific code
		}

		VerticalPanel alertPanel = new VerticalPanel();
		this.add(alertPanel);
	}

	/**
	 * By default, the authentication module use JavaScript rather than a Java
	 * applet.
	 * @param mode Mode to use : see the main constructor for more information.
	 */
	public AuthenticationModule(int mode) {
		this(mode, false);
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
	 * @return Authentication form.
	 */
	private WellForm getAuthenticationForm() {

		WellForm authenticationForm = new WellForm();
		authenticationForm.setType(FormType.HORIZONTAL);
		Fieldset fieldset = new Fieldset();
		authenticationForm.add(fieldset);
		Legend authenticationLegend = new Legend("Module d'authentification");
		fieldset.add(authenticationLegend);

		ControlGroup loginControlGroup;
		ControlLabel loginLabel;
		Controls loginControl;
		switch(this.mode) {
			// 0 : Test mode
			case 0:
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
				break;
			// 1 : Train mode
			case 1:
				break;
			// 2 : Production mode
			case 2:
				loginControlGroup = new ControlGroup();
				fieldset.add(loginControlGroup);
				loginLabel = new ControlLabel("Identifiant");
				loginControlGroup.add(loginLabel);
				loginControl = new Controls();
				loginControlGroup.add(loginControl);
				loginTextBox = new TextBox();
				loginTextBox.setMaxLength(13);
				loginControl.add(loginTextBox);
				break;
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
		Button submitButton = new Button("Valider");
		submitButton.setType(ButtonType.SUCCESS);
		submitButton.addStyleName("buttonsPanel");
		buttonsPanel.add(submitButton);
		if (mode == 2) {
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
		switch(this.mode) {
			case 0:
				// 0 : test mode -> login from a ListBox
				login = loginListBox.getValue(loginListBox.getSelectedIndex());
				break;
			case 1:
				// 1 : train mode -> login from session
				break;
			case 2:
				// 2 : production mode -> login from a TextBox
				login = loginTextBox.getText();
				break;
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
}
