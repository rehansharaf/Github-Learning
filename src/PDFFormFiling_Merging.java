
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BadPdfFormatException;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;


public class PDFFormFiling_Merging {
	
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();
	public static final String dbClassName= "com.mysql.jdbc.Driver";
	String myQuery1;
	String mySQL,myUName,myPswd;
	String p1,p4,p6,p7,p8,p9,p10,p11;
	int p2,p3;
	long p5;
	int i ;
	int d ;
	String T1,d1;
	public int xRows, xCols;
	public String xData[][];
	public String Data[][];
	public String InjuryDate;
	String FilenameF;
	String FilenameR;
	String remoteDirPath2;
	String inputr;
	String filenamed;
	String RemoteDir;
	int arraycount=1;
	int k =1;   //For Rows
	int l =0;   //For Columns
	int MoreCols;
	String Flocal;
    
    public String FormFiling_Court_Src = "C:/Users/adminisb/Desktop/PDFDocs/ChangeOfRep_Temp.pdf";
    public String FormFiling_Court_Desc = "C:/Users/adminisb/Desktop/PDFDocs/systemgenerated_court.pdf";
    

   // public String FormFiling_POS_Src = "C:/Users/adminisb/Desktop/PDFDocs/POS_Temp.pdf";
   // public String FormFiling_POS_Desc = "C:/Users/adminisb/Desktop/PDFDocs/systemgenerated_pos.pdf";

    
	//static String MergeFile1 = "C:/Users/adminisb/Desktop/PDFDocs/systemgenerated_pos.pdf";
	static String MergeFile2 = "C:/Users/adminisb/Desktop/PDFDocs/systemgenerated_court.pdf";
	static String MergeFile3;
	static String MergedFile;
    
    @Test
    public void test() throws DocumentException, IOException {
    	
    	
  
    	Flocal = "C:/Users/adminisb/Desktop/PDFDocs/final2.xls";
    	xlRead(Flocal);
        
    	
    	File file = new File(FormFiling_Court_Desc);
        file.getParentFile().mkdirs();
        
        
        for (i = 1; i<Data.length; i++) {
        
        manipulateCourtPdf(FormFiling_Court_Src, FormFiling_Court_Desc);
       // manipulatePOSPdf(FormFiling_POS_Src, FormFiling_POS_Desc);
        
        MergeFile3 = "C:/Users/adminisb/Desktop/PDFDocs/provider/"+Data[i][4]+".pdf";
        MergedFile = "C:/Users/adminisb/Desktop/PDFDocs/MergedFiles/"+Data[i][0]+"_"+Data[i][4]+".pdf";
        //mergerPDF(MergeFile1,MergeFile2,MergeFile3,MergedFile);
        mergerPDF(MergeFile2,MergeFile3,MergedFile);

        }
    
    }
    
