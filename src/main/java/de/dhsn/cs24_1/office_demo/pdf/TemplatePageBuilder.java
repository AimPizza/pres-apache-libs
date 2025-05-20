package de.dhsn.cs24_1.office_demo.pdf;

import static de.dhsn.cs24_1.office_demo.shared.Utilities.log;
import static de.dhsn.cs24_1.office_demo.shared.Utilities.logError;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class TemplatePageBuilder {

	public static void main(String[] args) {

		File file = new File("pdf/test.pdf");

		try (PDDocument doc = PDDocument.load(file)) {

			// text to add:
			String headline = "Meeting Notes";
			String date = "Datum:";
			String people = "Teilnehmer:";
			String agenda = "Agenda:";
			String notes = "Notizen:";
			String excel = "Tasks:";

			// === FIRST PAGE ===
			PDPage page = doc.getPage(0);

			// content stream to write on the first page
			try (PDPageContentStream contentStream = new PDPageContentStream(doc, page)) {

				// Headline
				contentStream.beginText();
				contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
				contentStream.setLeading(14.5f);
				contentStream.newLineAtOffset(70, 750);
				contentStream.showText(headline);
				contentStream.endText();

				// Date
				contentStream.beginText();
				contentStream.setFont(PDType1Font.HELVETICA, 10);
				contentStream.newLineAtOffset(70, 720);
				contentStream.showText(date);
				contentStream.endText();

				// Participants
				contentStream.beginText();
				contentStream.setFont(PDType1Font.HELVETICA_OBLIQUE, 12);
				contentStream.newLineAtOffset(70, 670);
				contentStream.showText(people);
				contentStream.endText();

				// Agenda
				contentStream.beginText();
				contentStream.setFont(PDType1Font.HELVETICA_OBLIQUE, 12);
				contentStream.newLineAtOffset(70, 570);
				contentStream.showText(agenda);
				contentStream.endText();

				// Notes
				contentStream.beginText();
				contentStream.setFont(PDType1Font.HELVETICA_OBLIQUE, 12);
				contentStream.newLineAtOffset(70, 420);
				contentStream.showText(notes);
				contentStream.endText();
			}

			// === SECOND PAGE ===
			PDPage secondPage = doc.getPage(1);

			try (PDPageContentStream contentStream = new PDPageContentStream(doc, secondPage)) {
				contentStream.beginText();
				contentStream.setFont(PDType1Font.HELVETICA_OBLIQUE, 12);
				contentStream.newLineAtOffset(70, 750);
				contentStream.showText(excel);
				contentStream.endText();
			}

			doc.save("pdf/meeting_notes_template.pdf");
			log("Template with headlines was created :)");

		} catch (IOException e) {
			logError(e.getMessage());
		}
	}
}
