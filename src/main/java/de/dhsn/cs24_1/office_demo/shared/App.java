package de.dhsn.cs24_1.office_demo.shared;

import static de.dhsn.cs24_1.office_demo.shared.Utilities.log;
import static de.dhsn.cs24_1.office_demo.shared.Utilities.promptWithOptions;

import java.util.logging.Level;

import de.dhsn.cs24_1.office_demo.pdf.ImageInserter;
import de.dhsn.cs24_1.office_demo.pdf.WordToPDFConverter;
import de.dhsn.cs24_1.office_demo.poi.ExcelTemplateWriter;
import de.dhsn.cs24_1.office_demo.poi.WordTemplate;

public class App {
	public static void main(String[] args) {

		// suppress warnings (missing fonts etc.)
		java.util.logging.Logger pdfboxLogger = java.util.logging.Logger.getLogger("org.apache.pdfbox");
		pdfboxLogger.setLevel(Level.SEVERE);

		// prompt flow
		int choice = promptWithOptions(
				new String[] { "Hallo, was soll erstellt werden?", "Meeting-Notes Template", "Management Report" });
		if (choice == 1) {
			// Meeting-Notes Template
			choice = promptWithOptions(new String[] { "Erstes Meeting?", "Ja", "Nein" });
			if (choice == 1) {
				log("Creating new Spreadsheet..");
				ExcelTemplateWriter excelWriter = new ExcelTemplateWriter();
				excelWriter.writeTemplate();
			}
			log("Creating Word Template..");
			WordTemplate wordTemplate = new WordTemplate();
			wordTemplate.writeTemplate();
		} else if (choice == 2) {
			// Management Report
			WordToPDFConverter.main(args);
			// optional: ein Bild mit den Tasks
			choice = promptWithOptions(new String[] {
					"Bitte fuege das Bild der Tabelle ein (Pfad: pdf/excel_table.png)", "Done", "brauch keins" });
			if (choice == 1) {
				ImageInserter imageInserter = new ImageInserter();
				imageInserter.insert();
			}
			log("\n" + "+" + "-".repeat(30) + "+", false);
			log("| thanks for joining our demo! |", false);
			log("+" + "-".repeat(30) + "+" + "\n", false);
		}

		log("--- done ---", false);
	}
}
