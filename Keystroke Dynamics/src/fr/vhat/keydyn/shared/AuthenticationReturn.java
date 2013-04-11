package fr.vhat.keydyn.shared;

import java.io.Serializable;

/**
 * Authentication return object.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
@SuppressWarnings("serial")
public class AuthenticationReturn implements Serializable {

	private boolean hasInfo = false;
	private boolean isAuthenticated;
	private boolean isSaved;
	private float distance;
	private float threshold;
	private long timeToWait;

	/**
	 * Error codes :
	 * 0 : none ;
	 * -1 : unknown ;
	 * -2 : distance > threshold ;
	 * -3 : wrong password ;
	 * -4 : login doesn't exist ;
	 * -5 : elapsed time between two attemps is too low.
	 */
	private int authenticationErrorCode;

	/**
	 * Empty constructor is defined.
	 */
	public AuthenticationReturn() {
		this(-1, false);
	}

	/**
	 * Constructor without information.
	 * @param authenticationCode Error code of the authentication (0 if none).
	 * @param isSaved True if new data have been saved in the data store.
	 */
	public AuthenticationReturn(int authenticationCode, boolean isSaved) {
		this.setAuthenticationErrorCode(authenticationCode);
		this.setSaved(isSaved);
	}

	/**
	 * Constructor with more information.
	 * @param authenticationCode Error code of the authentication (0 if none).
	 * @param isSaved True if new data have been saved in the data store.
	 * @param distance Distance between given and saved data.
	 * @param threshold Threshold below which distance must be to succeed.
	 */
	public AuthenticationReturn(int authenticationCode, boolean isSaved,
			float distance, float threshold) {
		this(authenticationCode, isSaved);
		this.hasInfo = true;
		this.setDistance(distance);
		this.setThreshold(threshold);
	}

	/**
	 * Build a string from the information of the object.
	 * @return Built string to display.
	 */
	public String getStringContent() {

		String newline = "<br />";

		String errorInformation = new String();
		if (this.getAuthenticationErrorCode() == -1) {
			errorInformation = "Erreur inconnue.";
		} else if (this.getAuthenticationErrorCode() == -2) {
			errorInformation = "La dynamique de frappe ne correspond pas.";
		} else if (this.getAuthenticationErrorCode() == -3) {
    		errorInformation = "Le mot de passe saisi n'est pas valide.";
    	} else if (this.getAuthenticationErrorCode() == -4) {
    		errorInformation = "Cet utilisateur n'existe pas.";
    	} else if (this.getAuthenticationErrorCode() == -5) {
    		errorInformation = "Veuillez patienter " +
    					this.getTimeToWait() / 1000 + " secondes avant de " +
    					"tenter une nouvelle authentification.";
    	}

		String finalString = new String();
    	if (!this.isAuthenticated && this.authenticationErrorCode != -1) {
    		finalString += newline + "Raison : " + errorInformation;
    	}

    	if (this.hasInfo) {
    		String distance = Float.toString(this.getDistance());
    		String threshold = Float.toString(this.getThreshold());
    		finalString += newline + "Distance : " + distance
    				+ " (Seuil : " + threshold + ").";
    	}

    	if (this.isSaved) {
    		finalString += newline + "Les données ont été enregistrées.";
    	}

    	return finalString;
	}

	/**
	 * Build a string from the information of the object.
	 * @return Built string to display as a title.
	 */
	public String getStringTitle() {
		String finalString = new String("Authentification : ");
    	if (this.isAuthenticated) {
    		finalString += "ACCEPTÉE.";
    	} else {
    		finalString += "REJETÉE.";
    	}
    	return finalString;
	}

	public boolean isAuthenticated() {
		return isAuthenticated;
	}

	public void setAuthenticated(boolean isAuthenticated) {
		this.isAuthenticated = isAuthenticated;
		this.authenticationErrorCode = 0;
	}

	public int getAuthenticationErrorCode() {
		return authenticationErrorCode;
	}

	public void setAuthenticationErrorCode(int authenticationErrorCode) {
		this.authenticationErrorCode = authenticationErrorCode;
		if (this.authenticationErrorCode == 0) {
			this.isAuthenticated = true;
		} else {
			this.isAuthenticated = false;
		}
	}

	public boolean isSaved() {
		return isSaved;
	}

	public void setSaved(boolean isSaved) {
		this.isSaved = isSaved;
	}

	public float getThreshold() {
		return threshold;
	}

	public void setThreshold(float threshold) {
		this.threshold = threshold;
		this.hasInfo = true;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
		this.hasInfo = true;
	}

	public long getTimeToWait() {
		return timeToWait;
	}

	public void setTimeToWait(long timeToWait) {
		this.timeToWait = timeToWait;
	}
}
