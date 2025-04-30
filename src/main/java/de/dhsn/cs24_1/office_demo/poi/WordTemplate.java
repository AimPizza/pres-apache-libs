package de.dhsn.cs24_1.office_demo.poi;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class WordTemplate extends XWPFDocument {

	public static final String documentHeading = "Meeting Notes Template";
	public static final String attendeesHeading = "Attendees";
	public static final String agendaHeading = "Agenda Items";
	public static final String tasksHeading = "Tasks";

	public static String output = "look-at-this.docx"; // TODO: rename into template or something
	private XWPFDocument doc;

	public WordTemplate() throws IOException {
		// 1) Load the .docx that already has Heading1 defined
		try (FileInputStream in = new FileInputStream("poi/base.docx")) {
			this.doc = new XWPFDocument(in);
		} // TODO

		// 2) Now any .setStyle("Heading1") will pick up the real Heading1 style
		createBaseTemplate();
	}

	public void createBaseTemplate() {
		// Elements come in Paragraphs
		XWPFParagraph title = doc.createParagraph();
		title.setStyle("Heading1");
		title.setAlignment(ParagraphAlignment.LEFT);

		// Content of an element comes in a Run
		XWPFRun titleRun = title.createRun(); // region of text with common set of properties
		titleRun.setText(documentHeading);
		titleRun.setColor("000000");
		titleRun.setBold(true);
		titleRun.setFontFamily("Helvetica");
		titleRun.setFontSize(20);
	}

	public void save() {
		try (FileOutputStream out = new FileOutputStream(output)) {
			doc.write(out);
			System.out.println("great success!");
		} catch (Exception e) {
			System.out.println("error saving");
			System.out.println(e.getLocalizedMessage());
		}
	}

	public static void main(String[] args) throws IOException {
		System.out.println("halo");

		WordTemplate document = new WordTemplate();
		document.save();

		// Close the document after saving
		try {
			document.close();
		} catch (Exception e) {
			System.out.println("error closing document");
			System.out.println(e.getLocalizedMessage());
		}
	}
}
