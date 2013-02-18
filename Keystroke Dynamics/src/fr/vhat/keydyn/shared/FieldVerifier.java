package fr.vhat.keydyn.shared;
// TODO:Implement
/**
 * <p>
 * FieldVerifier validates that the name the user enters is valid.
 * </p>
 * <p>
 * This class is in the <code>shared</code> package because we use it in both
 * the client code and on the server. On the client, we verify that the name is
 * valid before sending an RPC request so the user doesn't have to wait for a
 * network round trip to get feedback. On the server, we verify that the name is
 * correct to ensure that the input is correct regardless of where the RPC
 * originates.
 * </p>
 * <p>
 * When creating a class that is used on both the client and the server, be sure
 * that all code is translatable and does not use native JavaScript. Code that
 * is not translatable (such as code that interacts with a database or the file
 * system) cannot be compiled into client side JavaScript. Code that uses native
 * JavaScript (such as Widgets) cannot be run on the server.
 * </p>
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
		if (gender.equals("Male") || gender.equals("Female"))
			return true;
		else
			return false;
	}

	public static boolean isValidCountry(String country) {
		String[] countries = {"Canada", "France", "U.S.A.", "United Kingdom",
				"Spain", "Belgium", "Other"};
		for (int i = 0 ; i < countries.length ; ++i) {
			if (country.equals(countries[i])) {
				return true;
			}
		}
		return false;
	}

	public static boolean isValidExperience(String computerExperience) {
		String[] experienceTable = {"< 2 years", "~ 4 years", "~ 7 years",
				"~ 10 years", "> 13 years"};
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
		String[] usageTable = {"< 30 minutes a day", "~ 1 hour a day",
				"~ 2 hours a day", "~ 4 hours a day", "> 5 hours a day"};
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
