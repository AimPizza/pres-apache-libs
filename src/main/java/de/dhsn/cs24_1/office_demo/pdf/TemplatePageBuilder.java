package de.dhsn.cs24_1.office_demo.pdf;

import static de.dhsn.cs24_1.office_demo.shared.Utilities.log;
import static de.dhsn.cs24_1.office_demo.shared.Utilities.logError;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class TemplatePageBuilder {

	public static void main(String[] args) {

		File file = new File("pdf/test.pdf");

		try (PDDocument doc = Loader.loadPDF(file)) {

			// === FIRST PAGE ===
			PDPage page = doc.getPage(0);

			// content stream to write on the first page
			try (PDPageContentStream contentStream = new PDPageContentStream(doc, page)) {

				// Headline
				contentStream.beginText();
				contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 18);
				contentStream.setLeading(14.5f);
				contentStream.newLineAtOffset(70, 770);
				contentStream.showText("Meeting Notes");
				contentStream.endText();

				// Date
				contentStream.beginText();
				contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 10);
				contentStream.newLineAtOffset(70, 720);
				contentStream.showText("Datum: ");
				contentStream.endText();

				// Participants
				contentStream.beginText();
				contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_OBLIQUE), 12);
				contentStream.newLineAtOffset(70, 670);
				contentStream.showText("Teilnehmer: ");
				contentStream.endText();

				// Agenda
				contentStream.beginText();
				contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_OBLIQUE), 12);
				contentStream.newLineAtOffset(70, 570);
				contentStream.showText("Agenda: ");
				contentStream.endText();

				// Notes
				contentStream.beginText();
				contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_OBLIQUE), 12);
				contentStream.newLineAtOffset(70, 420);
				contentStream.showText("Notizen: ");
				contentStream.endText();
			}

			// === SECOND PAGE ===
			PDPage secondPage = doc.getPage(1);

			try (PDPageContentStream contentStream = new PDPageContentStream(doc, secondPage)) {
				contentStream.beginText();
				contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_OBLIQUE), 12);
				contentStream.newLineAtOffset(70, 750);
				contentStream.showText("Tasks: ");
				contentStream.endText();
			}

			String outputPath = "pdf/meeting_notes_template.pdf";
			doc.save(outputPath);
			log("Template with headlines created :] " + outputPath);

		} catch (IOException e) {
			logError(e.getMessage());
		}
	}
}
