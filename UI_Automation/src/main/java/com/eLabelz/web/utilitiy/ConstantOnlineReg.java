package com.eLabelz.web.utilitiy;

import java.nio.file.Path;
import java.nio.file.Paths;


public class ConstantOnlineReg {

	static Path currentRelativePath = Paths.get("");
	static String rootPath = currentRelativePath.toAbsolutePath().toString();
	public static String Path_TestData = rootPath + "\\src\\main\\java\\testData\\";

	public static String Path_ScreenShot = rootPath + "\\src\\main\\java\\com\\eLabelz\\web\\screenshots\\";

	public static final String File_TestData = "TestData.xlsx";

	//Test Data Sheet Columns
	public static final int Col_TestCaseName = 0;	
	public static final int Col_URL = 1;
	public static final int Col_Password = 2;
	public static final int Col_Days = 3;
	public static final int Col_Month = 4;
	public static final int Col_Years = 5;
	public static final int Col_State = 6;
	public static final int Col_ZipCode =7;
	public static final int Col_Mobile = 8;
	public static final int Col_Email = 9;
	public static final int Col_Product1 = 10;
	public static final int Col_Result = 11;







}
