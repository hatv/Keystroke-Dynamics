package fr.vhat.keydyn.shared;

import java.io.Serializable;

/**
 * A Keystroke Sequence contains a phrase, a press time sequence and a release
 * time sequence.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
@SuppressWarnings("serial")
public class KeystrokeSequence implements Serializable {

	private String phrase;
	private TimeSequence pressedSequence;
	private TimeSequence releasedSequence;

	public KeystrokeSequence() {}

	/**
	 * Constructor.
	 * @param kdString Keystroke Dynamics string to build the Keystroke Sequence
	 * from.
	 */
	public KeystrokeSequence(String kdString) {
		this(KDData.password(kdString), KDData.typingTimes(kdString)[0],
				KDData.typingTimes(kdString)[1]);
	}

	/**
	 * Constructor.
	 * @param phrase Phrase or password associated with the Keystroke Sequence.
	 * @param pressedSequence Pressed Time Sequence.
	 * @param releasedSequence Released Time Sequence.
	 */
	public KeystrokeSequence(String phrase, TimeSequence pressedSequence,
			TimeSequence releasedSequence) {
		if (phrase.length() == pressedSequence.length() &&
				phrase.length() == releasedSequence.length()) {
			this.setPhrase(phrase);
			this.setPressedSequence(pressedSequence);
			this.setReleasedSequence(releasedSequence);
		}
	}

	/**
	 * Constructor.
	 * @param phrase Phrase or password associated with the Keystroke Sequence.
	 * @param pressedSequence Integers table representing the Pressed Time
	 * Sequence.
	 * @param releasedSequence Integers table representing the Released Time
	 * Sequence.
	 */
	public KeystrokeSequence(String phrase, int[] pressedTimes,
			int[] releasedTimes) {
		this(phrase, new TimeSequence(pressedTimes),
				new TimeSequence(releasedTimes));
	}

	public String getPhrase() {
		return phrase;
	}

	public void setPhrase(String phrase) {
		this.phrase = phrase;
	}

	public TimeSequence getPressedSequence() {
		return pressedSequence;
	}

	public void setPressedSequence(TimeSequence pressedSequence) {
		this.pressedSequence = pressedSequence;
	}

	public TimeSequence getReleasedSequence() {
		return releasedSequence;
	}

	public void setReleasedSequence(TimeSequence releasedSequence) {
		this.releasedSequence = releasedSequence;
	}

	/**
	 * Return the length of the Keystroke Sequence as the length of the phrase.
	 * @return Length of the Keystroke Sequence.
	 */
	public int length() {
		return phrase.length();
	}

	/**
	 * Return a string describing the Keystroke Sequence in a printable format.
	 */
	public String toString() {
		return "WORD=" + this.getPhrase() + " ; PRESS=" +
				this.getPressedSequence().toString() + " ; RELEASE=" +
        		this.getReleasedSequence().toString();
	}

	/**
	 * Compute and return the Time Sequence corresponding to the delays between
	 * two key press.
	 * @return Pressed to pressed Time Sequence.
	 */
	public TimeSequence getPressedToPressedSequence() {
		int[] pressedToPressedTimes = new int[this.length() - 1];
		for (int i = 0 ; i < pressedToPressedTimes.length ; ++i) {
			pressedToPressedTimes[i] =
					this.pressedSequence.getTimeTable()[i + 1]
							- this.pressedSequence.getTimeTable()[i];
		}
		return new TimeSequence(pressedToPressedTimes);
	}

	/**
	 * Compute and return the Time Sequence corresponding to the delays between
	 * two key release.
	 * @return Released to released Time Sequence.
	 */
	public TimeSequence getReleasedToReleasedSequence() {
		int[] releasedToReleasedTimes = new int[this.length() - 1];
		for (int i = 0 ; i < releasedToReleasedTimes.length ; ++i) {
			releasedToReleasedTimes[i] =
					this.releasedSequence.getTimeTable()[i + 1]
							- this.releasedSequence.getTimeTable()[i];
		}
		return new TimeSequence(releasedToReleasedTimes);
	}

	/**
	 * Compute and return the Time Sequence corresponding to the delays between
	 * a key press and its release.
	 * @return Pressed to released Time Sequence.
	 */
	public TimeSequence getPressedToReleasedSequence() {
		int[] pressedToReleasedTimes = new int[this.length()];
		for (int i = 0 ; i < pressedToReleasedTimes.length ; ++i) {
			pressedToReleasedTimes[i] =
					this.releasedSequence.getTimeTable()[i]
							- this.pressedSequence.getTimeTable()[i];
		}
		return new TimeSequence(pressedToReleasedTimes);
	}

	/**
	 * Compute and return the Time Sequence corresponding to the delays between
	 * a key release and the following one press, which could be negative.
	 * @return Released to pressed Time Sequence.
	 */
	public TimeSequence getReleasedToPressedSequence() {
		int[] releasedToPressedTimes = new int[this.length() - 1];
		for (int i = 0 ; i < releasedToPressedTimes.length ; ++i) {
			releasedToPressedTimes[i] =
					this.pressedSequence.getTimeTable()[i + 1]
							- this.releasedSequence.getTimeTable()[i];
		}
		return new TimeSequence(releasedToPressedTimes);
	}
}
