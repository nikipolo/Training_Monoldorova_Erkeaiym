package ui;

import config.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ui.pages.MainPage;


public class MainPageTest extends BaseTest {
    MainPage mainPage;

    @BeforeClass
    @Override
    public void setUp() throws java.net.MalformedURLException {
        super.setUp();
        mainPage = new MainPage(driver);
    }

    @Test(description = "нажатие на кнопку создать отчет и переход на другую страницу")
    public void testWriteReportButtonRedirect() {
        mainPage.clickCreateReport();
        Assert.assertTrue(mainPage.isOnDailyReportCreatePage());
    }

    @Test(description = "переход по разделам")
    public void testGoToDailyReportAndBack() {
        MainPage mainPage = new MainPage(driver);

        String sectionUrlPart = ConfigReader.getValue("dailyReport");
        String mainUrl = ConfigReader.getValue("url");

        mainPage.goToSectionAndBack(mainPage.task, mainPage.subsectionDailyReport, sectionUrlPart, mainUrl);

        Assert.assertTrue(driver.getCurrentUrl().contains(mainUrl), "Не вернулись на главную страницу");
    }

    @Test(description = "Проверка трекера времени и KPI после обеда")
    public void testTrackerKpiFlow() throws InterruptedException {
        MainPage mainPage = new MainPage(driver);
        mainPage.simulateTrackerFlow();
    }
}
