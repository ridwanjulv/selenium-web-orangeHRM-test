package testngscenarios;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;
import utils.ConfigFile;

import org.testng.annotations.BeforeSuite;

import static org.testng.Assert.assertTrue;

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

public class Recruitment {
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

	@AfterSuite
	public void afterSuite() {
		System.out.println("closing time");
		// closing browser
		test.pass("Close the browser");
		driver.close();
		driver.quit();
		
		// log the test completion
		test.info("Test completed");

		// write the report
		extent.flush();
	}

	@BeforeClass
	public void beforeClass() {
		driver.get(ConfigFile.getProperty("web_url"));
	}

	@AfterClass
	public void afterClass() {
		driver.get(ConfigFile.getProperty("logout_url"));
	}

	@Test
	public void searchForVacancies() throws InterruptedException {
		test = extent.createTest("add KPI");
		test.log(Status.INFO, "Starting test case");
		String vacancy_role_name = "Senior QA Lead";
		
		//login
		String username = ConfigFile.getProperty("username");
		String password = ConfigFile.getProperty("password");
		driver.findElement(By.id("txtUsername")).sendKeys(username);
		driver.findElement(By.id("txtPassword")).sendKeys(password);
		driver.findElement(By.id("btnLogin")).click();
		
		//verify in homescreen
		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement lbl_welcome_greeting = wait.until(ExpectedConditions.elementToBeClickable(By.id("welcome")));
		
		AssertJUnit.assertTrue(lbl_welcome_greeting.isDisplayed());

		test.pass("Menu Vacancy opened");
		System.out.println("Menu Vacancy opened");
		//navigate to Recruitment menu and open Vacancies tab
		Actions action = new Actions(driver);
		WebElement mnu_recruitmentMenu = driver.findElement(By.id("menu_recruitment_viewRecruitmentModule"));
		//action.moveToElement(mnu_recruitmentMenu).build().perform();
		mnu_recruitmentMenu.click();
		System.out.println("opening menu 1");
		//for some web page implementation, the following web element only get created above action performed 
		WebElement mnu_recruitmentVacancies = driver.findElement(By.id("menu_recruitment_viewJobVacancy"));
		mnu_recruitmentVacancies.click();
		
		System.out.println("Menu Vacancies opened");
		
		//search for a Vacancy
		WebElement drpVacancy = driver.findElement(By.id("vacancySearch_jobVacancy"));
		//drpVacancy.click();
		Select selectVacancy = new Select(drpVacancy);
		selectVacancy.selectByVisibleText(vacancy_role_name);
		
		Thread.sleep(5000);
		
		driver.findElement(By.id("btnSrch")).click();
		
		System.out.println("Start search");
		
		//verify search result
		WebElement rowResult = driver.findElement(By.xpath("//tbody/tr[1]/td[2]"));
		AssertJUnit.assertTrue(rowResult.isDisplayed());
		AssertJUnit.assertEquals(vacancy_role_name, rowResult.getText());
		
		
		
		
		//open vacancy detail
		rowResult.click();
		
		test.pass("Vacancy detail opens");
		System.out.println("done");
	}

}
