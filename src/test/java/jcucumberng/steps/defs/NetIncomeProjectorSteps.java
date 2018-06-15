package jcucumberng.steps.defs;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ByChained;
import org.openqa.selenium.support.ui.Select;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;
import jcucumberng.api.Selenium;
import jcucumberng.steps.domain.Expense;
import jcucumberng.steps.domain.Income;
import jcucumberng.steps.hooks.ScenarioHook;

public class NetIncomeProjectorSteps {
	private static final Logger logger = LogManager.getLogger(NetIncomeProjectorSteps.class);
	private WebDriver driver = null;

	// PicoContainer injects ScenarioHook class
	public NetIncomeProjectorSteps(ScenarioHook scenarioHook) {
		driver = scenarioHook.getDriver();
	}

	@When("I Enter My Start Balance: {word}")
	public void I_Enter_My_Start_Balance(String startBalance) throws Throwable {
		Selenium.enterText(driver, startBalance, "start.balance.txt");
		logger.debug("Start Balance=" + startBalance);
		this.scrollToDivBox(0);
	}

	@When("I Enter My Regular Income Sources")
	public void I_Enter_My_Regular_Income_Sources(DataTable table) throws Throwable {
		List<Income> incomes = table.asList(Income.class);
		for (int ctr = 0; ctr < incomes.size() - 1; ctr++) {
			Selenium.clickElement(driver, "income.add.btn");
		}
		List<WebElement> nameFields = driver.findElements(Selenium.by("income.name.txt"));
		List<WebElement> amountFields = driver.findElements(Selenium.by("income.amount.txt"));
		List<Select> freqDropMenus = Selenium.getSelectElements(driver, "income.freq.drop");
		for (int ctr = 0; ctr < incomes.size(); ctr++) {
			Selenium.enterText(driver, incomes.get(ctr).getName(), nameFields.get(ctr));
			Selenium.enterText(driver, incomes.get(ctr).getAmount(), amountFields.get(ctr));
			Selenium.selectByText(driver, incomes.get(ctr).getFrequency(), freqDropMenus.get(ctr));
			logger.debug(incomes.get(ctr).toString());
		}
		this.scrollToDivBox(1);
	}

	@When("I Enter My Regular Expenses")
	public void I_Enter_My_Regular_Expenses(DataTable table) throws Throwable {
		List<Expense> expenses = table.asList(Expense.class);
		for (int ctr = 0; ctr < expenses.size() - 1; ctr++) {
			Selenium.clickElement(driver, "expense.add.btn");
		}
		List<WebElement> nameFields = driver.findElements(Selenium.by("expense.name.txt"));
		List<WebElement> amountFields = driver.findElements(Selenium.by("expense.amount.txt"));
		List<Select> freqDropMenus = Selenium.getSelectElements(driver, "expense.freq.drop");
		for (int ctr = 0; ctr < expenses.size(); ctr++) {
			Selenium.enterText(driver, expenses.get(ctr).getName(), nameFields.get(ctr));
			Selenium.enterText(driver, expenses.get(ctr).getAmount(), amountFields.get(ctr));
			Selenium.selectByText(driver, expenses.get(ctr).getFrequency(), freqDropMenus.get(ctr));
			logger.debug(expenses.get(ctr).toString());
		}
		this.scrollToDivBox(2);
	}

	@Then("I Should See Net Income Per Month: {word}")
	public void I_Should_See_Net_Income_Per_Month(String netPerMonth) throws Throwable {
		WebElement netPerMonthTd = driver.findElement(Selenium.by("net.per.month.td"));
		String netPerMonthText = netPerMonthTd.getText();
		Assertions.assertThat(netPerMonthText).isEqualTo(netPerMonth);
		logger.debug("Net Per Month=" + netPerMonthText);
		Selenium.scrollToElement(driver, netPerMonthTd);
	}

	@Then("I Should See Net Income Per Year: {word}")
	public void I_Should_See_Net_Income_Per_Year(String netPerYear) throws Throwable {
		WebElement netPerYearTd = driver.findElement(Selenium.by("net.per.year.td"));
		String netPerYearText = netPerYearTd.getText();
		Assertions.assertThat(netPerYearText).isEqualTo(netPerYear);
		logger.debug("Net Per Year=" + netPerYearText);
		Selenium.scrollToElement(driver, netPerYearTd);
	}

	private void scrollToDivBox(int index) throws IOException {
		List<WebElement> divBoxes = driver
				.findElements(new ByChained(Selenium.by("page.div.span7"), Selenium.by("page.div.box")));
		Selenium.scrollToElement(driver, divBoxes.get(index));
	}

}
