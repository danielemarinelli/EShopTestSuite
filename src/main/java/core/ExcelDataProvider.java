package core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelDataProvider implements ITestData {

	private String testDataFilePath;
	private FileInputStream fileStream;
	private XSSFWorkbook testDataWorkbook;
	private XSSFSheet testDataSheet;
	private int rowEnd = 0;

	public ExcelDataProvider(String testDataFile, String sheetName) throws IOException {
		this.testDataFilePath = testDataFile;

		fileStream = new FileInputStream(new File(this.testDataFilePath));

		testDataWorkbook = new XSSFWorkbook(fileStream);

		testDataSheet = testDataWorkbook.getSheet(sheetName);

		rowEnd = testDataSheet.getLastRowNum();
	}
		
	public String getTestData(String testCaseName, String columnName) {
		String value = "null";
		for (int i = 0; i <= rowEnd; i++) {
			String testName = getCellDataAsString(testDataSheet.getRow(i).getCell(0));
			if (testName.equalsIgnoreCase(testCaseName)) {
				int j = 0;
				String colName = getCellDataAsString(testDataSheet.getRow(0).getCell(j));

				while (!colName.isEmpty()) {
					colName = getCellDataAsString(testDataSheet.getRow(0).getCell(j));
					if (colName.equalsIgnoreCase(columnName)) {
						value = getCellDataAsString(testDataSheet.getRow(i).getCell(j));
						break;
					}
					j++;
				}
				break;
			}
		}
		return value;
	}
	
	private String getCellDataAsString(XSSFCell cell) {
		String value = "";
		if (cell != null) {
			CellType cellType = cell.getCellType();
			switch (cellType) {
			case BLANK:
				value = "";
				break;
			case BOOLEAN:
				value = (cell.getBooleanCellValue()) ? "true" : "false";
				break;
			case ERROR:
				value = cell.getErrorCellString();
				break;
			case FORMULA:
				value = cell.getStringCellValue();
				break;
			case NUMERIC:
				value = Double.toString(cell.getNumericCellValue());
				break;
			case STRING:
			default:
				value = cell.getStringCellValue();
			}
		}
		return value.trim();
	}
	
	// This method has a functionality to return total number of test
	// data for a given test. If the same test needs to be run with four
	// data set, it will return 4. To relate to it, look at the test data
	// excel and see how we represent multiple data for a single test.
	public int getIterationCountForTest(String testName) {
		int itr = 1;
		for (int i = 0; i <= rowEnd; i++) {
			String tcName = getCellDataAsString(testDataSheet.getRow(i).getCell(0));
			if (testName.equalsIgnoreCase(tcName)) {
				String nextTestName = getCellDataAsString(testDataSheet.getRow(i+1).getCell(0));
				if(!testName.equals(nextTestName)) break;
				itr++;
			}
		}
		return itr;
	}
	
	// This method will return all the test data as a list of hash maps.
	// Given a test case name, you have to return all the test data for that
	// test. Try to implement this.
	public List<HashMap<String, String>> getAllData(String testName) {	
		List<HashMap<String, String>> finalData = new ArrayList<>();	
		for(int iRow = 0;iRow <= rowEnd ; iRow++) {
			//Getting Test Case Name
			String tcName = getCellDataAsString(testDataSheet.getRow(iRow).getCell(0));
			// Checking if the extracted TestCase is equal to the parameter testName
			if (testName.equalsIgnoreCase(tcName.trim())) {
				// Getting the last column number for the test case
				int lastColNo = testDataSheet.getRow(iRow).getLastCellNum();
				HashMap<String,String> innerData = new HashMap<>();
				for(int iCol = 1;iCol < lastColNo ;iCol++) {
					String value = getCellDataAsString(testDataSheet.getRow(iRow).getCell(iCol));
					if(!value.isEmpty()) {
						innerData.put(getCellDataAsString(testDataSheet.getRow(0).getCell(iCol)).toLowerCase(), //key / col name
								value); // value / data
					}
				}

				finalData.add(innerData);
				String nextTestName = getCellDataAsString(testDataSheet.getRow(iRow+1).getCell(0));
				if(!testName.equals(nextTestName)) break;
			}	
		}
		return finalData;
	}
}
