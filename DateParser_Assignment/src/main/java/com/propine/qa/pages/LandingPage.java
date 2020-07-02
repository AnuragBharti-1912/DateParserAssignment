package com.propine.qa.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.propine.qa.base.BasePage;

public class LandingPage extends BasePage {

	@FindBy(name="date")
	WebElement Date;

	@FindBy(css="input.btn.btn-default")
	WebElement Submit;

	@FindBy(xpath="//body//div[2]//div/div[2]//div")
	WebElement Results;

	public LandingPage() 
	{
		PageFactory.initElements(driver, this);
	}

	public String viewResults() {
		return Results.getText();
	}
	public String getDate(String date) {
		Date.sendKeys(date);
		Submit.click();
		return date;
	}
}
