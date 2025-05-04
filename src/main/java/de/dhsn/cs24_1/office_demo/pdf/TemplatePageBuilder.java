package de.dhsn.cs24_1.office_demo.pdf;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class TemplatePageBuilder {

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
			String excel = "Meeting-Produktivität:";

			// First page
			PDPage page = doc.getPage(0);

			// Content stream to write on the first page
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

				// Teilnehmer
				contentStream.beginText();
				contentStream.setFont(PDType1Font.HELVETICA_OBLIQUE, 12);
				contentStream.newLineAtOffset(70, 670);
				contentStream.showText(people);
				contentStream.endText();

				contentStream.beginText();
				contentStream.setFont(PDType1Font.HELVETICA, 11);
				contentStream.newLineAtOffset(85, 650);
				contentStream.showText("Ronny Reader, ...");
				contentStream.endText();

				// Agenda
				contentStream.beginText();
				contentStream.setFont(PDType1Font.HELVETICA_OBLIQUE, 12);
				contentStream.newLineAtOffset(70, 570);
				contentStream.showText(agenda);
				contentStream.endText();

				// Bullet Point: Agenda - Text
				contentStream.beginText();
				contentStream.setFont(PDType1Font.HELVETICA, 11);
				contentStream.newLineAtOffset(85, 550);
				contentStream.showText("\u2022 Task 1");
				contentStream.endText();

				// Notes
				contentStream.beginText();
				contentStream.setFont(PDType1Font.HELVETICA_OBLIQUE, 12);
				contentStream.newLineAtOffset(70, 420);
				contentStream.showText(notes);
				contentStream.endText();

				// Bullet Point: Notes - Text
				contentStream.beginText();
				contentStream.setFont(PDType1Font.HELVETICA, 11);
				contentStream.newLineAtOffset(85, 400);
				contentStream.showText("\u2022 Notiz 1");
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

			// Save document
			doc.save(file);
			System.out.println("Content with bullet points added :)");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}