package de.dhsn.cs24_1.office_demo.pdf.basics;

import static de.dhsn.cs24_1.office_demo.shared.Utilities.log;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class TextExtractor {
	public static void main(String args[]) throws IOException {

		// loading an existing document
		File file = new File("pdf/test.pdf");
		PDDocument document = PDDocument.load(file);

		// instantiate PDFTextStripper class
		PDFTextStripper pdfStripper = new PDFTextStripper();

		String text = pdfStripper.getText(document);
		log(text);

		document.close();
	}
}
