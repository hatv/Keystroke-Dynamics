package fr.vhat.keydyn.client.widgets;

import com.github.gwtbootstrap.client.ui.ProgressBar;
import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

import fr.vhat.keydyn.client.services.DataTransmissionService;
import fr.vhat.keydyn.client.services.DataTransmissionServiceAsync;

/**
 * Training progress bar is a progress bar that retrieve the training progress
 * of an account in order to display it.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
public class TrainingProgressBar extends ProgressBar {

	// Services creation for RPC communication between client and server sides.
	private static DataTransmissionServiceAsync dataTransmissionService =
			GWT.create(DataTransmissionService.class);

	/**
	 * Constructor.
	 */
	public TrainingProgressBar() {
		super();
		this.setPercent(0);
		this.refresh();
	}

	/**
	 * Call server to get the training level of the account.
	 */
	public void refresh() {

		dataTransmissionService.getTrainingPercent(
				new AsyncCallback<Integer>() {

			@Override
			public void onSuccess(Integer percent) {
				if (percent == null)
					return;
				if (percent <= 30) {
					setColor(Color.DANGER);
				} else if (percent <= 60) {
					setColor(Color.WARNING);
				} else if (percent < 100) {
					setColor(Color.INFO);
				} else {
					setColor(Color.SUCCESS);
				}
				setPercent(percent);
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
