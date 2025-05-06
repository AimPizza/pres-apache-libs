package de.dhsn.cs24_1.office_demo.poi;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class ExcelTemplateWriter {

	public static String[] headerElements = { "Aufgabe", "Verantwortlich", "Deadline", "Status", "Priorität" };

	// TODO:
	// - format pretty table

	public static void main(String[] args) {
		ExcelDemoWriter document = new ExcelDemoWriter(new String[] { "Aufgaben" });
		XSSFSheet sheet = document.workbook.getSheetAt(0);

		Row headerRow = sheet.createRow(1);
		for (int i = 0; i < headerElements.length; i++) {
			String headerElement = headerElements[i];

			Cell cell = headerRow.createCell(i + 1);
			cell.setCellValue(headerElement);
		}

		document.save("./template.xlsx");
	}
}
