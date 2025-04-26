package de.dhsn.cs24_1.office_demo;

import java.io.FileOutputStream;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class WriteWordTemplate {

	public static void main(String[] args) {
		System.out.println("halo");
		String output = "look-at-this.docx"; // TODO: rename into template or sumthin

		XWPFDocument document = new XWPFDocument();

		// Elemente kommen in Paragraphs
		// TODO: welche Eigenschaften kommen auf den Paragraph und welche auf den Run?
		XWPFParagraph title = document.createParagraph();
		title.setAlignment(ParagraphAlignment.CENTER);

		// Inhalt eines Elements kommt in ein Run
		XWPFRun titleRun = title.createRun(); // region of text w common set of properties
		titleRun.setText("beep boop");
		titleRun.setColor("000000");
		titleRun.setBold(true);
		titleRun.setFontFamily("Courier");
		titleRun.setFontSize(20);

		try {
			FileOutputStream out = new FileOutputStream(output);
			document.write(out);
			out.close();
			document.close();
			System.out.println("great success!");
		} catch (Exception e) {
			System.out.println("error saving");
			System.out.println(e.getLocalizedMessage());
		}

	}

}
