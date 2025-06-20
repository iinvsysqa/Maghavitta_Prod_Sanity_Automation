package testcases;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pages.CommonPages;
import pages.ContactUsPage;
import pages.EMICalculatorPage;
import pages.LandingPageNew;
import utils.Reporter;
import wrappers.WebApplicationWrappers;

public class TC06_ContactUsCheck extends WebApplicationWrappers {
	ContactUsPage contactuspage;
	EMICalculatorPage emicalculatorpage;
	CommonPages commonpages;
	
	@BeforeClass
	public void startTestCase() {
		testCaseName = " TC06 - Contact Us Functional Check ";
		testDescription = " Check user able to fill contact details and send succesfully ";
	}


	@Test(retryAnalyzer = utils.RetryListener.class)
	public void landingPageValidation() throws InterruptedException {
		initDriver("Windows","Chrome");
		Reporter.reportStep("Browser : Chrome ","USER_INFO");
		Reporter.reportStep("Platform : Windows ","USER_INFO");
		launchApplication(loadProp().getProperty("URL"));
		contactuspage = new ContactUsPage(driver);
		commonpages = new CommonPages(driver);
		
		commonpages.clickContactUsButton();
		Thread.sleep(5000);
		contactuspage.checkContactUsImageLoads();
		contactuspage.enterName("Demo User");
		contactuspage.enterEmail("testuser001@yopmail.com");
		contactuspage.enterPhone("9867476363");
		contactuspage.enterMessage("Test Message for Automation Validation");
		contactuspage.clickSendMessageBtn();
	}

}
