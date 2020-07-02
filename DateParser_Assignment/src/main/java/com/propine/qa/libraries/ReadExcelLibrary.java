package com.propine.qa.libraries;

import java.io.File;
import java.io.FileInputStream;
import java.text.Format;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcelLibrary {

	XSSFWorkbook wb;
	XSSFSheet sheet;
	public ReadExcelLibrary(String excelPath) {

		try 
		{
			File src = new File(excelPath);	//reading from excel file from its location
			FileInputStream fis = new FileInputStream(src); //this class is for taking input from user
			wb = new XSSFWorkbook(fis); //separate class for reading sheets
		} 
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
		}
	}

	public String getData(int sheetnumber, int row, int column) 
	{
		sheet=wb.getSheetAt(sheetnumber);
		DataFormatter formatter = new DataFormatter();
		Cell cell = sheet.getRow(row).getCell(column);
		String Test_Data = formatter.formatCellValue(cell);
		return Test_Data;	
	}

	public int getRowCount(int sheetIndex) {
		int actualrowcount=0;
		int row=wb.getSheetAt(sheetIndex).getPhysicalNumberOfRows();
		for(int i=1;i<row;i++) {
			if (!wb.getSheetAt(sheetIndex).getRow(i).getCell(6).toString().isEmpty()) {
				actualrowcount++;
			}
		}
		return actualrowcount;
	}	
}
