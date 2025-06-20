package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import wrappers.GenericWrappers;
import utils.Reporter;

public class ContactUsPage extends GenericWrappers {

	private WebDriver driver;

	public ContactUsPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//*[@alt='contact us']")
	private WebElement contactUsPageTopImage;

	@FindBy(xpath = "//*[@name='name']")
	private WebElement nameTextBox;

	@FindBy(xpath = "//*[@name='email']")
	private WebElement emailTextBox;

	@FindBy(xpath = "//*[@name='phone']")
	private WebElement phoneTextBox;

	@FindBy(xpath = "//*[@name='message']")
	private WebElement messageTextBox;
	
	@FindBy(xpath = "//button[text()='Send Message']")
	private WebElement sendMessageBtn;
	
	@FindBy(xpath = "//div[@role='status']")
	private WebElement sendMessageToast;
	

	public void checkContactUsImageLoads() {

		Boolean imageLoaded = (Boolean) ((JavascriptExecutor) driver).executeScript(
				"return arguments[0].complete && typeof arguments[0].naturalWidth != 'undefined' && arguments[0].naturalWidth > 0",
				contactUsPageTopImage);

		if (imageLoaded) {
			Reporter.reportStep("Image loaded successfully. Refer Screenshot ", "PASS");
		} else {
			Reporter.reportStep("Image not loaded successfully. Refer Screenshot ", "FAIL");
		}
	}

	public void enterName(String name) {
		enterValuebyXpath(nameTextBox, " Name text box ", name);
	}

	public void enterEmail(String email) {
		enterValuebyXpath(emailTextBox, "Email text box", email);
	}

	public void enterPhone(String phone) {
		enterValuebyXpath(phoneTextBox, "Phone text box", phone);
	}

	public void enterMessage(String message) {
		enterValuebyXpath(messageTextBox, "Message text box", message);
	}
	
	public void clickSendMessageBtn() {
		clickbyXpath(sendMessageBtn, " Send Message Button ");
	}
	
	public void checkSendMessageToast(String msg) {
		verifyTextContainsByXpath(sendMessageToast, msg, " Success Toast ");
	}
}
