package testcases;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pages.LandingPageNew;
import io.github.cdimascio.dotenv.Dotenv;
import utils.Reporter;
import utils.RetryListener;
import wrappers.WebApplicationWrappers;

public class TC01_LandingPageCheck extends WebApplicationWrappers {
	LandingPageNew landingpagenew;
	
	
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
		
		landingpagenew.clickgetStartedButton();
		driver.navigate().back();
		Thread.sleep(3000);
		landingpagenew.clickgetStartedNowButton();
		driver.navigate().back();
		landingpagenew.playPauseVideo();
		landingpagenew.checkTestimonialScroll();
		
		
	}

}
