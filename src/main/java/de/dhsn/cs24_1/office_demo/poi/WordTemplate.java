package de.dhsn.cs24_1.office_demo.poi;

import java.io.FileOutputStream;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class WordTemplate extends XWPFDocument {

	public static String output = "look-at-this.docx"; // TODO: rename into template or something

	public WordTemplate() {
		super();
		createBaseTemplate();
	}

	public void createBaseTemplate() {
		// Elements come in Paragraphs
		XWPFParagraph title = createParagraph();
		title.setAlignment(ParagraphAlignment.CENTER);

		// Content of an element comes in a Run
		XWPFRun titleRun = title.createRun(); // region of text with common set of properties
		titleRun.setText("I changed this - Amogus");
		titleRun.setColor("000000");
		titleRun.setBold(true);
		titleRun.setFontFamily("Courier");
		titleRun.setFontSize(20);
	}

	public void save() {
		try (FileOutputStream out = new FileOutputStream(output)) {
			this.write(out);
			System.out.println("great success!");
		} catch (Exception e) {
			System.out.println("error saving");
			System.out.println(e.getLocalizedMessage());
		}
	}

	public static void main(String[] args) {
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
