package fr.vhat.keydyn.client.pages;

import com.github.gwtbootstrap.client.ui.base.TextNode;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.google.gwt.user.client.ui.Widget;

import fr.vhat.keydyn.client.widgets.GroupTabPanel;
import fr.vhat.keydyn.client.widgets.Page;

/**
 * Represent the home page.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
public class HomePage extends Page {

	/**
	 * Constructor of the home page.
	 */
	public HomePage(GroupTabPanel owner) {
		super("Accueil", IconType.HOME, owner);
	}

	@Override
	protected Widget getContent() {
		return new TextNode("Le but de cette application est...");
	}
}
