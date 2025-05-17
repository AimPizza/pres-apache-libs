package de.dhsn.cs24_1.office_demo.pdf;

import static de.dhsn.cs24_1.office_demo.shared.Utilities.log;
import static de.dhsn.cs24_1.office_demo.shared.Utilities.logError;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class ImageInserter {

	public void insert() {
		// Load the existing template
		File file = new File("pdf/meeting_notes.pdf");
		try (PDDocument doc = PDDocument.load(file)) {

			PDPage page = doc.getPage(1);

			// Create content stream in append mode
			addImage(doc, page);

			doc.save("pdf/meeting_report.pdf");
			log("Saved pdf with image. " + "pdf/meeting_report.pdf");
		} catch (Exception e) {
			logError("Failed to insert image: " + e.getLocalizedMessage());
		}
	}

	private void addImage(PDDocument doc, PDPage page) throws IOException {
		// Creating PDImageXObject object
		PDImageXObject pdImage = PDImageXObject.createFromFile("pdf/excel_table.png", doc);

		int originalWidth = pdImage.getWidth();
		int originalHeight = pdImage.getHeight();

		// calculate new dimensions, avoid distortion
		int resizedWidth = 500;
		int resizedHeight = (int) Math.round((double) originalHeight * resizedWidth / originalWidth);
		int x = 70, y = 600; // note: coordinates are at bottom-left corner

		// Append to existing page content
		try (PDPageContentStream contentStream = new PDPageContentStream(doc, page, AppendMode.APPEND, true)) {
			contentStream.drawImage(pdImage, x, y, resizedWidth, resizedHeight);
		}
	}

	public static void main(String[] args) throws IOException {
		WordToPDFConverter.main(args);
		ImageInserter inserter = new ImageInserter();
		inserter.insert();

		log("Table was inserted into PDF :)");
	}
}
