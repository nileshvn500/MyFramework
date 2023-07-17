package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.common.io.Files;

public class UpdateExcel {

	private XSSFWorkbook workbook = null;
	private XSSFSheet sheet = null;
	private FileOutputStream fos = null;
	private FileInputStream fs = null;
	static File dest = null;

	public XSSFWorkbook getTargetWorkbook() {

		File file = new File("src/test/resources/TestCases/");
		File f = new File(file, "Test.xlsx");

		if (dest == null) {

			String fileSuffix = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			File report = new File("C:\\Users\\NILESH NALAWADE\\Desktop\\Study\\Orange.tests\\TCReport");

			dest = new File(report, "Test" + "_" + fileSuffix + ".xlsx");
			try {
				Files.copy(f, dest);

			} catch (IOException e1) {

				e1.printStackTrace();
			}
		}

		// FileInputStream fs = null;
		try {
			fs = new FileInputStream(dest.getAbsolutePath());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			workbook = new XSSFWorkbook(fs);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// XSSFSheet sheet = workbook.getSheet("Demo");
		return workbook;

	}

	public int getColumnNumber(String targetSheet, String columnName) {

		sheet = getTargetWorkbook().getSheet(targetSheet);
		int firstRow = sheet.getFirstRowNum();
		int totalNoOfColumn = sheet.getRow(firstRow).getPhysicalNumberOfCells();
		int columnNumber = 0;
		for (int i = 0; i < totalNoOfColumn; i++) {

			Cell cell = sheet.getRow(firstRow).getCell(i);

			if (cell.getStringCellValue().equalsIgnoreCase(columnName)) {

				columnNumber = i;
				break;
			}
		}
		return columnNumber;
	}

	public int getRowNumber(String targetSheet, String testcaseId) {

		sheet = getTargetWorkbook().getSheet(targetSheet);
		int firstRow = sheet.getFirstRowNum();
		int lastRow = sheet.getLastRowNum();
		int totalRows = lastRow - firstRow;
		int columnNumber = getColumnNumber(targetSheet, "TestcaseID");
		int rowNumber = 0;
		for (int i = 1; i <= totalRows; i++) {

			Cell cell = sheet.getRow(firstRow + i).getCell(columnNumber);

			if (cell.getStringCellValue().equalsIgnoreCase(testcaseId)) {

				rowNumber = firstRow + i;
				break;
			}
		}
		return rowNumber;
	}

	public void printTestResult(String targetSheet, String testcaseId, String status, String comments) {

		workbook = getTargetWorkbook();
		sheet = workbook.getSheet(targetSheet);
		int testcaseRow = getRowNumber(targetSheet, testcaseId);
		int statusColumn = getColumnNumber(targetSheet, "status");
		int commentsColumn = getColumnNumber(targetSheet, "comments");

		Row row = sheet.getRow(testcaseRow);
		Cell cell = row.createCell(statusColumn);
		cell.setCellValue(status);
		cell = row.createCell(commentsColumn);
		cell.setCellValue(comments);
		try {
			fs.close();
		} catch (IOException e1) {

			e1.printStackTrace();
		}

		try {
			// File file = new File("src/test/resources");
			// File f = new File(file, "SampleTestcaseFile1.xlsx");
			fos = new FileOutputStream(dest.getAbsolutePath());

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			workbook.write(fos);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			workbook.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
