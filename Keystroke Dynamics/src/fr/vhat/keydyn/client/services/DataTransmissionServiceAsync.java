package fr.vhat.keydyn.client.services;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import fr.vhat.keydyn.shared.KeystrokeSequence;
import fr.vhat.keydyn.shared.StatisticsUnit;
import fr.vhat.keydyn.shared.entities.KDPassword;

/**
 * Keystroke Dynamics and statistics data communication.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
public interface DataTransmissionServiceAsync {
	void getKDData(AsyncCallback<List<KDPassword>> callback);
	void getMeans(AsyncCallback<StatisticsUnit> callback);
	void getSd(AsyncCallback<StatisticsUnit> callback);
	void getDistance(KeystrokeSequence keystrokeSequence,
			AsyncCallback<Float> callback);
	void getThreshold(AsyncCallback<Float> callback);
	void getTrainingPercent(AsyncCallback<Integer> callback);
}
