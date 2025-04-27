package de.dhsn.cs24_1.office_demo;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class ParseWordTemplate {

	public static void main(String[] args) {
		System.out.println("helloou");
		// TODO: import the stuff from WordTemplate

		Path msWordPath = Paths.get(WordTemplate.output);

		XWPFDocument document = new XWPFDocument(Files.newInputStream(msWordPath));
		System.out.println("ehehe I left off here");
	}

}
