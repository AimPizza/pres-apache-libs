package de.dhsn.cs24_1.office_demo.pdf.basics;

import static de.dhsn.cs24_1.office_demo.shared.Utilities.log;

import java.io.File;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;

public class PDFEncrypting {

	public static void main(String args[]) throws Exception {

		File file = new File("pdf/test.pdf");
		PDDocument document = Loader.loadPDF(file);

		// creating access permission object
		AccessPermission ap = new AccessPermission();

		// disable printing
		ap.setCanPrint(false);
		// disable copying
		ap.setCanExtractContent(false);

		// creating owner and user passwords
		StandardProtectionPolicy spp = new StandardProtectionPolicy("testowner", "testuser", ap);

		spp.setEncryptionKeyLength(128);

		document.protect(spp);

		log("Document encrypted. :0");

		document.save("pdf/test_pw.pdf");
		document.close();
	}
}
