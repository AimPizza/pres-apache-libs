package de.dhsn.cs24_1.office_demo.pdf;

import static de.dhsn.cs24_1.office_demo.shared.OurLog.log;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;

public class PageRemover {

	public static void main(String args[]) throws IOException {

		// Loading an existing document
		File file = new File("pdf/meeting-notes_template.pdf");
		PDDocument document = PDDocument.load(file);

		// Listing the number of existing pages
		int noOfPages = document.getNumberOfPages();
		int lastPage = noOfPages - 1;
		log("There are " + noOfPages + " pages. ");

		// Removing the pages (= index of the page)
		document.removePage(lastPage);

		log("Last page removed.");

		// Saving the document
		document.save("pdf/meeting-notes_template.pdf");

		// Closing the document
		document.close();

	}
}
