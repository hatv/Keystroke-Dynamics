package fr.vhat.keydyn.client.pages;

import com.github.gwtbootstrap.client.ui.Paragraph;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
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

		VerticalPanel panel = new VerticalPanel();
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		panel.setWidth("800px");

		Paragraph introduction = new Paragraph();
		String introductionText = new String(
			"Ce projet a été réalisé par Victor Hatinguais " +
			"dans le cadre de la Maîtrise en " +
			"Génie Électrique et Informatique de l'Université de Sherbrooke " +
			"sous la supervision du professeur Frédéric Mailhot.");
		introduction.setText(introductionText);
		introduction.addStyleName("indent");
		//panel.add(introduction);
		
		Paragraph objectives = new Paragraph();
		objectives.setText(
			"Les objectifs du projet sont multiples : découvrir l'étendue " +
			"des possibilités applicatives offertes par la dynamique " +
			"de frappe, mettre en oeuvre, à l'échelle d'un étudiant, " +
			"un prototype fonctionnel, faire connaître la technologie " +
			"au public et enfin, faciliter toute étude ultérieure dans " +
			"le domaine par la mise à disposition du code source du projet.");
		objectives.addStyleName("indent");
		panel.add(objectives);
		
		return panel;
	}
}
