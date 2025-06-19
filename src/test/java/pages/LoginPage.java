package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import wrappers.GenericWrappers;

public class LoginPage extends GenericWrappers {

	private WebDriver driver;

	@FindBy(xpath = "//input[@name='user_email']")
	private WebElement emailField;

	@FindBy(xpath = "//input[@name='password']")
	private WebElement passwordField;

	@FindBy(xpath = "//div[text()='Submit']")
	private WebElement submitButton;

	@FindBy(xpath = "//div[text()='Create Account']")
	private WebElement createAccountLink;

	@FindBy(xpath = "//div[text()='Forget Password']")
	private WebElement forgetPasswordLink;

	@FindBy(xpath = "//body/div[@id='root']/div[1]/div[1]")
	private WebElement loginFullpageview;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void enterEmailId(String email) {
		enterValuebyXpath(emailField, "User Email ID ", email);
	}

	public void enterPassword(String password) {
		enterValuebyXpath(passwordField, "User Email ID ", password);
	}

	public void clickSubmitButton() {
		clickbyXpath(submitButton, "Submit Button");

	}

	public void clickForgetPasswordLink() {
		clickbyXpath(forgetPasswordLink, " Forget Password ");

	}

	public void clickCreateAccountLink() {
		clickbyXpath(createAccountLink, " Create Account ");

	}
}
