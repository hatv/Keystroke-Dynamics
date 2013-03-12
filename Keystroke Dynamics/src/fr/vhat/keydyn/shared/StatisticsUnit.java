package fr.vhat.keydyn.shared;

import java.io.Serializable;

/**
 * Describe the Statics Unit object which is a set of four Time Sequence.
 * Used to store statistics information.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
@SuppressWarnings("serial")
public class StatisticsUnit implements Serializable {
	private TimeSequence pressedStatistics;
	private TimeSequence releasedStatistics;
	private TimeSequence pressedToReleasedStatistics;
	private TimeSequence releasedToPressedStatistics;

	public TimeSequence getPressedStatistics() {
		return pressedStatistics;
	}

	public void setPressedStatistics(TimeSequence pressedStatistics) {
		this.pressedStatistics = pressedStatistics;
	}

	public TimeSequence getReleasedStatistics() {
		return releasedStatistics;
	}

	public void setReleasedStatistics(TimeSequence releasedStatistics) {
		this.releasedStatistics = releasedStatistics;
	}

	public TimeSequence getPressedToReleasedStatistics() {
		return pressedToReleasedStatistics;
	}

	public void setPressedToReleasedStatistics(
			TimeSequence pressedToReleasedStatistics) {
		this.pressedToReleasedStatistics = pressedToReleasedStatistics;
	}

	public TimeSequence getReleasedToPressedStatistics() {
		return releasedToPressedStatistics;
	}

	public void setReleasedToPressedStatistics(
			TimeSequence releasedToPressedStatistics) {
		this.releasedToPressedStatistics = releasedToPressedStatistics;
	}

	public void set(int i, TimeSequence data) {
		switch(i) {
			case 0:
				this.setPressedStatistics(data);
				break;
			case 1:
				this.setReleasedStatistics(data);
				break;
			case 2:
				this.setPressedToReleasedStatistics(data);
				break;
			case 3:
				this.setReleasedToPressedStatistics(data);
				break;
			default:
				break;
		}
	}

	public void set(int i, int[] data) {
		this.set(i, new TimeSequence(data));
	}

	/**
	 * Add a keystroke sequence to the means.
	 * @param keystrokeSequence New keystroke sequence to add.
	 * @param dataNumber Number of data taken into account into the already
	 * computed mean.
	 */
	public void addToMeans(KeystrokeSequence keystrokeSequence,
			int dataNumber) {

		int[] pressToPressTimesUnit = this.pressedStatistics.getTimeTable();
		int[] pressToPressTimesNew =
				keystrokeSequence.getPressToPressSequence().getTimeTable();
		for (int i = 0 ; i < pressToPressTimesUnit.length ; ++i) {
			pressToPressTimesUnit[i] = Math.round(
					(dataNumber * pressToPressTimesUnit[i]
							+ pressToPressTimesNew[i])/(float)(dataNumber + 1));
		}
		this.pressedStatistics.setTimeTable(pressToPressTimesUnit);

		int[] releaseToReleaseTimesUnit =
				this.releasedStatistics.getTimeTable();
		int[] releaseToReleaseTimesNew =
				keystrokeSequence.getPressToPressSequence().getTimeTable();
		for (int i = 0 ; i < releaseToReleaseTimesUnit.length ; ++i) {
			releaseToReleaseTimesUnit[i] = Math.round(
					(dataNumber * releaseToReleaseTimesUnit[i]
							+ releaseToReleaseTimesNew[i])/
							(float)(dataNumber + 1));
		}
		this.releasedStatistics.setTimeTable(releaseToReleaseTimesUnit);

		int[] pressToReleaseTimesUnit =
				this.pressedToReleasedStatistics.getTimeTable();
		int[] pressToReleaseTimesNew =
				keystrokeSequence.getPressToReleaseSequence().getTimeTable();
		for (int i = 0 ; i < pressToReleaseTimesUnit.length ; ++i) {
			pressToReleaseTimesUnit[i] = Math.round(
					(dataNumber * pressToReleaseTimesUnit[i]
							+ pressToReleaseTimesNew[i])
							/(float)(dataNumber + 1));
		}
		this.pressedToReleasedStatistics.setTimeTable(pressToReleaseTimesUnit);

		int[] releaseToPressTimesUnit =
				this.releasedToPressedStatistics.getTimeTable();
		int[] releaseToPressTimesNew =
				keystrokeSequence.getReleaseToPressSequence().getTimeTable();
		for (int i = 0 ; i < releaseToPressTimesUnit.length ; ++i) {
			releaseToPressTimesUnit[i] = Math.round(
					(dataNumber * releaseToPressTimesUnit[i]
							+ releaseToPressTimesNew[i])/
							(float)(dataNumber + 1));
		}
		this.releasedToPressedStatistics.setTimeTable(releaseToPressTimesUnit);
	}
}
