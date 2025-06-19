	package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import wrappers.WebApplicationWrappers;

public class SignInPageNew extends WebApplicationWrappers {

	static ExtentTest test;
	static ExtentReports report;

	public SignInPageNew(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		this.jsExecutor = (JavascriptExecutor) driver;
	}

	@FindBy(xpath = "//div[contains(text(),'Sign In')]")
	private WebElement signInBtn;

	@FindBy(xpath = "//*[@class='signin-in-screen-signInText']")
	private WebElement signInPageTopic;
	
	@FindBy(xpath = "//input[@name='user_email']")
	private WebElement emailTxtBox;

	@FindBy(xpath = "//input[@name='password']")
	private WebElement passwordTxtBox;

	@FindBy(xpath = "//div[@class='signin-in-screen-forgetPassword']/a")
	private WebElement forgetPasswordBtn;

	@FindBy(xpath = "//div[contains(text(),'Email is a required field')]")
	private WebElement emailerrtxt0;

	@FindBy(xpath = "//div[contains(text(),'Email cannot be empty')]")
	private WebElement emailerrtxt1;

	@FindBy(xpath = "//div[contains(text(),'Invalid email')]")
	private WebElement emailerrtxt2;

	@FindBy(xpath = "//div[contains(text(),'Password is a required field')]")
	private WebElement passworderrtxt0;

	@FindBy(xpath = "//div[contains(text(),'Password cannot be empty')]")
	private WebElement passworderrtxt1;

	@FindBy(xpath = "//input[contains(@class,'Login_fieldInput')]")
	private WebElement Forgetpassemailfield;

	@FindBy(xpath = "//div[@class='auth-button-signInButton']")
	private WebElement generateOTPBtn;

	@FindBy(xpath = "//div[contains(@class,'Login_formikErrorText')]")
	private WebElement Forgetpassemailerrtxt;

	@FindBy(xpath = "//div[contains(@class,'Landing_Screen_button_Text')]")
	private WebElement landingpageexplorecourse;

	@FindBy(xpath = "//*[@role='status']")
	private WebElement ToastMessage;
	
	@FindBy(xpath = "//span[@class='forgetpassword-in-screen-signInText']")
	private WebElement forgetPasswordTitle;
	
	@FindBy(xpath = "//span[@class='otp-in-screen-otpText']")
	private WebElement otpVerificationTitle;
	
	@FindBy(xpath = "//input[contains(@class,'Otp_otpInput')][1]")
	private WebElement OTPbox1;
	
	@FindBy(xpath = "//span[@class='editLink-otp']")
	private WebElement editEmailLink;
	
	@FindBy(xpath = "//div[contains(text(),'Sign Up')]")
    private WebElement signUpBtn;
	
	@FindBy(xpath = "//div[contains(text(),'Yes')]")
    private WebElement sessionCheckBtn;
	
	
	private WebElement otpXpath(int num) {
		return driver.findElement(By.xpath("//input[contains(@class,'otpInput')]["+num+"]"));
	}
	
	public void clickSignInBtn() {
		clickbyXpath(signInBtn, "click on SignIn button ");
	}
	
	public void clickSignUpBtn() {
		clickbyXpath(signUpBtn, "click on SignIn button ");
	}

	public void clickgenerateOTPBtn() {
		clickbyXpath(generateOTPBtn, "click on SignIn button ");
	}
	
	public void clickSessionCheckBtn() {
		clickbyXpath(sessionCheckBtn, " Switch Session Yes/No ");
	}
	
	
	
	public void checkvalidemailandpasswordfield(String email, String password) throws InterruptedException {

		
		enterValuebyXpath(emailTxtBox, "To enter invalid email on Emailfield", randomnames(3)+"@gmail.com");

		enterValuebyXpath(passwordTxtBox, "To enter password ", password);
		clickbyXpath(signInBtn, "Sign In button");
		
		checkToast("Email not exist");
		Thread.sleep(2000);
		clearfield(emailTxtBox);
		enterValuebyXpath(emailTxtBox, "To enter email on Emailfield", email);
		clearfield(passwordTxtBox);
		enterValuebyXpath(passwordTxtBox, "To enter password ", password);
		clickbyXpath(signInBtn, "Sign In button");
		try {
		    if (sessionCheckBtn.isDisplayed()) {
		        clickSessionCheckBtn();
		    } else {
		        verifyTextContainsByXpath(ToastMessage, "Login successful", "Toast message");
		    }
		} catch (NoSuchElementException e) {
			 System.out.println("Session check button not found.");
		}
	}

public void signInUser(String email, String password) throws InterruptedException {

		enterValuebyXpath(emailTxtBox, "To enter email on Emailfield", email);
		enterValuebyXpath(passwordTxtBox, "To enter password ", password);
		clickbyXpath(signInBtn, "Sign In button");
		Thread.sleep(2000);
		try {
		    if (sessionCheckBtn.isDisplayed()) {
		        clickSessionCheckBtn();
		    }
		} catch (NoSuchElementException e) {
		    System.out.println("Session check button not found.");
		}

	}
	public void checkForgetPasswordPage() throws InterruptedException {

		// click on Forgetpassword and check page navigation
		clickbyXpath(forgetPasswordBtn, "Forget password field");
		verifyTextContainsByXpath(forgetPasswordTitle, "Forgot Password","Forget password title ");
		
		enterValuebyXpath(emailTxtBox, "Forget password email field", randomnames(4)+"@gmail.com");
		clickbyXpath(generateOTPBtn, "Generate OTP button ");
		checkToast("Email Id does not exist");
		Thread.sleep(5000);
		clearfield(emailTxtBox);
		enterValuebyXpath(emailTxtBox, "Forget password email field", "iinvsysqa@gmail.com");
		clickbyXpath(generateOTPBtn, "Generate OTP button ");
		checkToast("OTP has been sent to user registered Email");

	}
	
	public void otpPageVerification() throws InterruptedException {
		
		// navigate to signin page and check page title
		//clickbyXpath(signInBtn, "click on SignIn button ");
		//clickbyXpath(forgetPasswordBtn, "Forget password field");
		
		
		clickbyXpath(editEmailLink, " Edit Email Field ");
		clearfield(emailTxtBox);
		enterValuebyXpath(emailTxtBox, "Forget password email field", "iinvsysqa@gmail.com");
		Thread.sleep(5000);
		clickbyXpath(generateOTPBtn, "Generate OTP button ");
		
		// check title of OTP verification page
		verifyTextContainsByXpath(otpVerificationTitle, "OTP Verification","OTP verification title");
		
		// check OTPpage error message
		otpXpath(1).sendKeys("1");
		otpXpath(2).sendKeys("2");
		otpXpath(3).sendKeys("3");
		otpXpath(4).sendKeys("4");
		otpXpath(5).sendKeys("5");
		otpXpath(6).sendKeys("6");
		Thread.sleep(5000);
		clickbyXpath(generateOTPBtn, "verified and proceed button");
		checkToast("Invalid OTP");
			
	}
	public void checkToast(String toast) {
		verifyDynamicContentByXpath("//*[@role='status']", toast," Toast message ");
	}
	
	public void checkSignInPageTopic(String topicName) {
		verifyTextContainsByXpath(signInPageTopic, topicName, " Login Page title");
	}
}
