package fr.vhat.keydyn.client.widgets;

import com.github.gwtbootstrap.client.ui.Tab;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.google.gwt.user.client.ui.Widget;

/**
 * Abstract class from which inherit every page of the application : it is a
 * kind of Tab to add to a GroupTabPanel.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
public abstract class Page extends Tab {

	GroupTabPanel owner;

	/**
	 * Constructor.
	 * @param heading Title of the tab.
	 * @param icon Icon to add to the tab.
	 */
	protected Page(String heading, IconType icon, GroupTabPanel owner) {
		this.setHeading(heading);
		this.setIcon(icon);
		this.setOwner(owner);
		this.add(this.getContent());
	}

	/**
	 * Abstract method that should be overridden in the inherited classes.
	 * @return The content of the panel.
	 */
	protected abstract Widget getContent();

	public GroupTabPanel getOwner() {
		return owner;
	}

	private void setOwner(GroupTabPanel owner) {
		this.owner = owner;
	}
}
