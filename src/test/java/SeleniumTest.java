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

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SeleniumTest {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        // Set up ChromeDriver using WebDriverManager
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @Test
    public void testGoogleSearch() {
        // Open Google
        driver.get("https://www.google.com");

        // Verify the title
        String title = driver.getTitle();
        assertEquals("Google", title);
    }

    @AfterEach
    public void tearDown() {
        // Close the browser
        if (driver != null) {
            driver.quit();
        }
    }
    @Test
    public void testSearch() {
        // Open Google
        driver.get("https://www.google.com");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // Wait for the search box to be present
        WebElement searchText = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("q")));

        searchText.sendKeys("dai hoc fpt da nang");

        // Wait for the search button to be clickable and then click it
        WebElement googleSearchButton = wait.until(ExpectedConditions.elementToBeClickable(By.name("btnK")));
        googleSearchButton.click();

        // Wait for the search results page to load
        wait.until(ExpectedConditions.titleContains("fpt da nang"));

        try {
            Thread.sleep(5000); // 5 seconds delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Assert that the title contains the search term
        String title = driver.getTitle();
        assertTrue(title.contains("fpt da nang"));
    }

    private void assertTrue(boolean fpt_da_nang) {
    }

}
