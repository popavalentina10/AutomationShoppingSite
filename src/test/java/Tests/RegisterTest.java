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

        WebElement usernameElement = chromeDriver.findElement(By.id("reg_username"));
        usernameElement.sendKeys("");

        WebElement emailElement = chromeDriver.findElement(By.id("reg_email"));
        emailElement.sendKeys("vale.po@yahoo.com");

        validateForm();
        submitForm();

        WebElement errorElement = chromeDriver.findElement(By.className("woocommerce-error"));
        Assert.assertTrue("Error message is not displayed", errorElement.isDisplayed());
        Assert.assertEquals("Eroare: Te rog introdu un nume utilizator cu cont valid.", errorElement.getText());
    }

    @Test
    public void testMissingEmail() throws InterruptedException {
        navigateToLoginPage();

        WebElement usernameElement = chromeDriver.findElement(By.id("reg_username"));
        usernameElement.sendKeys("NN");

        WebElement emailElement = chromeDriver.findElement(By.id("reg_email"));
        emailElement.sendKeys("");

        validateForm();

        WebElement errorElement = chromeDriver.findElement(By.className("error"));
        Assert.assertTrue("Error message is not displayed", errorElement.isDisplayed());
        Assert.assertEquals("Introduceți o adresă de email validă.", errorElement.getText());
    }

    @Test
    public void testMissingCredentials() throws Exception {
        navigateToLoginPage();
        validateForm();

        WebElement errorElement = chromeDriver.findElement(By.className("error"));
        Assert.assertTrue("Error message is not displayed", errorElement.isDisplayed());
        Assert.assertEquals("Introduceți o adresă de email validă.", errorElement.getText());
    }

    @Test
    public void testInvalidEmail() throws InterruptedException {
        navigateToLoginPage();

        WebElement emailElement = chromeDriver.findElement(By.id("reg_email"));
        emailElement.sendKeys("test");

        validateForm();

        WebElement errorElement = chromeDriver.findElement(By.className("error"));
        Assert.assertTrue("Error message is not displayed", errorElement.isDisplayed());
        Assert.assertEquals("Introduceți o adresă de email validă.", errorElement.getText());
    }

    @Test
    public void testInvalidUsername() throws InterruptedException {
        navigateToLoginPage();

        WebElement usernameElement = chromeDriver.findElement(By.id("reg_username"));
        usernameElement.sendKeys("  ");

        WebElement emailElement = chromeDriver.findElement(By.id("reg_email"));
        emailElement.sendKeys("vale.p999999@yahoo.com");

        validateForm();
        submitForm();

        WebElement errorElement = chromeDriver.findElement(By.className("woocommerce-error"));
        Assert.assertTrue("Error message is not displayed", errorElement.isDisplayed());
        Assert.assertEquals("Eroare: Te rog introdu un nume utilizator cu cont valid.", errorElement.getText());
    }

    @Test
    public void testUncheckTermsAndConditions() throws InterruptedException {
        navigateToLoginPage();

        WebElement usernameElement = chromeDriver.findElement(By.id("reg_username"));
        usernameElement.sendKeys("sample");

        WebElement emailElement = chromeDriver.findElement(By.id("reg_email"));
        emailElement.sendKeys("sample@yahoo.com");

        uncheckCheckbox(chromeDriver, chromeDriver.findElement(By.id("terms")));
        validateForm();
        refreshPage(chromeDriver);
        uncheckCheckbox(chromeDriver, chromeDriver.findElement(By.id("terms")));
        submitForm();

        WebElement errorElement = chromeDriver.findElement(By.className("woocommerce-error"));
        Assert.assertTrue("Error message is not displayed", errorElement.isDisplayed());
        Assert.assertEquals(
                "Eroare: Te rog citește și acceptă termenii și condițiile pentru a continua.",
                errorElement.getText()
        );
    }

    @Test
    public void testCreateAnotherAccountWithSameEmail() throws InterruptedException {
        navigateToLoginPage();

        WebElement usernameElement = chromeDriver.findElement(By.id("reg_username"));
        usernameElement.sendKeys("SameEmail");

        WebElement emailElement = chromeDriver.findElement(By.id("reg_email"));
        emailElement.sendKeys("popa_valentina10@yahoo.com");

        validateForm();

        WebElement errorElement = chromeDriver.findElement(By.className("error"));
        Assert.assertTrue("Error message is not displayed", errorElement.isDisplayed());
        Assert.assertEquals("Este înregistrat deja un cont cu adresa ta de email.", errorElement.getText());
    }

    @Test
    public void testCreateAccount() throws InterruptedException {
        long timestamp = generateTimestamp();

        navigateToLoginPage();
        WebElement usernameElement = chromeDriver.findElement(By.id("reg_username"));
        usernameElement.sendKeys("validAccount" + timestamp);

        WebElement emailElement = chromeDriver.findElement(By.id("reg_email"));
        emailElement.sendKeys(timestamp + "valid.account@yahoo.com");

        validateForm();
        submitForm();

        WebElement myAccountElement = chromeDriver.findElement(By.id("my-account-menu"));
        Assert.assertTrue("Error message is not displayed", myAccountElement.isDisplayed());
        Assert.assertNotNull(myAccountElement);
    }

    private void navigateToLoginPage() throws InterruptedException {
        WebElement loginElement = chromeDriver.findElement(By.className("profile-icon"));
        loginElement.click();

        sleep(500);
        clickElementViaJavaScript(chromeDriver, chromeDriver.findElement(By.cssSelector("a[title=Inregistrare]")));
        sleep(500);

        WebElement termsElement = chromeDriver.findElement(By.id("terms"));
        Actions actions = new Actions(chromeDriver);
        actions.moveToElement(termsElement);
        actions.perform();
    }

    private void validateForm() throws InterruptedException {
        clickElementViaJavaScript(chromeDriver, chromeDriver.findElement(By.className("register-validate")));
        sleep(1500);
    }

    private void submitForm() {
        clickElementViaJavaScript(
                chromeDriver, chromeDriver.findElement(By.className("woocommerce-form-register__submit"))
        );
    }

    private void uncheckCheckbox(WebDriver driver, WebElement checkbox) {
        if (checkbox.isSelected()) {
            clickElementViaJavaScript(driver, checkbox);
        }
    }
}
