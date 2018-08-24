package jcucumberng.project;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;
import jcucumberng.framework.api.Config;

@RunWith(Cucumber.class)
@CucumberOptions(features = { "src/test/resources/jcucumberng/project/features" }, tags = { "not @ignore" }, glue = {
		"jcucumberng.project.typeregistry", "jcucumberng.project.stepdefs", "jcucumberng.project.hooks" }, plugin = {
				"pretty", "html:target/cucumber-html-default", "json:target/cucumber-report.json",
				"junit:target/cucumber-report.xml" }, snippets = SnippetType.UNDERSCORE, monochrome = true, strict = true, dryRun = false)

public class RunCukesTest {
	private static boolean isLoaded = false;

	// No edit
	@BeforeClass
	public static void loadLoggerConf() {
		if (!isLoaded) {
			Config.loggerConf();
			isLoaded = true;
		}
	}

}
