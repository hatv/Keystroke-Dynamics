package fr.vhat.keydyn.client.pages;

import com.github.gwtbootstrap.client.ui.Tab;
import com.github.gwtbootstrap.client.ui.base.TextNode;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.google.gwt.user.client.ui.Widget;

public class HomePage extends Tab {
	public HomePage() {
		this.setHeading("Accueil");
		this.setIcon(IconType.HOME);
		this.add(this.getContent());
	}

	private Widget getContent() {
		return new TextNode("Le but de cette application est...");
	}
}
