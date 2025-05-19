package de.dhsn.cs24_1.office_demo.pdf.basics;

import static de.dhsn.cs24_1.office_demo.shared.Utilities.log;

import java.io.File;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;

public class PDFEncrypting {

	public static void main(String args[]) throws Exception {
		// Loading an existing document
		File file = new File("pdf/test.pdf");
		PDDocument document = PDDocument.load(file);

		// Creating access permission object
		AccessPermission ap = new AccessPermission();

		// Creating StandardProtectionPolicy object
		StandardProtectionPolicy spp = new StandardProtectionPolicy("test", "test", ap);

		// Setting the length of the encryption key
		spp.setEncryptionKeyLength(128);

		// Setting the access permissions
		spp.setPermissions(ap);

		// Protecting the document
		document.protect(spp);

		log("Document encrypted. :0");

		// Saving the document
		document.save("pdf/test_pw.pdf");
		// Closing the document
		document.close();
	}
}
