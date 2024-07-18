package tests;

import pages.GoogleSearchPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GoogleSearchTest {
    private WebDriver driver;
    private GoogleSearchPage googleSearchPage;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://www.google.com");
        googleSearchPage = new GoogleSearchPage(driver);
    }

    @Test
    public void testSearch() {
        googleSearchPage.enterSearchTerm("fpt da nang");
        googleSearchPage.clickSearchButton();

        // Wait for the search results page to load
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.titleContains("fpt da nang"));

        // Assert that the title contains the search term
        // String title = driver.getTitle();
        //assertTrue(title.contains("fpt da nang"));
        // Assert that the title is exactly what we expect
        String expectedTitle = "fpt da nang - Tìm trên Google";
        String actualTitle = driver.getTitle();
        assertEquals(expectedTitle, actualTitle, "The page title should be 'fpt da nang - Google Search'");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

