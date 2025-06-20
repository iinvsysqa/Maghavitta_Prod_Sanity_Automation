package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import wrappers.GenericWrappers;

public class CommonPages extends GenericWrappers {

	private WebDriver driver;

	public CommonPages(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "(//*[text()='Contact Us'])[1]")
	private WebElement contactUsHeaderButton;
	
	public void clickContactUsButton() {
		clickbyXpath(contactUsHeaderButton," Contact Us Button in Header ");
		
	}
}
