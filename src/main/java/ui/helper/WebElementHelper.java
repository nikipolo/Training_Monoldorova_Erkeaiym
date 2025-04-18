package ui.helper;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ui.driver.DriverManager;

import java.time.Duration;

import static ui.driver.DriverManager.driver;

public class WebElementHelper {
    public Actions actions;

    public WebElementHelper waitForButtonToBeClickAble(WebElement element) {
        new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(7))
                .until(ExpectedConditions.elementToBeClickable(element));
        return this;
    }

    public WebElementHelper waitForElementToBeDisplayed(WebElement element) {
        new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(15)).until(ExpectedConditions.visibilityOf(element));
        return this;
    }

    public WebElementHelper click(WebElement element) {
        waitForButtonToBeClickAble(element);
        element.click();
        return this;
    }

    public WebElementHelper clickWithScrollToElement(WebElement element) {
        waitForButtonToBeClickAble(element);
        scrollToElement(element);
        element.click();
        return this;
    }

    public WebElementHelper jsClick(WebElement element) {
        waitForButtonToBeClickAble(element);
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].click();", element);
        return this;
    }

    public WebElementHelper doubleClick(WebElement element) {
        waitForButtonToBeClickAble(element);
        actions.doubleClick(element);
        return this;
    }

    public WebElementHelper sendKeys(WebElement element, String input) {
        waitForButtonToBeClickAble(element);
        element.sendKeys(input);
        return this;
    }

    public WebElementHelper scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        return this;
    }

    public WebElementHelper scrollDownPage(){
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("window.scrollBy(0, 200)");
        return this;
    }

    public WebElementHelper scrollDownPageAction(int deltaX, int deltaY) {
        actions = new Actions(DriverManager.getDriver());
        actions.scrollByAmount(deltaX,deltaY);
        return this;
    }

    public WebElementHelper moveToElement(WebElement element) {
        actions = new Actions(DriverManager.getDriver());
        actions.moveToElement(element).perform();
        return this;
    }

    public void waitForUrlContains(String urlPart) {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains(urlPart));
    }


    public void waitForPageLoad() {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                webDriver -> ((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState")
                        .equals("complete")
        );
    }

    public void waitForElementIsSelect(WebElement element) {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                driver -> element.getTagName().equalsIgnoreCase("select")
        );
    }


}
