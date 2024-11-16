package demo;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import demo.wrappers.WrapperMethods;
import io.github.bonigarcia.wdm.WebDriverManager;

public class TestCase {
    private WebDriver driver;
    private WebDriverWait wait;
    private WrapperMethods seleniumWrapper;

    @BeforeSuite(alwaysRun = true)
    public void initializeTestSuite() {
        System.out.println("Initializing Test Cases:");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        seleniumWrapper = new WrapperMethods(driver, wait);
    }

    @Test(priority = 1, enabled = true)
    public void validateLowRatingItems() throws InterruptedException {
        System.out.println("Test Case 1: Validate Low-Rating Items Started");
        try {
            seleniumWrapper.navigateToHomePage("https://www.flipkart.com/");
            seleniumWrapper.searchItem("Washing Machine", "//input[@class='Pke_EE']", "//button[@class='_2iLD__']");
            seleniumWrapper.filterByPopularity();
            Thread.sleep(2000);
            int count = seleniumWrapper.getLowRatedItemsCount(4.0);
            Thread.sleep(2000);
            System.out.println("Number of items with rating less than or equal to 4 stars: " + count);
        } catch (Exception e) {
            System.out.println("An unexpected exception occurred: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Test Case 1 Ended");
    }

    @Test(priority = 2, enabled = true)
    public void validateHighDiscountItems() throws InterruptedException {
        System.out.println("Test Case 2: Validate High Discount Items Started");
        try {
            seleniumWrapper.searchItem("iPhone", "//input[@class='zDPmFV']", "//button[@class='MJG8Up']");
            Thread.sleep(3000);
            seleniumWrapper.displayItemsWithHighDiscount(17);
        } catch (Exception e) {
            System.out.println("An unexpected exception occurred: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Test Case 2 Ended");
    }

    @Test(priority = 3, enabled = true)
    public void validateTopReviewedItems() throws InterruptedException {
        System.out.println("Test Case 3: Validate Top Reviewed Items Started");
        try {
            seleniumWrapper.searchItem("Coffee Mug", "//input[@class='zDPmFV']", "//button[@class='MJG8Up']");
            Thread.sleep(3000);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,300);");
            Thread.sleep(2000);
            seleniumWrapper.sortItemsByRating();
            Thread.sleep(3000);
            seleniumWrapper.listTop5ReviewedItems();
        } catch (StaleElementReferenceException exception) {
            System.out.println("An unexpected exception occurred: " + exception.getMessage());
            exception.printStackTrace();
        }
        System.out.println("Test Case 3 Ended");
        Thread.sleep(3000);
    }

    @AfterSuite(alwaysRun = true)
    public void terminateTestSuite() {
        System.out.println("Terminating Test Cases:");
        driver.close();
        driver.quit();
    }
}
