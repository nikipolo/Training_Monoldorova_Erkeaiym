package ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.annotations.BeforeMethod;

import static ui.driver.DriverManager.driver;

public class LoginPage extends BasePage {
    @FindBy(xpath = "//input[@id='loginform-login']")
    public WebElement login;
    @FindBy(xpath = "//input[contains(@id, 'loginform-password')]")
    public WebElement password;
    @FindBy(xpath = "//button[contains(text(), 'Вход')]")
    public WebElement loginButton;
    @FindBy(xpath = "//a[contains(text(), 'Регистрация')]")
    public WebElement registrationLink;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage enterLogin (String text) {
        webElementHelper.sendKeys(login, text);
        return this;
    }

    public LoginPage enterPassword (String text) {
        webElementHelper.sendKeys(password, text);
        return this;
    }

    public LoginPage clickLoginButton () {
        webElementHelper.click(loginButton);
        return this;
    }

    public LoginPage clickRegistrationLink () {
        webElementHelper.click(registrationLink);
        return this;
    }


    public void login (String username, String parol) {
            enterLogin(username);
            enterPassword(parol);
            clickLoginButton();

        System.out.println("Текущий URL: " + driver.getCurrentUrl());
    }
}
