package ui.TaskTests;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ui.BaseTest;
import ui.pages.Task.DailyReportsPage;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static config.ConfigReader.getValue;

public class DailyReportsTest extends BaseTest {
    DailyReportsPage dailyReportsPage;

    @BeforeClass
    @Override
    public void setUp() throws java.net.MalformedURLException {
        super.setUp();
        dailyReportsPage = new DailyReportsPage(driver);
    }

    @Test
    public void creatReportsTest() {
        dailyReportsPage.clickSidebarToggle(dailyReportsPage.task, dailyReportsPage.subsectionDailyReport, getValue("dailyReport"));

        dailyReportsPage.clickCreateReport();

        dailyReportsPage.fillReportForm("8", "18:00", "17.04.2025", "7","Отчет через автотест");
        dailyReportsPage.saveButton.click();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains("/daily-report/view?id="));

        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/daily-report/view?id="),
                "Ожидался редирект на/daily-report/view?id=, но текущий URL: " + currentUrl);
    }

    @Test(description = "нажатие на кнопку сегодня и проверка соответствия даты")
    public void checkTodayReportsDisplayedCorrectly() {
        dailyReportsPage.clickSidebarToggle(dailyReportsPage.task, dailyReportsPage.subsectionDailyReport, getValue("dailyReport"));

        dailyReportsPage.clickTodayButton();

        List<String> dates = dailyReportsPage.getReportsForToDay();
        System.out.println("Найденные даты: " + dates);

        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));


        for (String date : dates) {
            Assert.assertEquals(date, today, "Отчёт содержит не сегодняшнюю дату: " + date);
        }
    }


    @Test(description = "проверка по фильтру даты и исполнителя")
    public void checkReportDateRangeFiltering() {
        dailyReportsPage.clickSidebarToggle(dailyReportsPage.task, dailyReportsPage.subsectionDailyReport, getValue("dailyReport"));

        String start = "07.04.2025";
        String end = "10.04.2025";
        List<String> dates = dailyReportsPage.filterAndGetDates(start, end);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate startDate = LocalDate.parse(start, formatter);
        LocalDate endDate = LocalDate.parse(end, formatter);

        for (String dateStr : dates) {
            LocalDate actual = LocalDate.parse(dateStr, formatter);
            Assert.assertTrue(!actual.isBefore(startDate) && !actual.isAfter(endDate), "Дата вне диапазона: " + actual);
        }
    }

    @Test(description = "заполнение формы и кнопка Обновить")
    public void testUpdateButton() {
        dailyReportsPage.clickSidebarToggle(dailyReportsPage.task, dailyReportsPage.subsectionDailyReport, getValue("dailyReport"));

        dailyReportsPage.filterReportsAndClickUpdate("07.04.2025", "10.04.2025", "IT");

        // Получаем все даты из таблицы
        List<String> dates = dailyReportsPage.getReportsForToDay();
        System.out.println("Найденные даты: " + dates);

        // Границы
        LocalDate from = LocalDate.parse("07.04.2025", DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        LocalDate to = LocalDate.parse("10.04.2025", DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        for (String dateStr : dates) {
            LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            Assert.assertTrue((!date.isBefore(from)) && (!date.isAfter(to)),
                    "Дата вне диапазона: " + dateStr);
        }
    }
}
