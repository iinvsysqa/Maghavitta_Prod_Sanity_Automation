package testcases;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pages.EMICalculatorPage;
import pages.LandingPageNew;
import utils.Reporter;
import wrappers.WebApplicationWrappers;

public class TC04_Emi_Check extends WebApplicationWrappers {
	LandingPageNew landingpagenew;
	EMICalculatorPage emicalculatorpage;
	
	
	@BeforeClass
	public void startTestCase() {
		testCaseName = " TC01 - Landing Page Check ";
		testDescription = " Navigate to Landing Page and check Video is Playing and testimonilas scrollable ";
	}


	@Test(retryAnalyzer = utils.RetryListener.class)
	public void landingPageValidation() throws InterruptedException {
		initDriver("Windows","edge");
		Reporter.reportStep("Browser : Edge ","USER_INFO");
		Reporter.reportStep("Platform : Windows ","USER_INFO");
		launchApplication(loadProp().getProperty("URL"));
		landingpagenew= new LandingPageNew(driver);
		emicalculatorpage= new EMICalculatorPage(driver);
		
		Thread.sleep(3000);
		emicalculatorpage.changeInterestRate(12);
		emicalculatorpage.checkEmiCalculated("₹ 7,690");
		
		emicalculatorpage.changeTenure(3);
		emicalculatorpage.checkEmiCalculated("₹ 3,321");
		
		emicalculatorpage.enterLoanAmount("350000");
		emicalculatorpage.changeInterestRate(17);
		emicalculatorpage.changeTenure(5);
		emicalculatorpage.checkEmiCalculated("₹ 8,698");
		
		
		
		
		
		
	}

}
