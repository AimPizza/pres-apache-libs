package de.dhsn.cs24_1.office_demo.poi;

import static de.dhsn.cs24_1.office_demo.shared.Utilities.log;

import java.util.Arrays;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.ConditionalFormattingRule;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PatternFormatting;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.SheetConditionalFormatting;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFTable;

import de.dhsn.cs24_1.office_demo.poi.basics.ExcelDemoWriter;

public class ExcelTemplateWriter {

	public ExcelTemplateWriter() {
		this.document = new ExcelDemoWriter(new String[] { "Aufgaben" });
		this.sheet = document.workbook.getSheetAt(0);
	}

	public static String[] headerElements = { "Aufgabe", "Verantwortlich", "Deadline", "Erledigt", "Priorität" };
	public static String demoFilePath = "poi/template.xlsx";

	int horizontalOffset = 1;
	int verticalOffset = 1;
	public ExcelDemoWriter document;
	public XSSFSheet sheet;

	public void removeCompletedTasks() {

		document.open(demoFilePath);
		sheet = document.workbook.getSheetAt(0);

		int statusCol = Arrays.asList(headerElements).indexOf("Erledigt") + horizontalOffset;
		int descCol = Arrays.asList(headerElements).indexOf("Aufgabe") + verticalOffset;
		int lastRow = sheet.getLastRowNum();

		// iterate from bottom to just below header (1=header)
		for (int r = lastRow; r >= 2; r--) {
			Row row = sheet.getRow(r);
			if (row == null)
				continue;
			Cell cell = row.getCell(statusCol);
			// if there's text and it's not empty → delete
			if (cell != null && cell.getCellType() == CellType.STRING && !cell.getStringCellValue().trim().isEmpty()) {

				// purely for logging
				Cell descCell = row.getCell(descCol);
				String desc = (descCell != null ? descCell.getStringCellValue().trim() : "<that was an empty task!>");
				log("Removing completed task: " + desc);
				// remove and shift other rows safely
				sheet.removeRow(row);
				if (r < lastRow)
					sheet.shiftRows(r + 1, lastRow, -1);
				lastRow--;
			}
		}

		document.save(demoFilePath);
	}

	/// Will make row LIME colored if the completion column has any value
	public void addConditionalFormatting() {
		SheetConditionalFormatting sheetCF = sheet.getSheetConditionalFormatting();
		// E: Spalte des Status, 3: offset
		ConditionalFormattingRule rule = sheetCF.createConditionalFormattingRule("NOT(ISBLANK($E3))");

		PatternFormatting fill = rule.createPatternFormatting();
		fill.setFillBackgroundColor(IndexedColors.LIME.index); // https://www.linkedin.com/pulse/color-palette-poi-indexedcolors-aniruddha-duttachowdhury
		fill.setFillPattern(PatternFormatting.SOLID_FOREGROUND);

		ConditionalFormattingRule[] conditionalFormattingRules = new ConditionalFormattingRule[] { rule };

		CellRangeAddress[] regions = new CellRangeAddress[] { CellRangeAddress.valueOf("B3:F1000") };
		sheetCF.addConditionalFormatting(regions, conditionalFormattingRules);
	}

	public void writeTemplate() {
		AreaReference reference = document.workbook.getCreationHelper().createAreaReference(
				new CellReference(verticalOffset, horizontalOffset),
				new CellReference(verticalOffset + 4, horizontalOffset + 4));
		XSSFTable table = sheet.createTable(reference);
		// I didn't fix the IDs like a user on stackoverflow suggests doing

		table.setName("Aufgabentabelle");
		table.setDisplayName("Aufgabentabelle (Displayname?)");

		// filters
		table.getCTTable().addNewAutoFilter().setRef(table.getArea().formatAsString());

		Row headerRow = sheet.createRow(verticalOffset);
		for (int i = 0; i < headerElements.length; i++) {
			String headerElement = headerElements[i];
			Integer columnIndex = i + horizontalOffset;

			Cell cell = headerRow.createCell(columnIndex);
			cell.setCellValue(headerElement);

			sheet.autoSizeColumn(columnIndex);
			// make some extra space
			sheet.setColumnWidth(columnIndex, sheet.getColumnWidth(columnIndex) + 5 * 256);
		}

		addConditionalFormatting();

		document.save(demoFilePath);
	}

	public static void main(String[] args) {
		ExcelTemplateWriter writer = new ExcelTemplateWriter();

		writer.writeTemplate();
	}
}
