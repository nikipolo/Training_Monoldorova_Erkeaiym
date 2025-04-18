package ui.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import ui.driver.DriverManager;
import ui.helper.BrowserManager;



import java.time.Duration;

import static ui.driver.DriverManager.driver;

public class MainPage extends BasePage{
    public MainPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "a.sidebar-toggle")
    public WebElement sidebarToggle;
    @FindBy(xpath = "//ul[@class='sidebar-nav']//a[contains(@href, '#menu-7')]")
    public WebElement task;
    @FindBy(css = "a.sidebar-link[href='/task/index']")
    public WebElement subsectionTask;
    @FindBy(css = "a.sidebar-link[href='/daily-report/index']")
    public WebElement subsectionDailyReport;
    @FindBy(css = "a.sidebar-link[href='/task-templates/index']")
    public WebElement subsectionTaskTemplate;
    @FindBy(css = "a.sidebar-link[href='/pull-request/index']")
    public WebElement subsectionTaskPullRequest;
    @FindBy(xpath = "//ul[@class='sidebar-nav']//a[contains(@href, '#menu-16')]")
    public WebElement reports;
    @FindBy(css = "a.sidebar-link[href='/reports/task']")
    public WebElement subsectionTaskReport;
    @FindBy(css = "a.sidebar-link[href='/reports/task-by-flow']")
    public WebElement subsectionTaskReportByFlow;
    @FindBy(xpath = "//a[contains(@href, '#menu-18')]")
    public WebElement education;
    @FindBy(css = "a.sidebar-link[href='/a-d-b-it']")
    public WebElement subsectionADBIT;
    @FindBy(xpath = "//a[contains(@href, '#menu-26')]")
    public WebElement trainingCenter;
    @FindBy(css = "a.sidebar-link[href='/presentation/index']")
    public WebElement subsectionPresentation;
    @FindBy(css = "a.btn-primary[href='/daily-report/create']")
    public WebElement createReport;
    @FindBy(id = "time_tracker_start")
    public WebElement timeTrackerButton;
    @FindBy(css = "#pause_tracker_start")
    public WebElement pauseTrackerButton;
    @FindBy(css = "#dinner_tracker_start")
    public WebElement dinnerTrackerButton;
    @FindBy(css = "#time_tracker_stop")
    public WebElement dinnerTrackerStopButton;
    @FindBy(css = "#dinner_timer")
    public WebElement dinnerTimeText;
    @FindBy(css = "#time_tracker_stop")
    public WebElement timeTrackerStopButton;
    @FindBy(xpath = "//h3[contains(text(),'Часы')]/following::span[@class='main-hours'][1]")
    public WebElement todayHoursSpan;


    public void goToSectionAndBack(WebElement sectionElement, WebElement subsection, String sectionUrlPart, String mainUrlPart) {
        webElementHelper.click(sidebarToggle);
        webElementHelper.click(sectionElement);
        webElementHelper.click(subsection);

        // Ждём, пока перейдёт на нужный раздел
        webElementHelper.waitForUrlContains(sectionUrlPart);

        browserManager.goBack();

        // Ждём, пока вернёмся на главную
        webElementHelper.waitForUrlContains(mainUrlPart);
    }

    public void clickCreateReport() {
        createReport.click();
    }

    public boolean isOnDailyReportCreatePage() {
        return driver.getCurrentUrl().contains("/daily-report/create");
    }

    public void simulateTrackerFlow() throws InterruptedException {

        webElementHelper.scrollToElement(timeTrackerButton);
        webElementHelper.click(timeTrackerButton); // Старт трекера
        waitFor(4000);

        webElementHelper.click(dinnerTrackerButton);  // нажать на начало обеда
        waitFor(3000);

        webElementHelper.click(dinnerTrackerStopButton);  //нажать на закончить обед
        waitFor(1000);

        try {
            Alert alert = DriverManager.getDriver().switchTo().alert();
            alert.accept(); // или dismiss()
        } catch (NoAlertPresentException ignored) {}

        webElementHelper.click(timeTrackerStopButton);
        waitFor(1000);


        // Проверить, что обед не 00:00:00
        System.out.println("Проверяем значение времени обеда");
        String dinner = dinnerTimeText.getText().trim();
        Assert.assertNotEquals(dinner, "00:00:00", "Время обеда не обновилось");

        System.out.println("Проверяем KPI часы (Сегодня)");
        String todayHoursText = todayHoursSpan.getText().trim();
        Assert.assertFalse(todayHoursText.isEmpty(), "KPI часы (Сегодня) пустые");

        int todayHours = Integer.parseInt(todayHoursText);
        Assert.assertTrue(todayHours > 0, "KPI часы (Сегодня) не обновились");


    }

    private void waitFor(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
