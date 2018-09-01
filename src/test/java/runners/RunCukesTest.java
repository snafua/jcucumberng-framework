package runners;


import java.io.File;

import com.cucumber.listener.Reporter;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;
import jcucumberng.api.logger.Logger;

@RunWith(Cucumber.class)
@CucumberOptions(features = { "src/main/resources/features" }, tags = { "not @ignore" }, glue = { "project.datatable",
		"project.hooks", "project.stepdefs" }, plugin = { "pretty", "io.qameta.allure.cucumber3jvm.AllureCucumber3Jvm",
				"com.cucumber.listener.ExtentCucumberFormatter:target/cucumber-html-extent/index.html",
				"html:target/cucumber-html-default", "json:target/cucumber-report.json",
				"junit:target/cucumber-report.xml" }, snippets = SnippetType.UNDERSCORE, monochrome = true, strict = true, dryRun = false)

public class RunCukesTest {

	private static boolean isLoaded = false;

	/**
	 * This block executes before @Before.
	 */
	@BeforeClass
	public static void beforeClass() {
		if (!isLoaded) {
			Logger.init();
			isLoaded = true;
		}
	}

	/**
	 * This block executes after @After.
	 */
	@AfterClass
	public static void afterClass() {
		Reporter.loadXMLConfig(new File("/src/main/resources/extent-config.xml"));
		Reporter.setSystemInfo("user", System.getProperty("user.name"));
		Reporter.setSystemInfo("os", System.getProperty("os.name"));
		Reporter.setTestRunnerOutput("Extent Reports generated successfully!");
	}

}
