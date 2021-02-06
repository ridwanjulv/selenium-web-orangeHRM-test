package Hooks;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;

import io.cucumber.java.After;
import utils.ConfigFile;
import utils.WebDriverFactory;

public class Hook {
	@After
	public void logOut() {
		WebDriver driver = WebDriverFactory.getDriver();
		driver.get(ConfigFile.getProperty("logout_url"));
		
	}
	@AfterSuite
	public void cleanUp() {
		WebDriver driver = WebDriverFactory.getDriver();
		// closing browser
		driver.close();
		driver.quit();
	}
}
