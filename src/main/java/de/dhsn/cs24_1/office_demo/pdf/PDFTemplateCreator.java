package de.dhsn.cs24_1.office_demo.pdf;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

// Class for creating documents, adding pages & document attributes
public class PDFTemplateCreator {

	public static void main(String[] args) {

		int n = 2;

		// Creating PDF document object
		try (PDDocument document = new PDDocument()) {

			for (int i = 0; i < n; i++) {
				// Creating a blank page
				PDPage page = new PDPage(PDRectangle.A4); // default: US Letter Format

				// Adding the blank page to the document
				document.addPage(page);
			}

			// Add metadata information
			addDocumentInfo(document);

			// Saving the document
			document.save("pdf/meeting-notes_template.pdf");
			System.out.println("PDF with " + n + " pages created c:");

		} catch (IOException e) {
			e.printStackTrace();
		}
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
}