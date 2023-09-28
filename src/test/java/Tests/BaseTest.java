package Tests;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.sql.Timestamp;

public class BaseTest {
    public void sleep(Integer duration) throws InterruptedException {
        Thread.sleep(duration);
    }

    public void clickElementViaJavaScript(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click()", element);
    }

    public void refreshPage(WebDriver driver) {
        driver.navigate().refresh();
    }

    public long generateTimestamp() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        return timestamp.getTime();
    }
}
