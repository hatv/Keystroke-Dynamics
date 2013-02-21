package fr.vhat.keydyn.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import fr.vhat.keydyn.client.ComputationService;
import java.util.logging.Logger;

@SuppressWarnings("serial")
public class ComputationServiceImpl extends RemoteServiceServlet implements
		ComputationService {

	private static final Logger log = Logger.getLogger(
			ComputationServiceImpl.class.getName());
}
