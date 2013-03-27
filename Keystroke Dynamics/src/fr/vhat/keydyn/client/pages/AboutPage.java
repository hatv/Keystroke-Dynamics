package fr.vhat.keydyn.client.pages;

import com.github.gwtbootstrap.client.ui.Tab;
import com.github.gwtbootstrap.client.ui.base.TextNode;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.google.gwt.user.client.ui.Widget;

public class AboutPage extends Tab {
	public AboutPage() {
		this.setHeading("À propos");
		this.setIcon(IconType.SEARCH);
		this.add(this.getContent());
	}

	private Widget getContent() {
		return new TextNode("Description détaillée du projet.");
	}
}
