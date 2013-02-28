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
	private TimeSequence pressSequence;
	private TimeSequence releaseSequence;

	public KeystrokeSequence(String kdString) {
		this(KDData.password(kdString), KDData.typingTimes(kdString)[0],
				KDData.typingTimes(kdString)[1]);
	}

	public KeystrokeSequence(String phrase, TimeSequence pressSequence,
			TimeSequence releaseSequence) {
		if (phrase.length() == pressSequence.length() &&
				phrase.length() == releaseSequence.length()) {
			this.setPhrase(phrase);
			this.setPressSequence(pressSequence);
			this.setReleaseSequence(releaseSequence);
		}
	}

	public KeystrokeSequence(String phrase, int[] pressTimes,
			int[] releaseTimes) {
		this(phrase, new TimeSequence(pressTimes),
				new TimeSequence(releaseTimes));
	}

	public String getPhrase() {
		return phrase;
	}

	public void setPhrase(String phrase) {
		this.phrase = phrase;
	}

	public TimeSequence getPressSequence() {
		return pressSequence;
	}

	public void setPressSequence(TimeSequence pressSequence) {
		this.pressSequence = pressSequence;
	}

	public TimeSequence getReleaseSequence() {
		return releaseSequence;
	}

	public void setReleaseSequence(TimeSequence releaseSequence) {
		this.releaseSequence = releaseSequence;
	}

	public int length() {
		return phrase.length();
	}

	
}
