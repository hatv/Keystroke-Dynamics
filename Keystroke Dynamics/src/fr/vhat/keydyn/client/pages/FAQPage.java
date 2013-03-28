package fr.vhat.keydyn.client.pages;

import com.github.gwtbootstrap.client.ui.base.TextNode;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.google.gwt.user.client.ui.Widget;

import fr.vhat.keydyn.client.widgets.Page;

public class FAQPage extends Page {
	public FAQPage() {
		super("F.A.Q.", IconType.QUESTION_SIGN);
	}

	protected Widget getContent() {
		return new TextNode("Questions que vous pourriez vous poser !");
	}
}
