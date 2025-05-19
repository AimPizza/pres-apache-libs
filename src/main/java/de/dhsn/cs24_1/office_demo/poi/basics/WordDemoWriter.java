package de.dhsn.cs24_1.office_demo.poi.basics;

import static de.dhsn.cs24_1.office_demo.shared.Utilities.log;
import static de.dhsn.cs24_1.office_demo.shared.Utilities.logError;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class WordDemoWriter {

	public static void save(XWPFDocument document, String outputPath) {
		try (FileOutputStream out = new FileOutputStream(outputPath)) {
			document.write(out);
			log("success saving file to " + outputPath);
			document.close();
		} catch (Exception e) {
			logError(e.getMessage());
		}

	}

	public static void main(String[] args) {
		XWPFDocument document = new XWPFDocument();

		XWPFParagraph title = document.createParagraph();
		title.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun titleRun = title.createRun();
		titleRun.setText("Hallo, CS24-1!");
		titleRun.setColor("009933");
		titleRun.setBold(true);
		titleRun.setFontFamily("Courier");
		titleRun.setFontSize(20);
		titleRun.setUnderline(UnderlinePatterns.DOT_DASH);
		titleRun.setTextPosition(20); // make some space (here: raise above baseline)

		XWPFParagraph image = document.createParagraph();
		image.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun imageRun = image.createRun();
		Path imagePath = Paths.get("poi/example.jpg");
		try {
			imageRun.addPicture(Files.newInputStream(imagePath), XWPFDocument.PICTURE_TYPE_JPEG,
					imagePath.getFileName().toString(), Units.toEMU(150), Units.toEMU(150));
			// EMU: English Metric Unit (special for scaling images)
		} catch (Exception e) {
			logError("Failed to add picture to WordDemo");
		}

		save(document, "poi/demo.docx");

	}

}
