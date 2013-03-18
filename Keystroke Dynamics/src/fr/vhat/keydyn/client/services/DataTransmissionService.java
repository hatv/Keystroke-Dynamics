package fr.vhat.keydyn.client.services;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import fr.vhat.keydyn.shared.KeystrokeSequence;
import fr.vhat.keydyn.shared.StatisticsUnit;
import fr.vhat.keydyn.shared.entities.KDPassword;

/**
 * Keystroke Dynamics and statistics data communication.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
@RemoteServiceRelativePath("transmission")
public interface DataTransmissionService extends RemoteService {
	List<KDPassword> getKDData();
	StatisticsUnit getMeans();
	StatisticsUnit getSd();
	Float getDistance(KeystrokeSequence keystrokeSequence);
	Float getThreshold();
}
