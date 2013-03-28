package fr.vhat.keydyn.client.pages;

import com.github.gwtbootstrap.client.ui.base.TextNode;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.google.gwt.user.client.ui.Widget;

import fr.vhat.keydyn.client.widgets.Page;

public class HomePage extends Page {
	public HomePage() {
		super("Accueil", IconType.HOME);
	}

	protected Widget getContent() {
		return new TextNode("Le but de cette application est...");
	}
}
