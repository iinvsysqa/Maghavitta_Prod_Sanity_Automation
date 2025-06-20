package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utils.Reporter;

import org.openqa.selenium.*;

import wrappers.GenericWrappers;

public class EMICalculatorPage extends GenericWrappers {

	private WebDriver driver;

	public EMICalculatorPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//span[text()='Calculator']")
	private WebElement emailCalucatorTitle;

	@FindBy(xpath = "//div[contains(., 'Your EMI is')]/span")
	private WebElement emiText;
	
	@FindBy(xpath = "(//span[contains(@class,'MuiSlider-thumb')])[2]/parent::span")
	private WebElement interestSliderTrack;
	
	@FindBy(xpath = "(//span[contains(@class,'MuiSlider-thumb')])[2]")
	private WebElement interestsliderThumb;
	
	@FindBy(xpath = "(//*[@class='MuiSlider-valueLabelLabel'])[2]")
	private WebElement interestLabel;
	
	@FindBy(xpath = "(//span[contains(@class,'MuiSlider-thumb')])[3]/parent::span")
	private WebElement tenureSliderTrack;
	
	@FindBy(xpath = "(//span[contains(@class,'MuiSlider-thumb')])[3]")
	private WebElement tenuresliderThumb;
	
	@FindBy(xpath = "(//*[@class='MuiSlider-valueLabelLabel'])[3]")
	private WebElement tenureLabel;
	
	@FindBy(xpath = "//div[@role='group']/button[2]")
	private WebElement yearButton;
	
	@FindBy(xpath = "(//*[@type='text'])[1]")
	private WebElement loanAmountInput;
	
	
	
	public void enterLoanAmount(String amount) {
		scrollToElements(loanAmountInput);
		loanAmountInput.clear();
		enterValuebyXpath(loanAmountInput," Loan Amount " , amount);
	}
	
	public void changeTenure(double tenure) throws InterruptedException {
		scrollToElements(yearButton);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", yearButton);
		//clickbyXpath(yearButton, " Year Button ");
		setSliderToValue(tenure,tenureSliderTrack,tenuresliderThumb, tenureLabel,1,30);

		// Optional: wait and verify label update
		Thread.sleep(1000);

		// Optionally get the updated label text

		String labelText = tenureLabel.getText().replaceAll("[^0-9.]", "");
		double labelValue = Double.parseDouble(labelText);

		if (Double.compare(labelValue, tenure) == 0) {
			Reporter.reportStep(" Tenture is getting updated to correct value: " + tenureLabel.getText(), "PASS");
		} else {
			Reporter.reportStep(" Tenture is not getting updated to current value: " + tenureLabel.getText(), "FAIL");
		}


		
	}
	
	

	public void changeInterestRate(double interest) throws InterruptedException {
		scrollToElements(emailCalucatorTitle);
		setSliderToValue(interest,interestSliderTrack,interestsliderThumb, interestLabel,4.0,24.0);

		// Optional: wait and verify label update
		Thread.sleep(1000);

		// Optionally get the updated label text

		String labelText = interestLabel.getText().replaceAll("[^0-9.]", "");
		double labelValue = Double.parseDouble(labelText);

		if (Double.compare(labelValue, interest) == 0) {
			Reporter.reportStep(" Tenture is getting updated to correct value: " + interestLabel.getText(), "PASS");
		} else {
			Reporter.reportStep(" Tenture is not getting updated to current value: " + interestLabel.getText(), "FAIL");
		}

	}
	
	

	public void checkEmiCalculated(String emiAmount) {
		scrollToElements(emiText);
		verifyTextContainsByXpath(emiText, emiAmount, " EMI AMount Calculated ");
	}

	public void setSliderToValue(double targetValue,WebElement sliderTrack, WebElement sliderThumb, WebElement label, double minValue, double maxValue) throws InterruptedException {

		// Validate input
		if (targetValue < minValue || targetValue > maxValue) {
			throw new IllegalArgumentException("Target value must be between 4.0 and 24.0");
		}


		// Get slider track width in pixels
		int trackWidth = sliderTrack.getSize().getWidth();

		// Calculate pixels per unit
		double valueRange = maxValue - minValue;
		double pixelsPerUnit = trackWidth / valueRange;

		// Get current value from label
		double currentValue = Double.parseDouble(label.getText().replace("%", ""));

		// Calculate pixel offset
		double unitsToMove = targetValue - currentValue;
		int xOffset = (int) Math.round(unitsToMove * pixelsPerUnit);

		// Perform drag
		Actions actions = new Actions(driver);
		actions.clickAndHold(sliderThumb).moveByOffset(xOffset, 0).release().perform();
	}

}
