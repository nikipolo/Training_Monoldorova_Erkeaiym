package ui.pages.Task;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import ui.pages.BasePage;
import java.io.File;

import java.util.List;

import static ui.driver.DriverManager.driver;

public class TaskPage extends BasePage {
    @FindBy(css = "a.sidebar-toggle")
    public WebElement sidebarToggle;
    @FindBy(xpath = "//ul[@class='sidebar-nav']//a[contains(@href, '#menu-7')]")
    public WebElement task;
    @FindBy(css = "a.sidebar-link[href='/task/index']")
    public WebElement subsectionTask;
    @FindBy(css =  "a[href='/task/create']")
    public WebElement createTaskButton;
    @FindBy(css = "#task-theme")
    public WebElement taskTheme;
    @FindBy(css = "input.select2-search__field")
    public  WebElement taskTag;
    @FindBy(css = "span.s2-select-label")
    public WebElement tagDropdownToggle;
    @FindBy(css = "li.select2-results__option--highlighted")
    public WebElement tagDropdown;
    @FindBy(css = "#select2-task-project_id-container")
    public WebElement projectIdDropdown;
    @FindBy(css = "li.select2-results__option--highlighted")
    public WebElement projectId;
    @FindBy(css = "#select2-task-manager_id-container")
    public WebElement projectManagerIdDropdown;
    @FindBy(css = "#select2-task-product_manager_id-container")
    public WebElement productManagerIdDropdown;
    @FindBy(xpath = "//div[@id='task-description-editable'][1]")
    public WebElement descriptionEditable;
    @FindBy(css = "#task-description-editable-btn-bold")
    public WebElement boldButton;
    @FindBy(css = "#task-description-editable-btn-italic")
    public WebElement italicButton;
    @FindBy(xpath = "//button[contains(text(), 'Стиль')]")
    public WebElement styleButton;
    @FindBy(css = "#id-task_department")
    public WebElement departmentDropdown;
    @FindBy(css = "#select2-task-executor_id-container")
    public WebElement executorIdDropdown;
    @FindBy(xpath = "//span[contains(@class,'select2-search')]/input[@type='search']")
    public WebElement executor;
    @FindBy(xpath = "//ul[@id='select2-task-executor_id-results']//li[contains(@class, 'select2-results__option') and contains(text(), 'Tester')]")
    public WebElement executorResult;
    @FindBy(css = "#select2-task-observer_id-container")
    public WebElement observerIdDropdown;
    @FindBy(xpath = "//input[@class='select2-search__field' and @aria-controls='select2-task-observer_id-results']")
    public WebElement observer;
    @FindBy(xpath = "//ul[@id='select2-task-observer_id-results']//li[contains(@class, 'select2-results__option') and contains(normalize-space(.), 'Tester T.')]")
    public WebElement observerResult;
    @FindBy(xpath = "//span[@id='select2-task-assigned_id-container']/ancestor::span[contains(@class, 'select2-selection')]")
    public WebElement assignedIdDropdown;
    @FindBy(id = "task-priority")
    public WebElement priorityDropdown;
    @FindBy(css = "#task-planned_time")
    public WebElement plannedTimeDropdown;
    @FindBy(css = "#task-deliverydate")
    public WebElement deliveryDate;
    @FindBy(css = "#taskphoto-file")
    public WebElement photoFile;
    @FindBy(css = "#taskphoto-avatar")
    public WebElement photoAvatar;
    @FindBy(xpath = "//button[contains(text(),'Отменить')]")
    public List<WebElement> cancelUploadButtons;
    @FindBy(xpath = "//button[contains(text(), 'В работу')]")
    public List<WebElement> toWorkButton;
    @FindBy(xpath = "//button[contains(text(), 'Сохранить')]")
    public List<WebElement> saveButton;

    public TaskPage(WebDriver driver) {
        super(driver);
    }

    public void clickSidebarToggle(WebElement sectionElement, WebElement subsection, String sectionUrlPart) {
        webElementHelper.click(sidebarToggle);
        webElementHelper.click(sectionElement);
        webElementHelper.click(subsection);

        // Ждём, пока перейдёт на нужный раздел
        webElementHelper.waitForUrlContains(sectionUrlPart);
    }

    public void createTask() {
        createTaskButton.click();

    }

    public void selectDepartmentByValue(String value) {
        new Select(departmentDropdown).selectByValue(value);
    }

    public void selectDepartmentByText(String visibleText) {
        new Select(departmentDropdown).selectByVisibleText(visibleText);
    }

    public void selectPriority(String value) {
        new Select(priorityDropdown).selectByValue(value);
    }

    public void trySelect2Value(WebElement dropdown, WebElement inputField, String value) {
        try {
            dropdown.click();
            Thread.sleep(300);
            inputField.sendKeys(value);

            List<WebElement> options = driver.findElements(By.cssSelector("li.select2-results__option"));
            if (options.stream().anyMatch(WebElement::isDisplayed)) {
                inputField.sendKeys(Keys.ENTER);
            } else {
                System.out.println("⚠ Нет доступных значений: " + value);
            }
        } catch (Exception e) {
            System.out.println("⚠ Ошибка выбора значения: " + e.getMessage());
        }
    }

    public void clickOnlyAndLog(WebElement dropdown, String fieldName) {
        try {
            dropdown.click();
            Thread.sleep(300);
            List<WebElement> options = driver.findElements(By.cssSelector("li.select2-results__option"));
            if (options.isEmpty()) {
                System.out.println("⚠ В поле '" + fieldName + "' нет доступных значений.");
            }
        } catch (Exception e) {
            System.out.println("⚠ Ошибка при открытии поля '" + fieldName + "': " + e.getMessage());
        }
    }

    public void fillTaskForm(
            String theme,
            String tag,
            String project,
            String description,
            String department,
            String executors,
            String observers,
            String priorityValue,
            String plannedTime,
            String deliveryDates,
            String photoPath,
            String avatarPath
    ) throws InterruptedException {
        webElementHelper.waitForPageLoad(); // Ждём полной загрузки страницы

        taskTheme.sendKeys(theme);

        taskTag.click();
//        tagDropdownToggle.click(); // возможно, тут нужно выбрать элемент из выпадающего списка (добавь при необходимости)

        projectIdDropdown.click();
        projectId.click();

        descriptionEditable.sendKeys(description);
        departmentDropdown.sendKeys(department);

        //Выбираем исполнителя (Select2-эмуляция)
        wait.until(ExpectedConditions.elementToBeClickable(executorIdDropdown));

        Thread.sleep(300);


        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", executorIdDropdown);
        executorIdDropdown.click();
        executor.sendKeys(executors);

        wait.until(ExpectedConditions.elementToBeClickable(executorResult));
        executorResult.click();

        observerIdDropdown.click();
        observer.sendKeys(observers);
        wait.until(ExpectedConditions.elementToBeClickable(observerResult));
        observerResult.click();


        //Приоритет — через Select
        selectPriority(priorityValue);

        plannedTimeDropdown.sendKeys(plannedTime);
        deliveryDate.sendKeys(deliveryDates);

//        uploadFile(photoFile, photoPath);
//        uploadFile(photoAvatar, avatarPath);
    }

    public void uploadFile(WebElement fileInput, String filePath) {
        fileInput.sendKeys(filePath);
    }

}
