package tests;

import core.ExcelDataProvider;
import core.ITestData;
import core.TestConfig;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import core.DriverFactory;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

public class TestBase {
	
	private WebDriver driver;
	private ITestData testData;

	@Parameters({"env"})
	@BeforeSuite
	public void initSuite(String env) throws Exception {
		TestConfig.load(env);
	}

	@Parameters({"browser"})
	@BeforeClass
	public void initDriver(String browser) {
		System.out.println(browser);
		driver =  new DriverFactory().getDriver(browser);
	}

	@DataProvider
	public Object[][] getData(Method testCase) throws Exception {
		File testDataLocation = new File("src/test/resources/testdata");
		List<HashMap<String,String>> extractedData = null;
		String dataSource = TestConfig.getProperty("dataSource");
		System.out.println("Data Source : "+ dataSource);
		// Setting the data source
		if(dataSource.equalsIgnoreCase("excel")){
			String sheetName  =  System.getenv("env").toUpperCase();
			this.testData = new ExcelDataProvider(testDataLocation.getAbsolutePath()+"/TestData.xlsx",sheetName);
			extractedData = this.testData.getAllData(testCase.getName());
		}else if(dataSource.equalsIgnoreCase("json")){

		}else{
			throw new Exception("Invalid data source specified : "+dataSource);
		}
		return this.createDataProvider(extractedData);
	}

	private Object[][] createDataProvider(List<HashMap<String,String>> dataSet){
		int rowNo = dataSet.size();
		Object[][] dataArray = new Object[rowNo][2];
		int dim = 0;

		for(int iRow=0;iRow<rowNo;iRow++) {
			dataArray[dim][0] = iRow+1;
			dataArray[dim][1] = dataSet.get(iRow);
			dim++;
		}
		return dataArray;
	}



	public WebDriver getDriver() {
		return driver;
	}
	
	@BeforeMethod
	public void launchApp() {
		driver.get(TestConfig.getProperty("appBaseURL"));
	}
	
	@AfterClass
	public void cleanUp() {
		if(driver!=null) {
			driver.quit();
		}
	}
	

}
