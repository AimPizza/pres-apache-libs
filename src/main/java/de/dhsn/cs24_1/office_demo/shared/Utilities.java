package de.dhsn.cs24_1.office_demo.shared;

import java.util.Scanner;

// I think that'd be nice to have less System.out.println(); statements
public class Utilities {
	public static void log(String message) {
		System.out.println(message);
	}

	public static void logError(String errorMessage) {
		System.out.println("Error: " + errorMessage);
	}

	/// parses Integer from user input based on a prompt.
	/// returns -1 in case of error
	public static Integer readInteger(String prompt) {
		Scanner in = new Scanner(System.in);
		System.out.print(prompt + ": ");
		String userChoice = in.nextLine();
		try {
			int choiceAsInt = Integer.parseInt(userChoice);
			return choiceAsInt;
		} catch (Exception e) {
			logError("parsing Integer - " + e.getLocalizedMessage());
			return -1;
		}
	}
}
