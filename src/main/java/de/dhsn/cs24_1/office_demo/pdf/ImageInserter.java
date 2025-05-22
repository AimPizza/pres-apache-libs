package de.dhsn.cs24_1.office_demo.pdf;

import static de.dhsn.cs24_1.office_demo.shared.Utilities.log;
import static de.dhsn.cs24_1.office_demo.shared.Utilities.logError;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class ImageInserter {

	public void insert() {
		File file = new File("pdf/meeting_notes.pdf");
		try (PDDocument doc = Loader.loadPDF(file)) {

			PDPage page = doc.getPage(1);

			// create content stream in append mode
			addImage(doc, page);

			String outputPath = "pdf/meeting_report.pdf";
			doc.save(outputPath);
			log("PDF with image created :] " + outputPath);
		} catch (Exception e) {
			logError("Failed to insert image: " + e.getLocalizedMessage());
		}
	}

	private void addImage(PDDocument doc, PDPage page) throws IOException {
		String imagePath = "pdf/excel_table.png";
		PDImageXObject pdImage = PDImageXObject.createFromFile(imagePath, doc);

		int originalWidth = pdImage.getWidth();
		int originalHeight = pdImage.getHeight();

		// calculate new dimensions, avoid distortion
		int resizedWidth = 450;
		int resizedHeight = (int) Math.round((double) originalHeight * resizedWidth / originalWidth);
		int x = 70, y = 650;

		// append to existing page content
		try (PDPageContentStream contentStream = new PDPageContentStream(doc, page, AppendMode.APPEND, true)) {
			contentStream.drawImage(pdImage, x, y, resizedWidth, resizedHeight);
			log("Image inserted from " + imagePath);
		}
	}

	public static void main(String[] args) throws IOException {
		WordToPDFConverter.main(args);
		ImageInserter inserter = new ImageInserter();
		inserter.insert();

		log("Table insertion completed :]");
	}
}
