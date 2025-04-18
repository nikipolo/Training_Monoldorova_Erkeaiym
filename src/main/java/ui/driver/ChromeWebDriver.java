package ui.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ChromeWebDriver {

    public static WebDriver create() {
        ChromeOptions options = new ChromeOptions();

        // Общие Chrome настройки
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");

        // Selenoid совместимость через W3C
        Map<String, Object> selenoidOptions = new HashMap<>();
        selenoidOptions.put("enableVNC", true);
        selenoidOptions.put("enableVideo", false);

        options.setCapability("browserVersion", "128.0");
        options.setCapability("browserName", "chrome"); // правильно для W3C

        try {
            return new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Ошибка подключения к Selenoid", e);
        }
    }
}