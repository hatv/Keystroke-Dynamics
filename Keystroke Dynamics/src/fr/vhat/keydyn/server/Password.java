package fr.vhat.keydyn.server;

import java.util.Random;

public class Password {
	static String generatePassword() {
		String[] animals = {"lapin", "chien", "renard", "elephant", "serpent", 
				"zebre", "tigre", "leopard", "caribou", "saumon", "guepard",
				"mouton", "hamster", "kangourou", "macaque", "herisson",
				"cloporte", "papillon", "etalon", "chevreuil"};
		String[] features = {"sympathique", "bondissant", "enflamme",
				"rugissant", "effrayant", "etrange", "sournois", "capricieux",
				"sauvage", "docile", "habile", "manipulateur", "diabolique",
				"joyeux", "impatient"};
		Random randomGenerator = new Random();
		int randomIndexAnimals = randomGenerator.nextInt(animals.length);
		int randomIndexFeatures = randomGenerator.nextInt(features.length);
		String animal = animals[randomIndexAnimals];
		String feature = features[randomIndexFeatures];
		String pwd = animal + feature;
		return pwd;
	}
}
