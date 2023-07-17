package testBase;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Parameters;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.apache.logging.log4j.LogManager; //Log4j
import org.apache.logging.log4j.Logger; // Log4j

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle; // loading properties file
import java.util.Set;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class BaseClass {

	public WebDriver driver;
	public WebDriverWait wait;
	

	public WebDriver getBrowserDriver(String browser) {
		switch (browser) {
		case "chrome":
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			break;
		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			break;
		case "safari":
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			break;
		default:
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();

			break;
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		return driver;
	}

	// @AfterClass(groups = { "Master", "Sanity", "Regression" })
	public void tearDown() {
		driver.quit();
	}

	public String randomeString() {
		String generatedString = RandomStringUtils.randomAlphabetic(5);
		return generatedString;
	}

	public String randomeNumber() {
		String generatedNumber = RandomStringUtils.randomNumeric(10);
		return generatedNumber;
	}

	public String randomeAlphaNumeric() {
		String generatedString = RandomStringUtils.randomAlphabetic(3);
		String generatedNumber = RandomStringUtils.randomNumeric(5);
		return (generatedString + generatedNumber);

	}

	public String captureScreen(String tname) throws IOException {

		/*
		 * Date dt=new Date(); SimpleDateFormat sp=new
		 * SimpleDateFormat("yyyyMMddhhmmss"); String timestamp=sp.format(dt);
		 */
		String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		File source = takesScreenshot.getScreenshotAs(OutputType.FILE);
		String destination = System.getProperty("user.dir") + "\\screenshots\\" + tname + "_" + timeStamp + ".png";

		try {
			FileUtils.copyFile(source, new File(destination));
		} catch (Exception e) {
			e.getMessage();
		}
		return destination;
	}

	public int getValuePositionInList(List<WebElement> element, String value) {
		// List<WebElement> element = element;
		int index = 0;
		for (int i = 0; i < element.size(); i++) {
			if (element.get(i).getText().trim().toLowerCase().contains(value.toLowerCase())) {
				index = i;
				break;
			}
		}
		return index;
	}

	public int getValuePositionInGetAttributes(List<WebElement> element, String value) {
		// List<WebElement> element = element;
		int index = 0;
		for (int i = 0; i < element.size(); i++) {
			if (element.get(i).getAttribute("value").trim().contains(value)) {
				index = i;
				break;
			}
		}
		return index;
	}

	public boolean isValuePresentInList(List<WebElement> element, String value) {
		boolean flag = false;
		for (int i = 0; i < element.size(); i++) {
			System.out.println("actual : " + element.get(i).getText().trim().toLowerCase());
			System.out.println(value.toLowerCase());
			if (element.get(i).getText().trim().toLowerCase().contains(value.toLowerCase())) {
				System.out.println("****** in");
				flag = true;
				break;
			}
		}
		return flag;
	}

	public void sortDropDown(WebElement drpElement) {
		Select drpselect = new Select(drpElement);
		List<WebElement> options = drpselect.getOptions();

		ArrayList<String> originallist = new ArrayList<String>();
		ArrayList<String> templist = new ArrayList<String>();

		for (WebElement option : options) {
			originallist.add(option.getText());
			templist.add(option.getText());
		}
		System.out.println("Original list: " + originallist);
		System.out.println("Temp list: " + templist);

		Collections.sort(templist); // sorting

		System.out.println("Original list: " + originallist);
		System.out.println("Temp list: " + originallist);
		if (originallist.equals(templist)) {
			System.out.println("Dropdown sorted..");
		} else {
			System.out.println("Dropdown not sorted..");
		}
	}

	public void dropDownbyIndex(WebElement Element, int by_Index) {
		Select S = new Select(Element);
		S.selectByIndex(by_Index);
	}

	public void dropDownbyVisibleText(WebElement Element, String by_VisibleText) {
		Select S = new Select(Element);
		S.selectByVisibleText(by_VisibleText);
	}

	public void dropDownByValue(WebElement Element, String by_Value) {
		Select S = new Select(Element);
		S.selectByValue(by_Value);
	}

	public void switchFrame(String frameNameId) {
		driver.switchTo().frame(frameNameId);
	}

	public void all_linktext(List<WebElement> Elements) {
		String url = null;
		System.out.println("Total links on the Wb Page: " + Elements.size());
		// We will iterate through the list and will check the elements in the list.
		Iterator<WebElement> iterator = Elements.iterator();
		while (iterator.hasNext()) {
			url = iterator.next().getText();
			System.out.println(url);
		}
	}

	public void waitPresenceOfElementLocated(WebElement Element, int s) {
		wait = new WebDriverWait(driver, Duration.ofSeconds(s));
		wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(Element, null));
	}

	public void waitElementToBeClickable(WebElement Element, int s) {
		wait = new WebDriverWait(driver, Duration.ofSeconds(s));
		wait.until(ExpectedConditions.elementToBeClickable(Element));
	}

	public void waitFrameToBeAvailableAndSwitchToIt(WebElement Element, int s) {
		wait = new WebDriverWait(driver, Duration.ofSeconds(s));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(Element));
	}

	public void waitVisibilityOfElementLocated(WebElement Element, int s) {
		wait = new WebDriverWait(driver, Duration.ofSeconds(s));
		wait.until(ExpectedConditions.visibilityOf(Element));
	}

	@SuppressWarnings("deprecation")
	public void uploadfile_AutoIt(WebElement browse, String file_path, WebElement uploadButton) {
		try {
			browse.click();
			Runtime.getRuntime().exec(file_path);
			Thread.sleep(3000);
			uploadButton.click(); // Upload button
			System.out.println("File Uploaded Successfully");
		} catch (Exception e) {
			e.getMessage();
		}
	}

	public void switchToChildWindow() {
		Set<String> set = driver.getWindowHandles();
		Iterator<String> it = set.iterator();
		String parentWindowId = it.next();
		String childWindowId = it.next();
		System.out.println(set);
		System.out.println(parentWindowId + "/n" + childWindowId);
		driver.switchTo().window(childWindowId);
	}

	public void switchToWindowNumber(int index) {
		Set<String> windows = driver.getWindowHandles();
		int totalWin = windows.size();
		System.out.println(totalWin);
		String winTitle = null;
		for (int i = 0; i < totalWin; i++) {
			if (i == index) {
				winTitle = windows.toArray()[i].toString();
			}
		}
		driver.switchTo().window(winTitle);
		System.out.println(winTitle);
	}

	public String getText(WebElement Element) {
		return Element.getText();
	}

	public void clear(WebElement Element) {
		Element.clear();
	}

	public void scroll_page(int x, int y) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(" + x + "," + y + ")", "");
	}

	public WebElement getElement(By locator) {
		return driver.findElement(locator);
	}

	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}

	public WebElement doclick(WebElement Element) {
		Element.click();
		return Element;
	}

	public void dragToTarget(WebElement Element, WebElement Target) {
		(new Actions(driver)).dragAndDrop(Element, Target).perform();
	}

	public WebElement Enter(WebElement Element, String value) {
		Element.sendKeys(value);
		return Element;
	}

	public void getUrl(String link) {
		driver.get(link);
	}

}
