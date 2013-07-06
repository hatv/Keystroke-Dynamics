package fr.vhat.keydyn.client.pages;

import com.github.gwtbootstrap.client.ui.Paragraph;
import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import fr.vhat.keydyn.client.services.AuthenticationService;
import fr.vhat.keydyn.client.services.AuthenticationServiceAsync;
import fr.vhat.keydyn.client.widgets.GroupTabPanel;
import fr.vhat.keydyn.client.widgets.InformationPopup;
import fr.vhat.keydyn.client.widgets.Page;
import fr.vhat.keydyn.client.widgets.TrainingProgressBar;

/**
 * Represent the statistics page.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
public class StatisticsPage extends Page {

	// Services creation for RPC communication between client and server sides.
	private static AuthenticationServiceAsync authenticationService =
			GWT.create(AuthenticationService.class);

	private TrainingProgressBar trainingBar;
	private Paragraph numberOfStrokesRegisteredParagraph;

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

		numberOfStrokesRegisteredParagraph = new Paragraph();
		numberOfStrokesRegisteredParagraph.addStyleName("indent");
		panel.add(numberOfStrokesRegisteredParagraph);

		Paragraph progressBarParagraph = new Paragraph();
		progressBarParagraph.setText("Niveau de fiabilité du système :");
		progressBarParagraph.addStyleName("indent");
		panel.add(progressBarParagraph);

		trainingBar = new TrainingProgressBar();
		panel.add(trainingBar);

		return panel;
	}

	public void refresh() {

		trainingBar.refresh();

		authenticationService.getUserStrokesNumber(
				new AsyncCallback<Integer>() {

			@Override
			public void onSuccess(Integer keystrokes) {
				numberOfStrokesRegisteredParagraph.setText(
					"Nombre de frappes de votre mot de passe enregistrées : "
							+ keystrokes);
			}

			@Override
			public void onFailure(Throwable caught) {
				InformationPopup popup = new InformationPopup(
						"Frappes enregistrées", true);
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
