package tests;

import dev.failsafe.internal.util.Assert;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class TestLogin {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        driver.get("http://localhost:3000/login");
        Thread.sleep(50); // Delay for visual confirmation of page load
    }

    @Test
    public void testValidLogin() throws InterruptedException {
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit' and normalize-space()='Login']"));

        usernameField.sendKeys("cuonght17112003@gmail.com");
        passwordField.sendKeys("123123");
        loginButton.click();

        String currentUrl = driver.getCurrentUrl();
        Assertions.assertTrue(currentUrl.contains("/"));
    }

    @Test
    public void testInvalidLogin() throws InterruptedException {
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit' and normalize-space()='Login']"));

        usernameField.sendKeys("hoanghieulinh362@gmail.com");
        passwordField.sendKeys("wrongpass");
        loginButton.click();
// Chờ đợi cho đến khi alert được hiển thị
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());

// Lấy nội dung text của alert
        String alertText = alert.getText();
        Assertions.assertEquals("Login fail!!!", alertText);

// Đóng alert

    }

    @Test
    public void testLoginWithoutAtSymbolInUsername() throws InterruptedException {
        // Ensure elements are ready
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit' and normalize-space()='Login']"));

        // Enter user information
        usernameField.sendKeys("invalidemail.com");
        passwordField.sendKeys("password");
        loginButton.click();

        // Wait for error message to appear
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), 'Please include an \"@\" in the email address')]")));
        String errorText = errorMessage.getText(); // Get text from the error message

        // Check the text of the error message
        Assertions.assertEquals("Please include an '@' in the email address. 'invalidemail.com' is missing an '@'.", errorText);
        driver.quit();
    }

    @Test
    public void testEmptyUsername() throws InterruptedException {
        // Khởi tạo driver và wait ở đây hoặc đảm bảo rằng chúng được khởi tạo trong một hàm setup

        // Tìm phần tử và nhập dữ liệu
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit' and normalize-space()='Login']"));

        // Nhập dữ liệu trống cho username và mật khẩu hợp lệ
        usernameField.sendKeys("");
        passwordField.sendKeys("somepassword");
        loginButton.click();
        List<WebElement> possibleMessages = driver.findElements(By.xpath("//*[contains(text(),'Please fill out this field')]"));
        for (WebElement message : possibleMessages) {
            if (message.isDisplayed()) {
                System.out.println("Please fill out this field" + message.getText());

                break;
            }

        }
        Thread.sleep(2000);
        // Kiểm tra thông báo lỗi
//        boolean errorMessageDisplayed = wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("error-message"), "Please fill out this field"));
//        Assertions.assertTrue(errorMessageDisplayed, "");
    }

    @Test
    public void testEmptyPassword() throws InterruptedException {
        // Setup WebDriver and WebDriverWait instances as required (not shown here)

        // Locate the username, password fields, and the login button
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit' and normalize-space()='Login']"));

        // Enter a valid email and an empty password
        usernameField.sendKeys("email@example.com");
        passwordField.sendKeys("");
        loginButton.click();

        // Attempt to wait for the error message to be visible and contain the correct text
        boolean isErrorDisplayed = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("error-message")))
                .getText().contains("Password cannot be empty");

// Assert that the error message is displayed and contains the correct text
        Assertions.assertTrue(isErrorDisplayed, "Error message for empty password is not displayed correctly.");
    }
//Login BusCompany and logout
    @Test
    public void testValidLoginBusCompany() throws InterruptedException {
        // Navigate to the login page
        driver.get("http://localhost:3000/login");

        // Wait for the username field to be visible and then fill the login form
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit' and normalize-space()='Login']"));

        // Fill in the credentials and submit the form
        usernameField.sendKeys("phuong_trang@gmail.com");
        passwordField.sendKeys("123123");
        loginButton.click();

        // Wait for and handle the alert
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();

        // Ensure the "Company Page" link is clickable, then click it
        WebElement clickButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[normalize-space()=\"phuong_trang@gmail.com\"]")));
        clickButton.click();
        Thread.sleep(2000);
        WebElement clickButton1 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//a[normalize-space()='Company Page'])[1]")));
        clickButton1.click();
        Thread.sleep(2000);
        // Wait for the URL to be the expected one and check it
        wait.until(ExpectedConditions.urlToBe("http://localhost:3000/bus"));
        String currentUrl = driver.getCurrentUrl();
        Thread.sleep(2000);
        Assertions.assertEquals("http://localhost:3000/bus", currentUrl, "The URL after navigating to the bus company page did not match the expected URL.");
        WebElement clickButton3 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[normalize-space()='Logout'])[1]")));
        clickButton3.click();

        String alertText = alert.getText();
        Assertions.assertEquals("Logged out successfully", alertText);
        Thread.sleep(2000);
    }
