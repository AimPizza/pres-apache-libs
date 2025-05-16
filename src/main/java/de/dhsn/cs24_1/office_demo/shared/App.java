package de.dhsn.cs24_1.office_demo.shared;

import static de.dhsn.cs24_1.office_demo.shared.Utilities.log;
import static de.dhsn.cs24_1.office_demo.shared.Utilities.promptWithOptions;;

/**
 * I'll try out the command pattern TODO: - not usable yet !
 */

interface Command {
	void execute();
}

class CreateSpreadsheetCommand implements Command {
	@Override
	public void execute() {
		System.out.println("• Spreadsheet erstellen");
	}
}

class CreatePdfReportCommand implements Command {
	@Override
	public void execute() {
		System.out.println("• PDF-Template erstellen");
		System.out.println("• PDF füllen");
		System.out.println("  ◦ Screenshot machen?");
	}
}

class CreateWordTemplateCommand implements Command {
	@Override
	public void execute() {
		System.out.println("• Word template erstellen");
	}
}

public class App {
	public static void main(String[] args) {
		int choice = promptWithOptions(
				new String[] { "Hallo, was soll erstellt werden?", "Meeting-Notes Template", "Management Report" });
		if (choice == 1) {
			choice = promptWithOptions(new String[] { "Erstes Meeting?", "Ja", "Nein" });
			log(choice);
		}

		log("thank's for joining our demo!");
	}
}
