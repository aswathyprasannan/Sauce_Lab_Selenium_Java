package utilities;


import java.util.Date;
import java.util.List;
import java.io.File;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;

import org.apache.poi.ss.usermodel.Sheet;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import io.qameta.allure.Allure;
import org.openqa.selenium.WebDriver;
public class CommonUtilities {

	
	private static final boolean True = false;
	public FileInputStream fis = null;
    public XSSFWorkbook workbook = null;
    public XSSFSheet sheet = null;
    public XSSFRow row = null;
    public XSSFCell cell = null;
    public FileOutputStream fos = null;
	 String filePath = System.getProperty("user.dir")+"\\src\\";
	 String fileName = filePath+"TestData.xlsx";
	 String hFileName = filePath+"TestDataH.xlsx";
	 
	

	 public void findElementByClassNameAndText(String className, Object desiredText, WebDriver driver) {
		 
			// Find element by class name and text
		      

		 		        // Find all elements with the given class name
		        List<WebElement> elements = driver.findElements(By.className(className));

		        // Iterate through the elements and find the one with the desired text
		        for (WebElement element : elements) {
		        	
		        //	System.out.println(element.getText());
		            if (element.getText().equals(desiredText)) {
		                 element.click();
		                break;
		            }
		        }
			  
		  }
	 
	 
	 // method for step creation in the input field
	 public void type(WebDriver driver,String fieldName, By elTxtBox, String value)
     {
		 
        Allure.step("Enter the "+fieldName+" \"" +value+ "\" into the "+fieldName+" input field.");
        WebElement txtBx = driver.findElement(elTxtBox);
        txtBx.clear();
        txtBx.sendKeys(value);

     }
	 
	 
	 
	 public void slowType(WebDriver driver, By elTxtBox, String value)
	 {
		 
		 WebElement element = driver.findElement(elTxtBox);
		 for (char c : value.toCharArray()) {

			 element.sendKeys(Character.toString(c));
			 try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 }

	 }
	 
	 public void writeToExcels(String sheetName, String clnHeader, String value)
	    {
		 this.writeToExcel(sheetName, clnHeader, value);
		 this.writeToExcelH(sheetName, clnHeader, value);
	    }
	 
	 public void writeToExcel(String sheetName, String clnHeader, String value)
	    {
		
	        try
	        {
	        	File file = new File(fileName);
	            
	            if (file.exists()==false) {
	                System.out.println("File not exists: " + fileName);
	                
	                workbook = new XSSFWorkbook();
	               FileOutputStream outputStream = new FileOutputStream(fileName);
	                    workbook.write(outputStream);
	                    System.out.println("Excel file created: " + fileName);
	              
	               
	            } 
	        	
	        	
	            int col_Num = -1;
	            
	            
	        	fis = new FileInputStream(fileName);
	    		
	            
				workbook = new XSSFWorkbook(fis);
	            sheet = workbook.getSheet(sheetName);
	 if(sheet==null) {
		 
	 sheet = workbook.createSheet(sheetName);
	 }
	            XSSFRow row = sheet.getRow(0);
	            
	            System.out.println(sheet.getLastRowNum());
	            if (row==null) {
	            	Row header = sheet.createRow(0);
	            	Cell headerCell = header.createCell(0);
	            }
	            
	            row = sheet.getRow(0);
	            XSSFRow row1 = sheet.getRow(1);
	            Row r = sheet.getRow(0);
	            System.out.println(r);
	            int maxCell=  r.getLastCellNum();
	            col_Num = maxCell;
	            
	            
	            for (int i = 0; i < row.getLastCellNum(); i++) {
	                if (row.getCell(i).getStringCellValue().trim().equals(clnHeader))
	                {
	                    col_Num = i;
	                }
	                
	                	 
	             
	            }
	           
	            sheet.autoSizeColumn(col_Num);
	            row = sheet.getRow(0);
	        
	            
	            if(row.getCell(0).getStringCellValue().isEmpty()) {
	            	System.out.println("first cell is null");
	            	cell = row.getCell(0);
	            	 cell.setCellValue(clnHeader);
	            	 col_Num = 0;
	            }
	            else {
	                cell = row.createCell(col_Num);
	                
	               
	            cell.setCellValue(clnHeader);
	            }
	            if (row1==null) {
	            	Row headern = sheet.createRow(1);
	            	Cell Cell1 = headern.createCell(col_Num);
	            	Cell1.setCellValue(value);
	            }
	            else {
	            	  // Row lastRow = sheet.createRow(1);
	            	Cell celljn = row1.createCell(col_Num);
	                  // Cell celljn = row1.getCell(col_Num);
	                   celljn.setCellValue(value);
	            }
	        

	            fos = new FileOutputStream(fileName);
	            workbook.write(fos);
	            fos.close();
	            workbook.close();
	        }
	        catch (Exception ex)
	        {
	            ex.printStackTrace();
	            
	        }
	      
	    }
	  
