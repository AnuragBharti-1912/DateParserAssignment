package com.propine.qa.test;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.ResolverStyle;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.propine.qa.base.BasePage;
import com.propine.qa.libraries.ReadExcelLibrary;
import com.propine.qa.pages.LandingPage;

public class LandingPageTest extends BasePage {
	public static Logger log = LogManager.getLogger(BasePage.class.getName());

	public Object[][] Test_Data;
	public String flag=null;

	String expdate = null;
	String expmonth=null ;
	String expyear=null;

	@BeforeTest
	public void initialize() throws IOException{
		driver=initializeDriver();
	}

	@Test(dataProvider="dateParserTestData")
	public void DateParser(String Test_Data ) {
		try {

			DateTimeFormatter ALL_POSSIBLE_DATE_FORMAT = new DateTimeFormatterBuilder()
					.appendOptional(DateTimeFormatter.ofPattern("dd-MMMM-uuuu"))
					.appendOptional(DateTimeFormatter.ofPattern("dd-MMMM-uu"))
					.appendOptional(DateTimeFormatter.ofPattern("dd-MMM-uuuu"))
					.appendOptional(DateTimeFormatter.ofPattern("dd-MMM-uu"))
					.appendOptional(DateTimeFormatter.ofPattern("dd-MM-uuuu"))
					.appendOptional(DateTimeFormatter.ofPattern("dd-MM-uu"))
					.appendOptional(DateTimeFormatter.ofPattern("MMMM-dd-uuuu"))
					.appendOptional(DateTimeFormatter.ofPattern("MMMM-dd-uu"))
					.appendOptional(DateTimeFormatter.ofPattern("MMM-dd-uuuu"))
					.appendOptional(DateTimeFormatter.ofPattern("MMM-dd-uu"))					
					.appendOptional(DateTimeFormatter.ofPattern("MM-dd-uuuu"))
					.appendOptional(DateTimeFormatter.ofPattern("MM-dd-uu"))
					.appendOptional(DateTimeFormatter.ofPattern("dd/MM/uuuu"))
					.appendOptional(DateTimeFormatter.ofPattern("dd/MM/uu"))
					.appendOptional(DateTimeFormatter.ofPattern("dd/MMM/uuuu"))
					.appendOptional(DateTimeFormatter.ofPattern("dd MM uuuu"))
					.appendOptional(DateTimeFormatter.ofPattern("dd MM uu"))
					.appendOptional(DateTimeFormatter.ofPattern("MM dd uu"))
					.appendOptional(DateTimeFormatter.ofPattern("MM/dd/uu"))
					.appendOptional(DateTimeFormatter.ofPattern("MM/dd/uuuu"))
					.appendOptional(DateTimeFormatter.ofPattern("dd-MM-uu"))
					.appendOptional(DateTimeFormatter.ofPattern("dd/MM/uu"))
					.appendOptional(DateTimeFormatter.ofPattern("dd MMMM-uuuu"))
					.appendOptional(DateTimeFormatter.ofPattern("d/M-uuuu"))
					.appendOptional(DateTimeFormatter.ofPattern("dd/MMM/uu"))
					.appendOptional(DateTimeFormatter.ofPattern("dd/MMMM/uuuu"))
					.appendOptional(DateTimeFormatter.ofPattern("dd-MMMM-uuuu"))
					.appendOptional(DateTimeFormatter.ofPattern("dd/M-uuuu")).toFormatter(Locale.ENGLISH);

			LocalDate parseddate = LocalDate.parse(Test_Data.toString(), ALL_POSSIBLE_DATE_FORMAT.withResolverStyle(ResolverStyle.STRICT));
			//System.out.println(parseddate.getYear());
			//System.out.println(parseddate.getMonthValue());
			expdate= String.valueOf(parseddate.getDayOfMonth());
			expmonth = String.valueOf(parseddate.getMonth());
			expyear = String.valueOf(parseddate.getYear());
			System.out.println("Day= "+parseddate.getDayOfMonth() +", " + "Month="+parseddate.getMonthValue()+", "+ "Year="+parseddate.getYear());
			flag = "Valid Date";
		}
		catch(Exception e) {
			//e.printStackTrace();
			System.out.println("Invalid date/format :" + " " + Test_Data.toString());
			flag = "Invalid date";
		}

		LandingPage lp= new LandingPage();
		lp.getDate(Test_Data.toString());
		log.debug("data is inputed "+Test_Data);
		if(!lp.viewResults().equals("Invalid date")) {
			String[] date = lp.viewResults().split(" ");
			Assert.assertEquals(expdate, date[2],"Invalid Date");
			Assert.assertEquals(expmonth, date[1],"Invalid Month");
			Assert.assertEquals(expyear,date[3],"Invalid Year");
		}else {
			Assert.assertEquals(flag, lp.viewResults().equals("Invalid date"),"Error !!!");
		}

	}

	@DataProvider(name = "dateParserTestData")
	public Object[][] passData() {
		ReadExcelLibrary config = new ReadExcelLibrary("C:\\Users\\Anurag Bharti\\git\\DateParser\\DateParser_Assignment\\src\\main\\java\\com\\propine\\testdata\\Propine_Test_Data.xlsx");
		int rows = config.getRowCount(0) + 1;
		System.out.println(rows);

		Test_Data = new Object[rows - 1][1];
		int j = 0;
		for (int i = 1; i < rows; i++) {
			Test_Data[j][0] = config.getData(0, i, 6);
			j++;
		}
		return Test_Data;
	}

	//	 @AfterTest
	//	 public void teardown()
	//	 {
	//	 driver.close();
	//	 driver=null;
	//	 }

}
