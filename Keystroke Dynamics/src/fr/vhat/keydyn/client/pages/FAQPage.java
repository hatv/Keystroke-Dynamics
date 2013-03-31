package fr.vhat.keydyn.client.pages;

import com.github.gwtbootstrap.client.ui.base.TextNode;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.google.gwt.user.client.ui.Widget;

import fr.vhat.keydyn.client.widgets.GroupTabPanel;
import fr.vhat.keydyn.client.widgets.Page;

/**
 * Represent the Frequently Asked Questions page.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
public class FAQPage extends Page {

	/**
	 * Constructor of the Frequently Asked Questions page.
	 */
	public FAQPage(GroupTabPanel owner) {
		super("F.A.Q.", IconType.QUESTION_SIGN, owner);
	}

	@Override
	protected Widget getContent() {
		return new TextNode("Questions que vous pourriez vous poser !");
	}
}
