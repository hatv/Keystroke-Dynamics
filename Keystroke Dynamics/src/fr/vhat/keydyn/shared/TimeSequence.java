package fr.vhat.keydyn.shared;

import java.io.Serializable;

/**
 * A Time Sequence is an integer table used to store keystroke dynamics times.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
@SuppressWarnings("serial")
public class TimeSequence implements Serializable {
	private int[] timeTable;

	public TimeSequence() {}
	
	public TimeSequence(int[] timeTable) {
		this.setTimeTable(timeTable);
	}

	public TimeSequence(String timeString) {
		this.setTimeTable(timeString);
	}

	public int[] getTimeTable() {
		return timeTable;
	}

	public void setTimeTable(int[] timeTable) {
		this.timeTable = timeTable;
	}

	public void setTimeTable(String timeString) {
		String[] times =
				timeString.substring(1, timeString.length() - 1).split(",");
		this.timeTable = new int[times.length];
		for (int i = 0 ; i < times.length ; ++i) {
			this.timeTable[i] = Integer.parseInt(times[i].trim());
		}
	}

	public int length() {
		return this.timeTable.length;
	}

	public String toString() {
		String result = "[";
		for (int i = 0 ; i < this.timeTable.length ; ++i) {
			result += this.timeTable[i];
			if (i != this.timeTable.length - 1) {
				result += ", ";
			} else {
				result += "]";
			}
		}
		return result;
	}
}
