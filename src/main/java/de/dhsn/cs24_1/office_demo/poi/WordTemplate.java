package de.dhsn.cs24_1.office_demo.poi;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFAbstractNum;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFNum;
import org.apache.poi.xwpf.usermodel.XWPFNumbering;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTAbstractNum;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTInd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTLvl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STNumberFormat;

import de.dhsn.cs24_1.office_demo.shared.OurLog;
import de.dhsn.cs24_1.office_demo.shared.ReportModel;

/* IDEAS FOR PRESENTATION
 * - show xml output of a document https://modkp-technotes.blogspot.com/2013/04/poi-ordered-list-and-unordered-list.html 
 * - explain chicanery with "Heading1" and other styles
 */
public class WordTemplate extends XWPFDocument {

	public static final String documentHeading = "Meeting Notes Template";
	public static final String attendeesHeading = "Attendees";
	public static final String agendaHeading = "Agenda Items";
	public static final String tasksHeading = "Tasks";

	public static String output = "poi/output.docx";
	private XWPFDocument doc;
	private XWPFNumbering numbering;
	BigInteger bulletNumID;

	public WordTemplate() throws IOException {
		// certain styles only exist when a docx file has been written with those
		// included - they're not there by default
		// at least they're consistent between word and libreoffice
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

			this.removeAllParagraphs();
		}
		createBaseTemplate();
	}

	public void createBaseTemplate() {
		// Elements come in Paragraphs
		XWPFParagraph title = doc.createParagraph();
		title.setStyle("Heading1");
		writeRun(title, documentHeading, 35);

		XWPFParagraph date = doc.createParagraph();
		writeRun(date, ReportModel.dateFormat.format(new Date()));

		XWPFParagraph attendeesHeading = doc.createParagraph();
		attendeesHeading.setStyle("Heading2");
		writeRun(attendeesHeading, WordTemplate.attendeesHeading, 25);

		XWPFParagraph attendees = doc.createParagraph();
		writeRun(attendees, "mit, Komma, getrennt");

		XWPFParagraph agendaHeading = doc.createParagraph();
		agendaHeading.setStyle("Heading2");
		writeRun(agendaHeading, WordTemplate.agendaHeading, 25);

		XWPFParagraph agendaPoint = doc.createParagraph();
		agendaPoint.setNumID(bulletNumID);
		writeRun(agendaPoint, "erster Punkt der Agenda");

		XWPFParagraph tasksHeading = doc.createParagraph();
		tasksHeading.setStyle("Heading2");
		writeRun(tasksHeading, WordTemplate.tasksHeading, 25);

		XWPFParagraph taskPoint = doc.createParagraph();
		taskPoint.setNumID(bulletNumID);
		writeRun(taskPoint, "eine erste Aufgabe");
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

	BigInteger customNumbering() {
		numbering = doc.createNumbering();
		CTAbstractNum abstractNum = CTAbstractNum.Factory.newInstance();
		abstractNum.setAbstractNumId(BigInteger.valueOf(0));
		CTLvl lvl = abstractNum.addNewLvl();
		lvl.setIlvl(BigInteger.ZERO);
		lvl.addNewNumFmt().setVal(STNumberFormat.BULLET);
		lvl.addNewLvlText().setVal("•");
		CTInd indent = lvl.addNewPPr().addNewInd();
		indent.setLeft(BigInteger.valueOf(720));

		XWPFAbstractNum xwpfAbstractNum = new XWPFAbstractNum(abstractNum);
		BigInteger abstractNumID = numbering.addAbstractNum(xwpfAbstractNum);
		BigInteger numID = numbering.addNum(abstractNumID);
		return numID;
	}

	public void save() {
		try (FileOutputStream out = new FileOutputStream(output)) {
			doc.write(out);
			OurLog.log("success saving file");
		} catch (Exception e) {
			OurLog.logError(e.getMessage());
		}
	}

	public static void main(String[] args) throws IOException {
		OurLog.log("creating word template..");

		WordTemplate document = new WordTemplate();
		document.save();

		// Close the document after saving
		try {
			document.close();
		} catch (Exception e) {
			OurLog.logError(e.getLocalizedMessage());

		}
	}
}
