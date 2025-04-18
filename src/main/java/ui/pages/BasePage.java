package ui.pages;

import config.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import ui.driver.DriverManager;
import ui.helper.BrowserManager;
import ui.helper.WebElementHelper;

import java.time.Duration;
import java.util.Random;

public class BasePage {
    public WebElementHelper webElementHelper = new WebElementHelper();
    public WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(10));
    public BrowserManager browserManager = new BrowserManager(DriverManager.getDriver());

    public ConfigReader configReader = new ConfigReader();
    public BasePage(WebDriver driver){
        PageFactory.initElements(DriverManager.getDriver(), this);
    }

}
