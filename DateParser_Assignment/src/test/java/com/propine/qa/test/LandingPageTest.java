package com.propine.qa.test;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.ResolverStyle;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import javax.swing.text.DateFormatter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.util.datetime.Format;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.propine.qa.base.BasePage;
import com.propine.qa.libraries.ReadExcelLibrary;
import com.propine.qa.pages.LandingPage;

public class LandingPageTest extends BasePage{

	public static Logger log=LogManager.getLogger(BasePage.class.getName());

	public Object [][] Test_Data;
	//	@BeforeTest
	//	public void initialize() throws IOException{
	//		driver=initializeDriver();
	//	}

	@Test(dataProvider="dateParserTestData")
	public void DateParserTest(String Test_Data ) {
		//	ReadExcelLibrary obje= new ReadExcelLibrary();
		//obje.getData(sheetnumber, row, column)
		//		driver.get(prop.getProperty("url"));
		//		driver.manage().window().maximize();
		//		log.debug("window is maximized");
		try {
			DateTimeFormatter ALL_POSSIBLE_DATE_FORMAT = new DateTimeFormatterBuilder()
					.appendOptional(DateTimeFormatter.ofPattern("DD-MMM-YYYY"))
					.appendOptional(DateTimeFormatter.ofPattern("DD-MM-YY"))
					.appendOptional(DateTimeFormatter.ofPattern("DD-MMMM-YYYY"))
					.appendOptional(DateTimeFormatter.ofPattern("DD-MMM-YYYY"))
					.appendOptional(DateTimeFormatter.ofPattern("DD-MMM-YY"))
					.appendOptional(DateTimeFormatter.ofPattern("DD-MMMM-YYYY"))
					.appendOptional(DateTimeFormatter.ofPattern("DD-MM-YYYY"))
					.appendOptional(DateTimeFormatter.ofPattern("DD-MM-YY"))
					.appendOptional(DateTimeFormatter.ofPattern("DD/MM/YYYY"))
					.appendOptional(DateTimeFormatter.ofPattern("DD/MM/YY"))
					.appendOptional(DateTimeFormatter.ofPattern("DD MM YYYY"))
					.appendOptional(DateTimeFormatter.ofPattern("DD MM YY"))
					.appendOptional(DateTimeFormatter.ofPattern("MM DD YY"))
					.appendOptional(DateTimeFormatter.ofPattern("DD/M-YYYY")).toFormatter();
			TemporalAccessor ta = ALL_POSSIBLE_DATE_FORMAT.parse(Test_Data.toString());
			System.out.println("Formatted "+  ta  + " " + Test_Data.toString());
//			Gson gson = new Gson();
//			Map<String ,String> attributes = gson.fromJson(gson.toJson(ta.toString()),Map.class);
//			System.out.println(attributes.toString());
			String [] arr = ta.toString().split(",");
//			for(String d : arr) {
////				System.out.println(d);
//				String[] entry =  d.split("=");
//				System.out.println(entry[1].toString());	
//			
//			}
			
		}
		catch(Exception e) {
			System.out.println("Unknown" + Test_Data.toString());
		}

		//		try {
		//			
		//			TemporalAccessor ta = DateTimeFormatter.ofPattern("DD-MMM-YYYY").withResolverStyle(ResolverStyle.LENIENT).withChronology(IsoChronology.INSTANCE).parse(data.toString());
		//			System.out.println(ta +" Actual:"+ data.toString());
		//		}catch(Exception e) {
		//			System.out.println("Unknown Format");
		//		}
		////		SimpleDateFormat smf;
		//		Date d = null;
		//		try {
		//			smf = new SimpleDateFormat();
		//			smf.setLenient(true);
		//			smf.applyPattern("DD-MM-YYYY");
		//			smf.parse(data);
		//			System.out.println("Pattern1 Matched" +data);
		//		}
		//		catch (Exception ex) {
		//			System.out.println("pattern not found");
		//		}
		//		try {
		//			smf = new SimpleDateFormat();
		//			smf.setLenient(true);
		//			smf.applyPattern("DD-MM-YY");
		//			smf.parse(data);
		//			System.out.println("Pattern2 Matched" +data);
		//
		//		} catch (Exception ex) {
		//			System.out.println("pattern not found");
		//			// do something for invalid dateformat
		//		}
		//		LandingPage lp= new LandingPage();
		//		lp.getDate(data);
		//driver.findElement(By.xpath("//input[@class='form-control']")).sendKeys(data);
		//		log.debug("data is inputed "+data);

		//driver.findElement(By.xpath("//input[@type='submit']")).click();
		//log.debug("clicked");
		//		if (!lp.viewResults().equals("Invalid Date")) {
		//			String[] text=lp.viewResults().split(" ");
		//			String month = text[1];
		//			String date = text[2];
		//			String year = text[3];
		//		}

		//	String text = driver.findElement(By.xpath("//body//div[2]//div/div[2]//div")).getText();
		//log.info("Results: " +text);

	}

	@DataProvider(name="dateParserTestData")
	public Object[][] passData() 
	{
		ReadExcelLibrary config= new ReadExcelLibrary("C:\\Users\\Anurag Bharti\\eclipse-workspace\\DateParser_Assignment"
				+ "\\src\\main\\java\\com\\propine\\testdata\\Propine_Test_Data.xlsx");

		int rows=config.getRowCount(0)+1;
		System.out.println(rows);

		Test_Data= new Object[rows-1][1];
		int j=0;
		for (int i=1;i<rows; i++) 
		{	
			Test_Data[j][0] = config.getData(0, i, 6);
			j++;
		}
		return Test_Data;
	}

	//	@AfterTest
	//	public void teardown() 
	//	{
	//		driver.close();
	//		driver=null;
	//	}

}
