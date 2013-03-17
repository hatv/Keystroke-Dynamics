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

	public static boolean isValidAge(String age) {
		if (age.matches("^[0-9]{1,2}$"))
			return true;
		else
			return false;
	}

	public static boolean isValidAge(int age) {
		if (age <= 99 && age >= 1)
			return true;
		else
			return false;
	}

	public static boolean isValidGender(String gender) {
		if (gender.equals("Masculin") || gender.equals("Féminin"))
			return true;
		else
			return false;
	}

	public static boolean isValidCountry(String country) {
		String[] countries = {"Canada", "France", "U.S.A.", "Royaume-Uni",
				"Espagne", "Belgique", "Suisse", "Autre"};
		for (int i = 0 ; i < countries.length ; ++i) {
			if (country.equals(countries[i])) {
				return true;
			}
		}
		return false;
	}

	public static boolean isValidExperience(String computerExperience) {
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

	public static boolean isValidUsage(String typingUsage) {
		String[] usageTable = {"< 30 minutes", "~ 1 heure",
				"~ 2 heures", "~ 4 heures", "> 5 heures"};
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
