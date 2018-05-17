import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Before;
import org.junit.Test;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfAction;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfFormField;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PushbuttonField;
import com.itextpdf.text.pdf.parser.LocationTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;


public class AddFieldPDF {
	
	
		public static final String local = "C:/Users/Rehan/Desktop/New folder/";
	    public static final String SRC = local + "2.pdf";
	    public static final String DEST = local + "sysgenerated.pdf";
	    public static final String DEST1 = local;
	    int ycordinate = 390;
	    int xcordinate = 45;
	    int i = 0;
	    PdfContentByte over;
	    BaseFont bf;
	    
	    public static final String dbClassName= "com.mysql.jdbc.Driver";
		Connection myCon = null;
		Statement mySt,mySt1;
		PreparedStatement myPst,myPst1;
		ResultSet rs1,rs2,rs3,rs4;
		String myQuery1;
		String mySQL,myUName,myPswd;
		String p1,p4,p6,p7,p8,p9,p10,p11;
		int p2,p3;
		int count;
		String Data[][];
		String ADJNumber, MRN, Provider;
		int page = 1;
		int j,n=1;
		int b=1,count1;
		PdfReader reader;
		PdfStamper stamper;
		
	    
	@Before
	public void setUp() throws SQLException, ClassNotFoundException {
		
		
		  mySQL  = "jdbc:mysql://66.171.243.205:3306/SkypeCDRBackLog";
		  myUName  = "skypecdrbacklog";
		  myPswd  =  "skypecdrbacklog@7700";
		   
		     Class.forName(dbClassName);

		     myCon = DriverManager.getConnection(mySQL,myUName,myPswd);
		
		
		
	}
	
	@Test
	public void test() throws DocumentException, IOException, SQLException {
		
		
		//File file = new File(DEST);
        //file.getParentFile().mkdirs();
        //manipulatePdf(SRC, DEST);
		
		mySt = myCon.createStatement ();
		myQuery1 = "select count(*) as count from test.Address ;";
		myPst = myCon.prepareStatement(myQuery1);
		rs1 = myPst.executeQuery();
		rs1.next();
			 
			 count = rs1.getInt("count");
			 
			 Data = new String[count][5];
			 
			 
			 mySt = myCon.createStatement ();
			 myQuery1 = "select * from test.Address ;";
			 myPst = myCon.prepareStatement(myQuery1);
			 rs1 = myPst.executeQuery();
			 while (rs1.next()) {
				 
				 Data[i][0] = rs1.getString("SR");
				 Data[i][1] = rs1.getString("PartyName");
				 Data[i][2] = rs1.getString("AddressLine1");
				 Data[i][3] = rs1.getString("CityStateZip");
				 Data[i][4] = rs1.getString("RA");
				 i++;
				 
			 }
			 
		
			 for(j = 0; j<Data.length; j++) {
				 
				 predefined();
				 
				 reader = new PdfReader(DEST); // input PDF
			     stamper = new PdfStamper(reader,
			         new FileOutputStream(DEST1+"/"+Data[j][4]+"_"+Data[j][1]+".pdf")); // output PDF
			       
			     Font font = FontFactory.getFont("/fonts/Sansation_Regular.ttf",
			    		    BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 0.8f, Font.NORMAL, BaseColor.BLACK);
			    		bf = font.getBaseFont();
			       
			         bf = BaseFont.createFont(
			    		   //BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED); // set font
			        		 BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED); // set font
			        
			 
			            over = stamper.getOverContent(page);
				 
				 p1 = Data[j][0];
			     //p1 = "2028";
			     mySt = myCon.createStatement ();
				 myQuery1 = "select count(*) as count from ( select AR.ADJNUMBER, AR.MRN, AR.PROVIDER from test.ADJ ad join test.AR on ad.ADJCASE_NUMBER = AR.ADJNUMBER where ad.SR = ?) t";
				 myPst = myCon.prepareStatement (myQuery1);
				 myPst.setString(1,p1);
				 rs1 = myPst.executeQuery();
				 rs1.next();
				 
				 count1 = rs1.getInt("count");
				
				 
				 p1 = Data[j][0];
				 //p1 = "2028";
				 mySt = myCon.createStatement ();
				 myQuery1 = " select AR.ADJNUMBER, AR.MRN, AR.PROVIDER from test.ADJ ad join test.AR on ad.ADJCASE_NUMBER = AR.ADJNUMBER where ad.SR = ?";
				 myPst = myCon.prepareStatement (myQuery1);
				 myPst.setString(1,p1);
				 rs1 = myPst.executeQuery();
				 while(rs1.next()) {

					 ADJNumber = rs1.getString("ADJNUMBER");
					 MRN = rs1.getString("MRN");
					 Provider = rs1.getString("PROVIDER");
					 
					 
					 
					 addtext();
					 b++;
					 System.out.println("column is "+ycordinate);
					 
				 }
				 
				 b=1;
				 n=1;
				 ycordinate = 390; 
				 
			 }

		
	}

	public void predefined() throws IOException, DocumentException {
		
		PdfReader reader = new PdfReader(SRC);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(DEST));
        AcroFields form = stamper.getAcroFields();
		
		   form = stamper.getAcroFields();
		   form.setField("text2", Data[j][1]);
		   form.setField("text3", Data[j][2]);
		   form.setField("text4", Data[j][3]);
		   
