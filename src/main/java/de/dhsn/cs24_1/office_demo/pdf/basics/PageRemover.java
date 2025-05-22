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
		log("There are " + noOfPages + " pages. ");

		// removing the pages (= index of the page)
		document.removePage(lastPage);

		log("Last page removed.");

		document.save(file);

		document.close();
	}
}
