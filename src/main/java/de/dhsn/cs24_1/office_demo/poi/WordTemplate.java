package de.dhsn.cs24_1.office_demo.poi;

import static de.dhsn.cs24_1.office_demo.shared.Utilities.log;
import static de.dhsn.cs24_1.office_demo.shared.Utilities.logError;

import java.io.FileInputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFNum;
import org.apache.poi.xwpf.usermodel.XWPFNumbering;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STNumberFormat;

import de.dhsn.cs24_1.office_demo.poi.basics.WordDemoWriter;
import de.dhsn.cs24_1.office_demo.shared.ReportModel;

public class WordTemplate {

	public static final String documentHeading = "Meeting Notes Template";
	public static final String attendeesHeading = "Attendees";
	public static final String agendaHeading = "Agenda Items";
	public static final String notesHeading = "Notes";

	public static String output = "poi/output.docx";
	private XWPFDocument doc;
	private XWPFNumbering numbering;
	BigInteger bulletNumID;

	public WordTemplate() {
		copyStylesFromBase();
	}

	/// Will set the basic properties and "metadata" for our document
	void copyStylesFromBase() {
		// open base file and copy styles over to our document:
		// certain styles only exist when a DOCX file has been written with those
		// included - they're not there by default
		// at least they're consistent between word and LibreOffice
		try (FileInputStream in = new FileInputStream("poi/base.docx")) {
			this.doc = new XWPFDocument(in);

			numbering = doc.getNumbering();
			for (XWPFNum num : numbering.getNums()) {
				BigInteger thisNumId = num.getCTNum().getNumId();
				// check, ob das zugehörige AbstractNum BULLET ist
				if (numbering.getAbstractNum(num.getCTNum().getAbstractNumId().getVal()).getAbstractNum().getLvlArray(0)
						.getNumFmt().getVal().equals(STNumberFormat.BULLET)) {
					bulletNumID = thisNumId;
					break;
				}
			}
			if (bulletNumID == null) {
				throw new IllegalStateException("Keine Bullet-Nummerierung in base.docx gefunden!");
			}

			this.removeAllParagraphs(); // clear the content of what we've copied from the template
		} catch (Exception e) {
			logError(e.getLocalizedMessage());
		}
	}

	void setTemplateContent() {
		// Text goes into Paragraphs
		// hard-coded B)
		XWPFParagraph title = doc.createParagraph();
		title.setStyle("Heading1");
		writeRun(title, documentHeading, 35);

		XWPFParagraph date = doc.createParagraph();
		writeRun(date, ReportModel.dateFormat.format(new Date()));

		XWPFParagraph attendeesHeading = doc.createParagraph();
		attendeesHeading.setStyle("Heading2");
		writeRun(attendeesHeading, WordTemplate.attendeesHeading, 25);

		XWPFParagraph attendees = doc.createParagraph();
		writeRun(attendees, "Name A, Name B, Name C");

		XWPFParagraph agendaHeading = doc.createParagraph();
		agendaHeading.setStyle("Heading2");
		writeRun(agendaHeading, WordTemplate.agendaHeading, 25);

		XWPFParagraph agendaPoint = doc.createParagraph();
		agendaPoint.setNumID(bulletNumID);
		writeRun(agendaPoint, "erster Punkt der Agenda");

		XWPFParagraph notesHeading = doc.createParagraph();
		notesHeading.setStyle("Heading2");
		writeRun(notesHeading, WordTemplate.notesHeading, 25);

		XWPFParagraph notePoint = doc.createParagraph();
		notePoint.setNumID(bulletNumID);
		writeRun(notePoint, "eine erste Notiz");
	}

	void removeAllParagraphs() {
		List<XWPFParagraph> paragraphsToRemove = new ArrayList<>(this.doc.getParagraphs());
		for (XWPFParagraph paragraph : paragraphsToRemove) {
			this.doc.removeBodyElement(this.doc.getPosOfParagraph(paragraph));
		}
	}

	// since there are no default paramenter values, *sigh*...

	void writeRun(XWPFParagraph paragraph, String text) {
		writeRun(paragraph, text, 20, "000000", "Helvetica"); // Default font size, color, and family
	}

	void writeRun(XWPFParagraph paragraph, String text, int fontSize) {
		writeRun(paragraph, text, fontSize, "000000", "Helvetica"); // Default font size and family
	}

	void writeRun(XWPFParagraph paragraph, String text, int fontSize, String color) {
		writeRun(paragraph, text, fontSize, color, "Helvetica"); // Default font size
	}

	void writeRun(XWPFParagraph paragraph, String text, int fontSize, String color, String fontFamily) {
		XWPFRun paragraphRun = paragraph.createRun(); // region of text with common set of properties
		paragraphRun.setText(text);
		paragraphRun.setColor(color);
		paragraphRun.setFontFamily(fontFamily);
		paragraphRun.setFontSize(fontSize);
	}

	void writeEmptyLine() {
		writeRun(doc.createParagraph(), "");
	}

	public void writeTemplate() {
		setTemplateContent();
		WordDemoWriter.save(this.doc, WordTemplate.output); // output is static, that's why not 'this.output'
	}

	public static void main(String[] args) {
		log("creating word template..");

		WordTemplate document = new WordTemplate();
		document.writeTemplate();
	}
}
