package com.propine.qa.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.propine.qa.utils.TestUtil;

public class BasePage {

	public static WebDriver driver;
	public Properties prop;
	public String Path;

	public WebDriver initializeDriver() throws IOException {


		prop = new Properties();
		Path = new File("").getAbsolutePath();
		FileInputStream fis=  new FileInputStream(Path+"\\src\\main\\java\\com\\propine\\qa\\config\\file.properties");
		prop.load(fis);
		System.setProperty("webdriver.chrome.driver", Path+"\\chromedriver.exe");
		
		String browsername=prop.getProperty("browser");
		if (browsername.equals("chrome")) {
			driver= new ChromeDriver();
		}
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);
		
		driver.get(prop.getProperty("url"));
		return driver;
	}

}
