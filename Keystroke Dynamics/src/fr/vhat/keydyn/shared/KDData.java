package fr.vhat.keydyn.shared;

/**
 * Implement several static functions to manage Keystroke Dynamics data.
 */
public class KDData {

	/**
	 * Convert the Keystroke Dynamics data from a string to three strings.
	 * @param kdData Keystroke Dynamics data string.
	 * @return Keystroke Dynamics data strings.
	 */
	public static String[] strings(String kdData) {
		return kdData.split(";");
	}

	/**
	 * Convert the Keystroke Dynamics data from a string and two integer tables
	 * to three strings.
	 * @param password Password string.
	 * @param pressedTimes Integer table of pressed times.
	 * @param releasedTimes Integer table of released times.
	 * @return Keystroke Dynamics data strings.
	 */
	public static String[] strings(String password, int[] pressedTimes,
			int[] releasedTimes) {
		String[] result = new String[3];
		result[0] = password;

		String pressedTimesString = "[";
		for (int i = 0 ; i < pressedTimes.length ; ++i) {
			pressedTimesString += pressedTimes[i];
			if (i != pressedTimes.length - 1) {
				pressedTimesString += ", ";
			}
		}
		pressedTimesString += "]";
		result[1] = pressedTimesString;

		String releasedTimesString = "[";
		for (int i = 0 ; i < releasedTimes.length ; ++i) {
			releasedTimesString += releasedTimes[i];
			if (i != releasedTimes.length - 1) {
				releasedTimesString += ", ";
			}
		}
		releasedTimesString += "]";
		result[2] = releasedTimesString;

		return result;
	}

	/**
	 * Convert the Keystroke Dynamics data from a string table to a string.
	 * @param kdData Keystroke Dynamics data strings.
	 * @return Keystroke Dynamics data string.
	 */
	public static String string(String[] kdData) {
		return kdData[0] + ";" + kdData[1] + ";" + kdData[2];
	}

	/**
	 * Convert the Keystroke Dynamics data from a string and two integer tables
	 * to a string.
	 * @param password Password string.
	 * @param pressedTimes Integer table of pressed times.
	 * @param releasedTimes Integer table of released times.
	 * @return Keystroke Dynamics data string.
	 */
	public static String string(String password, int[] pressedTimes,
			int[] releasedTimes) {
		return string(strings(password, pressedTimes, releasedTimes));
	}

	/**
	 * Get the password from a Keystroke Dynamics data string.
	 * @param kdData Keystroke Dynamics data string.
	 * @return Password string.
	 */
	public static String password(String kdData) {
		return password(strings(kdData));
	}

	/**
	 * Get the password from Keystroke Dynamics data strings.
	 * @param kdData Keystroke Dynamics data strings.
	 * @return Password string.
	 */
	public static String password(String[] kdData) {
		return kdData[0];
	}

	/**
	 * Get the typing times from a Keystroke Dynamics data string.
	 * Field 0 contains pressed times, field 1 contains released times.
	 * @param kdData Keystroke Dynamics data string.
	 * @return Pressed times into two tables of integer.
	 */
	public static int[][] typingTimes(String kdData) {
		return typingTimes(strings(kdData));
	}

	/**
	 * Get the typing times from Keystroke Dynamics data strings.
	 * Field 0 contains pressed times, field 1 contains released times.
	 * @param kdData Keystroke Dynamics data strings.
	 * @return Pressed times into two tables of integer.
	 */
	public static int[][] typingTimes(String[] kdData) {
		int tmpTime;

		String pressedTimesString = kdData[1];
		String[] pressedTimesStrings =
				pressedTimesString.substring(1, pressedTimesString.length() - 1)
				.split(",");
		int[] pressedTimes = new int[pressedTimesStrings.length];
		for (int i = 0 ; i < pressedTimesStrings.length ; ++i) {
			tmpTime = Integer.parseInt(pressedTimesStrings[i].trim());
			pressedTimes[i] = tmpTime;
		}

		String releasedTimesString = kdData[2];
		String[] releasedTimesStrings = releasedTimesString
				.substring(1, releasedTimesString.length() - 2).split(",");
		int[] releasedTimes = new int[releasedTimesStrings.length];
		for (int i = 0 ; i < releasedTimesStrings.length ; ++i) {
			tmpTime = Integer.parseInt(releasedTimesStrings[i].trim());
			releasedTimes[i] = tmpTime;
		}

		int[][] result = new int[2][pressedTimes.length];
		result[0] = pressedTimes;
		result[1] = releasedTimes;

		return result;
	}

	/**
	 * Get a kind of typing times (pressed or released) from a string.
	 * @param typingTime String of pressed or released times.
	 * @return Pressed or released times table.
	 */
	public static int[] typingTime(String typingTime) {
		String[] typedTimeStrings =
				typingTime.substring(1, typingTime.length() - 1).split(",");
		int characters = typedTimeStrings.length;
		int[] typingTimeTable = new int[characters];
		for (int i = 0 ; i < characters ; ++i) {
			typingTimeTable[i] = Integer.parseInt(typedTimeStrings[i].trim());
		}
		return typingTimeTable;
	}
}
