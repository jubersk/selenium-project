package com.eLabelz.web.tests;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.eLabelz.web.utilitiy.BaseClass;
import com.eLabelz.web.utilitiy.ConstantOnlineReg;
import com.eLabelz.web.utilitiy.ExcelUtils;
import com.eLabelz.web.utilitiy.Log;
import org.apache.log4j.xml.DOMConfigurator;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;



public class TC_01_UserRegistration extends BaseClass{
	private String sTestCaseName;
	private int iTestCaseRow;
	private static String navigateURL = "";

	@BeforeClass
	public void init() throws Exception {
		ReportInitialization();
		// Configuring Log4j logs
		DOMConfigurator.configure("log4j.xml");
		// The below method will refine your test case name, exactly the name use have used
		sTestCaseName = getTestCaseName(this.toString());
		System.out.println("Test Case Name:" + sTestCaseName);
		// Test Case name
		Log.startTestCase("Test Case Name:" + sTestCaseName);

		// Setting up the Test Data Excel file using Constants variables
		ExcelUtils.setExcelFile(ConstantOnlineReg.Path_TestData + ConstantOnlineReg.File_TestData,"TestData");
		iTestCaseRow = ExcelUtils.getRowContains(sTestCaseName,ConstantOnlineReg.Col_TestCaseName);
		System.out.println("TestCase Row Number" +iTestCaseRow);
		navigateURL = ExcelUtils.getCellData(iTestCaseRow, ConstantOnlineReg.Col_URL);	
		System.out.println(navigateURL);
		driverinit();
		driver.get(navigateURL);
		Log.info("Web application launched successfully");
		testLogger = extent.createTest("User Registration", "This test verifies User Registration");
		testLogger.log(Status.INFO, "Web application launched successfully");
		//wait = new WebDriverWait(driver, 120);
	}

	@Test
	public void userAccountRegistration() throws Exception {
		try{

			testLogger.log(Status.INFO, "User Registration Test Started"+this.getClass().getName());
			Log.info(" User Registration Tests started");
			Reporter.log(" User Registration Tests started");
			//Tests Actual Steps
			fn_Registration(iTestCaseRow);

		}catch (Exception e){
			Log.info(" User Registration Tests Failed because of Verification");
			Reporter.log(" User Registration Tests Failed because of Verification");
			testLogger.log(Status.FAIL, "User Registration Tests Failed because of Verification"+this.getClass().getName());
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
		Reporter.log("User Registration Test Ends::" + sTestCaseName);
		testLogger.log(Status.INFO,"User Registration Test Ends::" + sTestCaseName);
		WindUp();
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
		Thread.sleep(1000);

		driver.findElement(By.cssSelector("#postcode")).clear();
		driver.findElement(By.cssSelector("#postcode")).sendKeys(ExcelUtils.getCellData(iTestCaseRow, ConstantOnlineReg.Col_ZipCode));

		driver.findElement(By.cssSelector("#phone_mobile")).clear();
		driver.findElement(By.cssSelector("#phone_mobile")).sendKeys(ExcelUtils.getCellData(iTestCaseRow, ConstantOnlineReg.Col_Mobile));


		//Click register button
		driver.findElement(By.cssSelector("#submitAccount")).click();
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".account"))));
		//Verify that verification code page is displayed
		if(isElementPresent(driver,By.cssSelector(".account"))){
			Log.info("User Registration Completed Successfully");
			Reporter.log("User Registration Completed Successfully");
			testLogger.log(Status.PASS, "User Registration Completed Successfully");

		}
		else{
			Log.info("User Registration Failed");
			Reporter.log("User Registration Failed");
			testLogger.log(Status.FAIL, "User Registration Failed");
			takeScreenshot(driver, "Registration");
		}



	}
}
