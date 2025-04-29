package de.dhsn.cs24_1.office_demo.shared;

// I think that'd be nice to have less System.out.println(); statements
public class OurLog {
	public static void log(String message) {
		System.out.println(message);
	}

	public static void logError(String errorMessage) {
		System.out.println("Error: " + errorMessage);
	}
}
