package fr.vhat.keydyn.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import fr.vhat.keydyn.client.ComputationService;
import fr.vhat.keydyn.client.entities.KDPassword;
import fr.vhat.keydyn.shared.KeystrokeSequence;
import fr.vhat.keydyn.shared.StatisticsUnit;
import fr.vhat.keydyn.shared.TimeSequence;

import java.util.List;
import java.util.logging.Logger;

/**
 * The Computation Service provides the necessary functions to compute or
 * retrieve the statistics, distances and thresholds needed to authenticate
 * users.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
@SuppressWarnings("serial")
public class ComputationServiceImpl extends RemoteServiceServlet implements
		ComputationService {

	private static final Logger log = Logger.getLogger(
			ComputationServiceImpl.class.getName());

	/**
	 * Compute and update the standard deviation vectors of an user.
	 * @param login Login of the user.
	 * @return Standard deviations of the user in a StatisticsUnit format or
	 * null.
	 */
	public static StatisticsUnit computeSd(String login) {
		List<KDPassword> kdData =
				DataTransmissionServiceImpl.getUserKDData(login);
		if (kdData != null) {
			int kdDataNumber = kdData.size();
			if (kdDataNumber > 0) {
				StatisticsUnit result = new StatisticsUnit();
				TimeSequence[] pressedData =
						new TimeSequence[kdDataNumber];
				TimeSequence[] releasedData =
						new TimeSequence[kdDataNumber];
				TimeSequence[] pressedToReleasedData =
						new TimeSequence[kdDataNumber];
				TimeSequence[] releasedToPressedData =
						new TimeSequence[kdDataNumber];
				KeystrokeSequence keystrokeSequence;
				for (int i = 0 ; i < kdDataNumber ; ++i) {
					keystrokeSequence =
							kdData.get(i).getKeystrokeSequence();
					pressedData[i] =
							keystrokeSequence
							.getPressToPressSequence();
					releasedData[i] =
							keystrokeSequence
							.getReleaseToReleaseSequence();
					pressedToReleasedData[i] =
							keystrokeSequence
							.getPressToReleaseSequence();
					releasedToPressedData[i] =
							keystrokeSequence
							.getReleaseToPressSequence();
				}
				TimeSequence[] timeSequenceTable;
				int[] sdTimeTable;
				StatisticsUnit means =
						DataTransmissionServiceImpl.getUserMeans(login);
				TimeSequence vectorMeans;
				// For each vector
				for (int i = 0 ; i < 4 ; ++i) {
					switch(i) {
						case 0:
							timeSequenceTable = pressedData;
							vectorMeans = means.getPressedStatistics();
							break;
						case 1:
							timeSequenceTable = releasedData;
							vectorMeans = means.getReleasedStatistics();
							break;
						case 2:
							timeSequenceTable = pressedToReleasedData;
							vectorMeans =
									means.getPressedToReleasedStatistics();
							break;
						case 3:
							timeSequenceTable = releasedToPressedData;
							vectorMeans =
									means.getReleasedToPressedStatistics();
							break;
						default:
							timeSequenceTable = pressedData;
							vectorMeans = means.getPressedStatistics();
							break;
					}
					sdTimeTable = new int[vectorMeans.length()];
					// For each element
					for (int j = 0 ; j < vectorMeans.length() ;
							++j) {
						int variance = 0;
						int mean = vectorMeans.getTimeTable()[j];
						// Compute the standard deviation
						for (int k = 0 ; k < timeSequenceTable.length ; ++k) {
							variance +=
									(int)Math.pow(
											timeSequenceTable[k]
													.getTimeTable()[j] - mean,
												2);
						}
						variance /= timeSequenceTable.length;
						sdTimeTable[j] = (int)Math.sqrt(variance);
					}
					result.set(i, sdTimeTable);
				}
				return result;
			}
		} else {
			log.info("Unable to compute standard deviations.");
		}
		return null;
	}

	/**
	 * Compute the distance between a Keystroke Sequence and the means and
	 * standard deviations stored in the data store.
	 * @param keystrokeSequence Keystroke Sequence to compute the distance from.
	 * @param means Means vector stored in the data store.
	 * @param sd Standard deviations vector stored in the data store.
	 * @return Distance between the Keystroke Sequence element and the data
	 * stored in the data store.
	 */
	public static Float distance(KeystrokeSequence keystrokeSequence,
			StatisticsUnit means, StatisticsUnit sd) {
		Float distance = (float)0;
		int sdSum = 0;
		int parametersNumber = 0;
		TimeSequence pressToPressSequence =
				keystrokeSequence.getPressToPressSequence();
		TimeSequence pressToPressSequenceMeans = means.getPressedStatistics();
		TimeSequence pressToPressSequenceSd = sd.getPressedStatistics();
		for (int i = 0 ; i < pressToPressSequence.length() ; ++i) {
			distance += Math.abs((float)pressToPressSequence.getTimeTable()[i] 
					- (float)pressToPressSequenceMeans.getTimeTable()[i])
					/ (float)pressToPressSequenceSd.getTimeTable()[i];
			sdSum += pressToPressSequenceSd.getTimeTable()[i];
			parametersNumber++;
		}
		distance /= sdSum * parametersNumber;
		return ((float)Math.round(1000000 * distance)) / 1000;
	}
}
