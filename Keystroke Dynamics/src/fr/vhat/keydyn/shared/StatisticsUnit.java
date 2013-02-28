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
}
