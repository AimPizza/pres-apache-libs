package de.dhsn.cs24_1.office_demo.pdf.basics;

import static de.dhsn.cs24_1.office_demo.shared.Utilities.log;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.multipdf.PDFMergerUtility;

public class PDFMerger {
	public static void main(String[] args) throws IOException {
		File file1 = new File("pdf/test1.pdf");
		File file2 = new File("pdf/test2.pdf");

		PDFMergerUtility PDFmerger = new PDFMergerUtility();

		PDFmerger.setDestinationFileName("pdf/merged.pdf");

		PDFmerger.addSource(file1);
		PDFmerger.addSource(file2);

		PDFmerger.mergeDocuments(null, null);
		log("Documents merged! :]");
	}
}
