package fr.vhat.keydyn.client.pages;

import com.github.gwtbootstrap.client.ui.ControlGroup;
import com.github.gwtbootstrap.client.ui.ControlLabel;
import com.github.gwtbootstrap.client.ui.Controls;
import com.github.gwtbootstrap.client.ui.Fieldset;
import com.github.gwtbootstrap.client.ui.Form;
import com.github.gwtbootstrap.client.ui.Legend;
import com.github.gwtbootstrap.client.ui.TextBox;
import com.github.gwtbootstrap.client.ui.base.TextNode;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.google.gwt.user.client.ui.Widget;

import fr.vhat.keydyn.client.widgets.Page;

public class RegistrationPage extends Page {
	public RegistrationPage() {
		super("Inscription", IconType.OK);
	}

	protected Widget getContent() {
		Form registrationForm = new Form();
		Fieldset fieldset = new Fieldset();
		registrationForm.add(fieldset);
		Legend registrationLegend = new Legend("Formulaire de test");
		fieldset.add(registrationLegend);
		ControlGroup controlGroup1 = new ControlGroup();
		fieldset.add(controlGroup1);
		ControlLabel controlLab1 = new ControlLabel("Identifiant");
		Controls control1 = new Controls();
		controlGroup1.add(controlLab1);
		controlGroup1.add(control1);
		TextBox tb = new TextBox();
		control1.add(tb);
		return registrationForm;
	}
}