	 public void writeToExcelH(String sheetName, String clnHeader, String value)
	    {
		
	        try
	        {
	        	
File file = new File(hFileName);
	            
	            if (file.exists()==false) {
	                System.out.println("File not exists: " + hFileName);
	                
	                workbook = new XSSFWorkbook();
	               FileOutputStream outputStream = new FileOutputStream(hFileName);
	                    workbook.write(outputStream);
	                    System.out.println("Excel file created: " + hFileName);
	              
	               
	            } 
	            int col_Num = -1;
	            
	            
	        	fis = new FileInputStream(hFileName);
	    		
	            
				workbook = new XSSFWorkbook(fis);
	            sheet = workbook.getSheet(sheetName);
	 if(sheet==null) {
		 
	 sheet = workbook.createSheet(sheetName);
	 }
	            XSSFRow row = sheet.getRow(0);
	            System.out.println(sheet.getLastRowNum());
	            if (row==null) {
	            	Row header = sheet.createRow(0);
	            	Cell headerCell = header.createCell(0);
	            }
	            row = sheet.getRow(0);
	            XSSFRow row1 = sheet.getRow(1);
	            Row r = sheet.getRow(0);
	            System.out.println(r);
	            int maxCell=  r.getLastCellNum();
	            col_Num = maxCell;
	            
	            
	            for (int i = 0; i < row.getLastCellNum(); i++) {
	                if (row.getCell(i).getStringCellValue().trim().equals(clnHeader))
	                {
	                    col_Num = i;
	                }
	                
	                	 
	             
	            }
	           
	            sheet.autoSizeColumn(col_Num);
	            row = sheet.getRow(0);
	        
	            
	            if(row.getCell(0).getStringCellValue().isEmpty()) {
	            	System.out.println("first cell is null");
	            	cell = row.getCell(0);
	            	 cell.setCellValue(clnHeader);
	            	 col_Num = 0;
	            }
	            else {
	                cell = row.createCell(col_Num);
	                
	               
	            cell.setCellValue(clnHeader);
	            }
	            int lastRowValue =0;
	            System.out.println("last row"+sheet.getLastRowNum());
	         //   XSSFCell cell1 = row1.createCell(col_Num);
	          //  cell1.setCellValue(value);
	            for (int j = 0; j <= sheet.getLastRowNum(); j++) {
	            	 XSSFRow rowj = sheet.getRow(j);
	                if (rowj.getCell(col_Num)==null)
	                {
	                	System.out.println("test");
	                	System.out.println(j);
	                	
	                    XSSFCell cellj = rowj.createCell(col_Num);
	                    
	                    
	                    cellj.setCellValue(value);
	                	 break;
	                }
	                  
	                lastRowValue =j;
	             
	            }

	            if (lastRowValue == sheet.getLastRowNum()) {
	            	
	                System.out.println("test2");
	               // XSSFRow rowjn = sheet.getRow(j+1);
	                Row lastRow = sheet.createRow(lastRowValue+1);
	                Cell celljn = lastRow.createCell(col_Num);
	                
	                
	                celljn.setCellValue(value);
	            }
	 
	            fos = new FileOutputStream(hFileName);
	            workbook.write(fos);
	            fos.close();
	            workbook.close();
	        }
	        catch (Exception ex)
	        {
	            ex.printStackTrace();
	            
	        }
	      
	    }
	
	 
	 public void setCellDataLC(String sheetName, String clnHeader, String value)
	    {
	        try
	        {
	            int col_Num = -1;
	            
	            
	        	fis = new FileInputStream(fileName);
	    		
	            
				workbook = new XSSFWorkbook(fis);
	            sheet = workbook.getSheet(sheetName);
	           // if(sheet==null) {
	           	 
	            	 //sheet = workbook.createSheet(sheetName);
	            	// }
	            
	            row = sheet.getRow(0);
	            XSSFRow row1 = sheet.getRow(1);
	            Row r = sheet.getRow(0);
	            int maxCell=  r.getLastCellNum();
	            col_Num = maxCell;
	            for (int i = 0; i < row.getLastCellNum(); i++) {
	                if (row.getCell(i).getStringCellValue().trim().equals(clnHeader))
	                {
	                    col_Num = i;
	                }
	                
	                	 
	             
	            }
	           
	            sheet.autoSizeColumn(col_Num);
	            row = sheet.getRow(0);
	        
	                cell = row.createCell(col_Num);
	                
	                XSSFCell cell1 = row1.createCell(col_Num);
	            cell.setCellValue(clnHeader);
	            cell1.setCellValue(value);
	            

	 
	            fos = new FileOutputStream(fileName);
	            workbook.write(fos);
	            fos.close();
	            workbook.close();
	        }
	        catch (Exception ex)
	        {
	            ex.printStackTrace();
	            
	        }
	       
	    }
	 
