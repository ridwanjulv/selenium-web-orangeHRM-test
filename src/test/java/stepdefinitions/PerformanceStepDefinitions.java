package stepdefinitions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.AssertJUnit;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utils.WebDriverFactory;

public class PerformanceStepDefinitions {

	@And("User click Add new button")
	public void user_click_add_new_button() {
		WebDriver driver = WebDriverFactory.getDriver();

		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement btnAdd = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnAdd")));
		btnAdd.click();
	}

	@And("User populate KPI data")
	public void user_populate_kpi_data() throws InterruptedException {
		WebDriver driver = WebDriverFactory.getDriver();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		
		//WebElement drpJobTitle = driver.findElement(By.id("defineKpi360_jobTitleCode"));
		WebElement drpJobTitle = wait.until(ExpectedConditions.elementToBeClickable(By.id("defineKpi360_jobTitleCode")));

		Thread.sleep(5000);
		Select selectJobTitle = new Select(drpJobTitle);
		selectJobTitle.selectByVisibleText("Software Engineer");
		driver.findElement(By.id("defineKpi360_keyPerformanceIndicators")).sendKeys("Test Add some KPI");
	}

	@When("User cancel the Add KPI")
	public void user_cancel_the_add_kpi() throws InterruptedException {
		WebDriver driver = WebDriverFactory.getDriver();
		driver.findElement(By.id("btnCancel")).click();
		Thread.sleep(2000);
	}

	@Then("System will goes back to KPI management landing page")
	public void system_will_goes_back_to_kpi_management_landing_page() {
		WebDriver driver = WebDriverFactory.getDriver();
		WebElement lblSearchKPI = driver.findElement(By.xpath("//div[@id='divFormContainer']//h1"));
		AssertJUnit.assertTrue(lblSearchKPI.isDisplayed());
		AssertJUnit.assertTrue(lblSearchKPI.getText().toString().equalsIgnoreCase("Search Key Performance Indicators"));

	}
}
