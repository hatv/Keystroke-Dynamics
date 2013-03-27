package fr.vhat.keydyn.client.pages;

import com.github.gwtbootstrap.client.ui.Tab;
import com.github.gwtbootstrap.client.ui.base.TextNode;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.google.gwt.user.client.ui.Widget;

public class LoginPage extends Tab {
	public LoginPage() {
		this.setHeading("Connexion");
		this.setIcon(IconType.SIGNIN);
		this.add(this.getContent());
	}

	private Widget getContent() {
		return new TextNode("Bienvenue sur la page de connexion.");
	}
}
