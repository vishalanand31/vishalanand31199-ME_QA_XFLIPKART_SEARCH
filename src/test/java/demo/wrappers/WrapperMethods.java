package demo.wrappers;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WrapperMethods {
    private WebDriver driver;
    private WebDriverWait wait;

    public WrapperMethods(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void navigateToHomePage(String url) {
        System.out.println("Navigating to homepage: " + url);
        driver.get(url);
    }

    public void searchItem(String itemName, String searchBoxXPath, String searchButtonXPath) {
        System.out.println("Searching for item: " + itemName);
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(searchBoxXPath)));
        searchBox.clear();
        searchBox.sendKeys(itemName);
        WebElement searchButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(searchButtonXPath)));
        searchButton.click();
    }

    public void filterByPopularity() {
        System.out.println("Filtering by: Popularity");
        WebElement popularityFilter = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='Popularity']")));
        popularityFilter.click();
    }

    public int getLowRatedItemsCount(double maxRating) {
        System.out.println("Getting count of items with ratings <= " + maxRating);
        List<WebElement> ratings = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='XQDdHH']")));
        int count = 0;
        for (WebElement ratingElement : ratings) {
            double rating = Double.parseDouble(ratingElement.getText());
            if (rating <= maxRating) {
                count++;
            }
        }
        return count;
    }

    public void displayItemsWithHighDiscount(int minDiscountPercentage) {
        System.out.println("Displaying items with discount > " + minDiscountPercentage + "%");
        List<WebElement> items = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='tUxRFH']")));
        for (WebElement item : items) {
            WebElement titleElement = item.findElement(By.xpath(".//div[@class='KzDlHZ']"));
            String title = titleElement.getText();
            WebElement discountElement = item.findElement(By.xpath(".//div[@class='UkUFwK']"));
            String discountText = discountElement.getText().replaceAll("[^0-9]", "");
            int discount = Integer.parseInt(discountText);
            if (discount > minDiscountPercentage) {
                System.out.println("Title: " + title + ", Discount: " + discount + "%");
            }
        }
    }

    public void sortItemsByRating() {
        System.out.println("Sorting items by 4 stars and above ratings");
        WebElement ratingSort = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='XqNaEv'])[1]")));
        ratingSort.click();
    }

    public void listTop5ReviewedItems() {
        System.out.println("Listing top 5 items with the highest review counts:");
        List<WebElement> products = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='slAVV4']")));
        products.sort((p1, p2) -> {
            WebElement reviewCountElement1 = p1.findElement(By.xpath(".//span[@class='Wphh3N']"));
            WebElement reviewCountElement2 = p2.findElement(By.xpath(".//span[@class='Wphh3N']"));
            int reviewCount1 = Integer.parseInt(reviewCountElement1.getText().replaceAll("[^0-9]", ""));
            int reviewCount2 = Integer.parseInt(reviewCountElement2.getText().replaceAll("[^0-9]", ""));
            return Integer.compare(reviewCount2, reviewCount1);
        });

        for (int i = 0; i < Math.min(products.size(), 5); i++) {
            WebElement product = products.get(i);
            WebElement title = product.findElement(By.xpath(".//a[@class='wjcEIp']"));
            WebElement image = product.findElement(By.xpath(".//img[@class='DByuf4']"));
            System.out.println("Title: " + title.getText());
            System.out.println("Image URL: " + image.getAttribute("src"));
            System.out.println();
        }
    }

    public void openHomePage(String string) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'openHomePage'");
    }
}
