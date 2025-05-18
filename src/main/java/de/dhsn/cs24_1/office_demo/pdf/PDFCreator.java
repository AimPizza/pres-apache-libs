package de.dhsn.cs24_1.office_demo.pdf;

import static de.dhsn.cs24_1.office_demo.shared.Utilities.log;
import static de.dhsn.cs24_1.office_demo.shared.Utilities.logError;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

// Class for creating documents, adding pages & document attributes
public class PDFCreator {

	public static void main(String[] args) throws IOException {

		int n = 3;

		// Creating PDF document object
		PDDocument document = new PDDocument();

		for (int i = 0; i < n; i++) {
			// Creating a blank page
			PDPage page = new PDPage(PDRectangle.A4); // default: US Letter Format

			// Adding the blank page to the document
			document.addPage(page);
		}

		// Add metadata information
		addDocumentInfo(document);
		helloPDF(document);

		// Saving & closing the document
		document.save("pdf/test.pdf");
		log("PDF with " + n + " pages, content and metadata created c:");
		document.close();
	}

	private static void addDocumentInfo(PDDocument document) {

		String author = "Linda";

		// Creating the PDDocumentInformation object
		PDDocumentInformation pdd = document.getDocumentInformation();

		// Setting metadata
		pdd.setAuthor(author);
		pdd.setTitle("Meeting Notes - Template");
		pdd.setCreator(author);
		pdd.setSubject("Template for Meeting Notes");
		pdd.setKeywords("template, meeting, notes");

	}

	private static void helloPDF(PDDocument document) throws IOException {
		addTextToPage(document, 0, "Hallo PDF! Ich stelle euch nun einige Funktionen von Apache PDFBox vor.");

		addTextToPage(document, 1, "Dies ist Seite 2! Auch hier kann man Text einfügen.");
	}

	private static void addTextToPage(PDDocument document, int pageIndex, String text) throws IOException {
		if (document.getNumberOfPages() <= pageIndex) {
			logError("Page " + (pageIndex + 1) + " does not exist.");
			return;
		}

		PDPage page = document.getPage(pageIndex);
		try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
			contentStream.beginText();
			contentStream.setFont(PDType1Font.COURIER, 12);
			contentStream.newLineAtOffset(25, 500);
			contentStream.showText(text);
			contentStream.endText();
			log("Text added on page " + (pageIndex + 1) + ".");
		}
	}
}
