package com.eLabelz.web.utilitiy;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelUtils {
	private static XSSFSheet ExcelWSheet;
	private static XSSFWorkbook ExcelWBook;
	private static XSSFCell Cell;
	private static XSSFRow Row;
	
	
	/*public String[][] getExcelData(String fileName, String sheetName) {
		String[][] arrayExcelData = null;
		try {
			FileInputStream fs = new FileInputStream(fileName);
			XSSFWorkbook wb = new XSSFWorkbook(fs);
			XSSFSheet sh = wb.getSheetAt(1);

			int totalNoOfCols = sh.getLeftCol();
			int totalNoOfRows = getRowUsed();
			
			arrayExcelData = new String[totalNoOfRows-1][totalNoOfCols];
			
			for (int i= 1 ; i < totalNoOfRows; i++) {

				for (int j=0; j < totalNoOfCols; j++) {
					arrayExcelData[i-1][j] = sh.getCell(j, i).getContents();
				}

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			e.printStackTrace();
		} catch (BiffException e) {
			e.printStackTrace();
		}
		return arrayExcelData;
	}*/
	
	//This method is to set the File path and to open the Excel file, Pass Excel Path and Sheetname as Arguments to this method
	public static void setExcelFile(String Path,String SheetName) throws Exception {
		try {
			// Open the Excel file
			FileInputStream ExcelFile = new FileInputStream(Path);
			// Access the required test data sheet
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			System.out.println(ExcelWSheet);
			Log.info("Excel sheet opened");
		} catch (Exception e){
			throw (e);
		}
	}
	//This method is to read the test data from the Excel cell, in this we are passing parameters as Row num and Col num
	public static String getCellData(int RowNum, int ColNum) throws Exception{
		try{

			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
			Cell.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
			String CellData = Cell.getStringCellValue();
			return CellData;
		}catch (Exception e){
			return"";
		}
	}
	//This method is to write in the Excel cell, Row num and Col num are the parameters
	@SuppressWarnings("static-access")
	public static void setCellData(String Result,  int RowNum, int ColNum) throws Exception    {
		try{
			Row  = ExcelWSheet.getRow(RowNum);
			Cell = Row.getCell(ColNum, org.apache.poi.ss.usermodel.Row.RETURN_BLANK_AS_NULL);
			if (Cell == null) {
				Cell = Row.createCell(ColNum);
				Cell.setCellValue(Result);
			} else {
				Cell.setCellValue(Result);
			}
			// Constant variables Test Data path and Test Data file name
		/*	FileOutputStream fileOut = new FileOutputStream(Constant.Path_TestData + Constant.File_TestData);
			ExcelWBook.write(fileOut);
			// fileOut.flush();
			fileOut.close();*/
			
		    // Reload the workbook, workaround for bug 49940
		    // https://issues.apache.org/bugzilla/show_bug.cgi?id=49940
			//ExcelWBook = new XSSFWorkbook(new FileInputStream(Constant.Path_TestData + Constant.File_TestData));
		}catch(Exception e){
			throw (e);
		}
	}

	public static int getRowContains(String sTestCaseName, int colNum) throws Exception{
		int i;
		try {
			int rowCount = ExcelUtils.getRowUsed();
			for ( i=0 ; i<rowCount; i++){
				if  (ExcelUtils.getCellData(i,colNum).equalsIgnoreCase(sTestCaseName)){
					break;
				}
			}
			System.out.println(sTestCaseName+ "::"+ (i));
			return i;
		}catch (Exception e){
			Log.error("Class ExcelUtil | Method getRowContains | Exception desc : " + e.getMessage());
			throw(e);
		}
	}

	public static int getRowUsed() throws Exception {
		try{
			int RowCount = ExcelWSheet.getLastRowNum();
			Log.info("Total number of Row used return as < " + RowCount + " >.");		
			return RowCount;
		}catch (Exception e){
			Log.error("Class ExcelUtil | Method getRowUsed | Exception desc : "+e.getMessage());
			System.out.println(e.getMessage());
			throw (e);
		}

	}
	public static void writeWorkbook()throws Exception{
		
		FileOutputStream fileOut = new FileOutputStream(ConstantOnlineReg.Path_TestData + ConstantOnlineReg.File_TestData);
		ExcelWBook.write(fileOut);
		// fileOut.flush();
		fileOut.close();
			}
	public static void clearCells()throws Exception{
		
		//Clear previous result 
	
			}
	
	public static Iterator<org.apache.poi.ss.usermodel.Row> getCurrentSheetRows() {
		return ExcelWSheet.iterator();
	}
	
}