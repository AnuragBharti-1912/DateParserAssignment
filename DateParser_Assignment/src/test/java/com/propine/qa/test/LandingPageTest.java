package com.propine.qa.test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.ResolverStyle;
import java.util.Date;
import java.util.Locale;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.propine.qa.base.BasePage;
import com.propine.qa.libraries.ReadExcelLibrary;
import com.propine.qa.pages.LandingPage;

public class LandingPageTest extends BasePage {
	@BeforeTest
	public void initialize() throws IOException {
		driver = initializeDriver();
	}

	@Test(dataProvider = "dateParserTestData")
	public void DateParser(String strDateFromExcel) {
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

			LocalDate parsedLocalDate = LocalDate.parse(strDateFromExcel,ALL_POSSIBLE_DATE_FORMAT.withResolverStyle(ResolverStyle.STRICT));

			ZoneId defaultZoneId = ZoneId.systemDefault();
			Date dateFromExcel = Date.from(parsedLocalDate.atStartOfDay(defaultZoneId).toInstant());
			LandingPage lp = new LandingPage();
			lp.getDate(strDateFromExcel);
			if (!lp.viewResults().equals("Invalid date")) {
				String strDateFromWebPage = lp.viewResults();
				SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss");
				Date dateFromWebPage = format.parse(strDateFromWebPage);
				if (dateFromExcel != null && dateFromWebPage != null) {
					System.out.println("d1 = "+dateFromExcel+ " d2 = "+dateFromWebPage+" compare returns "+dateFromExcel.compareTo(dateFromWebPage));
					Assert.assertTrue(dateFromExcel.compareTo(dateFromWebPage) == 0, "");
				}
			}
		} catch (Exception e) {
			System.out.println("Invalid date/format :" + " " + strDateFromExcel);
		}
	}

	@DataProvider(name = "dateParserTestData")
	public Object[][] passData() {
		ReadExcelLibrary config = new ReadExcelLibrary(Path+ "\\src\\main\\java\\com\\propine\\testdata\\Propine_Test_Data.xlsx");
		int rows = config.getRowCount(0) + 1;
		System.out.println("Total no of rows :"+rows);

		Object[][] testData = new Object[rows - 1][1];
		int j = 0;
		for (int i = 1; i < rows; i++) {
			testData[j][0] = config.getData(0, i, 6);
			j++;
		}
		return testData;
	}

	@AfterTest
	public void teardown()
	{
		driver.close();
		driver=null;
	}
}
