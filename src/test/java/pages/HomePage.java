package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import wrappers.GenericWrappers;

public class HomePage extends GenericWrappers {

	private WebDriver driver;

	// Locate all elements on the page

	@FindBy(xpath = "//div[@class='home-page-header-menu-container-svg-circle home-page-header-menu-container-words-hideable profile-icon-container']")
	private WebElement profileBtn;

	@FindBy(xpath = "//*[@class='home-page-header-menu-container-words']/a[1]")
	private WebElement homeButton;

	@FindBy(xpath = "//div[@class='logout-button-out']/div[1]")
	private WebElement logOutBtn;

	@FindBy(xpath = "//div[@class='get-started-button']")
	private WebElement exploreCourseBtn;

	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// Methods to interact with elements

	public void checkUserInUserHomePage() {

		isUserOnNextPage(driver, "//*[@class='home-page-header-menu-container-words']/a[1]", "User Home Page");
	}

	public void clickProfileBtn() {
		clickbyXpath(profileBtn, " Profile Button ");
	}

	public void clickLogOutBtn() {
		clickbyXpath(logOutBtn, " Logout Button ");

	}

	public void clickExploreCourseButton() {

		clickbyXpath(exploreCourseBtn, " Get Started ");
	}

	public void clickMyCourseButton() {
		// TODO Auto-generated method stub

	}

	public void clickAllCourseButton() {
		// TODO Auto-generated method stub

	}

}
