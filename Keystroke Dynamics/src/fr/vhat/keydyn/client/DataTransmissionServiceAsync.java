package fr.vhat.keydyn.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DataTransmissionServiceAsync {
	void saveKDData(String password, String pressTimes, String releaseTimes,
			AsyncCallback<Boolean> callback);
	void getKDData(AsyncCallback<String[][]> callback);
}
