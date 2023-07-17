package pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.*;

public class DashboardPage extends BasePage {

	@FindBy(xpath = "//h6")
	WebElement dashboardTextElement;

	@FindBy(xpath = "//ul/li/a/span")
	List<WebElement> dashboardMenuElements;

	public DashboardPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public boolean getdashboardElement() {
		if (dashboardTextElement.isDisplayed()) {
		}
		return dashboardTextElement.isDisplayed();
	}
}
