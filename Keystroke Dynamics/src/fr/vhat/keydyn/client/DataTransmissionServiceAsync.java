package fr.vhat.keydyn.client;

import java.util.List;
import com.google.gwt.user.client.rpc.AsyncCallback;
import fr.vhat.keydyn.client.entities.KDPassword;
import fr.vhat.keydyn.shared.StatisticsUnit;

/**
 * Keystroke Dynamics and statistics data communication.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
public interface DataTransmissionServiceAsync {
	void saveKDData(String kdData, AsyncCallback<Boolean> callback);
	void getKDData(AsyncCallback<List<KDPassword>> callback);
	void getMeans(AsyncCallback<StatisticsUnit> callback);
}
