package fr.vhat.keydyn.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import fr.vhat.keydyn.client.ComputationService;
import java.util.logging.Logger;

/**
 * The Computation Service provides the necessary functions to compute the
 * statistics, distances and thresholds needed to authenticate users.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
@SuppressWarnings("serial")
public class ComputationServiceImpl extends RemoteServiceServlet implements
		ComputationService {

	private static final Logger log = Logger.getLogger(
			ComputationServiceImpl.class.getName());
}
