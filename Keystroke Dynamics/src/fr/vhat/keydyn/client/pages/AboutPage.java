package fr.vhat.keydyn.client.pages;

import com.github.gwtbootstrap.client.ui.base.TextNode;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.google.gwt.user.client.ui.Widget;

import fr.vhat.keydyn.client.widgets.Page;

public class AboutPage extends Page {
	public AboutPage() {
		super("À propos", IconType.SEARCH);
	}

	protected Widget getContent() {
		return new TextNode("Description détaillée du projet.");
	}
}
