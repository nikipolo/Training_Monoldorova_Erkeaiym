package ui.driver;

import org.openqa.selenium.WebDriver;
import config.ConfigReader;

public class DriverManager {
    public static WebDriver driver;


    public static WebDriver getDriver() {
        if (driver == null){
            switch (ConfigReader.getValue("browser").toLowerCase()){
                case "chrome":
                    driver = ChromeWebDriver.create();
                    break;
                default:
                    throw new IllegalArgumentException("You provided wrong Driver name");
            }
        }
        return driver;
    }

    public static void closeDriver() {
        try {
            if (driver!=null){
                driver.close();
                driver.quit();
                driver=null;
            }
        } catch (Exception e) {
            System.out.println("Error while closing driver");
        }
    }
}
