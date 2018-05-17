import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.After;
//import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.SocketException;




public class ComparisonDBExcel {

	   public WebDriver driver;
	   String baseUrl;
	   public static final String dbClassName= "com.mysql.jdbc.Driver";
	   public static final String dbClassName1= "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	   Connection myCon = null;
	   Connection myCon1 = null;
	   Statement mySt;
	   PreparedStatement myPst,myPst1;
	   ResultSet rs1,rs2;
	   String myQuery1;
	   String mySQL,myUName,myPswd;
	   String mySQL1,myUName1,myPswd1;
	   String p1;
	   String p2;
	   int xRows, xCols;
	   String Data[][];
	   String DataF[][];
	   int arraycount=1;
	   int k = 1, a=1;
	   int count;
	   String output = "";
	
	
	@Before
	public void setUp() throws ClassNotFoundException, SQLException {
		
		System.setProperty("webdriver.chrome.driver", "C:/chromedriver.exe");
		driver = new ChromeDriver();
    	//driver = new HtmlUnitDriver(BrowserVersion.CHROME);
	
    	//baseUrl = "https://portal.dwcexchange.com/";
    	baseUrl = "http://portal.dwcexchange.com/Login.aspx";

    	driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    	
    	 mySQL  = "jdbc:mysql://66.171.243.205:3306/SkypeCDRBackLog";
	     myUName  = "skypecdrbacklog";
	     myPswd  =  "skypecdrbacklog@7700";
	   
	     Class.forName(dbClassName);

	     myCon = DriverManager.getConnection(mySQL,myUName,myPswd);
	     
	     
		
		
	}
	
	@Test
	public void test() throws Exception {
		
		
		for (int i = 1; i<Data.length ; i++) {
			
			
		
		p1 = Data[i][0];
		p2 = Data[i][4];
		mySt = myCon.createStatement();
		myQuery1 = "select count(*) as count from dwceams.dbo.EAMS_REQUEST where form_id = 11 and created_by = 26 and state= 'OPEN' and case_no = ? and lienreservation = ?;";
		myPst = myCon.prepareStatement(myQuery1);
		myPst.setString(1,p1);
		myPst.setString(2,p2);
		rs1 = myPst.executeQuery();
		//System.out.println(myQuery1);
		rs1.next();
		
		count = rs1.getInt("count");
		
		if (count == 1) {
			
			DataF[a][0] = Data[i][0];
			DataF[a][1] = Data[i][1];
			DataF[a][2] = Data[i][2];
			DataF[a][3] = Data[i][3];
			DataF[a][4] = Data[i][4];
			DataF[a][5] = Data[i][5];
			DataF[a][6] = Data[i][6];
			
			a++;
			
			}
		
		}
		
		xlwrite(output,Data);
		
	}
	
	public void xlRead(String sPath) throws IOException {
		

      	
      	//try {
      	
      	File myxl = new File (sPath);
  		FileInputStream mystream = new FileInputStream (sPath);
  		HSSFWorkbook workbook = new HSSFWorkbook (mystream);
  		//HSSFWorkbook mySheet = new HSSFSheet (myWB);
  		
  		HSSFSheet sheet = workbook.getSheetAt(0); 	//change the sheet no for different sheets
  		

  		

  		xRows = sheet.getLastRowNum() + 1;
  		//System.out.println("rows are "+xRows);
  		xCols = sheet.getRow(0).getLastCellNum();
  		System.out.println("Rows are "+xRows);
  		System.out.println("Cells are "+xCols);
  		
  		int remain = xRows -1;
  		
  		Data = new String [xRows][xCols];
  		//Data = new String [xRows][4];
  		

  			
  				for (int i = 1 ; i < xRows ; i++) {    	//change the i value to change the row  
  	 			
  	 			
  	 			HSSFRow row = sheet.getRow (i);
  	 			
  	 			if (row!=null) {
  	 				
  	 				 for (short j =0 ; j < 1 ; j++){
  	 					 
  	 					if (row.getCell(j) != null) {
  	 						
  	 						HSSFCell Cell = row.getCell(j);
  			 				String value = celltoString (Cell);
  			 				
  			 				if (value.equals("") || value.equals(" ") || value == null) {
  								
  								//System.out.println("Ignore");
  								
  							
  									}else {
  								
  										arraycount++;
  										//System.out.println("araycount is "+arraycount);
  								}
  			 				
  	 						}
  	 				
  	 					 
  	 				 }
  		 				
  		 				
  	 			}
  	 			
  	
  			}
  			
  			//arraycount--;
  			Data = new String [arraycount][7];
  			DataF = new String [arraycount][7];
  			
  			xRows = arraycount;
  			remain = xRows -1;
  			System.out.println("Actual Data Rows Are "+(xRows-1));
  			
  	 		for (int i = 1 ; i <xRows ; i++) {    	//change the i value to change the row                  
   			
   			HSSFRow row = sheet.getRow (i);
   			for (short j =0 ; j < 7 ; j++){
   				HSSFCell Cell = row.getCell(j);
   				String value = celltoString (Cell);

   				

   				if (!value.equals("") || !value.equals(" ")) {
   					
   					//System.out.println("i is "+i);
   					//System.out.println("k is "+k);
   					//System.out.println("l is "+l);
   					//xData[k][j] = value;
       				Data[k][j] = value;

       				
       				System.out.print(Data[k][j]);
       				System.out.println();
       				
       				
   				
   					}
  	
   				
   				}
  			
   					k++;
  	 		}	
  			
  	
  		
      	//}catch(Exception e) {
      		
      		//excep=1;
      		
      	//}

      
	}
	
