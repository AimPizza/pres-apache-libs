package de.dhsn.cs24_1.office_demo.poi;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelDemoWriter {

	public static void main(String[] args) {
		writeSomething();
	}

	public static void writeSomething() {
		// create workbook
		XSSFWorkbook workbook = new XSSFWorkbook();

		XSSFSheet sheet = workbook.createSheet("Testdaten");

		Row row = sheet.createRow(0);
		Cell cell = row.createCell(0);
		cell.setCellValue("Hello from my test!");

		try {
			FileOutputStream out = new FileOutputStream(new File("test.xlsx"));
			workbook.write(out);
			out.close();
			workbook.close();
			System.out.println("successfully written to file :)");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}
