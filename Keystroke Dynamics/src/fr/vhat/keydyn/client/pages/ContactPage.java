package fr.vhat.keydyn.client.pages;

import com.github.gwtbootstrap.client.ui.base.TextNode;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.google.gwt.user.client.ui.Widget;

import fr.vhat.keydyn.client.widgets.Page;

public class ContactPage extends Page {
	public ContactPage() {
		super("Contact", IconType.ENVELOPE_ALT);
	}

	protected Widget getContent() {
		return new TextNode("Pour me contacter, remplissez le formulaire :");
	}
}
