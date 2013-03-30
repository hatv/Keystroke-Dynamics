package fr.vhat.keydyn.client.pages;

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
import com.github.gwtbootstrap.client.ui.base.TextNode;
import com.github.gwtbootstrap.client.ui.constants.ButtonType;
import com.github.gwtbootstrap.client.ui.constants.ControlGroupType;
import com.github.gwtbootstrap.client.ui.constants.FormType;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.github.gwtbootstrap.client.ui.constants.Placement;
import com.github.gwtbootstrap.client.ui.constants.ToggleType;
import com.github.gwtbootstrap.client.ui.constants.Trigger;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import fr.vhat.keydyn.client.widgets.Page;

public class RegistrationPage extends Page {
	public RegistrationPage() {
		super("Inscription", IconType.OK);
	}

	protected Widget getContent() {

		VerticalPanel panel = new VerticalPanel();
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

		Paragraph registrationDirections = new Paragraph("Remplissez le formulaire ci-dessous, vous recevrez votre mot de passe par courriel dans les plus brefs délais. N'oubliez pas de vérifier votre boîte de courrier indésirable.");
		panel.add(registrationDirections);
		panel.setCellHorizontalAlignment(registrationDirections, HasHorizontalAlignment.ALIGN_JUSTIFY);

		WellForm registrationForm = new WellForm();
		registrationForm.setType(FormType.HORIZONTAL);
		panel.add(registrationForm);
		Fieldset fieldset = new Fieldset();
		registrationForm.add(fieldset);
		Legend registrationLegend = new Legend("Formulaire d'inscription");
		fieldset.add(registrationLegend);

		ControlGroup loginControlGroup = new ControlGroup();
		fieldset.add(loginControlGroup);
		ControlLabel loginLabel = new ControlLabel("Identifiant");
		loginControlGroup.add(loginLabel);
		Controls loginControl = new Controls();
		loginControlGroup.add(loginControl);
		TextBox loginTextBox = new TextBox();
		loginControl.add(loginTextBox);
		Tooltip loginTooltip =
				new Tooltip("Entre 5 et 13 caractères minuscules uniquement.");
		loginTooltip.setWidget(loginTextBox);
		loginTooltip.setTrigger(Trigger.FOCUS);
		loginTooltip.setPlacement(Placement.RIGHT);
		loginTooltip.reconfigure();

		ControlGroup emailControlGroup = new ControlGroup();
		fieldset.add(emailControlGroup);
		ControlLabel emailLabel = new ControlLabel("Courriel");
		emailControlGroup.add(emailLabel);
		Controls emailControl = new Controls();
		emailControlGroup.add(emailControl);
		TextBox emailTextBox = new TextBox();
		emailControl.add(emailTextBox);
		Tooltip emailTooltip =
				new Tooltip("Adresse courriel valide pour la réception du mot" +
						" de passe. Vous ne recevrez aucun courriel " +
						"indésirable.");
		emailTooltip.setWidget(emailTextBox);
		emailTooltip.setTrigger(Trigger.FOCUS);
		emailTooltip.setPlacement(Placement.RIGHT);
		emailTooltip.reconfigure();

		ControlGroup birthYearControlGroup = new ControlGroup();
		fieldset.add(birthYearControlGroup);
		ControlLabel birthYearLabel = new ControlLabel("Année de naissance");
		birthYearControlGroup.add(birthYearLabel);
		Controls birthYearControl = new Controls();
		birthYearControlGroup.add(birthYearControl);
		TextBox birthYearTextBox = new TextBox();
		birthYearControl.add(birthYearTextBox);

		ControlGroup genderControlGroup = new ControlGroup();
		fieldset.add(genderControlGroup);
		ControlLabel genderLabel = new ControlLabel("Sexe");
		genderControlGroup.add(genderLabel);
		Controls genderControl = new Controls();
		genderControlGroup.add(genderControl);
		Button maleButton = new Button("Homme");
		Button femaleButton = new Button("Femme");
		ButtonGroup genderRadio = new ButtonGroup(maleButton, femaleButton);
		genderRadio.setToggle(ToggleType.RADIO);
		genderControl.add(genderRadio);

		ControlGroup countryControlGroup = new ControlGroup();
		fieldset.add(countryControlGroup);
		ControlLabel countryLabel = new ControlLabel("Pays");
		countryControlGroup.add(countryLabel);
		Controls countryControl = new Controls();
		countryControlGroup.add(countryControl);
		ListBox countryList = new ListBox();
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

		ControlGroup computerExperienceControlGroup = new ControlGroup();
		fieldset.add(computerExperienceControlGroup);
		ControlLabel computerExperienceLabel =
				new ControlLabel("Expérience informatique");
		computerExperienceControlGroup.add(computerExperienceLabel);
		Controls computerExperienceControl = new Controls();
		computerExperienceControlGroup.add(computerExperienceControl);
		ListBox computerExperienceList = new ListBox();
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

		ControlGroup typingUsageControlGroup = new ControlGroup();
		typingUsageControlGroup.setType(ControlGroupType.ERROR);
		fieldset.add(typingUsageControlGroup);
		ControlLabel typingUsageLabel =
				new ControlLabel("Utilisation du clavier");
		typingUsageControlGroup.add(typingUsageLabel);
		Controls typingUsageControl = new Controls();
		typingUsageControlGroup.add(typingUsageControl);
		ListBox typingUsageList = new ListBox();
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

		return panel;
	}
}
