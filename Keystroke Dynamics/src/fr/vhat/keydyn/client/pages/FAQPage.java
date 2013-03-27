package fr.vhat.keydyn.client.pages;

import com.github.gwtbootstrap.client.ui.Tab;
import com.github.gwtbootstrap.client.ui.base.TextNode;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.google.gwt.user.client.ui.Widget;

public class FAQPage extends Tab {
	public FAQPage() {
		this.setHeading("F.A.Q.");
		this.setIcon(IconType.QUESTION_SIGN);
		this.add(this.getContent());
	}

	private Widget getContent() {
		return new TextNode("Questions que vous pourriez vous poser !");
	}
}
