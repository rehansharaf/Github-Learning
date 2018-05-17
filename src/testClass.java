

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Test;


public class testClass {
    
    public String SRC = "C:/Users/Rehan/Desktop/PDF/NORAssigned.pdf";
    public String DEST = "C:/Users/Rehan/Desktop/PDF/NORAssignedEdited.pdf";
    String a1 = "First Field variable";
    
    @Test
    public void test() throws DocumentException, IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        manipulatePdf(SRC, DEST);
    }
    public void manipulatePdf(String src, String dest) throws DocumentException, IOException {
        PdfReader reader = new PdfReader(src);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
        AcroFields form = stamper.getAcroFields();
        form.setField("txtRepERN", a1);
        form.setField("txtRepUAN", "Rehan Khan");
        form.setField("txtRepAddress", "House1128 Phase 2 Ext. Street 9");
        form.setField("txtRepCity", "Islamabad");
        form.setField("txtRepPhone", "03352107719");
        form.setField("txtPAtientName", "ALEX HALES");
        form.setField("txtEmployerName", "ADNAN SHARAF");
        form.setField("txtInsuranceCompany", "Insurance Company");
        form.setField("txtInsuranceCompany", "Insurance Company");
           
        stamper.setFormFlattening(true);
        stamper.close();
    }
    
}
