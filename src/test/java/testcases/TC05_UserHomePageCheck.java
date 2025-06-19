package testcases;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pages.LandingPageNew;
import pages.SignInPageNew;
import pages.CoursesPage;
import pages.HomePage;
import utils.Reporter;
import wrappers.WebApplicationWrappers;

public class TC05_UserHomePageCheck extends WebApplicationWrappers {
	SignInPageNew signInPagenew;
	LandingPageNew landingpagenew;
	CoursesPage coursepage;
	HomePage homepage;
	
	@BeforeClass
	public void startTestCase() {
		testCaseName = " TC05 - User Home Page functional Check ";
		testDescription = " Login with Valid User and Check Home and Course details Page ";
	}


	@Test
	public void userHomePageCheck() throws InterruptedException {
		
		initDriver("Windows","chrome");
		Reporter.reportStep("Browser : Chrome ","USER_INFO");
		Reporter.reportStep("Platform : Windows ","USER_INFO");
		landingpagenew= new LandingPageNew(driver);
		signInPagenew=new SignInPageNew(driver);
		coursepage= new CoursesPage(driver);
		homepage= new HomePage(driver);
		
		launchApplication(loadProp().getProperty("URL"));	
		
		landingpagenew.clickgetStartedButton();
		signInPagenew.signInUser("iinvsysqa@gmail.com","Welcome@123");
		homepage.checkUserInUserHomePage();
		homepage.clickExploreCourseButton();
		coursepage.checkUserInUserCoursesPage();
		coursepage.clickExploreBtn();
		coursepage.clickCourseTopicCheck();
		Thread.sleep(2000);
		coursepage.clickProfileBtn();
		coursepage.clickLogOutBtn(); 
		
		
	}
}
