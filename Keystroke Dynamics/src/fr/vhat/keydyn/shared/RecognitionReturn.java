package fr.vhat.keydyn.shared;

import java.io.Serializable;

/**
 * Recognition return object.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
@SuppressWarnings("serial")
public class RecognitionReturn implements Serializable {

	private float[] probabilities;
	private String[] users;
	private int numberofUsersToReturn = 3;

	/**
	 * Default constructor.
	 */
	public RecognitionReturn() {
		this.probabilities = new float[numberofUsersToReturn];
		this.users = new String[numberofUsersToReturn];
		for (int i = 0 ; i < numberofUsersToReturn ; ++i) {
			this.users[i] = "";
		}
		for (int i = 0 ; i < numberofUsersToReturn ; ++i) {
			this.probabilities[i] = -1;
		}
	}

	/**
	 * Build a string from the information of the object.
	 * @return Built string to display.
	 */
	public String getStringContent() {
		return "";
	}

	/**
	 * Build a string from the information of the object.
	 * @return Built string to display as a title.
	 */
	public String getStringTitle() {
		return "";
	}

	/**
	 * Add user to the data if it is among the best probabilities.
	 * @param login Login of the user.
	 * @param probability Probability of the user to be the typer.
	 */
	public void addUser(String login, float probability) {
		int minProbabilityIndex = this.getMinProbabilityIndex();
		if (probability > this.probabilities[minProbabilityIndex]) {
			this.probabilities[minProbabilityIndex] = probability;
			this.users[minProbabilityIndex] = login;
		}
	}

	/**
	 * Give the index of the user with the minimum probability to be the typer.
	 * @return Index of the minimum probability user.
	 */
	private int getMinProbabilityIndex() {
		float currentMin = -1;
		int minIndex = -1;
		for (int i = 0 ; i < numberofUsersToReturn ; ++i) {
			if (currentMin > this.probabilities[i] || currentMin == -1) {
				currentMin = this.probabilities[i];
				minIndex = i;
			}
		}
		if (currentMin == -1)
			return -1;
		else
			return minIndex;
	}
}
