package fr.vhat.keydyn.client.pages;

import com.github.gwtbootstrap.client.ui.Tab;
import com.github.gwtbootstrap.client.ui.base.TextNode;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.google.gwt.user.client.ui.Widget;

public class ContactPage extends Tab {
	public ContactPage() {
		this.setHeading("Contact");
		this.setIcon(IconType.ENVELOPE_ALT);
		this.add(this.getContent());
	}

	private Widget getContent() {
		return new TextNode("Pour me contacter, remplissez le formulaire :");
	}
}
