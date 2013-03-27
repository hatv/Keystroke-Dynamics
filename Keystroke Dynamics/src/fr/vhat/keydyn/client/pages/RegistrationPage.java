package fr.vhat.keydyn.client.pages;

import com.github.gwtbootstrap.client.ui.Tab;
import com.github.gwtbootstrap.client.ui.base.TextNode;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.google.gwt.user.client.ui.Widget;

public class RegistrationPage extends Tab {
	public RegistrationPage() {
		this.setHeading("Inscription");
		this.setIcon(IconType.OK);
		this.add(this.getContent());
	}

	private Widget getContent() {
		return new TextNode("Inscription.");
	}
}
