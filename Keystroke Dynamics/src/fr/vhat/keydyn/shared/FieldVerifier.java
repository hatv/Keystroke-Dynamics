package fr.vhat.keydyn.shared;

/**
 * Verifier class to control the validity of the registration form.
 * This class is used in the client as well as in the server.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
public class FieldVerifier {

	public static boolean isValidLogin(String login) {
		if (login.matches("^[a-z]{5,13}$")) {
			return true;
		}
		else
			return false;
	}

	public static boolean isValidEmail(String email) {
		if (email.matches("^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*" +
				"+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z" +
				"0-9](?:[a-z0-9-]*[a-z0-9])?$")) {
			return true;
		} else
			return false;
	}

	public static boolean isValidBirthYear(String birthYear) {
		if (birthYear.matches("^[0-9]{4}")
				&& isValidBirthYear(Integer.parseInt(birthYear)))
			return true;
		else
			return false;
	}

	public static boolean isValidBirthYear(int birthYear) {
		if (birthYear <= 2015 && birthYear >= 1901)
			return true;
		else
			return false;
	}

	public static boolean isValidGender(String gender) {
		if (gender.equals("Homme") || gender.equals("Femme"))
			return true;
		else
			return false;
	}

	public static boolean isValidCountry(String country) {
		String[] countries = {"Canada", "France", "États-Unis", "Royaume-Uni",
				"Belgique", "Suisse", "Autre pays d'Europe", "Pays d'Afrique",
				"Pays d'Asie", "Autre pays"};
		for (int i = 0 ; i < countries.length ; ++i) {
			if (country.equals(countries[i])) {
				return true;
			}
		}
		return false;
	}

	public static boolean isValidComputerExperience(String computerExperience) {
		String[] experienceTable = {"< 2 ans", "~ 4 ans", "~ 7 ans",
				"~ 10 ans", "> 13 ans"};
		for (int i = 0 ; i < experienceTable.length ; ++i) {
			if (computerExperience.equals(experienceTable[i])) {
				return true;
			}
		}
		return false;
	}

	public static boolean isValidExperience(int computerExperienceIndex) {
		if (computerExperienceIndex >= 1 && computerExperienceIndex <= 5)
			return true;
		else
			return false;
	}

	public static boolean isValidTypingUsage(String typingUsage) {
		String[] usageTable = {"< 30 minutes par jour", "~ 1 heure par jour",
				"~ 2 heures par jour", "~ 4 heures par jour",
				"> 5 heures par jour"};
		for (int i = 0 ; i < usageTable.length ; ++i) {
			if (typingUsage.equals(usageTable[i])) {
				return true;
			}
		}
		return false;
	}

	public static boolean isValidUsage(int typingUsageIndex) {
		if (typingUsageIndex >= 1 && typingUsageIndex <= 5)
			return true;
		else
			return false;
	}
}
