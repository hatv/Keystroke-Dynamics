package fr.vhat.keydyn.client.pages;

import com.github.gwtbootstrap.client.ui.base.TextNode;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.google.gwt.user.client.ui.Widget;

import fr.vhat.keydyn.client.widgets.GroupTabPanel;
import fr.vhat.keydyn.client.widgets.Page;

/**
 * Represent the contact page.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
public class ContactPage extends Page {

	/**
	 * Constructor of the contact page.
	 */
	public ContactPage(GroupTabPanel owner) {
		super("Contact", IconType.ENVELOPE_ALT, owner);
	}

	@Override
	protected Widget getContent() {
		return new TextNode("Pour me contacter, remplissez le formulaire :");
	}
}
