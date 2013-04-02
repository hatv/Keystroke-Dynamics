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

	/**
	 * Constructor.
	 * @param mode Save data behavior : 0 means "never save data" (test),
	 * 1 means "always save data" (train) and 2 means "save data only if
	 * authenticated" (production). Furthermore, in train mode the login is
	 * unused as the session login is used, in production mode, there is a
	 * field where to enter the login and in test mode, there is a ListBox
	 * with multiple logins and a field to display the matching password.
	 */
	public AuthenticationModule(int mode) {

		this.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

		WellForm authenticationForm = new WellForm();
		this.add(authenticationForm);
		authenticationForm.setType(FormType.HORIZONTAL);
		Fieldset fieldset = new Fieldset();
		authenticationForm.add(fieldset);
		Legend authenticationLegend = new Legend("Module d'authentification");
		fieldset.add(authenticationLegend);

		ControlGroup loginControlGroup;
		ControlLabel loginLabel;
		Controls loginControl;
		switch(mode) {
			// 0 : Test mode
			case 0:
				loginControlGroup = new ControlGroup();
				fieldset.add(loginControlGroup);
				loginLabel = new ControlLabel("Identifiant");
				loginControlGroup.add(loginLabel);
				loginControl = new Controls();
				loginControlGroup.add(loginControl);
				ListBox loginListBox = new ListBox();
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
				TextBox loginTextBox = new TextBox();
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
		PasswordTextBox passwordTextBox = new PasswordTextBox();
		passwordControl.add(passwordTextBox);
		HelpBlock passwordHelpBlock = new HelpBlock();
		passwordHelpBlock.setVisible(false);
		passwordControl.add(passwordHelpBlock);

		HorizontalPanel buttonsPanel = new HorizontalPanel();
		buttonsPanel.addStyleName("buttonsPanel");
		Button submitButton = new Button("Valider");
		submitButton.setType(ButtonType.SUCCESS);
		buttonsPanel.add(submitButton);
		if (mode == 2) {
			Button forgotPasswordButton = new Button("Mot de passe oublié");
			forgotPasswordButton.setType(ButtonType.INFO);
			buttonsPanel.add(forgotPasswordButton);
		}
		authenticationForm.add(buttonsPanel);

		this.add(this.getApplet());

		VerticalPanel alertPanel = new VerticalPanel();
		this.add(alertPanel);
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
			"type=\"application/x-java-applet\" height=\"1\" width=\"1\">" +
			"<param name=\"mayscript\" value=\"yes\">" +
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
}
