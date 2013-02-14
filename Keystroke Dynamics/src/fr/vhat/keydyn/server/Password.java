package fr.vhat.keydyn.server;

import java.util.Random;

public class Password {

	static String generatePassword() {
		String[] animals = {"ecureuil", "renard", "elephant", "serpent", 
				"albatros", "leopard", "caribou", "saumon", "guepard",
				"mouton", "hamster", "kangourou", "macaque", "herisson",
				"cloporte", "papillon", "etalon", "chevreuil", "pingouin"};
		String[] features = {"sympathique", "bondissant", "joueur",
				"rugissant", "effrayant", "etrange", "sournois", "capricieux",
				"sauvage", "docile", "habile", "malicieux", "diabolique",
				"joyeux", "impatient", "insensible"};
		Random randomGenerator = new Random();
		int randomIndexAnimals = randomGenerator.nextInt(animals.length);
		int randomIndexFeatures = randomGenerator.nextInt(features.length);
		String animal = animals[randomIndexAnimals];
		String feature = features[randomIndexFeatures];
		String pwd = animal + feature;
		return pwd;
	}
	
	static String hash(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}
	
	static boolean check(String candidate, String stored) {
		return BCrypt.checkpw(candidate, stored);
	}
}
