package fr.vhat.keydyn.client.pages;

import com.github.gwtbootstrap.client.ui.base.TextNode;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.google.gwt.user.client.ui.Widget;

import fr.vhat.keydyn.client.widgets.GroupTabPanel;
import fr.vhat.keydyn.client.widgets.Page;

/**
 * Represent the about page.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
public class AboutPage extends Page {

	/**
	 * Constructor of the about page.
	 */
	public AboutPage(GroupTabPanel owner) {
		super("À propos", IconType.SEARCH, owner);
	}

	@Override
	protected Widget getContent() {
		return new TextNode("Description détaillée du projet.");
	}
}
