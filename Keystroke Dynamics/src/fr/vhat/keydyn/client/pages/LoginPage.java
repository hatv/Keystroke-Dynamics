package fr.vhat.keydyn.client.pages;

import com.github.gwtbootstrap.client.ui.base.TextNode;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.google.gwt.user.client.ui.Widget;

import fr.vhat.keydyn.client.widgets.Page;

public class LoginPage extends Page {
	public LoginPage() {
		super("Connexion", IconType.SIGNIN);
	}

	protected Widget getContent() {
		return new TextNode("Bienvenue sur la page de connexion.");
	}
}
