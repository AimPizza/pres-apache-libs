package de.dhsn.cs24_1.office_demo.poi;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

/* Here we try to parse the Word template specified in @see de.dhsn.cs24_1.office_demo.WordTemplate
 * 
 */
public class ParseWordTemplate {

	// TODO: should we handle this IOException?
	public static void main(String[] args) throws IOException {
		System.out.println("helloou");
		// TODO: import the stuff from WordTemplate

		Path msWordPath = Paths.get(WordTemplate.output);

		XWPFDocument document = new XWPFDocument(Files.newInputStream(msWordPath));
		document = new XWPFDocument(Files.newInputStream(msWordPath));

		List<XWPFParagraph> paragraphs = document.getParagraphs();
		document.close();

		List<XWPFParagraph> bulletParagraphs = paragraphs.stream().filter(p -> p.getNumID() != null)
				.collect(Collectors.toList());
		for (XWPFParagraph paragraph : bulletParagraphs) {
			System.out.println("• " + paragraph.getText());
		}
		System.out.println("ehehe I left off here");
	}

}
