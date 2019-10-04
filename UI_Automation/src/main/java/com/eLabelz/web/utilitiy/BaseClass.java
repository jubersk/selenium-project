package com.eLabelz.web.utilitiy;

import java.awt.Robot;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Random;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;




public class BaseClass {
	
	public static WebDriverWait wait;
	public static boolean bResult;
	public static Properties messageValdition = null; 
	public static Properties api = null;
	public static Properties docsprop = null; 
	public static ExtentReports extent;
	public static ExtentTest logger;
	
	@BeforeSuite
	public void extentReport(){
		extent = new ExtentReports(System.getProperty("user.dir") + "/test-output/ExtentReportNew.html", true);
	}

	@AfterSuite
	public void flushExtentReport(){
		extent.flush();
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

		
	public static void takeScreenshot(RemoteWebDriver driver, String sTestCaseName) throws Exception{
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
