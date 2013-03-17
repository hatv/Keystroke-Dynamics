package fr.vhat.keydyn.shared;

import java.io.Serializable;

/**
 * Describe the Statics Unit object which is a set of four Time Sequence.
 * Used to store statistics information.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
@SuppressWarnings("serial")
public class StatisticsUnit implements Serializable {

	private int length;
	private TimeSequence pressedToPressedStatistics;
	private TimeSequence releasedToReleasedStatistics;
	private TimeSequence pressedToReleasedStatistics;
	private TimeSequence releasedToPressedStatistics;

	public StatisticsUnit() {}

	/**
	 * Constructor which initialize the Statistics Unit object with zero Time
	 * Sequences.
	 * @param length Length of the element.
	 */
	public StatisticsUnit(int length) {
		this.setLength(length);
		this.setPressedToPressedStatistics(
				new TimeSequence(this.getLength() - 1));
		this.setReleasedToReleasedStatistics(
				new TimeSequence(this.getLength() - 1));
		this.setPressedToReleasedStatistics(
				new TimeSequence(this.getLength()));
		this.setReleasedToPressedStatistics(
				new TimeSequence(this.getLength() - 1));
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public TimeSequence getPressedToPressedStatistics() {
		return pressedToPressedStatistics;
	}

	public void setPressedToPressedStatistics(
			TimeSequence pressedToPressedStatistics) {
		this.pressedToPressedStatistics = pressedToPressedStatistics;
	}

	public TimeSequence getReleasedToReleasedStatistics() {
		return releasedToReleasedStatistics;
	}

	public void setReleasedToReleasedStatistics(
			TimeSequence releasedToReleasedStatistics) {
		this.releasedToReleasedStatistics = releasedToReleasedStatistics;
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

	/**
	 * Set the given index Time Sequence to the specified value.
	 * @param i Index of the Time Sequence to set.
	 * @param data Time Sequence to set.
	 */
	public void set(int i, TimeSequence data) {
		switch(i) {
			case 0:
				this.setPressedToPressedStatistics(data);
				break;
			case 1:
				this.setReleasedToReleasedStatistics(data);
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

	/**
	 * Set the given index Time Sequence to the specified value.
	 * @param i Index of the Time Sequence to set.
	 * @param data Integer tables to set as Time Sequence.
	 */
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

		int[] pressToPressTimesUnit =
				this.pressedToPressedStatistics.getTimeTable();
		int[] pressToPressTimesNew =
				keystrokeSequence.getPressedToPressedSequence().getTimeTable();
		for (int i = 0 ; i < pressToPressTimesUnit.length ; ++i) {
			pressToPressTimesUnit[i] = Math.round(
					(dataNumber * pressToPressTimesUnit[i]
							+ pressToPressTimesNew[i])/(float)(dataNumber + 1));
		}
		this.pressedToPressedStatistics.setTimeTable(pressToPressTimesUnit);

		int[] releaseToReleaseTimesUnit =
				this.releasedToReleasedStatistics.getTimeTable();
		int[] releaseToReleaseTimesNew =
				keystrokeSequence.getPressedToPressedSequence().getTimeTable();
		for (int i = 0 ; i < releaseToReleaseTimesUnit.length ; ++i) {
			releaseToReleaseTimesUnit[i] = Math.round(
					(dataNumber * releaseToReleaseTimesUnit[i]
							+ releaseToReleaseTimesNew[i])/
							(float)(dataNumber + 1));
		}
		this.releasedToReleasedStatistics.setTimeTable(releaseToReleaseTimesUnit);

		int[] pressToReleaseTimesUnit =
				this.pressedToReleasedStatistics.getTimeTable();
		int[] pressToReleaseTimesNew =
				keystrokeSequence.getPressedToReleasedSequence().getTimeTable();
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
				keystrokeSequence.getReleasedToPressedSequence().getTimeTable();
		for (int i = 0 ; i < releaseToPressTimesUnit.length ; ++i) {
			releaseToPressTimesUnit[i] = Math.round(
					(dataNumber * releaseToPressTimesUnit[i]
							+ releaseToPressTimesNew[i])/
							(float)(dataNumber + 1));
		}
		this.releasedToPressedStatistics.setTimeTable(releaseToPressTimesUnit);
	}
}
