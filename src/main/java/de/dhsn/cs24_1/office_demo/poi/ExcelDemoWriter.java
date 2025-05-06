package de.dhsn.cs24_1.office_demo.poi;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import de.dhsn.cs24_1.office_demo.shared.OurLog;

public class ExcelDemoWriter {

	public XSSFWorkbook workbook;

	public ExcelDemoWriter(String[] sheetNames) {
		this.workbook = new XSSFWorkbook();

		for (String sheetName : sheetNames) {
			workbook.createSheet(sheetName);
		}
	}

	public static void main(String[] args) {
		ExcelDemoWriter writer = new ExcelDemoWriter(new String[] { "Testdaten" });

		XSSFSheet sheet = writer.workbook.getSheetAt(0);

		Row row = sheet.createRow(0);
		Cell cell = row.createCell(0);
		cell.setCellValue("Hello from my test!");

		writer.save("test.xlsx");
	}

	public void save(String path) {
		try {
			FileOutputStream out = new FileOutputStream(new File(path));
			workbook.write(out);
			out.close();
			workbook.close();
			OurLog.log("successfully written to file :) " + path);
		} catch (Exception e) {
			OurLog.logError(e.getMessage());
		}

	}

}
