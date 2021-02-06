package stepdefinitions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.AssertJUnit;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utils.ConfigFile;
import utils.WebDriverFactory;

public class CommonStepDefinitions {

	@Given("User navigate to HRM website")
	public void user_navigate_to_hrm_website() {
		WebDriver driver = WebDriverFactory.getDriver();
		driver.get(ConfigFile.getProperty("web_url"));
	}

	@When("User do login as Admin")
	public void user_do_login_as_admin() throws InterruptedException {
		WebDriver driver = WebDriverFactory.getDriver();

		// login
		String username = ConfigFile.getProperty("username");
		String password = ConfigFile.getProperty("password");
		driver.findElement(By.id("txtUsername")).sendKeys(username);
		driver.findElement(By.id("txtPassword")).sendKeys(password);
		driver.findElement(By.id("btnLogin")).click();

	}

	@Then("Application will shows homescreen with welcome message")
	public void application_will_shows_homescreen_with_welcome_message() throws InterruptedException {
		WebDriver driver = WebDriverFactory.getDriver();
		// verify in homescreen
		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement lbl_welcome_greeting = wait.until(ExpectedConditions.elementToBeClickable(By.id("welcome")));
		AssertJUnit.assertTrue(lbl_welcome_greeting.isDisplayed());
		Thread.sleep(1000);
	}

	@Given("User navigate to {string} menu")
	public void user_navigate_to_menu(String menuName) throws InterruptedException {
		if (menuName.equalsIgnoreCase("Performance/Configure/KPI")) {
			navigateToPerformanceConfigureKPI();
		} else if (menuName.equalsIgnoreCase("Recruitment/Vacancy")) {
			navigateToVacancy();
		}
		Thread.sleep(2000);
	}

	private static void navigateToPerformanceConfigureKPI() {
		WebDriver driver = WebDriverFactory.getDriver();

		// navigate to Performance > Configure > KPIs
		Actions action = new Actions(driver);
		action.moveToElement(driver.findElement(By.id("menu__Performance"))).build().perform();
		action.moveToElement(driver.findElement(By.id("menu_performance_viewEmployeePerformanceTrackerList"))).build()
				.perform();
		action.moveToElement(driver.findElement(By.id("menu_performance_Configure"))).build().perform();

		WebElement mnu_KPIs = driver.findElement(By.id("menu_performance_searchKpi"));
		mnu_KPIs.click();

	}

	private static void navigateToVacancy() {
		WebDriver driver = WebDriverFactory.getDriver();
		
		// navigate to Recruitment menu and open Vacancies tab
		Actions action = new Actions(driver);
		WebElement mnu_recruitmentMenu = driver.findElement(By.id("menu_recruitment_viewRecruitmentModule"));
		// action.moveToElement(mnu_recruitmentMenu).build().perform();
		mnu_recruitmentMenu.click();
		System.out.println("opening menu 1");
		// for some web page implementation, the following web element only get created
		// above action performed
		WebElement mnu_recruitmentVacancies = driver.findElement(By.id("menu_recruitment_viewJobVacancy"));
		mnu_recruitmentVacancies.click();
	}
}
