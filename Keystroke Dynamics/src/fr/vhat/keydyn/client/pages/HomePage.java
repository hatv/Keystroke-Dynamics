package fr.vhat.keydyn.client.pages;

import com.github.gwtbootstrap.client.ui.Paragraph;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import fr.vhat.keydyn.client.widgets.GroupTabPanel;
import fr.vhat.keydyn.client.widgets.Page;

/**
 * Represent the home page.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
public class HomePage extends Page {

	/**
	 * Constructor of the home page.
	 */
	public HomePage(GroupTabPanel owner) {
		super("Accueil", IconType.HOME, owner);
	}

	@Override
	protected Widget getContent() {

		VerticalPanel panel = new VerticalPanel();
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		panel.setWidth("800px");

		Paragraph introduction = new Paragraph();
		String introductionText = new String(
			"La dynamique de frappe correspond, pour un individu, à sa façon " +
			"de taper sur un clavier. De la même manière que " +
			"l'écriture manuscrite est spécifique à chacun, ce site " +
			"cherche à mettre en exergue le caractère spécifique, et donc " +
			"biométrique, de la dynamique de frappe.");
		introduction.setText(introductionText);
		introduction.addStyleName("indent");
		panel.add(introduction);
		
		Paragraph objectives = new Paragraph();
		objectives.setText(
			"Une fois inscrit, vous pourrez expérimenter " +
			"les possibilités offertes par l'analyse de la dynamique " +
			"de frappe au travers de deux applications : authentification " +
			"et identification.");
		objectives.addStyleName("indent");
		panel.add(objectives);
		
		return panel;
	}
}
