package fr.vhat.keydyn.client.widgets;

import com.github.gwtbootstrap.client.ui.Tab;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.google.gwt.user.client.ui.Widget;

public abstract class Page extends Tab {
	protected Page(String heading, IconType icon) {
		this.setHeading(heading);
		this.setIcon(icon);
		this.add(this.getContent());
	}

	protected abstract Widget getContent();
}