    public String getCellData(String sheetName, String colName)
    {
    	int col_Num = 0;
    	 try {
			fis = new FileInputStream(fileName);
			workbook = new XSSFWorkbook(fis);         
            sheet = workbook.getSheet(sheetName);
            row = sheet.getRow(0);
            fis.close();
            for(int i = 0; i < row.getLastCellNum(); i++)
            {
                if(row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
                    col_Num = i;
            }
 
            row = sheet.getRow(2 - 1);
            cell = row.getCell(col_Num);
 
            if(cell.getCellTypeEnum() == CellType.STRING)
                return cell.getStringCellValue();
            else if(cell.getCellTypeEnum() == CellType.NUMERIC || cell.getCellTypeEnum() == CellType.FORMULA)
            {
                String cellValue = String.valueOf(cell.getNumericCellValue());
                if(HSSFDateUtil.isCellDateFormatted(cell))
                {
                    DateFormat df = new SimpleDateFormat("dd/MM/yy");
                    Date date = cell.getDateCellValue();
                    cellValue = df.format(date);
                }
                
                return cellValue;
            }else if(cell.getCellTypeEnum() == CellType.BLANK)
                return "";
            else
                return String.valueOf(cell.getBooleanCellValue());
            
        }
    	 
                catch(Exception e)
                {
                   // e.printStackTrace();
                	
                	System.out.println("passed");
                    return "row "+2+" or column "+ col_Num  +" does not exist  in Excel";
                }
                       	 
              
     }
   //To get selection for dropdowns  
    public String dropdownmethod(WebDriver driver, String path) throws Exception
    {
		
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(path)));
		WebElement element = driver.findElement(By.cssSelector(path));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		Thread.sleep(500);
		return path; 	
    }    
    
    
    public void dropDownClick(WebDriver driver, By dropDownName, String strDropDownValue ) throws Exception
	{
    	
    	WebElement element = driver.findElement(dropDownName);
    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    	Thread.sleep(500);
         
		
		driver.findElement(dropDownName).click();;
		//Thread.sleep(4000);
		
		
		String drpsel="//li[contains(.,\'"+strDropDownValue+"\')]";
		By dropDownValue= By.xpath(drpsel);
		
	    driver.findElement(dropDownValue).click();
	  
		
		
	}
    
    public void dropDownEnter(WebDriver driver, By dropDownName, String strDropDownValue, By strDropDownNameTwo) throws Exception
   	{
       	
       	WebElement element = driver.findElement(dropDownName);
       	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
       	Thread.sleep(500); 
              		
   		driver.findElement(dropDownName).click();
   		Thread.sleep(500);
   		
   	    WebElement w= driver.findElement(strDropDownNameTwo);
		Thread.sleep(1000);
		//Thread.sleep(4000);
		w.sendKeys(strDropDownValue);
		w.sendKeys(Keys.ENTER);	
   	    
   	}
    public void dropdownVerify(WebDriver driver, WebElement path, String exptText)
    {
    	SoftAssert softAssert=new SoftAssert();
		String acttext =path.getText();
		String excptdText = exptText;
		softAssert.assertEquals(acttext, excptdText, "Field Data Mismatched");
    }
    public void checkboxVerify(WebDriver driver, WebElement path)
    {
    	SoftAssert softAssert=new SoftAssert();
		boolean acText= path.isSelected();
		boolean expText =True;
		softAssert.assertEquals(acText, expText, "Mismatch");
    }
    
    public void tableDropdownVerification(WebDriver driver)
    {

		WebElement table = driver.findElement(By.xpath("")); 

		String headerName = "Marketplace"; 

		int columnIndex = -1; // initialize column index to -1

		List<WebElement> headerCells = table.findElements(By.tagName("th")); // get all the header cells

		for (int i = 0; i < headerCells.size(); i++) {

			if (headerName.equals(headerCells.get(i).getText())) {

				columnIndex = i; // update the column index when header name matches
				System.out.println("Number of coloumn : "+columnIndex);
				break; // exit the loop once the header name is found

			}

		}

	List<WebElement> rows = table.findElements(By.tagName("tr")); // get all the rows in the table

	for(WebElement row : rows)
	{
		List<WebElement> cells = row.findElements(By.tagName("td")); // get all the cells in each row
		if (columnIndex < cells.size()) 
		{ 
			// check if the column index is within bounds
			String cellText = cells.get(columnIndex).getText().toString();
			System.out.println("values found : "+cellText);
		
		}
		
		

	}
	
	
  }
    
    
    
    
}
	
	
	
