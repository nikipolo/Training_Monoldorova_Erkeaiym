package ui;

import config.ConfigReader;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ui.pages.LoginPage;

import java.net.MalformedURLException;
import java.time.Duration;

import static config.ConfigReader.getValue;


@Slf4j
public class LoginPageTest extends BaseTest {
    @BeforeClass
    @Override
    public void setUp() throws MalformedURLException {
        super.setUp();
        loginPage = new LoginPage(driver);
    }



    @Test(description = "позитивный  кейс")
    public void testLoginPositive() {

        try {
            String expectedUrl = ConfigReader.getValue("url");
            new WebDriverWait(driver, Duration.ofSeconds(15))
                    .until(ExpectedConditions.not(ExpectedConditions.urlContains("/login")));

            String currentUrl = driver.getCurrentUrl();
            Assert.assertTrue(currentUrl.contains(expectedUrl),
                    "URL после логина не начинается с базового: " + currentUrl);
        } catch (Exception e) {
            Assert.fail("Не удалось дождаться выхода со страницы логина.");
        }


    }

    @Test(description = "негативный кейс если пустая форма")
    public void testLoginEmptyFields() {
        loginPage
                .enterLogin("")      // оставляем поле логина пустым
                .enterPassword("")   // оставляем поле пароля пустым
                .clickLoginButton();

        Assert.assertTrue(driver.getCurrentUrl().contains("/login"), "Ожидалось остаться на странице логина.");
    }

    @Test(description = "если заполнено только поле Login")
    public void testLoginOnlyUsername() {
        loginPage.enterLogin(getValue("login"))
                .enterPassword("")
                .clickLoginButton();

        Assert.assertTrue(driver.getCurrentUrl().contains("/login"), "Ожидалось остаться на странице логина.");

        WebElement error = driver.findElement(By.xpath("//*[contains(text(), 'пароль') or contains(text(), 'не может быть пустым')]"));
        Assert.assertTrue(error.isDisplayed(), "Ожидалось сообщение об ошибке для пустого пароля.");
    }

    @Test(description = "если заполнено только поле Password")
    public void testLoginOnlyPassword() {
        loginPage.enterLogin("")
                .enterPassword(getValue("password"))
                .clickLoginButton();

        Assert.assertTrue(driver.getCurrentUrl().contains("/login"), "Ожидалось остаться на странице логина.");

        WebElement error = driver.findElement(By.xpath("//*[contains(text(), 'Логин') or contains(text(), 'заполнить')]"));
        Assert.assertTrue(error.isDisplayed(), "Ожидалось сообщение об ошибке для пустого логина.");
    }

}
