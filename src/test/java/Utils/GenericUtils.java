package Utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.sql.Timestamp;

public class GenericUtils {
    public static void sleep(Integer duration) throws InterruptedException {
        Thread.sleep(duration);
    }

    public static long generateTimestamp() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        return timestamp.getTime();
    }

    public static void refreshPage(WebDriver driver) {
        driver.navigate().refresh();
    }
}
