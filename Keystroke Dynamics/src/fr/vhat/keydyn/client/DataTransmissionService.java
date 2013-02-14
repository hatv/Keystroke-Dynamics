package fr.vhat.keydyn.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("transmission")
public interface DataTransmissionService extends RemoteService {
	boolean saveKDData(String password, String pressTimes, String releaseTimes);
	String[][] getKDData();
}
