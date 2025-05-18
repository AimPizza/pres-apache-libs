package de.dhsn.cs24_1.office_demo.pdf;

import static de.dhsn.cs24_1.office_demo.shared.Utilities.log;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import de.dhsn.cs24_1.office_demo.poi.WordTemplate;
import de.dhsn.cs24_1.office_demo.poi.WordTemplateParser;
import de.dhsn.cs24_1.office_demo.shared.ReportModel;

public class WordToPDFConverter {
	// Layout constants matching TemplatePageBuilder
	private static final float MARGIN = 70f;
	private static final float DATE_Y = 720f;
	private static final float PARTICIPANTS_Y = 670f;
	private static final float AGENDA_Y = 570f;
	private static final float NOTES_Y = 420f;
	private static final float LINE_SPACING = 20f;
	private static final float INDENT = 15f;

	public void convert(ReportModel model, String outputPath) throws IOException {
		// Load the existing template
		File templateFile = new File("pdf/meeting-notes_template.pdf");
		try (PDDocument doc = PDDocument.load(templateFile)) {

			PDPage page = doc.getPage(0);

			// Create content stream in append mode
			try (PDPageContentStream contentStream = new PDPageContentStream(doc, page, AppendMode.APPEND, true)) {
				addDate(contentStream, model.getDate());

				addParticipants(contentStream, model.getParticipants());

				addAgenda(contentStream, model.getAgenda());

				addNotes(contentStream, model.getNotes());
			}

			doc.save(outputPath);
		}
	}

	private void addDate(PDPageContentStream contentStream, java.util.Date date) throws IOException {
		contentStream.beginText();
		contentStream.setFont(PDType1Font.HELVETICA, 10);
		contentStream.newLineAtOffset(120, DATE_Y);
		contentStream.showText(ReportModel.dateFormat.format(date));
		contentStream.endText();
	}

	private void addParticipants(PDPageContentStream contentStream, ArrayList<String> participants) throws IOException {
		contentStream.beginText();
		contentStream.setFont(PDType1Font.HELVETICA, 11);
		contentStream.newLineAtOffset(MARGIN + INDENT, PARTICIPANTS_Y - LINE_SPACING);

		String line = String.join(", ", participants);
		contentStream.showText(line);
		contentStream.endText();
	}

	private void addAgenda(PDPageContentStream contentStream, ArrayList<String> agenda) throws IOException {
		contentStream.beginText();
		contentStream.setFont(PDType1Font.HELVETICA, 11);
		// one absolute move to the first bullet position
		contentStream.newLineAtOffset(MARGIN + INDENT, AGENDA_Y - LINE_SPACING);
		contentStream.setLeading(LINE_SPACING);

		for (String item : agenda) {
			contentStream.showText("\u2022 " + item);
			contentStream.newLine();
		}
		contentStream.endText();
	}

	private void addNotes(PDPageContentStream contentStream, ArrayList<String> notes) throws IOException {
		contentStream.beginText();
		contentStream.setFont(PDType1Font.HELVETICA, 11);
		// initial absolute position:
		contentStream.newLineAtOffset(MARGIN + INDENT, NOTES_Y - LINE_SPACING);
		contentStream.setLeading(LINE_SPACING);
		for (String note : notes) {
			contentStream.showText("\u2022 " + note);
			contentStream.newLine(); // moves down by leading
		}
		contentStream.endText();
	}

	public static void main(String[] args) {
		try {
			// First, create the template if it doesn't exist
			PDFCreator.main(args);

			// Then build the template structure
			TemplatePageBuilder.main(args);

			// Parse the Word document and get the model
			ReportModel model = WordTemplateParser.parseWordDocument(WordTemplate.output);

			// Convert to PDF using the template
			WordToPDFConverter converter = new WordToPDFConverter();

			String outputPath = "pdf/meeting_notes.pdf";
			converter.convert(model, outputPath);
			log("PDF created successfully! " + outputPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
