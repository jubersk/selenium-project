package com.eLabelz.web.tests;

import org.testng.annotations.Test;

import com.eLabelz.web.utilitiy.BaseClass;
import com.eLabelz.web.utilitiy.ConstantOnlineReg;
import com.eLabelz.web.utilitiy.ExcelUtils;
import com.eLabelz.web.utilitiy.Log;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.xml.DOMConfigurator;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;



public class TC_01_UserRegistration extends BaseClass{
	public static RemoteWebDriver driver = null;
	private static String rootPath = "";
	public static WebDriverWait wait;
	public static Properties prop = null; 
	private String sTestCaseName;
	private int iTestCaseRow;
	public static boolean bResult;
	private static String navigateURL = "";

	@BeforeClass
	public void init() throws Exception {
		bResult = true;

		// Configuring Log4j logs
		DOMConfigurator.configure("log4j.xml");
		// The below method will refine your test case name, exactly the name use have used
		sTestCaseName = getTestCaseName(this.toString());
		System.out.println("Test Case Name:" + sTestCaseName);
		// Start printing the logs and printing the Test Case name
		Log.startTestCase("Test Case Name:" + sTestCaseName);

		// Setting up the Test Data Excel file using Constants variables
		ExcelUtils.setExcelFile(ConstantOnlineReg.Path_TestData + ConstantOnlineReg.File_TestData,"TestData");
		// Fetching the Test Case row number from the Test Data Sheet
		// This row number will be feed to so many functions, to get the relevant data from the Test Data sheet 
		iTestCaseRow = ExcelUtils.getRowContains(sTestCaseName,ConstantOnlineReg.Col_TestCaseName);
		System.out.println("TestCase Row Number" +iTestCaseRow);
		navigateURL = ExcelUtils.getCellData(iTestCaseRow, ConstantOnlineReg.Col_URL);	
		System.out.println(navigateURL);
		
		String driverPath = rootPath + "Drivers\\";
		System.setProperty("webdriver.chrome.driver", driverPath+ "chromedriver.exe");
		driver = new ChromeDriver();
		Log.info("New driver instantiated");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		Log.info("Implicit wait applied on the driver for 20 seconds");
		driver.get(navigateURL);
		Log.info("Web application launched successfully");
	//	logger.log(LogStatus.INFO, "Web application launched successfully"+TC_01_UserRegistration.class.getName());
		wait = new WebDriverWait(driver, 120);
	}

	@Test
	public void userAccountRegistration() throws Exception {
		try{

		//	logger.log(LogStatus.INFO, "User Registration Test Started"+this.getClass().getName());
			Log.info(" User Registration Tests started");
			Reporter.log(" User Registration Tests started");
			//Tests Actual Steps
			fn_Registration(iTestCaseRow);

			if(bResult==true){
				Reporter.log(" User Registration Tests verified successfully");
				// If the value of boolean variable is True, then your test is complete pass and do this
				ExcelUtils.setCellData("Pass", iTestCaseRow, ConstantOnlineReg.Col_Result);
				ExcelUtils.writeWorkbook();

			}else{

				Log.info(" User Registration Tests Failed");
				Reporter.log(" User Registration TestsFailed");
				//logger.log(LogStatus.FAIL, "User Registration Test Failed"+this.getClass().getName());
				throw new Exception("User Registration Test Failed");

			}



		}catch (Exception e){
			Log.info(" User Registration Tests Failed because of Verification");
			Reporter.log(" User Registration Tests Failed because of Verification");
			// If in case you got any exception during the test, it will mark your test as Fail in the test result sheet
			ExcelUtils.setCellData("Fail", iTestCaseRow, ConstantOnlineReg.Col_Result);
			ExcelUtils.writeWorkbook();
			//Take Screenshot
			takeScreenshot(driver, sTestCaseName);
			// This will print the error log message
			Log.error(e.getMessage());
			throw (e);
		}

	}


	@AfterClass
	public void quit() {
		Log.endTestCase(sTestCaseName);
		Reporter.log(" User Registration Test Ends::" + sTestCaseName);
		//logger.log(LogStatus.INFO, " User Registration Test Ends::" + sTestCaseName+this.getClass().getName());
	//	extent.endTest(logger);
		driver.close();
		
	}

	

	public static void fn_Registration(int iTestCaseRow)throws Exception{

		driver.findElement(By.cssSelector(".login")).click();
		driver.findElement(By.cssSelector("#email_create")).sendKeys (fn_getAlphabeticString_en() + "@gmail.com");
		
		driver.findElement(By.cssSelector("#SubmitCreate")).click();
		Thread.sleep(2000);
		//Assert Registraton page opned sucessfully
		Assert.assertTrue(driver.findElement(By.cssSelector("#customer_firstname")).isDisplayed());
		
		driver.findElement(By.cssSelector("#id_gender1")).click();

		driver.findElement(By.cssSelector("#customer_firstname")).clear();
		driver.findElement(By.cssSelector("#customer_firstname")).sendKeys(fn_getAlphabeticString_en());

		driver.findElement(By.cssSelector("#customer_lastname")).clear();
		driver.findElement(By.cssSelector("#customer_lastname")).sendKeys(fn_getAlphabeticString_en());

		driver.findElement(By.cssSelector("#passwd")).clear();
		driver.findElement(By.cssSelector("#passwd")).sendKeys(ExcelUtils.getCellData(iTestCaseRow,ConstantOnlineReg.Col_Password));

		Select days = new Select(driver.findElement(By.cssSelector("#days")));
		days.selectByValue(ExcelUtils.getCellData(iTestCaseRow, ConstantOnlineReg.Col_Days));

		Select months = new Select(driver.findElement(By.cssSelector("#months")));
		months.selectByIndex(4);

		Select years = new Select(driver.findElement(By.cssSelector("#years")));
		years.selectByValue(ExcelUtils.getCellData(iTestCaseRow, ConstantOnlineReg.Col_Years));
		
		driver.findElement(By.cssSelector("#address1")).clear();
		driver.findElement(By.cssSelector("#address1")).sendKeys(fn_getAlphabeticString_en());
		
		driver.findElement(By.cssSelector("#city")).clear();
		driver.findElement(By.cssSelector("#city")).sendKeys(fn_getAlphabeticString_en());

		Select state = new Select(driver.findElement(By.cssSelector("#id_state")));
		state.selectByIndex(4);
		
		driver.findElement(By.cssSelector("#postcode")).clear();
		driver.findElement(By.cssSelector("#postcode")).sendKeys(ExcelUtils.getCellData(iTestCaseRow, ConstantOnlineReg.Col_ZipCode));
		
		driver.findElement(By.cssSelector("#phone_mobile")).clear();
		driver.findElement(By.cssSelector("#phone_mobile")).sendKeys(ExcelUtils.getCellData(iTestCaseRow, ConstantOnlineReg.Col_Mobile));
		
		
		//Click register button
		driver.findElement(By.cssSelector("#submitAccount")).click();
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".account"))));
		//Verify that verification code page is displayed
		if(isElementPresent(driver,By.cssSelector(".account"))){
			//logger.log(LogStatus.PASS, "User Registration Sucess"+TC_01_UserRegistration.class.getName());
			Log.info("User Registration Sucess");
			Reporter.log("User Registration Sucess");
				}
		else{
			//logger.log(LogStatus.FAIL, "User Registration Failed"+TC_01_UserRegistration.class.getName());
			Log.info("User Registration Failed");
			Reporter.log("User Registration Failed");
			bResult = false;
			takeScreenshot(driver, "Registration");
		}



	}
}
