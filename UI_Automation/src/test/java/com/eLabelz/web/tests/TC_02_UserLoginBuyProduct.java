package com.eLabelz.web.tests;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.eLabelz.web.utilitiy.BaseClass;
import com.eLabelz.web.utilitiy.ConstantOnlineReg;
import com.eLabelz.web.utilitiy.ExcelUtils;
import com.eLabelz.web.utilitiy.Log;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class TC_02_UserLoginBuyProduct extends BaseClass {

	private String sTestCaseName;
	private int iTestCaseRow;
	private static String navigateURL = "";



	@BeforeClass
	public void init() throws Exception {

		// Configuring Log4j logs
		DOMConfigurator.configure("log4j.xml");
		// The below method will refine your test case name, exactly the name use have used
		sTestCaseName = getTestCaseName(this.toString());
		System.out.println("Test Case Name:" + sTestCaseName);
		//Test Case name
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
		testLogger = extent.createTest("User Login and Buy Product", "This test verifies User Login and Buy Product");
		testLogger.log(Status.INFO, "Web application launched successfully");
	}


	@Test
	public void userLogin() throws Exception {
		try{
			
			testLogger.log(Status.INFO, "User Login and Buy Product Test Started::"+sTestCaseName);
			Log.info("Login Test started::" + sTestCaseName);
			Reporter.log("Login Test started::" + sTestCaseName);

			//User Login
			fn_LoginFlow(iTestCaseRow);

			//add to cart and buy
			fn_AddToCart(iTestCaseRow);


		}catch (Exception e){
			Log.info("Login Tests Failed because of Verification");
			Reporter.log("Login Tests Failed because of Verification");
			// If in case you got any exception during the test, it will mark your test as Fail in the test result sheet
			ExcelUtils.setCellData("Fail", iTestCaseRow, ConstantOnlineReg.Col_Result);
			ExcelUtils.writeWorkbook();
			// If the exception is in between the test, bcoz of any element not found or anything, this will take a screen shot
			takeScreenshot(driver, sTestCaseName);
			// This will print the error log message
			Log.error(e.getMessage());
			throw (e);
		}
	}



	@AfterClass
	public void quit() {
		Log.endTestCase(sTestCaseName);
		Reporter.log("  Login Test Ends::" + sTestCaseName);
		testLogger.log(Status.INFO,"User Registration Test Ends::" + sTestCaseName);
		WindUp();
		driver.close();
	}


	public static void fn_LoginFlow(int iTestCaseRow) throws Exception{
		//click on signin
		driver.findElement(By.cssSelector(".login")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#email")));

		driver.findElement(By.cssSelector("#email")).sendKeys(ExcelUtils.getCellData(iTestCaseRow, ConstantOnlineReg.Col_Email));
		Thread.sleep(1000);

		driver.findElement(By.cssSelector("#passwd")).sendKeys(ExcelUtils.getCellData(iTestCaseRow, ConstantOnlineReg.Col_Password));
		Thread.sleep(1000);

		//Click on submit button
		driver.findElement(By.cssSelector("#SubmitLogin")).click();
		Thread.sleep(2000);

		if(isElementPresent(driver,By.cssSelector(".account"))){
			Log.info("User Login Successfully");
			Reporter.log("User Login Successfully");
			testLogger.log(Status.PASS, "User Login Completed Successfully");

		}
		else{
			Log.info("User Login Failed");
			Reporter.log("User Login Failed");
			testLogger.log(Status.FAIL, "User Login Failed");
			takeScreenshot(driver, "Registration");
		}

	}

	public static void fn_AddToCart(int iTestCaseRow) throws Exception{
		driver.get(ExcelUtils.getCellData(iTestCaseRow, ConstantOnlineReg.Col_Product1));

		//Click on bag and check that item is added to cart
		driver.findElement(By.cssSelector("button.exclusive")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".layer_cart_product")));

		driver.findElement(By.cssSelector("a.btn:nth-child(2)")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".standard-checkout")));
		driver.findElement(By.cssSelector(".standard-checkout")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#address_delivery")));

		driver.findElement(By.cssSelector("	button.button:nth-child(4)")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".delivery_options_address")));

		driver.findElement(By.cssSelector("#cgv")).click();
		driver.findElement(By.cssSelector("	button.button:nth-child(4)")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#HOOK_PAYMENT")));

		driver.findElement(By.cssSelector(".bankwire")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button.button-medium")));

		driver.findElement(By.cssSelector("button.button-medium")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".box")));

		if(isElementPresent(driver,By.cssSelector(".box"))){
			Log.info("User Ordered the Product Successfully");
			Reporter.log("User Ordered the Product Successfully");
			testLogger.log(Status.PASS, "User Ordered the Product Successfully");

		}
		else{
			Log.info("User Order Failed");
			Reporter.log("User Order Failed");
			testLogger.log(Status.FAIL, "User Order Failed");
			takeScreenshot(driver, "Registration");
		}


	}


}
