package utility;

import java.io.File;
import java.io.FileInputStream;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadexcelLibrary {


 XSSFWorkbook wb;
	 

 XSSFSheet sheetNum;
	
	public ReadexcelLibrary(String filepath)
	{
      try 
      {
		 File src = new File(filepath);
		  
		  FileInputStream fis = new FileInputStream(src);
		  
		  wb = new XSSFWorkbook(fis);
		  
		
 } 
      catch (Exception e) 
      {
        System.out.println(e.getMessage());

	}  
      
      
      }
	public String getData(int sheetNumber, int row, int cell)
    {
		  sheetNum = wb.getSheetAt(sheetNumber);
		 
           String data = sheetNum.getRow(row).getCell(cell).getStringCellValue();
  	 
           return data;
           
       
    }
	
	public int totalCell(int sheetNumber)
	{ 
		sheetNum = wb.getSheetAt(sheetNumber);
		
		int totalcell = wb.getSheetAt(sheetNumber).getDefaultColumnWidth();
		
		return totalcell;
	}


	public  int totalRow(int sheetNumber) {
		
		  sheetNum = wb.getSheetAt(sheetNumber);
	
			int totalrows = wb.getSheetAt(sheetNumber).getLastRowNum();
		    
			totalrows = totalrows+1;
			
		 //  System.out.println("total number of rows =" +totalrows);
		    
			return totalrows; 
			
	}
}
