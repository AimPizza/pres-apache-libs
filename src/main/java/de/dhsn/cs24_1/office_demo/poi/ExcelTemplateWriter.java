package de.dhsn.cs24_1.office_demo.poi;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFTable;

public class ExcelTemplateWriter {

	public static String[] headerElements = { "Aufgabe", "Verantwortlich", "Deadline", "Status", "Priorität" };

	// TODO:
	// - format pretty table

	public static void main(String[] args) {
		ExcelDemoWriter document = new ExcelDemoWriter(new String[] { "Aufgaben" });
		XSSFSheet sheet = document.workbook.getSheetAt(0);

		AreaReference reference = document.workbook.getCreationHelper().createAreaReference(new CellReference(1, 1),
				new CellReference(5, 5));
		XSSFTable table = sheet.createTable(reference);
		// I didn't fix the IDs like a user on stackoverflow suggests doing

		table.setName("Aufgabentabelle");
		table.setDisplayName("Aufgabentabelle (Displayname?)");

		// filter
		table.getCTTable().addNewAutoFilter().setRef(table.getArea().formatAsString());

		Row headerRow = sheet.createRow(1);
		for (int i = 0; i < headerElements.length; i++) {
			String headerElement = headerElements[i];
			Integer columnIndex = i + 1;

			Cell cell = headerRow.createCell(columnIndex);
			cell.setCellValue(headerElement);

			sheet.autoSizeColumn(columnIndex);
			// make some extra space
			sheet.setColumnWidth(columnIndex, sheet.getColumnWidth(columnIndex) + 5 * 256);
		}

		document.save("./template.xlsx");
	}
}
