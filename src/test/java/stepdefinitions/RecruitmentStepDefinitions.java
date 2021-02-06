package stepdefinitions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.AssertJUnit;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utils.WebDriverFactory;

public class RecruitmentStepDefinitions {
	@When("User search for existing Vacancy on Role {string}")
	public void user_search_for_existing_vacancy_on_role(String roleName) {
		WebDriver driver = WebDriverFactory.getDriver();
		//search for a Vacancy
		WebElement drpVacancy = driver.findElement(By.id("vacancySearch_jobVacancy"));
		//drpVacancy.click();
		Select selectVacancy = new Select(drpVacancy);
		selectVacancy.selectByVisibleText(roleName);
		
		driver.findElement(By.id("btnSrch")).click();
	}

	@Then("System will shows all existing vacancy on {string}")
	public void system_will_shows_all_existing_vacancy_on_given_role(String roleName) {
		WebDriver driver = WebDriverFactory.getDriver();
		WebElement rowResult = driver.findElement(By.xpath("//tbody/tr[1]/td[2]"));

		AssertJUnit.assertTrue(rowResult.isDisplayed());
		AssertJUnit.assertEquals(roleName, rowResult.getText());
	}

	@And("User can open the vacancy detail")
	public void user_can_open_the_vacancy_detail() {
		WebDriver driver = WebDriverFactory.getDriver();
		WebElement rowResult = driver.findElement(By.xpath("//tbody/tr[1]/td[2]"));
		rowResult.click();
	}
}
