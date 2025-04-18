package ui;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import config.ConfigReader;
import ui.driver.ChromeWebDriver;
import ui.pages.LoginPage;

import java.net.MalformedURLException;

public class BaseTest {

    protected WebDriver driver;
    protected ConfigReader configReader;
    protected LoginPage loginPage;

    @BeforeClass
    public void setUp() throws MalformedURLException {
        configReader = new ConfigReader();

        // Подключаемся к удалённому драйверу через ChromeWebDriver
        driver = ChromeWebDriver.create();

        loginPage = new LoginPage(driver);
        // Переход на страницу логина
        driver.get(ConfigReader.getValue("url") + ConfigReader.getValue("loginPage"));

        // Авторизация
//        loginPage = new LoginPage(driver);
        loginPage.login(
                ConfigReader.getValue("login"),
                ConfigReader.getValue("password")
        );
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}