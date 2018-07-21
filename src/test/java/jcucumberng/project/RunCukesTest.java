package jcucumberng.project;

import java.io.File;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import com.cucumber.listener.Reporter;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;
import jcucumberng.api.logger.LoggerHelper;

@RunWith(Cucumber.class)
@CucumberOptions(features = { "src/test/resources/jcucumberng/project/features" }, tags = { "not @ignore" }, glue = {
		"jcucumberng.project.datatable", "jcucumberng.project.hooks", "jcucumberng.project.stepdefs" }, plugin = {
				"pretty", "com.cucumber.listener.ExtentCucumberFormatter:target/cucumber-html-extent/report.html",
				"html:target/cucumber-html-default", "json:target/cucumber-report.json",
				"junit:target/cucumber-report.xml" }, snippets = SnippetType.UNDERSCORE, monochrome = true, strict = true, dryRun = false)

public class RunCukesTest {

	// No edit
	private static boolean isLoaded = false;

	@BeforeClass
	public static void beforeClass() {
		if (!isLoaded) {
			LoggerHelper.initLogger();
			isLoaded = true;
		}
	}

	@AfterClass
	public static void writeExtentReport() {
		Reporter.loadXMLConfig(new File("src/test/resources/extent-config.xml"));
		Reporter.setSystemInfo("user", System.getProperty("user.name"));
		Reporter.setSystemInfo("os", System.getProperty("os.name"));
		Reporter.setTestRunnerOutput("Extent Reports generated successfully!");
	}

}
