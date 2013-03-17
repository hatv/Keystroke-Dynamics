package fr.vhat.keydyn.server;

import java.util.Random;

/**
 * Password management class.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
public class Password {

	/**
	 * Generate a random password for an user.
	 * @return A randomly generated password.
	 */
	public static String generatePassword() {
		/*String[] animals = {"ecureuil", "renard", "elephant", "serpent", 
				"albatros", "leopard", "caribou", "saumon", "guepard",
				"mouton", "hamster", "kangourou", "macaque", "herisson",
				"cloporte", "papillon", "etalon", "chevreuil", "pingouin",
				"saumon", "taureau", "pigeon"};
		String[] features = {"sympathique", "bondissant", "arrogant",
				"rugissant", "effrayant", "etrange", "sournois", "capricieux",
				"sauvage", "docile", "habile", "malicieux", "diabolique",
				"joyeux", "impatient", "insensible", "tenebreux"};*/
		String[] frenchCommonsPreAdjectives = {"super", "jeune", "pauvre",
				"mauvais", "ancien", "dernier", "prochain", "grand", "petit",
				"terrible", "premier", "heureux"};
		String[] frenchCommonsMasculineNouns = {"enfant", "professeur",
				"propos", "choix", "travers", "accord", "avion", "bonheur",
				"capitaine", "docteur", "ennemi", "gouvernement", "homme",
				"jardin", "journal", "mariage", "moment", "monde", "mouvement",
				"passage", "prince", "quartier", "sentiment", "village",
				"visage", "voyage", "sourire", "service", "pouvoir", "oiseau"};
		String[] frenchCommonsPostAdjectives = {"bizarre", "dfficile", "facile",
				"impossible", "malade", "possible", "tranquille", "simple",
				"triste", "important", "vivant", "libre"};
		Random randomGenerator = new Random();
		int randomPrePost = randomGenerator.nextInt(1);
		int randomIndexAdjectives = randomGenerator.nextInt(
				frenchCommonsPreAdjectives.length);
		int randomIndexNouns = randomGenerator.nextInt(
				frenchCommonsMasculineNouns.length);
		String adjective = (randomPrePost == 1)?
				frenchCommonsPostAdjectives[randomIndexAdjectives]:
					frenchCommonsPreAdjectives[randomIndexAdjectives];
		String noun = frenchCommonsMasculineNouns[randomIndexNouns];
		String password = (randomPrePost == 1)?noun+adjective:adjective+noun;
		return password;
	}

	/**
	 * Hash a password to securely store it in the data store.
	 * @param password Password to hash.
	 * @return Hash signature.
	 */
	public static String hash(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}

	/**
	 * Check the validity of a password according to his hash signature.
	 * @param candidate Plain text password to control.
	 * @param stored Hashed signature to compare.
	 * @return Validity of the candidate password.
	 */
	public static boolean check(String candidate, String stored) {
		return BCrypt.checkpw(candidate, stored);
	}
}