		   stamper.setFormFlattening(true);
	       stamper.close();
	       reader.close();
	        
		
	}

	
	public void addtext() throws FileNotFoundException, DocumentException, IOException {
	
	  
           
           
           /*xcordinate sno45
           xcordinate ADJ145
           xcordinate mrn305
           xcordinate pro425*/
		
        if (ycordinate==50 || ycordinate==60) {
      	   
            page++;	
      	   over.endText(); 
      	   stamper.insertPage(reader.getNumberOfPages() + 1,
         		    reader.getPageSizeWithRotation(1));
      	   
      	   over = stamper.getOverContent(page);
            newpageheading();
      	   
         }
            
            if (b==count1) {
            	
            	
            	over.setTextMatrix(45,ycordinate);   // set x,y position (0,0 is at the bottom left)
                over.showText(Data[j][4]);  // set text
                over.setTextMatrix(145,ycordinate);   // set x,y position (0,0 is at the bottom left)
                over.showText(ADJNumber);  // set text
                over.setTextMatrix(305,ycordinate);   // set x,y position (0,0 is at the bottom left)
                over.showText(MRN);  // set text 
                over.setTextMatrix(425,ycordinate);   // set x,y position (0,0 is at the bottom left)
                over.showText(Provider);  // set text 
                ycordinate = 60;
                
               
            	over.setTextMatrix(450,ycordinate);   // set x,y position (0,0 is at the bottom left)
                over.showText("Thank you");  // set text
                ycordinate = ycordinate -20;
                over.setTextMatrix(450,ycordinate);   // set x,y position (0,0 is at the bottom left)
                over.showText("Angel Salazar");  // set text
                over.endText(); 
                stamper.close();
                reader.close();
            	
            	
            }
            


            	
            	 if (n==1) {
                     
              	   heading();
              	   
              	 over.setTextMatrix(45,ycordinate);   // set x,y position (0,0 is at the bottom left)
                 over.showText(Data[j][4]);  // set text
                 over.setTextMatrix(145,ycordinate);   // set x,y position (0,0 is at the bottom left)
                 over.showText(ADJNumber);  // set text
                 over.setTextMatrix(305,ycordinate);   // set x,y position (0,0 is at the bottom left)
                 over.showText(MRN);  // set text 
                 over.setTextMatrix(425,ycordinate);   // set x,y position (0,0 is at the bottom left)
                 over.showText(Provider);  // set text 
                 ycordinate = ycordinate - 20;
                 
                 }else {
                	 
                	  over.setTextMatrix(45,ycordinate);   // set x,y position (0,0 is at the bottom left)
                      over.showText(Data[j][4]);  // set text
                      over.setTextMatrix(145,ycordinate);   // set x,y position (0,0 is at the bottom left)
                      over.showText(ADJNumber);  // set text
                      over.setTextMatrix(305,ycordinate);   // set x,y position (0,0 is at the bottom left)
                      over.showText(MRN);  // set text 
                      over.setTextMatrix(425,ycordinate);   // set x,y position (0,0 is at the bottom left)
                      over.showText(Provider);  // set text 
                      ycordinate = ycordinate - 20;
                      
                 }
            	
            
            
          

          

   }
	

public void heading() {
	
	 over.beginText();
     over.setFontAndSize(bf, 11);    // set font and size
     over.setTextMatrix(45,ycordinate);   // set x,y position (0,0 is at the bottom left)
     over.showText("S.No");  // set text
     over.setTextMatrix(145,ycordinate);   // set x,y position (0,0 is at the bottom left)
     over.showText("ADJ Number");  // set text
     over.setTextMatrix(305,ycordinate);   // set x,y position (0,0 is at the bottom left)
     over.showText("MRN");  // set text 
     over.setTextMatrix(425,ycordinate);   // set x,y position (0,0 is at the bottom left)
     over.showText("Provider Name");  // set text 
    // over.endText();
     ycordinate = ycordinate - 20;
     n++;
	
}

public void newpageheading() {
	
	 ycordinate = 740;
	 over.beginText();
     over.setFontAndSize(bf, 11);    // set font and size
     over.setTextMatrix(45,ycordinate);   // set x,y position (0,0 is at the bottom left)
     over.showText("S.No");  // set text
     over.setTextMatrix(145,ycordinate);   // set x,y position (0,0 is at the bottom left)
     over.showText("ADJ Number");  // set text
     over.setTextMatrix(305,ycordinate);   // set x,y position (0,0 is at the bottom left)
     over.showText("MRN");  // set text 
     over.setTextMatrix(425,ycordinate);   // set x,y position (0,0 is at the bottom left)
     over.showText("Provider Name");  // set text 
    // over.endText();
     ycordinate = ycordinate - 20;
     n++;
     
     over.setTextMatrix(45,ycordinate);   // set x,y position (0,0 is at the bottom left)
     over.showText(Data[j][4]);  // set text
     over.setTextMatrix(145,ycordinate);   // set x,y position (0,0 is at the bottom left)
     over.showText(ADJNumber);  // set text
     over.setTextMatrix(305,ycordinate);   // set x,y position (0,0 is at the bottom left)
     over.showText(MRN);  // set text 
     over.setTextMatrix(425,ycordinate);   // set x,y position (0,0 is at the bottom left)
     over.showText(Provider);  // set text 
     ycordinate = ycordinate - 20;
	
	
}
	

}
