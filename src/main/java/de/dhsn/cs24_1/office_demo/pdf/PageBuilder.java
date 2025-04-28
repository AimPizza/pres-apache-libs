package de.dhsn.cs24_1.office_demo.pdf;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class PageBuilder {

	public static void main(String[] args) {

		// Loading an existing document
		File file = new File("pdf/meeting-notes_template.pdf");

		try (PDDocument doc = PDDocument.load(file)) {

			// Text to add:
			String headline = "Meeting Notes";
			String date = "Date:";
			String people = "Teilnehmer:";
			String agenda = "Agenda:";
			String notes = "Notizen:";
			String summary = "Zusammenfassung:";
			String excel = "Zufriedenheit mit dem Meeting:";

			// Creating a PDF Doc
			PDPage page = doc.getPage(0);

			// Content stream to write on the page
			try (PDPageContentStream contentStream = new PDPageContentStream(doc, page)) {
				// Begin the Content stream for headline
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
				contentStream.setFont(PDType1Font.HELVETICA_OBLIQUE, 12);
				contentStream.beginText();
				contentStream.newLineAtOffset(70, 670);
				contentStream.showText(people);
				contentStream.endText();

				// Agenda
				contentStream.beginText();
				contentStream.newLineAtOffset(70, 570);
				contentStream.showText(agenda);
				contentStream.endText();

				// Notes
				contentStream.beginText();
				contentStream.newLineAtOffset(70, 400);
				contentStream.showText(notes);
				contentStream.endText();

				// Summary
				contentStream.beginText();
				contentStream.newLineAtOffset(70, 250);
				contentStream.showText(summary);
				contentStream.endText();
			}

			// === SECOND PAGE ===
			PDPage secondPage = doc.getPage(1);

			// Content stream to write on the second page
			try (PDPageContentStream contentStream = new PDPageContentStream(doc, secondPage)) {
				contentStream.beginText();
				contentStream.setFont(PDType1Font.HELVETICA_OBLIQUE, 12);
				contentStream.newLineAtOffset(70, 750);
				contentStream.showText(excel);
				contentStream.endText();
			}

			// Saving the document
			doc.save(file);
			System.out.println("Content added :]");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}