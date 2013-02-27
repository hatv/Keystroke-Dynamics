package fr.vhat.keydyn.shared;

import java.io.Serializable;

/**
 * Describe the Statics Unit object which is a set of four Time Sequence.
 * Used to store statistics information.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
@SuppressWarnings("serial")
public class StatisticsUnit implements Serializable {
	private int[] pressedStatistics;
	private int[] releasedStatistics;
	private int[] pressedToReleasedStatistics;
	private int[] releasedToPressedStatistics;

	public int[] getPressedStatistics() {
		return pressedStatistics;
	}

	public void setPressedStatistics(int[] pressedStatistics) {
		this.pressedStatistics = pressedStatistics;
	}

	public int[] getReleasedStatistics() {
		return releasedStatistics;
	}

	public void setReleasedStatistics(int[] releasedStatistics) {
		this.releasedStatistics = releasedStatistics;
	}

	public int[] getPressedToReleasedStatistics() {
		return pressedToReleasedStatistics;
	}

	public void setPressedToReleasedStatistics(
			int[] pressedToReleasedStatistics) {
		this.pressedToReleasedStatistics = pressedToReleasedStatistics;
	}

	public int[] getReleasedToPressedStatistics() {
		return releasedToPressedStatistics;
	}

	public void setReleasedToPressedStatistics(
			int[] releasedToPressedStatistics) {
		this.releasedToPressedStatistics = releasedToPressedStatistics;
	}

	public void set(int i, int[] data) {
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

	private static String toString(int[] dataTable) {
		String result = "[";
		for (int i = 0 ; i < dataTable.length ; ++i) {
			result += dataTable[i];
			if (i != dataTable.length-1) {
				result += ", ";
			} else {
				result += "]";
			}
		}
		return result;
	}

	public String displayPressedMeans() {
		return toString(this.pressedStatistics);
	}
}
