package wrappers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.safari.SafariDriver;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import utils.Reporter;

public class GenericWrappers {

	public static WebDriver driver;
	static ExtentTest test;
	static ExtentReports report;
	public String sUrl, primaryWindowHandle, sHubUrl, sHubPort, attribute;
	public JavascriptExecutor jsExecutor;
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
	
	public Properties loadProp() {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(new File("./config.properties")));
			sHubUrl = prop.getProperty("HUB");
			sHubPort = prop.getProperty("PORT");
			sUrl = prop.getProperty("URL");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}

	public static WebDriver initDriver(String platform, String browserName) {
        // Check platform and initialize browser accordingly
        if (platform.equalsIgnoreCase("Windows")) {
            // For Windows platform, we can use Chrome, Firefox, and Edge
            switch (browserName.toLowerCase()) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver();
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    break;
                case "edge":
                    WebDriverManager.edgedriver().setup();
                    driver = new EdgeDriver();
                    break;
                default:
                    throw new IllegalArgumentException("Browser not supported on Windows: " + browserName);
            }
        } else if (platform.equalsIgnoreCase("macos") || platform.equalsIgnoreCase("mac")) {
            // For macOS, we can use Chrome, Firefox, Safari
            switch (browserName.toLowerCase()) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver();
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    break;
                case "safari":
                    // Safari only works on macOS
                    driver = new SafariDriver();
                    break;
                default:
                    throw new IllegalArgumentException("Browser not supported on macOS: " + browserName);
            }
        } else {
            throw new IllegalArgumentException("Unsupported platform: " + platform);
        }

        if (driver != null) {
            driver.manage().window().maximize(); // Maximize the window on initialization
        }

        return driver;
    }

	public boolean invokeApp(String browser, String url) {
		boolean bReturn = false;
		try {

			DesiredCapabilities dc = new DesiredCapabilities();
			dc.setBrowserName(browser);
			dc.setPlatform(Platform.WINDOWS);
			if (browser.equalsIgnoreCase("chrome")) {
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
			} else {
				WebDriverManager.edgedriver();
				driver = new EdgeDriver();
			}

			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			driver.get(url);

			primaryWindowHandle = driver.getWindowHandle();
			Thread.sleep(3000);			
			Reporter.reportStep("The URL : " + url + " launched successfully in" + browser + " browser ", "PASS");
			bReturn = true;

		} catch (Exception e) {
			e.printStackTrace();
			Reporter.reportStep("The browser:" + browser + " could not be launched", "FAIL");
		}
		return bReturn;
	}

	public static boolean launchApplication(String url) {
		boolean bReturn = false;
		try {
			driver.get(url);
			Thread.sleep(2000);
			Reporter.reportStep("The browser: " + url + " launched successfully", "PASS");
			bReturn = true;
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.reportStep("The browser: " + url + " could not be launched", "FAIL");

		}
		return bReturn;
	}

	public static boolean clickbyXpath(WebElement xpath, String fieldName) {
		boolean bReturn = false;
		try {
			expWait(xpath);
			xpath.click();
			Reporter.reportStep(fieldName + " button is clicked Successfully.", "PASS");
			bReturn = true;

	} catch (Exception e) {
			e.printStackTrace();
			Reporter.reportStep("The Field "+fieldName+" could not be clicked.", "FAIL");
	}
	return bReturn;

	}

	//It will wait until the element is visible for 3 minutes 
	public static boolean clickbyXpathlongwait(WebElement xpath, String fieldName) {
		boolean bReturn = false;
		try {
			expWaitlong(xpath);
			xpath.click();
			Reporter.reportStep(fieldName + " field is clicked Successfully.", "PASS");
			bReturn = true;

		} catch (Exception e) {
			Reporter.reportStep("The Field " + fieldName + " could not be clicked.", "FAIL");
		}
		return bReturn;

	}

