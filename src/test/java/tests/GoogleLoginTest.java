package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GoogleLoginTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("http://localhost:3000/login");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testLoginWithGoogle() {
        // Tìm và nhấp vào nút đăng nhập Google trên trang ứng dụng của bạn
        WebElement googleLoginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Login with Google')]")));
        googleLoginButton.click();

        // Chuyển sang cửa sổ đăng nhập Google
        String mainWindowHandle = driver.getWindowHandle();
        Set<String> allWindowHandles = driver.getWindowHandles();
        for (String windowHandle : allWindowHandles) {
            if (!windowHandle.equals(mainWindowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }

        // Điền thông tin đăng nhập vào trang Google
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("identifierId")));
        emailField.sendKeys("cuonghtde170264@fpt.edu.vn");
        driver.findElement(By.id("identifierNext")).click();

        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
        passwordField.sendKeys("KIEMTIENGIOI1");
        driver.findElement(By.id("passwordNext")).click();

        // Chờ để đảm bảo đăng nhập thành công và chuyển lại cửa sổ chính
        driver.switchTo().window(mainWindowHandle);

        // Kiểm tra nếu đăng nhập thành công
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userProfile")));
        assertTrue(successElement.isDisplayed());
    }
}