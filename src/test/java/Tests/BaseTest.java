package Tests;

import core.utils.ConfigUtils;
import core.utils.ConstantUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.sql.Timestamp;

public class BaseTest {
    protected WebDriver chromeDriver;

    @BeforeMethod
    public void setup() {
        System.setProperty(
                "webdriver.chrome.driver",
                ConfigUtils.getGenericElement(ConstantUtils.CONFIG_FILE, "chromedriver")
        );
        chromeDriver = new ChromeDriver();
        chromeDriver.manage().window().maximize();
        chromeDriver.get(ConfigUtils.getGenericElement(ConstantUtils.CONFIG_FILE, "hostname"));
    }

    @AfterMethod
    public void quit() {
        chromeDriver.quit();
    }

    protected void sleep(Integer duration) throws InterruptedException {
        Thread.sleep(duration);
    }

    protected void clickElementViaJavaScript(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click()", element);
    }

    protected void clickElementViaActions(WebDriver driver, WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        actions.click();
        actions.build().perform();
    }

    protected void refreshPage(WebDriver driver) {
        driver.navigate().refresh();
    }

    protected long generateTimestamp() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        return timestamp.getTime();
    }

    protected void populateInputViaActions(WebDriver driver, WebElement element, String text) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        actions.click();
        actions.sendKeys(text);
        actions.build().perform();
    }

    protected void scrollToElementViaActions(WebDriver driver, WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        actions.perform();
    }

    protected void scrollToElementViaJavaScript(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }
}