    public void xlRead(String sPath) throws IOException {
    	

    	
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
		
		xData = new String [xRows][xCols];
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
			Data = new String [arraycount][xCols];
			MoreCols=0;
			
			xRows = arraycount;
			remain = xRows -1;
			System.out.println("Actual Data Rows Are "+(xRows-1));
			
	 		for (int i = 1 ; i <xRows ; i++) {    	//change the i value to change the row                  
 			
 			HSSFRow row = sheet.getRow (i);
 			for (short j =0 ; j < xCols ; j++){
 				HSSFCell Cell = row.getCell(j);
 				
 				if (Cell!=null) {
 					
 					String value = celltoString (Cell);
 					
 					if (!value.equals("") || !value.equals(" ") || value!=null) {
 	 					
 	 					//System.out.println("i is "+i);
 	 					//System.out.println("k is "+k);
 	 					//System.out.println("l is "+l);
 	 					xData[k][j] = value;
 	     				Data[k][j] = value;

 	     				
 	     				System.out.print(Data[k][j]);
 	     				System.out.println();
 	     				
 	     				
 	 				
 	 					}
 					
 				}else {
 					
 						xData[k][j] = "-";
	     				Data[k][j] = "-";
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
    

    
public void manipulateCourtPdf(String src, String dest) throws DocumentException, IOException {
    	
	/*
	
	CaseNo = Data[][0]
	PatientName = Data[][1]
	Employer = Data[][2]
	Claim admin = Data[][3]
	provider name = Data[][4]

	
	*/
        PdfReader reader = new PdfReader(src);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
        AcroFields form = stamper.getAcroFields();
        
        if(!Data[i][1].equals("-")) {
        
        	form.setField("Text1", Data[i][1]);
        	
        }
        
        if(!Data[i][2].equals("-")) {
            
        	form.setField("Text2", Data[i][2]+" ;");
        	
        }
        
        if(!Data[i][3].equals("-")) {
            
        	form.setField("Text3", Data[i][3]);
        	
        }
        
        if(!Data[i][0].equals("-")) {
            
        	form.setField("Text4", Data[i][0]);
        	
        }
        
        if(!Data[i][4].equals("-")) {
            
        	form.setField("Text5", Data[i][4]);
        	
        }
        
        
           
        stamper.setFormFlattening(true);
        stamper.close();
        reader.close();
        
    }
    

public void manipulatePOSPdf(String src, String dest) throws DocumentException, IOException {
	
	/*
	
	CaseNo = Data[][0]
	PatientName = Data[][1]
	Employer = Data[][2]
	Claim admin = Data[][3]
	provider name = Data[][4]

	
	*/
        PdfReader reader = new PdfReader(src);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
        AcroFields form = stamper.getAcroFields();
        form.setField("Text1", "01/08/2018");
        
        if (!Data[i][0].equals("-")) {
       
        	 form.setField("Text2", Data[i][0]);
        	
        }
        
        if (!Data[i][6].equals("-")) {
            
       	 form.setField("Text3", Data[i][6]);
       	
       }

        form.setField("Text4", "01/08/2018");
        
        if (Data[1][5].equals("1")) {
        	
        	if (!Data[i][7].equals("-")) {
        		
        		form.setField("Text5", Data[i][7]);
        		
        	}
        	
        	if (!Data[i][8].equals("-")) {
        		
        		form.setField("Text6", Data[i][8]);
        		
        	}
        	
        	if (!Data[i][9].equals("-")) {
        		
        		form.setField("Text7", Data[i][9]);
        		
        	}

        	
        }else {
        

        	
        	if (!Data[i][7].equals("-")) {
        		
        		form.setField("Text5", Data[i][7]);
        		
        	}
        	
        	if (!Data[i][8].equals("-")) {
        		
        		form.setField("Text6", Data[i][8]);
        		
        	}
        	
        	if (!Data[i][9].equals("-")) {
        		
        		form.setField("Text7", Data[i][9]);
        		
        	}

        	
		        	if (!Data[i][10].equals("-")) {
		        		
		        		form.setField("Text8", Data[i][10]);
		        		
		        	}
		        
		        	
		        	if (!Data[i][11].equals("-")) {
		        		
		        		form.setField("Text9", Data[i][11]);
		        		
		        	}
		        

		        	if (!Data[i][12].equals("-")) {
		        		
		        		form.setField("Text10", Data[i][12]);
		        		
		        	}
		        
        	
        }
        
        
        
        stamper.setFormFlattening(true);
        stamper.close();
        reader.close();
        
    }

public void mergerPDF(String file2, String file3, String merge_File) throws IOException, DocumentException {
	

    Document document = new Document();
    PdfCopy copy = new PdfCopy(document, new FileOutputStream(merge_File));

    document.open();
/*
        PdfReader reader = new PdfReader(file1);
        for (int i = 1; i <= reader.getNumberOfPages(); i++){
            // optionally write an if statement to include the page
            copy.addPage(copy.getImportedPage(reader, i));
        }
        copy.freeReader(reader);
        reader.close();*/
        
        PdfReader reader1 = new PdfReader(file2);
        for (int i = 1; i <= reader1.getNumberOfPages(); i++){
            // optionally write an if statement to include the page
            copy.addPage(copy.getImportedPage(reader1, i));
        }
        copy.freeReader(reader1);
        reader1.close();
        
        PdfReader reader2 = new PdfReader(file3);
        for (int i = 1; i <= reader2.getNumberOfPages(); i++){
            // optionally write an if statement to include the page
            copy.addPage(copy.getImportedPage(reader2, i));
        }
        copy.freeReader(reader2);
        reader2.close();

    document.close();


	
}

}

