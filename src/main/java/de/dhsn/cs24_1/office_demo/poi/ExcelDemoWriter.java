package de.dhsn.cs24_1.office_demo.poi;

import static de.dhsn.cs24_1.office_demo.shared.OurLog.log;
import static de.dhsn.cs24_1.office_demo.shared.OurLog.logError;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
			log("successfully written to file :) " + path);
		} catch (Exception e) {
			logError("saving Excel document failed, " + e.getLocalizedMessage());
		}
	}

	public void open(String path) {
		try {
			InputStream inp = new FileInputStream(path);
			workbook = (XSSFWorkbook) WorkbookFactory.create(inp);
		} catch (Exception e) {
			logError("opening Excel document failed, " + e.getLocalizedMessage());
		}
	}

}
