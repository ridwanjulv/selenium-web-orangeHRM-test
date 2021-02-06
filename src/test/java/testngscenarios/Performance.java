package testngscenarios;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.AssertJUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;
import utils.ConfigFile;

public class Performance {
	WebDriver driver = null;
	private ExtentReports extent = null;
	private ExtentTest test = null;

	@BeforeSuite
	public void beforeSuite() {
		String browser = ConfigFile.getProperty("browser");
		System.out.println("Run test using browser: " + browser);

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

		// setup the reporter
		ExtentSparkReporter reporter = new ExtentSparkReporter("test-report/extent_report.html");
		extent = new ExtentReports();
		extent.attachReporter(reporter);
	}

	@BeforeClass
	public void beforeClass() {
		driver.get(ConfigFile.getProperty("web_url"));
	}

	@AfterClass
	public void afterClass() {
		driver.get(ConfigFile.getProperty("logout_url"));

	}

	@AfterSuite
	public void afterSuite() {
		// closing browser
		test.pass("Close the browser");
		driver.close();
		driver.quit();
		
		// log the test completion
		test.info("Test completed");

		// write the report
		extent.flush();
	}

	@Test
	public void login() throws InterruptedException {
		// login
		String username = ConfigFile.getProperty("username");
		String password = ConfigFile.getProperty("password");
		driver.findElement(By.id("txtUsername")).sendKeys(username);
		driver.findElement(By.id("txtPassword")).sendKeys(password);
		driver.findElement(By.id("btnLogin")).click();

		// verify in homescreen
		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement lbl_welcome_greeting = wait.until(ExpectedConditions.elementToBeClickable(By.id("welcome")));
		AssertJUnit.assertTrue(lbl_welcome_greeting.isDisplayed());
		Thread.sleep(1000);
	}

	@Test(dependsOnMethods = "login")
	public void addKPI() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		
		test = extent.createTest("add KPI");
		test.log(Status.INFO, "Starting test case");
		// navigate to Performance > Configure > KPIs
		Actions action = new Actions(driver);
		action.moveToElement(driver.findElement(By.id("menu__Performance"))).build().perform();
		action.moveToElement(driver.findElement(By.id("menu_performance_viewEmployeePerformanceTrackerList"))).build()
				.perform();
		action.moveToElement(driver.findElement(By.id("menu_performance_Configure"))).build().perform();

		WebElement mnu_KPIs = driver.findElement(By.id("menu_performance_searchKpi"));
		mnu_KPIs.click();
		Thread.sleep(2000);
		test.pass("Menu KPI opened");
		System.out.println("Menu KPI opened");

		//driver.findElement(By.id("btnAdd")).click();
		WebElement btnAdd = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnAdd")));
		btnAdd.click();
		test.pass("Menu Add new KPI opened");
		System.out.println("Menu add new KPI opened");
		
		
		//WebElement drpJobTitle = driver.findElement(By.id("defineKpi360_jobTitleCode"));
		WebElement drpJobTitle = wait.until(ExpectedConditions.elementToBeClickable(By.id("defineKpi360_jobTitleCode")));

		Thread.sleep(5000);
		Select selectJobTitle = new Select(drpJobTitle);
		selectJobTitle.selectByVisibleText("QA Engineer");
		driver.findElement(By.id("defineKpi360_keyPerformanceIndicators")).sendKeys("Test Add some KPI");
		test.addScreenCaptureFromPath("add new KPI page.png");
		
		driver.findElement(By.id("btnCancel")).click();
		test.addScreenCaptureFromPath("back to Search KPI page.png");
		WebElement lblSearchKPI = driver.findElement(By.xpath("//div[@id='divFormContainer']//h1"));
		AssertJUnit.assertTrue(lblSearchKPI.isDisplayed());
		AssertJUnit.assertTrue(lblSearchKPI.getText().toString().equalsIgnoreCase("Search Key Performance Indicators"));

		test.pass("Cancel succees.");
	}
}
