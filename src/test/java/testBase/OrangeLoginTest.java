package testBase;

import java.io.File;

import org.testng.Assert;
import org.testng.annotations.Test;

import pages.DashboardPage;
import pages.LoginPage;
import utilities.UpdateExcel;

public class OrangeLoginTest extends BaseClass {

	UpdateExcel excel;
	final boolean runFlag = true;
	String sheetName = "Login";
	File screenshotFile;

	@Test(enabled = runFlag)
	public void test_Login() {
		try {
			excel = new UpdateExcel();
			getBrowserDriver("chrome");
			getUrl("https://opensource-demo.orangehrmlive.com");
			LoginPage lp = new LoginPage(driver);
			lp.getUsernameElement("Admin");
			lp.getPasswordElement("admin123");
			lp.getLoginButtonElement();
			DashboardPage dp = new DashboardPage(driver);
			boolean targetpage = dp.getdashboardElement();
			Assert.assertEquals(targetpage, true);
			excel.printTestResult(sheetName, "TC 100", "Pass", "Valid Login SuccessFull");
			captureScreen("passed login");
		} catch (Exception e) {
			excel.printTestResult(sheetName, "TC 100", "Fail", e.getMessage());
			Assert.fail();
		}
		driver.close();
	}
}
