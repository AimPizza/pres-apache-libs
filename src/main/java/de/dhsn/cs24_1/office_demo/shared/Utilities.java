package de.dhsn.cs24_1.office_demo.shared;

import java.util.Scanner;

// I think that'd be nice to have less System.out.println(); statements
public class Utilities {

	static final Scanner IN = new Scanner(System.in);

	public static void log(String message) {
		log(message, true);
	}

	public static void log(String message, Boolean showPrefix) {
		if (showPrefix)
			System.out.println("Info: " + message);
		else
			System.out.println(message);
	}

	public static void log(Integer message) {
		log(Integer.toString(message), true);
	}

	public static void logError(String errorMessage) {
		System.out.println("Error: " + errorMessage);
	}

	/// parses Integer from user input based on a prompt.
	/// returns -1 in case of error
	public static Integer readInteger(String prompt) {
		System.out.print(prompt);
		String userChoice = IN.nextLine();
		try {
			int choiceAsInt = Integer.parseInt(userChoice);
			return choiceAsInt;
		} catch (Exception e) {
			logError("parsing Integer - " + e.getLocalizedMessage());
			return -1;
		}
	}

	public static Integer promptWithOptions(String[] titleAndOptions) {
		log("----------", false);
		for (int i = 0; i < titleAndOptions.length; i++) {
			if (i == 0)
				log(titleAndOptions[i], false);
			else
				log(Integer.toString(i) + ") " + titleAndOptions[i], false);
		}
		return readInteger("> ");
	}

}
