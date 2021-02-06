package testngrunner;
import org.testng.annotations.Test;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/resources/features"
, glue = { "stepdefinitions", "Hooks" }
, plugin = { "pretty", "html:target/cucumber" })
@Test
public class Runner extends AbstractTestNGCucumberTests {
}
