package testcases;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pages.LandingPageNew;
import pages.SignInPageNew;
import utils.Reporter;
import wrappers.WebApplicationWrappers;

public class TC03_SignInInvalidCheck extends WebApplicationWrappers {
	SignInPageNew signInPagenew;
	LandingPageNew landingpagenew;
	
	@BeforeClass
	public void startTestCase() {
		testCaseName = " TC03 - Forget Password Functional Check ";
		testDescription = " Forget Password and OTP Verification function check ";
	}


	@Test
	public void forgetPasswordPageandOTPPageCheck() throws InterruptedException {
		
		initDriver("Windows","firefox");
		landingpagenew= new LandingPageNew(driver);
		signInPagenew=new SignInPageNew(driver);
		Reporter.reportStep("Browser : Firefox ","USER_INFO");
		Reporter.reportStep("Platform : Windows ","USER_INFO");
		launchApplication(loadProp().getProperty("URL"));	
		
		landingpagenew.clickgetStartedButton();
		
		signInPagenew.checkForgetPasswordPage();
		signInPagenew.otpPageVerification();
	}
}

