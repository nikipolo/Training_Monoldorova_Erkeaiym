package ui.TaskTests;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ui.BaseTest;
import ui.pages.Task.TaskPage;

import java.time.Duration;

import static config.ConfigReader.getValue;

public class TaskPageTest extends BaseTest {
    TaskPage taskPage;

    @BeforeClass
    @Override
    public void setUp() throws java.net.MalformedURLException {
        super.setUp();
        taskPage = new TaskPage(driver);
    }

    @Test(description = "создание задачи")
    public void creatTaskTest() throws InterruptedException {
        String sectionUrlPart = getValue("task");


        taskPage.clickSidebarToggle(taskPage.task, taskPage.subsectionTask, getValue("task"));

    }

    @Test
    public void creatTaskTest2() throws InterruptedException {
        taskPage.clickSidebarToggle(taskPage.task, taskPage.subsectionTask, getValue("task"));
        taskPage.createTask();

        taskPage.fillTaskForm(
                "Техническая",                   // theme
                "Здрасьте",                             // tag
                "Taskmanager",                          // project
                "Описание задачи через автотест",
                "IT (In velit.)",            // department
                "Test",                           // executor
                "Tes",                             // observer
                "2",                                   // priority
                "3",                                   // planned time
                "30.04.2025",                          // delivery date
                "/Users/erkeaiym/Downloads/Наркология Психиатрия .pdf",   // photo file
                "/Users/erkeaiym/Downloads/Наркология Психиатрия .pdf"  // avatar
        );

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", taskPage.saveButton.get(0));

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains("/task/view?id="));

        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/task/view?id="),
                "Ожидалось, что URL содержит /task/view?id=, но был: " + currentUrl);
    }
}