	 public static String celltoString (HSSFCell cell) {
	  		
	  		int type = cell.getCellType();
	  		Object result;
	  		switch (type){
	  		case HSSFCell.CELL_TYPE_NUMERIC: //0
	  			result = cell.getNumericCellValue();
	  			break;
	  		case HSSFCell.CELL_TYPE_STRING: //1
	  			result = cell.getStringCellValue();
	  			break;
	  		case HSSFCell.CELL_TYPE_FORMULA: //2
	  			throw new RuntimeException ("We can't evaluate formulas in java");
	  			
	  		case HSSFCell.CELL_TYPE_BLANK: //3
	  			result = "";
	  			break;
	  		case HSSFCell.CELL_TYPE_BOOLEAN: //4
	  			result = cell.getBooleanCellValue();
	  			break;
	  		case HSSFCell.CELL_TYPE_ERROR: //5
	  			throw new RuntimeException ("This cell has an error");
	  			
	  			default:
	  				throw new RuntimeException("We dont support this cell type:"+type);

	  		}
	  	
	  		return result.toString();
	  	}
	     
	     
	     
	     public void xlwrite(String xlpath_Res, String[][] xldata) throws Exception {
	   	  
	   	  String Headings[] = new String[7];
	   	  Headings[0] = "ADJCASE_NUMBER";
	   	  Headings[1] = "PROVIDER";
	   	  Headings[2] = "FIRST_NAME";
	   	  Headings[3] = "LAST_NAME";
	   	  Headings[4] = "LIENRESERVATION";
	   	  Headings[5] = "EMPLOYER";
	   	  Headings[6] = "INSURANCE";



	   	  
	   		File outFile = new File(output);
	   		HSSFWorkbook myworkbook = new HSSFWorkbook();
	   		HSSFSheet osheet = myworkbook.createSheet("Results");
	   		
	   		for (int myrow = 0; myrow<1 ; myrow++) {
	   			
	   			HSSFRow row =osheet.createRow(myrow);
	   			for (short mycol =0; mycol < 7; mycol++) { 
	   				

	   				
	   				HSSFCell cell = row.createCell(mycol);
	   				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	   				HSSFFont font = myworkbook.createFont();
	   				HSSFCellStyle cellStyle = myworkbook.createCellStyle();
	   				font.setFontName(HSSFFont.FONT_ARIAL);
	   				font.setFontHeightInPoints((short)10);
	   				font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	   				font.setColor(IndexedColors.RED.getIndex());
	   				cellStyle.setFont(font);
	   		        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	   				cell.setCellStyle(cellStyle);
	   				cell.setCellValue(Headings[mycol]);
	   				
	   				}
	   				
	   			}
	   			
	   		
	   		
	   		
	   			for (int myrow =1; myrow<a; myrow++) {
	   				HSSFRow row =osheet.createRow(myrow);
	   				for (short mycol =0; mycol <7; mycol++) {
	   					HSSFCell cell = row.createCell(mycol);
	   					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	   					
	   					/*HSSFCellStyle cellStyle1 = myworkbook.createCellStyle();
	   				
	   					cellStyle1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	   					cell.setCellStyle(cellStyle1);*/
	   													
	   					cell.setCellValue(Data[myrow][mycol]);
	   					FileOutputStream fOut = new FileOutputStream(outFile);
	   					myworkbook.write(fOut);
	   					fOut.flush();
	   					fOut.close();
	   					}
	   			 }
	   		} 
	      
	
	
	
}
