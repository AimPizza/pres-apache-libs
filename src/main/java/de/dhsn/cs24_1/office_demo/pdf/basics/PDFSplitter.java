package de.dhsn.cs24_1.office_demo.pdf.basics;

import static de.dhsn.cs24_1.office_demo.shared.Utilities.log;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;

public class PDFSplitter {
	public static void main(String[] args) throws IOException {

		File file = new File("pdf/test.pdf");
		PDDocument document = Loader.loadPDF(file);

		Splitter splitter = new Splitter();

		// splitting pages of a PDF document
		List<PDDocument> Pages = splitter.split(document);

		// creating an iterator
		Iterator<PDDocument> iterator = Pages.listIterator();

		// saving each page as an individual document
		int i = 1;
		while (iterator.hasNext()) {
			PDDocument pd = iterator.next();
			pd.save("pdf/test" + i++ + ".pdf");
		}

		log("Multiple PDFs created ^-^");
		document.close();
	}
}
