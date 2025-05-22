package de.dhsn.cs24_1.office_demo.pdf.basics;

import static de.dhsn.cs24_1.office_demo.shared.Utilities.log;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;

public class PageRemover {

	public static void main(String args[]) throws IOException {

		File file = new File("pdf/test.pdf");
		PDDocument document = Loader.loadPDF(file);

		int noOfPages = document.getNumberOfPages();
		int lastPage = noOfPages - 1;
		log("Processing file: " + file.getPath() + " (Pages: " + noOfPages + ")");

		// removing the pages (= index of the page)
		document.removePage(lastPage);

		log("Last page removed and saved to " + file.getPath());

		document.save(file);

		document.close();
	}
}
