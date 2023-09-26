package Tests;

import core.utils.ConfigUtils;
import core.utils.ConstantUtils;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class RegisterTest extends BaseTest {
    private WebDriver chromeDriver;

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

    @Test
    public void testMissingUsername() throws InterruptedException {
        navigateToLoginPage();


    }

    @Test
    public void testMissingEmail() {

    }

    @Test
    public void testMissingCredentials() throws Exception {
        navigateToLoginPage();
        submitForm();

        WebElement errorElement = chromeDriver.findElement(By.className("error"));
        Assert.assertTrue("Error message is not displayed", errorElement.isDisplayed());
        Assert.assertEquals("Introduceți o adresă de email validă.", errorElement.getText());
    }

    @Test
    public void testInvalidEmail() throws InterruptedException {
        navigateToLoginPage();

        WebElement emailElement = chromeDriver.findElement(By.id("reg_email"));
        emailElement.sendKeys("test");

        submitForm();

        WebElement errorElement = chromeDriver.findElement(By.className("error"));
        Assert.assertTrue("Error message is not displayed", errorElement.isDisplayed());
        Assert.assertEquals("Introduceți o adresă de email validă.", errorElement.getText());
    }

    @Test
    public void testInvalidUsername() throws InterruptedException {
        navigateToLoginPage();

        WebElement usernameElement = chromeDriver.findElement(By.id("reg_username"));
        usernameElement.sendKeys("Valentina");

        WebElement emailElement = chromeDriver.findElement(By.id("reg_email"));
        emailElement.sendKeys("popa_valentina10@yahoo.com");

        submitForm();

        WebElement errorElement = chromeDriver.findElement(By.className("error"));
        Assert.assertTrue("Error message is not displayed", errorElement.isDisplayed());
        Assert.assertEquals("Introduceți o adresă de email validă.", errorElement.getText());
    }

    @Test
    public void testInvalidCredentials() {

    }

    @Test
    public void testUncheckTermsAndConditions() {

    }

    @Test
    public void testCreateAnotherAccountWithSameEmail() {

    }

    private void navigateToLoginPage() throws InterruptedException {
        WebElement loginElement = chromeDriver.findElement(By.className("profile-icon"));
        loginElement.click();

        sleep(500);

        WebElement registerElement = chromeDriver.findElement(By.cssSelector("a[title=Inregistrare]"));
        JavascriptExecutor js = (JavascriptExecutor) chromeDriver;
        js.executeScript("arguments[0].click()", registerElement);

        sleep(500);

        WebElement termsElement = chromeDriver.findElement(By.id("terms"));
        Actions actions = new Actions(chromeDriver);
        actions.moveToElement(termsElement);
        actions.perform();
    }

    private void submitForm() throws InterruptedException {
        WebElement submitElement = chromeDriver.findElement(By.className("register-validate"));
        JavascriptExecutor js = (JavascriptExecutor) chromeDriver;
        js.executeScript("arguments[0].click()", submitElement);
        sleep(1500);
    }
}
