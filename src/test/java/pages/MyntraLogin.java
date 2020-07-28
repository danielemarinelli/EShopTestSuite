package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyntraLogin extends BasePage{

	// WebElements for Login
	@FindBy(xpath=".//input[@name='email']")
	private WebElement txtEmail;
	
	@FindBy(xpath=".//input[@name='password']")
	private WebElement txtPassword;
	
	@FindBy(xpath=".//button[text()='Log in']")
	private WebElement btnLogin;
	
	
	private WebDriver driver = null;
	
	// Constructor to Initialize the Page Factory
	
	public MyntraLogin(WebDriver driver) throws Exception {
		super(driver);
		this.driver =  driver;
		if(!isLoginPageDisplayed()) throw new Exception("Login Page not displayed");
	}
		
	
	// Actions to be performed in Login Page
	public boolean isLoginPageDisplayed() {
		return txtEmail.isDisplayed();
	}
	
	public void performLogin(String email, String password) {
		txtEmail.sendKeys(email);
		txtPassword.sendKeys(password);
		btnLogin.click();
	}
	
		
}
