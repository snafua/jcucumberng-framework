package project.hooks;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.paulhammant.ngwebdriver.NgWebDriver;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import jcucumberng.api.browser.BrowserFactory;
import jcucumberng.api.properties.PropsLoader;
import jcucumberng.api.selenium.Selenium;
import jcucumberng.api.utils.SystemUtil;

public class ScenarioHook {

	private static final Logger LOGGER = LoggerFactory.getLogger(ScenarioHook.class);
	private Selenium selenium = null;

	@Before
	public void beforeScenario(Scenario scenario) throws Throwable {
		LOGGER.info("BEGIN TEST -> {}", scenario.getName());

		String webBrowser = PropsLoader.framework("web.browser");
		WebDriver driver = BrowserFactory.getInstance(webBrowser);
		if (Boolean.parseBoolean(PropsLoader.framework("wait.for.angular"))) {
			NgWebDriver ngWebDriver = new NgWebDriver((JavascriptExecutor) driver);
			ngWebDriver.waitForAngularRequestsToFinish();
		}
		LOGGER.info("Browser={}", webBrowser);

		selenium = new Selenium(driver, scenario);

		Dimension dimension = SystemUtil.getDimension();
		selenium.getDriver().manage().window().setSize(dimension);
		LOGGER.info("Screen Resolution (WxH)={}x{}", dimension.getWidth(), dimension.getHeight());
	}

	@After
	public void afterScenario() throws Throwable {
		if (!Boolean.parseBoolean(PropsLoader.framework("screenshot.off"))) {
			if (Boolean.parseBoolean(PropsLoader.framework("screenshot.on.fail"))) {
				if (selenium.getScenario().isFailed()) {
					selenium.embedScreenshot();
				}
			}
		}
		LOGGER.info("END TEST -> {} - {}", selenium.getScenario().getName(), selenium.getScenario().getStatus());
		selenium.getDriver().quit();
	}

	public Selenium getSelenium() {
		return selenium;
	}

}
