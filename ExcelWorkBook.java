package com.svk.foxwebsite;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelWorkBook {
	private String fileName = null;
	private String sheetName = null;


	public ExcelWorkBook(String fileName, String sheetName) {
		if (fileName != null) {
			this.fileName = fileName;
		} else {
			this.fileName = System.getProperty("user.dir") + "\\Book1.xlsx";
		}
		if (fileName != null) {
			this.sheetName = sheetName;
		} else {
			this.sheetName = "Sheet1";
		}
	}

	public void writeData(Object[][] dataToWrite) {
		FileOutputStream outputStream = null;
		Workbook workbook = null;
		try {
			File file = new File(this.fileName);
			if (this.fileName.toUpperCase().endsWith(".XLSX")) {
				if(file.exists()) {
					workbook = (XSSFWorkbook) WorkbookFactory.create(new FileInputStream(file));
				}else {
					workbook = new XSSFWorkbook();
				}				

			} else {
				if(file.exists()) {
					workbook = (HSSFWorkbook) WorkbookFactory.create(new FileInputStream(file));
				}else {
					workbook = new HSSFWorkbook();
				}	
			}
			
			
			
			Sheet sheet = workbook.createSheet(this.sheetName);

			int rowCount = 0;
			for (Object[] rows : dataToWrite) {
				Row row = sheet.createRow(rowCount);
                rowCount++;
				int columnCount = 0;

				for (Object field : rows) {
					Cell cell = row.createCell(columnCount);
					columnCount++;
					if (field instanceof String) {
						cell.setCellValue((String) field);
					} else if (field instanceof Integer) {
						cell.setCellValue((Integer) field);
					} else if (field instanceof Date) {
						cell.setCellValue((Date) field);
					}
				}
			}
			outputStream = new FileOutputStream(file);
			workbook.write(outputStream);
		} catch (Exception ex) {
			System.err.println("Error in writing to excel : "+ex.getMessage());
		} finally {
			try {
				workbook.close();
				outputStream.close();
			} catch (Exception excep) {
			}
		}

	}
	public Object[][] readData() {
		FileInputStream inputStream = null;
		Workbook workbook = null;
		Object[][] readData = null;
		try {
			File file = new File(this.fileName);
			inputStream = new FileInputStream(file);
			if (this.fileName.toUpperCase().endsWith(".XLSX")) {
				workbook = new XSSFWorkbook(inputStream);
			} else {
				workbook = new HSSFWorkbook(inputStream);
			}
			Sheet sheet = null;
			if(this.sheetName != null && "".equals(this.sheetName)){
				sheet =workbook.getSheet(this.sheetName);
			}else{
				sheet =workbook.getSheetAt(0);
			}
			int rowCount = sheet.getLastRowNum()-sheet.getFirstRowNum();
			int colCount = sheet.getRow(0).getLastCellNum() - sheet.getRow(0).getFirstCellNum();
			readData = new Object[rowCount][colCount];
		    
		    for(int i =0; i < sheet.getLastRowNum(); i++){
		    	Row row = sheet.getRow(i);
			    for(int j = 0; j < row.getLastCellNum(); j++){			    	
			    	readData[i][j] = row.getCell(j).getStringCellValue();
			    }
		    }

		} catch (Exception ex) {
			System.err.println("Error in reading excel : "+ex.getMessage());
		} finally {
			try {
				workbook.close();
				inputStream.close();
			} catch (Exception excep) {
			}
		}
		return readData;
	}

}
