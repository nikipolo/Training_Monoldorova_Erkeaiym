package ui.helper;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.LinkedList;
import java.util.Set;

public class BrowserManager {
    private WebDriver driver;

    public BrowserManager(WebDriver driver) {
        this.driver = driver;
    }

    public void openByNavigate(final String URL) {
        driver.navigate().to(URL);
    }

    public void openByGet(final String URL) {
        driver.get(URL);
    }

    public void goBack() {
        driver.navigate().back();
    }

    public void goForward() {
        driver.navigate().forward();
    }

    public void refreshThePage() {
        driver.navigate().refresh();
    }

    public static class FrameHelper {

        private WebDriver driver;
        private WebDriverWait wait;

        public FrameHelper(WebDriver driver) {
            this.driver = driver;
            this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        }

        public FrameHelper switchToFrame(WebElement element) {
            try {
                wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(element));
            } catch (TimeoutException e){
                e.printStackTrace();
            }
            return this;
        }

        public FrameHelper switchToParentFrame(WebElement element) {
            driver.switchTo().parentFrame();
            return this;
        }

        public FrameHelper switchToFrameByIndex(int index) {
            driver.switchTo().frame(index);
            return this;
        }
    }

    public static class WindowHelper {

        private WebDriver driver;
        private WebDriverWait wait;

        public WindowHelper(WebDriver driver) {
            this.driver = driver;
            this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        }

        public Set<String> getWindowHandles() {
            return driver.getWindowHandles();
        }

        public void switchToWindow(int index) {
            LinkedList<String> windowsId = new LinkedList<>(getWindowHandles());
            if (index < 0 || index > windowsId.size()) {
                throw new IllegalArgumentException("You provide wrong index " + index);
            }
            driver.switchTo().window(windowsId.get(index));
        }

        public void switchToParent() {
            LinkedList<String> windowsId = new LinkedList<>(getWindowHandles());
            driver.switchTo().window(windowsId.get(0));
        }

        public void switchToParentWithChildClose() {
            switchToParent();
            LinkedList<String> windowsId = new LinkedList<>(getWindowHandles());
            for (int i = 0; i < windowsId.size(); i++){
                driver.switchTo().window(windowsId.get(i));
                driver.close();
            }
        }
    }
}
