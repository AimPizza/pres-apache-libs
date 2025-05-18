package de.dhsn.cs24_1.office_demo.pdf;

import static de.dhsn.cs24_1.office_demo.shared.Utilities.log;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class TextExtractor {
	public static void main(String args[]) throws IOException {

		// Loading an existing document
		File file = new File("pdf/test.pdf");
		PDDocument document = PDDocument.load(file);

		// Instantiate PDFTextStripper class
		PDFTextStripper pdfStripper = new PDFTextStripper();

		// Retrieving text from PDF document
		String text = pdfStripper.getText(document);
		log(text);

		// Closing the document
		document.close();
	}
}