//clear textbox field
	public boolean clearfield(WebElement fieldName) {
		boolean bReturn = false;

		try {
			fieldName.clear();
			bReturn = true;
			Reporter.reportStep("Field get cleared", "PASS");

		} catch (Exception e) {
			Reporter.reportStep("Field not  cleared", "FAIL");
		}
		return bReturn;

	}

	
	public boolean verifyTitle(String title) {
		boolean bReturn = false;
		try {
			if (driver.getTitle().equalsIgnoreCase(title)) {
				Reporter.reportStep("The title of the page matches with the value :" + title, "PASS");
				bReturn = true;
			} else {
				Reporter.reportStep(
						"The title of the page:" + driver.getTitle() + " did not match with the value :" + title,
						"SUCCESS");
			}

		} catch (Exception e) {
			Reporter.reportStep("The title did not match", "FAIL");
		}

		return bReturn;
	}

	public boolean selectById(WebElement id, int value, String fieldName) {
		boolean bReturn = false;
		try {
			expWait(id);
			new Select(id).selectByIndex(value);
			Reporter.reportStep("The element with id: " + fieldName + " is selected with value :" + value, "PASS");

			bReturn = true;

		} catch (Exception e) {
			Reporter.reportStep("The value: " + value + " could not be selected.", "FAIL");
		}
		return bReturn;
	}


	public boolean enterValuebyXpath(WebElement xpath, String fieldname, String value) {
		boolean bReturn = false;
		try {
			expWait(xpath);
			xpath.sendKeys(value);
			Reporter.reportStep(fieldname + " field is entered with value : " + value, "PASS");

		} catch (Exception e) {

			e.printStackTrace();
			Reporter.reportStep("The value: "+value+" could not be entered.", "FAIL");
		}
		return bReturn;
	}

	//to send keys to profileimage field
	public void sendkeys(String value) {

		driver.switchTo().alert().sendKeys(value);
		driver.switchTo().alert().accept();
	}
	
	
	public boolean entertoiFrame(WebElement xpath, String fName) {
		boolean bReturn = false;
		try {
			expWait(xpath);
			WebElement frame = xpath;
			driver.switchTo().frame(frame);
			Reporter.reportStep("iframe " + fName + " entered successfully", "PASS");
			bReturn = true;

		} catch (Exception e) {
			Reporter.reportStep("iframe could not be entered :", "FAIL");
		}
		return bReturn;
	}

	public boolean selectByVisibleText(WebElement xpath, String fieldName) {
		boolean bReturn = false;
		try {
			expWait(xpath);
			List<WebElement> size = new Select(xpath).getOptions();
			for (WebElement s : size) {
				if (s.isEnabled()) {
					new Select(xpath).selectByVisibleText(s.getText());
					Reporter.reportStep("The dropdown: " + fieldName + " is selected", "PASS");
					bReturn = true;
					break;

				}

				else {
					Reporter.reportStep("The dropdown: " + fieldName + " is not selected", "FAIL");
				}
			}
		} catch (Exception e) {
			Reporter.reportStep("The dropdown: " + fieldName + " is not selected", "FAIL");
		}
		return bReturn;
	}
	
	public boolean verifyTextContainsByXpath(WebElement xpath, String text, String field) {
	
		boolean bReturn = false;
		try {
			expWait(xpath);
			String sText = xpath.getText();
			if (sText.trim().contains(text)) {
				Reporter.reportStep(field +"contains "+ text , "PASS");
				bReturn = true;
				}
			else {
				Reporter.reportStep(field+" did not contain :" + text, "FAIL");				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bReturn;
	}

	public void quitBrowser() {
		try {
			if (driver != null) {
				driver.quit();
			}
		} catch (Exception e) {
			Reporter.reportStep("The browser could not be closed.", "FAIL");
		}

	}

	public static void expWait(WebElement xpath) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(xpath));
		} catch (Exception e) {
			System.out.println(e);
		}

	}
	
	protected void scrollToElements(WebElement element) {
		expWait(element);
		jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
        try {
            Thread.sleep(2000); // Adding a slight delay to allow the scroll to complete
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
	
	public boolean verifyElementVisible(WebElement xpath, String fieldName){
		boolean bReturn = false;
		try {
			expWait(xpath);
		    if (xpath.isDisplayed()){
			Reporter.reportStep("Page contains "+ fieldName, "PASS");
			bReturn = true;
		    }
			else {
				Reporter.reportStep("Page Doesn't contains "+ fieldName, "FAIL");
			}
		} catch (Exception e) {
				
		}
		return bReturn;
	}

	public static void expWaitlong(WebElement xpath) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(3));
			wait.until(ExpectedConditions.visibilityOf(xpath));
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public static void attributevalue(WebElement xpath, String attribute, String txt) {
		try {

			String ele = xpath.getAttribute(attribute);
			if (ele.contains(txt)) {
				Reporter.reportStep("The attribute value :" + attribute + "equals to expected valule:" + txt, "PASS");
			} else {
				Reporter.reportStep("The attribute value :" + attribute + " not equals to expected valule:" + txt,
						"FAIL");
			}

			return;
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public void ifelseequals(WebElement element, String input, String attributevalue, String entersysout)
			throws Exception {
		Thread.sleep(3000);
		String attribute = element.getAttribute(attributevalue);
		if (attribute.equals(input)) {
			System.out.println(entersysout);
			Reporter.reportStep("The input value :" + input + "equals to expected valule:", "PASS");
		} else {
			Reporter.reportStep("The input value :" + input + " not equals to expected valule:", "FAIL");
			Assert.fail();
		}

	}

	

	public String randomnames(int num) {

		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		int length = num;

		for (int i = 0; i < length; i++) {

			int index = random.nextInt(alphabet.length());
			char randomChar = alphabet.charAt(index);
			sb.append(randomChar);
		}

		String randomString = sb.toString();
		String character = randomString.toLowerCase();

		System.out.println(character);
		return character;
	}

	public String randomnumbers(int num) {

		String numbers = "123456789";

		// Create a StringBuilder to store the random numbers
		StringBuilder sb = new StringBuilder();

		// Create an object of Random class
		Random random = new Random();

		// Specify the length of the random string
		int length = num;

		for (int i = 0; i < length; i++) {
			int index = random.nextInt(numbers.length());
			char randomNum = numbers.charAt(index);
			sb.append(randomNum);
		}
		String randomString = sb.toString();
		return randomString;
	}

	public boolean clickeyeicon(WebElement xpath) {
		boolean bReturn = false;
		try {
			expWait(xpath);
			xpath.click();
			String text = xpath.getAttribute("type");
			if (text.contentEquals("password")) {
				Reporter.reportStep("Eye icon enabled", "PASS");
			} else {
				Reporter.reportStep("Eye icon not enabled", "FAIL");

			}
		} catch (Exception e) {

		}
		return bReturn;
	}

	@SuppressWarnings("deprecation")	     
	public static void enableWifi() {
		try {
			// Process process = Runtime.getRuntime().exec("netsh interface set interface\\
			// Wi-Fi\\ enabled");
			Process process = Runtime.getRuntime().exec("cmd /c ipconfig /renew");
			process.waitFor();
			Thread.sleep(3000);
			System.out.println("Wi-Fi enabled successfully");
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	public static void disableWifi() {
		try {
			Process process = Runtime.getRuntime().exec("cmd /c ipconfig /release");
			// Process process = Runtime.getRuntime().exec("netsh interface set interface\\
			// Wi-Fi \\disabled");
			process.waitFor();
			System.out.println("Wi-Fi disabled successfully");
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static boolean backnavigation(WebElement xpath, String text) {
		boolean bReturn = false;
		try {
             
			driver.navigate().back();
			expWaitlong(xpath);
			String text2 = xpath.getText();
			if (text2.contains(text)) {
				Reporter.reportStep("Navigated back to page contains " + text, "PASS");
				bReturn = true;
			} else {
				Reporter.reportStep("Page not navigated back to expected page", "FAIL");

			}
		}

		catch (Exception e) {

		}
		return bReturn;
	}

	
	
	public WebElement scrollToElement(WebElement element) {
		int maxScrolls = 20; // Maximum number of scroll attempts
		int scrollCount = 0;

		while (scrollCount < maxScrolls) {
			try {

				if (element.isDisplayed()) {
					System.out.println("Element is now visible: " + element);
					Reporter.reportStep("element found", "PASS");
					return element;
				} else {
					jsExecutor.executeScript("window.scrollBy(0, 250);"); // Scroll down by 250 pixels
					Thread.sleep(500); // Adding a slight delay to allow the scroll to complete
					scrollCount++;
				}
			} catch (Exception e) {
				System.out.println("Exception encountered: " + e.getMessage());
				jsExecutor.executeScript("window.scrollBy(0, 250);"); // Continue scrolling if exception occurs
				scrollCount++;
			}
		}

		System.out.println("Element not found after scrolling " + scrollCount + " times. Locator: " + element);
		Reporter.reportStep("element not found ", "FAIL");
		return element;
	}
	 
		public void scrollToElementAndClick(WebElement xpath) {
			int maxScrolls = 20; // Maximum number of scrolls to perform in one direction
			int scrollCount = 0;
			boolean isElementFound = false;

			while (scrollCount < maxScrolls) {
				try {
					if (xpath.isDisplayed()) {
						jsExecutor.executeScript("arguments[0].scrollIntoView(true);", xpath);
						xpath.click();
						System.out.println("Element found and clicked.");
						isElementFound = true;
						Reporter.reportStep("element found and clicked", "PASS");
						break;
					}
				} catch (Exception e) {
					scrollPageDown();
					scrollCount++;
				}
			}

			if (!isElementFound) {
				scrollCount = 0; // Reset scroll count for scrolling up
				while (scrollCount < maxScrolls) {
					try {
						if (xpath.isDisplayed()) {
							xpath.click();
							System.out.println("Element found and clicked.");
							isElementFound = true;
							break;
						}
					} catch (Exception e) {
						scrollPageUp();
						scrollCount++;
					}
				}
			}

			if (!isElementFound) {
				Reporter.reportStep("element not found", "FAIL");
				System.out.println("Element not found after scrolling up and down.");
			}
		}

		
		public void scrollPageDown() {
			jsExecutor.executeScript("window.scrollBy(0, window.innerHeight);");
		}

		public void scrollPageUp() {
			jsExecutor.executeScript("window.scrollBy(0, -window.innerHeight);");
		}
    

    
    
    
    

  
    
    public int extractintvalue(String str) {
    	 // Use regular expression to remove all non-digit characters
        String numbersOnly = str.replaceAll("\\D+", "");
        
        // Convert the extracted string to an integer (optional)
        int extractedValue = Integer.parseInt(numbersOnly);
        
//        System.out.println("Extracted numbers: " + numbersOnly);
        System.out.println("Extracted integer value: " + extractedValue);
		return extractedValue;
	}
    


    
    public void pass(String message) {
		
    	Reporter.reportStep(message, "PASS");
	}
    public void fail(String message) {
    	
    	Reporter.reportStep(message, "FAIL");
    }

    public boolean isUserOnNextPage(WebDriver driver, String element, String Pname) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element)));
            Reporter.reportStep("User navigated to Correct Page : "+Pname, "INFO");
            return true;
        } catch (Exception e) {
        	 Reporter.reportStep("User navigated is not navigated to correct Page : "+Pname, "FAIL");
        	 return false;
        }
    }
    
    public boolean verifyDynamicContentByXpath(String xpath, String text, String field) {
    	
		boolean bReturn = false;
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
			String sText = driver.findElement(By.xpath(xpath)).getText();
			if (sText.trim().contains(text)) {
				Reporter.reportStep(field +" contains "+ text , "PASS");
				bReturn = true;
				}
			else {
				//Reporter.reportStep(field+" did not contain :" + text, "FAIL");				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bReturn;
	}
}
