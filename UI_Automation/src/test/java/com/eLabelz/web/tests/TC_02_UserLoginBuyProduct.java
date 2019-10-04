package com.eLabelz.web.tests;

import org.testng.annotations.Test;

import com.eLabelz.web.utilitiy.BaseClass;
import com.eLabelz.web.utilitiy.ConstantOnlineReg;
import com.eLabelz.web.utilitiy.ExcelUtils;
import com.eLabelz.web.utilitiy.Log;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class TC_02_UserLoginBuyProduct extends BaseClass {

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
		//driver = new HtmlUnitDriver();
		driver = new ChromeDriver();
		Log.info("New driver instantiated");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		Log.info("Implicit wait applied on the driver for 20 seconds");
		driver.get(navigateURL);
		Log.info("Web application launched successfully");
		//logger = extent.startTest(sTestCaseName);
		//logger.log(LogStatus.INFO, "Web application launched successfully"+TC_02_UserLogin.class.getName());
		wait = new WebDriverWait(driver, 120);
	}


	@Test
	public void userLogin() throws Exception {
		try{
			//extent = new ExtentReports(System.getProperty("user.dir") + "/test-output/ExtentReportNew.html", true);
			//logger.log(LogStatus.INFO, "  Login Test started"+TC_02_UserLogin.class.getName());
			Log.info("  Login Test started::" + sTestCaseName);
			Reporter.log("  Login Test started::" + sTestCaseName);
			//Execute the action on below test
			bResult = true;

			//Tests Actual Steps
			fn_LoginFlow(iTestCaseRow);

			//add to cart and buy
			fn_AddToCart(iTestCaseRow);
			if(bResult==true){
				//logger.log(LogStatus.PASS, "  Login Tests has been verified successfully"+TC_02_UserLogin.class.getName());
				Reporter.log(" Login Tests has been verified successfully");
				// If the value of boolean variable is True, then your test is complete pass and do this
				ExcelUtils.setCellData("Pass", iTestCaseRow, ConstantOnlineReg.Col_Result);
				ExcelUtils.writeWorkbook();


			}else{
				//logger.log(LogStatus.FAIL, "  Login   Tests Failed"+TC_02_UserLogin.class.getName());
				Log.info("Login Tests Failed");
				Reporter.log("Login Tests Failed");
				//	logger.log(LogStatus.FAIL, "  Login   Tests Failed"+TC_02_UserLogin.class.getName());
				throw new Exception("Login Tests Failed");
			}

			// Below are the steps you may like to perform in case of failed test or any exception faced before ending your test 
		}catch (Exception e){
			Log.info("Login Tests Failed because of Verification");
			//logger.log(LogStatus.FAIL, "  Login  Tests Failed because of Verification"+TC_02_UserLogin.class.getName());
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
		//logger.log(LogStatus.INFO, "  Login Test Ends::" + sTestCaseName+TC_02_UserLogin.class.getName());
		//extent.endTest(logger);
		//extent.close();
		//driver.close();
		driver.quit();



	}


	public static void fn_LoginFlow(int iTestCaseRow) throws Exception{
		//click on signin
		driver.findElement(By.cssSelector(".login")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#email")));

		driver.findElement(By.cssSelector("#email")).sendKeys(ExcelUtils.getCellData(iTestCaseRow, ConstantOnlineReg.Col_Email));
		Thread.sleep(1000);

		driver.findElement(By.cssSelector("#passwd")).sendKeys(ExcelUtils.getCellData(iTestCaseRow, ConstantOnlineReg.Col_Password));
		Thread.sleep(1000);

		//Click on Login with  button
		driver.findElement(By.cssSelector("#SubmitLogin")).click();
		Thread.sleep(2000);

		//verify user login sucess
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



	}


}
