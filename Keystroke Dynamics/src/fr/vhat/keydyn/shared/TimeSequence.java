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

	/**
	 * Constructor which initialize each element to 0.
	 * @param length Length of the Time Sequence to initialize.
	 */
	public TimeSequence(int length) {
		this.setTimeTable(new int[length]);
	}

	/**
	 * Constructor.
	 * @param timeTable Integers table to initialize to set the time table.
	 */
	public TimeSequence(int[] timeTable) {
		this.setTimeTable(timeTable);
	}

	/**
	 * Constructor.
	 * @param timeString String to use to initialize the time table.
	 */
	public TimeSequence(String timeString) {
		this.setTimeTable(timeString);
	}

	public int[] getTimeTable() {
		return timeTable;
	}

	public void setTimeTable(int[] timeTable) {
		this.timeTable = timeTable;
	}

	/**
	 * Set the time table of the Time Sequence from a well formated string.
	 * @param timeString String to use to set the time table.
	 */
	public void setTimeTable(String timeString) {
		String[] times =
				timeString.substring(1, timeString.length() - 1).split(",");
		this.timeTable = new int[times.length];
		for (int i = 0 ; i < times.length ; ++i) {
			this.timeTable[i] = Integer.parseInt(times[i].trim());
		}
	}

	/**
	 * Return the length of the Time Sequence which is the length of the time
	 * table.
	 * @return Length of the Time Sequence.
	 */
	public int length() {
		return this.timeTable.length;
	}

	/**
	 * Convert the Time Sequence into a printable string.
	 */
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
