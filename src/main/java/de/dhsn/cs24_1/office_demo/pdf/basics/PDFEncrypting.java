package de.dhsn.cs24_1.office_demo.pdf.basics;

import static de.dhsn.cs24_1.office_demo.shared.Utilities.log;

import java.io.File;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;

public class PDFEncrypting {

	public static void main(String args[]) throws Exception {

		File file = new File("pdf/test.pdf");
		PDDocument document = PDDocument.load(file);

		// creating access permission object
		AccessPermission ap = new AccessPermission();

		// creating StandardProtectionPolicy object
		StandardProtectionPolicy spp = new StandardProtectionPolicy("test", "test", ap);

		// setting the length of the encryption key
		spp.setEncryptionKeyLength(128);

		spp.setPermissions(ap);

		document.protect(spp);

		log("Document encrypted. :0");

		document.save("pdf/test_pw.pdf");
		document.close();
	}
}
