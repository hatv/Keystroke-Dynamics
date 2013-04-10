package fr.vhat.keydyn.client.pages;

import com.github.gwtbootstrap.client.ui.Paragraph;
import com.github.gwtbootstrap.client.ui.ProgressBar;
import com.github.gwtbootstrap.client.ui.base.ProgressBarBase.Color;
import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import fr.vhat.keydyn.client.services.DataTransmissionService;
import fr.vhat.keydyn.client.services.DataTransmissionServiceAsync;
import fr.vhat.keydyn.client.widgets.GroupTabPanel;
import fr.vhat.keydyn.client.widgets.InformationPopup;
import fr.vhat.keydyn.client.widgets.Page;

/**
 * Represent the statistics page.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
public class StatisticsPage extends Page {

	private ProgressBar trainingBar;

	// Services creation for RPC communication between client and server sides.
	private static DataTransmissionServiceAsync dataTransmissionService =
			GWT.create(DataTransmissionService.class);

	/**
	 * Constructor of the statistics page.
	 */
	public StatisticsPage(GroupTabPanel owner) {

		super("Statistiques", IconType.BAR_CHART, owner);

		this.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				refreshProgressBar();
			}
		});
	}

	@Override
	protected Widget getContent() {

		VerticalPanel panel = new VerticalPanel();
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		//panel.setWidth("800px");

		Paragraph introduction = new Paragraph();
		String introductionText = new String("Cette page donne des " +
				"informations sur le compte : entraînement, activité " +
				"et statistiques.");
		introduction.setText(introductionText);
		introduction.addStyleName("indent");
		panel.add(introduction);

		trainingBar = new ProgressBar();
		trainingBar.setText("Entraînement du système");
		trainingBar.setPercent(0);
		this.refreshProgressBar();
		panel.add(trainingBar);

		return panel;
	}

	/**
	 * Call server to get the training level of the account.
	 */
	private void refreshProgressBar() {

		dataTransmissionService.getTrainingPercent(
				new AsyncCallback<Integer>() {

			@Override
			public void onSuccess(Integer percent) {
				if (percent == null)
					return;
				if (percent <= 30) {
					trainingBar.setColor(Color.DANGER);
				} else if (percent <= 60) {
					trainingBar.setColor(Color.WARNING);
				} else if (percent < 100) {
					trainingBar.setColor(Color.INFO);
				} else {
					trainingBar.setColor(Color.SUCCESS);
				}
				trainingBar.setPercent(percent);
			}

			@Override
			public void onFailure(Throwable caught) {
				InformationPopup popup = new InformationPopup(
						"Statistiques", true);
				popup.setAlertType(AlertType.WARNING);
				popup.setAlertTitle("Échec de connexion au serveur.");
				popup.setAlertContent("Vérifiez votre connexion internet.");
				popup.showAlert();
				popup.show();
				popup.hideWithDelay();
			}
		});
	}
}
