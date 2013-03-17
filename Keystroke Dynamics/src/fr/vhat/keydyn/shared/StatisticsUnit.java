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

		int[] pressedToPressedTimesUnit =
				this.pressedToPressedStatistics.getTimeTable();
		int[] pressedToPressedTimesNew =
				keystrokeSequence.getPressedToPressedSequence().getTimeTable();
		for (int i = 0 ; i < pressedToPressedTimesUnit.length ; ++i) {
			pressedToPressedTimesUnit[i] = Math.round(
					(dataNumber * pressedToPressedTimesUnit[i]
							+ pressedToPressedTimesNew[i])
							/(float)(dataNumber + 1));
		}
		this.pressedToPressedStatistics.setTimeTable(pressedToPressedTimesUnit);

		int[] releasedToReleasedTimesUnit =
				this.releasedToReleasedStatistics.getTimeTable();
		int[] releasedToReleasedTimesNew =
				keystrokeSequence.getReleasedToReleasedSequence().getTimeTable();
		for (int i = 0 ; i < releasedToReleasedTimesUnit.length ; ++i) {
			releasedToReleasedTimesUnit[i] = Math.round(
					(dataNumber * releasedToReleasedTimesUnit[i]
							+ releasedToReleasedTimesNew[i])/
							(float)(dataNumber + 1));
		}
		this.releasedToReleasedStatistics.setTimeTable(
				releasedToReleasedTimesUnit);

		int[] pressedToReleasedTimesUnit =
				this.pressedToReleasedStatistics.getTimeTable();
		int[] pressedToReleasedTimesNew =
				keystrokeSequence.getPressedToReleasedSequence().getTimeTable();
		for (int i = 0 ; i < pressedToReleasedTimesUnit.length ; ++i) {
			pressedToReleasedTimesUnit[i] = Math.round(
					(dataNumber * pressedToReleasedTimesUnit[i]
							+ pressedToReleasedTimesNew[i])
							/(float)(dataNumber + 1));
		}
		this.pressedToReleasedStatistics.setTimeTable(
				pressedToReleasedTimesUnit);

		int[] releasedToPressedTimesUnit =
				this.releasedToPressedStatistics.getTimeTable();
		int[] releasedToPressedTimesNew =
				keystrokeSequence.getReleasedToPressedSequence().getTimeTable();
		for (int i = 0 ; i < releasedToPressedTimesUnit.length ; ++i) {
			releasedToPressedTimesUnit[i] = Math.round(
					(dataNumber * releasedToPressedTimesUnit[i]
							+ releasedToPressedTimesNew[i])/
							(float)(dataNumber + 1));
		}
		this.releasedToPressedStatistics.setTimeTable(
				releasedToPressedTimesUnit);
	}
}
