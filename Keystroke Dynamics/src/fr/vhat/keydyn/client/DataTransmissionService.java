package fr.vhat.keydyn.client;

import java.util.List;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import fr.vhat.keydyn.client.entities.KDPassword;
import fr.vhat.keydyn.shared.StatisticsUnit;

/**
 * Keystroke Dynamics and statistics data communication.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
@RemoteServiceRelativePath("transmission")
public interface DataTransmissionService extends RemoteService {
	boolean saveKDData(String password, String pressTimes, String releaseTimes);
	List<KDPassword> getKDData();
	StatisticsUnit getMeans();
}
