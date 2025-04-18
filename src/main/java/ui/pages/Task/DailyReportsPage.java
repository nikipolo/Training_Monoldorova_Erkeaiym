package ui.pages.Task;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ui.pages.BasePage;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static config.ConfigReader.getValue;
import static ui.driver.DriverManager.driver;

public class DailyReportsPage extends BasePage {
    public DailyReportsPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "a.sidebar-toggle")
    public WebElement sidebarToggle;
    @FindBy(xpath = "//ul[@class='sidebar-nav']//a[contains(@href, '#menu-7')]")
    public WebElement task;
    @FindBy(css = "a.sidebar-link[href='/daily-report/index']")
    public WebElement subsectionDailyReport;
    @FindBy(xpath = "//a[contains(@href, '/daily-report/create')]")
    public WebElement createReport;
    @FindBy(css = "a.btn.btn-warning[href*=\"/daily-report/index\"]")
    public WebElement todayReports;
    @FindBy(id = "select2-dailyreportsearch-department_id-container")
    public WebElement selectDepartmentDropdown;
    @FindBy(xpath = "//li[text()='Все']")
    public WebElement selectDepartment1;
    @FindBy(xpath = "//li[contains(text(),'IT')]")
    public WebElement selectDepartment2;
    @FindBy(xpath = "//li[contains(text(),'Колл-центр')]")
    public WebElement selectDepartment3;
    @FindBy(xpath = "//button[contains(text(), 'Обновить')]")
    public WebElement updateButton;
    @FindBy(xpath = "//a[@class='btn btn-danger' and contains(text(),'Сбросить')]")
    public WebElement resetButton;
    @FindBy(xpath = "//input[@name='start_date']")
    public WebElement startDate;
    @FindBy(xpath = "//input[@name='end_date']")
    public WebElement endDate;
    @FindBy(id= "select2-w4-container")
    public WebElement executorDropdown;
    @FindBy(xpath = "//ul[@id='select2-w4-results']/li[contains(text(), 'Tester T.')]")
    public WebElement executorResult;
    @FindBy(xpath = "//button[contains(text(), 'Показать итоги за период')]")
    public WebElement resultsForThePeriodButton;
    @FindBy(xpath = "//tbody//td[@data-col-seq='2']")
    public List<WebElement> resultsForThePeriodList;
    @FindBy(xpath = "//input[@name='DailyReport[total_time]']")
    public WebElement dailyReportTotalTime;
    @FindBy(xpath = "//input[@name='DailyReport[endTime]']")
    public WebElement dailyReportEndTime;
    @FindBy(css = "#dailyreport-acceptedat")
    public WebElement dailyReportDate;
    @FindBy(css = "#daily_report_hours_0")
    public WebElement dailyReportHours;
    @FindBy(css = "#daily_report_comments_0")
    public WebElement comments;
    @FindBy(xpath = "//button[contains(text(), 'Сохранить')]")
    public WebElement saveButton;


    public void clickSidebarToggle(WebElement sectionElement, WebElement subsection, String sectionUrlPart) {
        webElementHelper.click(sidebarToggle);
        webElementHelper.click(sectionElement);
        webElementHelper.click(subsection);

        // Ждём, пока перейдёт на нужный раздел
        webElementHelper.waitForUrlContains(sectionUrlPart);
    }

   public void clickCreateReport() {
        webElementHelper.click(createReport);
   }

   public void fillReportForm(String totalTime, String leaveTime, String date, String hours,String reportText) {
        dailyReportTotalTime.click();
       dailyReportTotalTime.clear();
       dailyReportTotalTime.click();
       dailyReportTotalTime.sendKeys(totalTime);

       dailyReportEndTime.clear();
       dailyReportEndTime.click();
       dailyReportEndTime.sendKeys(leaveTime);

       dailyReportDate.clear();
       dailyReportDate.click();
       dailyReportDate.sendKeys(date);

       dailyReportHours.clear();
       dailyReportHours.sendKeys(hours);

       comments.click();
       comments.sendKeys(reportText);
    }

    public void clickTodayButton() {
        todayReports.click();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.textToBePresentInElementLocated(
                        By.xpath("//tbody//tr[1]/td[3]"),
                        LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                ));
    }

    public List<String> getReportsForToDay() {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return driver.findElements(By.xpath("//td[@class='report-date']"))
                .stream()
                .map(WebElement::getText)
                .map(String::trim)
                .filter(date -> date.equals(today))
                .collect(Collectors.toList());
    }


    public  List<String> filterAndGetDates (String startDates, String endDates) {
        startDate.clear();
        startDate.sendKeys(startDates);

        endDate.clear();
        endDate.sendKeys(endDates);

        executorDropdown.click();
        executorResult.click();

        resultsForThePeriodButton.click();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Сбор дат из таблицы
        List<WebElement> dateCells = driver.findElements(By.xpath("//td[2]")); // если вторая колонка — это дата
        return dateCells.stream()
                .map(WebElement::getText)
                .map(String::trim)
                .collect(Collectors.toList());
    }

    public void filterReportsAndClickUpdate (String startDates, String endDates, String dropdownValue) {
        startDate.clear();
        startDate.sendKeys(startDates);

        endDate.clear();
        endDate.sendKeys(endDates);

        executorDropdown.click();
        executorResult.click();

        selectDepartmentDropdown.click();

        selectDepartment(dropdownValue);

        updateButton.click();

    }


    public void filterReportsAndClickReset (String startDates, String endDates, String dropdownValue) {
        startDate.clear();
        startDate.sendKeys(startDates);

        endDate.clear();
        endDate.sendKeys(endDates);

        executorDropdown.click();
        executorResult.click();

        selectDepartmentDropdown.click();

        selectDepartment(dropdownValue);

        resetButton.click();
    }

    public void selectDepartment(String department) {
        selectDepartmentDropdown.click();

        switch (department) {
            case "Все" -> selectDepartment1.click();
            case "IT" -> selectDepartment2.click();
            case "Колл-центр" -> selectDepartment3.click();
            default -> throw new IllegalArgumentException("Такого отдела нет: " + department);
        }
    }
}
