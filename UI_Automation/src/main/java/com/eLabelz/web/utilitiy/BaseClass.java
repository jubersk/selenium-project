package com.eLabelz.web.utilitiy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;




public class BaseClass {
	
	public static WebDriverWait wait;
	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports extent;
	public static ExtentTest testLogger;
	public static WebDriver driver;
	private static String rootPath = "";
		
	public void ReportInitialization() {
		//Creating html report
		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir")+"\\Reports\\ExtentReport.html");
		//Initializing extent report class object
		extent = new ExtentReports();
		//Attaching html report to Extent report
		extent.attachReporter(htmlReporter);
		
		//config
		htmlReporter.config().setDocumentTitle("ExtentReports V4");
		htmlReporter.config().setTheme(Theme.DARK);
		htmlReporter.config().setReportName("Automation Results Report");
		
		//Add additional information to report
		extent.setSystemInfo("Application Name", "ecomm");
		extent.setSystemInfo("Environment", "qmg");
		extent.setSystemInfo("OS", "Windows 10");
		extent.setSystemInfo("Engineer", "JGS");
				
	}
	
	public void WindUp() {		
		extent.flush();		
	}
	
	public void driverinit() {
		String driverPath = rootPath + "Drivers\\";
		System.setProperty("webdriver.chrome.driver", driverPath+ "chromedriver.exe");
		driver = new ChromeDriver();
		Log.info("New driver instantiated");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		Log.info("Implicit wait applied on the driver for 20 seconds");
		wait = new WebDriverWait(driver, 120);
	}

	public static boolean isElementPresent(WebDriver driver,By Locator){

		if(driver.findElements(Locator).size()>0)
		{
			return true;
		}else
		{
			return false;
		}
	}

	
	public static String getTestCaseName(String sTestCase)throws Exception{
		String value = sTestCase;
		try{
			int posi = value.indexOf("@");
			value = value.substring(0, posi);
			posi = value.lastIndexOf(".");	
			value = value.substring(posi + 1);
			//System.out.println("Value of the getTestCaseName in class Utils" + value);
			return value;
		}catch (Exception e){
			Log.error("Class Utils | Method getTestCaseName | Exception desc : "+e.getMessage());
			throw (e);
		}
	}

		
	public static void takeScreenshot(WebDriver driver, String sTestCaseName) throws Exception{
		try{
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(ConstantOnlineReg.Path_ScreenShot + sTestCaseName +".jpg"));	
		} catch (Exception e){
			Log.error("Class Utils | Method takeScreenshot | Exception occured while capturing ScreenShot : "+e.getMessage());
			throw new Exception();
		}
	}

	public static void deleteScreenshot()
	{
		try {
			Files.deleteIfExists(Paths.get(ConstantOnlineReg.Path_ScreenShot + ".jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String fn_getAlphabeticString_en() {
		String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 10) {
			int index = (int) (rnd.nextFloat() * CHARS.length());
			salt.append(CHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;

	}

	public static String fn_getNumber() {
		String CHARS = "123456789";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 5) {
			int index = (int) (rnd.nextFloat() * CHARS.length());
			salt.append(CHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;

	}
	

}
