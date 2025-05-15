package de.dhsn.cs24_1.office_demo.pdf;

import static de.dhsn.cs24_1.office_demo.shared.Utilities.log;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;

public class PDFSplitter {
	public static void main(String[] args) throws IOException {

		// Loading an existing PDF document
		File file = new File("pdf/meeting_notes.pdf");
		PDDocument document = PDDocument.load(file);

		// Instantiating Splitter class
		Splitter splitter = new Splitter();

		// splitting pages of a PDF document
		List<PDDocument> Pages = splitter.split(document);

		// Creating an iterator
		Iterator<PDDocument> iterator = Pages.listIterator();

		// Saving each page as an individual document
		int i = 1;
		while (iterator.hasNext()) {
			PDDocument pd = iterator.next();
			pd.save("pdf/meeting_notes" + i++ + ".pdf");
		}

		log("Multiple PDFs create ^-^");
		document.close();
	}
}