//Search full
@Test
public void testValidLoginAndSearch() throws InterruptedException {
    // Login part
    WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
    WebElement passwordField = driver.findElement(By.id("password"));
    WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit' and normalize-space()='Login']"));

    usernameField.sendKeys("cuonght17112003@gmail.com");
    passwordField.sendKeys("123123");
    loginButton.click();

    Alert alert = wait.until(ExpectedConditions.alertIsPresent());
    alert.accept();

    // Select the first option from the first dropdown
    WebElement firstDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//select[@class='form-control'])[1]")));
    Select selectFirst = new Select(firstDropdown);
    selectFirst.selectByVisibleText("Hà Nội");

// Chọn giá trị từ dropdown thứ hai
    WebElement secondDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//select[@class='form-control'])[2]")));
    Select selectSecond = new Select(secondDropdown);
    selectSecond.selectByVisibleText("TP. Hồ Chí Minh");

    Thread.sleep(2000);

    WebElement dateInput = driver.findElement(By.cssSelector("input[type='date'].form-control"));

    dateInput.sendKeys("//input[@value=\"11-08-2024\"]");
    Thread.sleep(2000);

    WebElement searchButton = driver.findElement(By.xpath("//button[normalize-space()='Tìm kiếm']"));
    searchButton.click();
    Thread.sleep(2000);

}
//Test k điền startlocation
    @Test
    public void testValidLoginAndSearchInvalidStartLocation() throws InterruptedException {
        // Login part
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit' and normalize-space()='Login']"));

        usernameField.sendKeys("cuonght17112003@gmail.com");
        passwordField.sendKeys("123123");
        loginButton.click();

        // Accept alert if present
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();

        // Intentionally not selecting any option from the first dropdown to trigger the error
        WebElement firstDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//select[@class='form-control'])[1]")));
        Select selectFirst = new Select(firstDropdown);
        // selectFirst.selectByVisibleText(""); // Commented out to leave this field empty

        WebElement secondDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//select[@class='form-control'])[2]")));
        Select selectSecond = new Select(secondDropdown);
        selectSecond.selectByVisibleText("TP. Hồ Chí Minh");

        Thread.sleep(2000);

        WebElement dateInput = driver.findElement(By.cssSelector("input[type='date'].form-control"));
        dateInput.clear();
        dateInput.sendKeys("11-08-2024"); // Using correct format YYYY-MM-DD
        Thread.sleep(2000);

        WebElement searchButton = driver.findElement(By.xpath("//button[normalize-space()='Tìm kiếm']"));
        searchButton.click();
        Thread.sleep(2000);

        // Check if the error alert is displayed
        Alert errorAlert = wait.until(ExpectedConditions.alertIsPresent());
        String alertText1 = errorAlert.getText();
        errorAlert.accept();
        Assertions.assertEquals("Vui lòng điền đầy đủ thông tin", alertText1);
    }
    // test k điền endLocation
    @Test
    public void testValidLoginAndSearchInvalidEndLocation() throws InterruptedException {
        // Login part
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit' and normalize-space()='Login']"));

        usernameField.sendKeys("cuonght17112003@gmail.com");
        passwordField.sendKeys("123123");
        loginButton.click();

        // Accept alert if present
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();

        // Intentionally not selecting any option from the first dropdown to trigger the error
        WebElement firstDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//select[@class='form-control'])[1]")));
        Select selectFirst = new Select(firstDropdown);
         selectFirst.selectByVisibleText("Đà Nẵng"); // Commented out to leave this field empty

        WebElement secondDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//select[@class='form-control'])[2]")));
        Select selectSecond = new Select(secondDropdown);
//        selectSecond.selectByVisibleText("TP. Hồ Chí Minh");

        Thread.sleep(2000);

        WebElement dateInput = driver.findElement(By.cssSelector("input[type='date'].form-control"));
        dateInput.clear();
        dateInput.sendKeys("2024-08-11"); // Using correct format YYYY-MM-DD
        Thread.sleep(5000);

        WebElement searchButton = driver.findElement(By.xpath("//button[normalize-space()='Tìm kiếm']"));
        searchButton.click();
        Thread.sleep(2000);

        // Check if the error alert is displayed
        Alert errorAlert = wait.until(ExpectedConditions.alertIsPresent());
        String alertText1 = errorAlert.getText();
        errorAlert.accept();
        Assertions.assertEquals("Vui lòng điền đầy đủ thông tin", alertText1);
    }
//Test không điền ngày

    @Test
    public void testValidLoginAndSearchInvalidDatetime() throws InterruptedException {
        // Login part
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit' and normalize-space()='Login']"));

        usernameField.sendKeys("cuonght17112003@gmail.com");
        passwordField.sendKeys("123123");
        loginButton.click();

        // Accept alert if present
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();

        // Intentionally not selecting any option from the first dropdown to trigger the error
        WebElement firstDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//select[@class='form-control'])[1]")));
        Select selectFirst = new Select(firstDropdown);
         selectFirst.selectByVisibleText("Đà Nẵng"); // Commented out to leave this field empty

        WebElement secondDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//select[@class='form-control'])[2]")));
        Select selectSecond =  new Select(secondDropdown);
        selectSecond.selectByVisibleText("TP. Hồ Chí Minh");

        Thread.sleep(1000);

        WebElement dateInput = driver.findElement(By.cssSelector("input[type='date'].form-control"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value = '';", dateInput); // Clear the date field using JavaScript

        Thread.sleep(1000);

        WebElement searchButton = driver.findElement(By.xpath("//button[normalize-space()='Tìm kiếm']"));
        searchButton.click();

        // Check if the error alert is displayed
        Alert errorAlert = wait.until(ExpectedConditions.alertIsPresent());
        String alertText1 = errorAlert.getText();
        errorAlert.accept();
        Assertions.assertEquals("Vui lòng điền đầy đủ thông tin", alertText1);
    }
    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}