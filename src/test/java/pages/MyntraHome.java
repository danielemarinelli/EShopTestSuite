package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import javax.swing.*;

public class MyntraHome extends BasePage{
	
	// WebElements for Home
	@FindBy(xpath=".//div[@class='slick-slide slick-active slick-center carousel-slide']")
	private WebElement slider;
	
	@FindBy(xpath=".//span[text()='Profile']/..")
	private WebElement profile;

	@FindBy(xpath=".//a[text()='log in']")
	private WebElement btnLogin;

	@FindBy(xpath=".//div[text()=' Logout ']")
	private WebElement linkLogout;

	private WebDriver driver = null;
	
	// Constructor to Initialize the Page Factory	
	public MyntraHome(WebDriver driver) throws Exception {
		super(driver);
		this.driver =  driver;
		if(!isHomePageDisplayed()) throw new Exception("Home Page not displayed");
	}
	
	// Actions to be performed in Home Page
	
	public boolean isHomePageDisplayed() {
		return slider.isDisplayed();
	}
	
	public void navigateToLoginPage() {
		moveToElement(driver, profile);
		btnLogin.click();
	}

	public void performLogout(){
		moveToElement(driver,profile);
		linkLogout.click();
	}

	public boolean verifyLoginError(String errMsg){
		return waitElement(driver,By.xpath(".//p[text()='"+errMsg+"']"),20).isDisplayed();
	}


	
	
	

}
