package de.dhsn.cs24_1.office_demo.shared;

import static de.dhsn.cs24_1.office_demo.shared.Utilities.log;
import static de.dhsn.cs24_1.office_demo.shared.Utilities.readInteger;;

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
		log("Hello World!");
		int myInt = readInteger("input something");
		log(Integer.toString(myInt));

		log("thank's for joining our demo!");
	}
}
