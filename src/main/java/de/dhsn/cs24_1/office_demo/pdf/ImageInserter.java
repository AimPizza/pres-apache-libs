package de.dhsn.cs24_1.office_demo.pdf;

import static de.dhsn.cs24_1.office_demo.shared.OurLog.log;
import static de.dhsn.cs24_1.office_demo.shared.OurLog.logError;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class ImageInserter {

	public void insert() throws IOException {
		// Load the existing template
		File file = new File("pdf/meeting_notes.pdf");
		try (PDDocument doc = PDDocument.load(file)) {

			PDPage page = doc.getPage(1);

			// Create content stream in append mode
			addImage(doc, page);

			doc.save("pdf/meeting_report.pdf");
		}
	}

	private void addImage(PDDocument doc, PDPage page) throws IOException {
		// Creating PDImageXObject object
		PDImageXObject pdImage = PDImageXObject.createFromFile("pdf/excel_table.png", doc);

		// Append to existing page content
		try (PDPageContentStream contentStream = new PDPageContentStream(doc, page, AppendMode.APPEND, true)) {
			contentStream.drawImage(pdImage, 70, 370); // TODO: adjust position & size
		}
	}

	public static void main(String[] args) throws IOException {
		try {
			WordToPDFConverter.main(args);
			ImageInserter inserter = new ImageInserter();
			inserter.insert();

			log("Table was inserted into PDF :)");

		} catch (IOException e) {
			logError("An error occurred while inserting the image into the PDF:");
			e.printStackTrace();
		}
	}
}
