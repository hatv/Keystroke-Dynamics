package fr.vhat.keydyn.client.pages;

import com.github.gwtbootstrap.client.ui.Paragraph;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import fr.vhat.keydyn.client.widgets.GroupTabPanel;
import fr.vhat.keydyn.client.widgets.Page;
import fr.vhat.keydyn.client.widgets.TrainingProgressBar;

/**
 * Represent the statistics page.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
public class StatisticsPage extends Page {

	private TrainingProgressBar trainingBar;

	/**
	 * Constructor of the statistics page.
	 */
	public StatisticsPage(GroupTabPanel owner) {

		super("Statistiques", IconType.BAR_CHART, owner);

		this.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				trainingBar.refresh();
			}
		});
	}

	@Override
	protected Widget getContent() {

		VerticalPanel panel = new VerticalPanel();
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		panel.setWidth("800px");

		Paragraph introduction = new Paragraph();
		String introductionText = new String("Cette page donne des " +
				"informations sur le compte : entraînement, activité " +
				"et statistiques.");
		introduction.setText(introductionText);
		introduction.addStyleName("indent");
		panel.add(introduction);

		trainingBar = new TrainingProgressBar();
		panel.add(trainingBar);

		return panel;
	}
}
