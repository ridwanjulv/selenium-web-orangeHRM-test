package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebDriverFactory {
	
	public static WebDriverFactory thisObj = new WebDriverFactory();
	public WebDriver driver = null;
	public String browser = "";
	
	public WebDriverFactory() {
	}
	
	private void generateWebDriver() {
		browser = ConfigFile.getProperty("browser");
		
		if (browser.equalsIgnoreCase("firefox")){
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		} else if (browser.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		} else {
			WebDriverManager.iedriver().setup();
			driver = new InternetExplorerDriver();
		}
	}
	
	public static WebDriver getDriver() {
		if(thisObj.driver == null) {			
			thisObj.generateWebDriver();
		}
		
		return thisObj.driver;
	}
	
}
