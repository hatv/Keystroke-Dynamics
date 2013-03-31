package fr.vhat.keydyn.client.pages;

import com.github.gwtbootstrap.client.ui.base.TextNode;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.google.gwt.user.client.ui.Widget;

import fr.vhat.keydyn.client.widgets.GroupTabPanel;
import fr.vhat.keydyn.client.widgets.Page;

/**
 * Represent the registration page.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
public class LoginPage extends Page {

	/**
	 * Constructor of the login page.
	 */
	public LoginPage(GroupTabPanel owner) {
		super("Connexion", IconType.SIGNIN, owner);
	}

	@Override
	protected Widget getContent() {
		return new TextNode("Bienvenue sur la page de connexion.");
	}
}
