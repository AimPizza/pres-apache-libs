package de.dhsn.cs24_1.office_demo.pdf.basics;

import static de.dhsn.cs24_1.office_demo.shared.Utilities.log;
import static de.dhsn.cs24_1.office_demo.shared.Utilities.logError;

import java.io.IOException;
import java.util.Calendar;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

/**
 * Class for demonstrating Apache PDFBox. Creates a pdf document with pages,
 * sample text and metadata
 */

public class PDFCreator {

	public static void main(String[] args) {
		try {
			createPdf(3, "pdf/test.pdf");
		} catch (IOException e) {
			logError("Error while creating the PDF: " + e.getMessage());
		}
	}

	private static void createPdf(int pages, String filePath) throws IOException {
		PDDocument document = new PDDocument();

		for (int i = 0; i < pages; i++) {
			// create a blank page
			PDPage page = new PDPage(PDRectangle.A4); // default: US Letter Format

			// add the blank page to the document
			document.addPage(page);
		}

		addDocumentInfo(document);
		addSampleText(document);

		document.save(filePath);
		log("PDF with " + pages + " pages was created and saved at " + filePath);
		document.close();
	}

	private static void addDocumentInfo(PDDocument document) {

		PDDocumentInformation pdd = new PDDocumentInformation();

		// setting metadata
		pdd.setAuthor("Rick & Linda");
		pdd.setTitle("Meeting Notes - Template");
		pdd.setCreator("Linda");
		pdd.setProducer("Kriston Kraut");
		pdd.setSubject("Template for Meeting Notes");
		pdd.setKeywords("template, meeting, notes");

		Calendar now = Calendar.getInstance();
		pdd.setCreationDate(now);
		pdd.setModificationDate(now);

		document.setDocumentInformation(pdd);
	}

	private static void addSampleText(PDDocument document) throws IOException {
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
