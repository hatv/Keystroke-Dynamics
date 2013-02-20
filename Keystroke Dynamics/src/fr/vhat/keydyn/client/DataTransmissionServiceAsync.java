package fr.vhat.keydyn.client;

import java.util.List;
import com.google.gwt.user.client.rpc.AsyncCallback;
import fr.vhat.keydyn.client.entities.KDPassword;

public interface DataTransmissionServiceAsync {
	void saveKDData(String password, String pressTimes, String releaseTimes,
			AsyncCallback<Boolean> callback);
	void getKDData(AsyncCallback<List<KDPassword>> callback);
}
