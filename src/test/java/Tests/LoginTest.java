package Tests;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;

public class LoginTest extends BaseTest {
    @Test
    public void testValidCredentials() throws InterruptedException {
        navigateToLoginPage();
        populateInputViaActions(chromeDriver, chromeDriver.findElement(By.id("username")), "popa_valentina10@yahoo.com");
        populateInputViaActions(chromeDriver, chromeDriver.findElement(By.id("password")), "Letmein2020!VP");
        clickElementViaActions(chromeDriver, chromeDriver.findElement(By.cssSelector("button[name=login]")));

        WebDriverWait wait = new WebDriverWait(chromeDriver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("my-account-menu")));

        WebElement userDetailsElement = chromeDriver.findElement(By.className("user_details_title"));
        Assert.assertTrue("Error message is not displayed", userDetailsElement.isDisplayed());
        Assert.assertEquals("Detalii contul meu", userDetailsElement.getText());
    }

    @Test
    public void testMissingUsername() throws InterruptedException {
        navigateToLoginPage();
        populateInputViaActions(chromeDriver, chromeDriver.findElement(By.id("password")), "Sample password");
        clickElementViaActions(chromeDriver, chromeDriver.findElement(By.cssSelector("button[name=login]")));
        sleep(1000);
        WebElement errorElement = chromeDriver.findElement(By.className("error"));
        Assert.assertTrue("Error message is not displayed", errorElement.isDisplayed());
        Assert.assertEquals("Introduceti un nume de utilizator sau un email.", errorElement.getText());
    }

    @Test
    public void testMissingPassword() throws InterruptedException {
        navigateToLoginPage();
        populateInputViaActions(chromeDriver, chromeDriver.findElement(By.id("username")), "popa_valentina10@yahoo.com");
        populateInputViaActions(chromeDriver, chromeDriver.findElement(By.id("password")), "");

        clickElementViaActions(chromeDriver, chromeDriver.findElement(By.cssSelector("button[name=login]")));
        sleep(1000);

        WebElement errorElement = chromeDriver.findElement(By.className("error"));
        Assert.assertTrue("Error message is not displayed", errorElement.isDisplayed());
        Assert.assertEquals("Parola trebuie sa aiba cel putin 7 caractere.", errorElement.getText());
    }

    @Test
    public void testInvalidPassword() throws InterruptedException {
        navigateToLoginPage();
        populateInputViaActions(chromeDriver, chromeDriver.findElement(By.id("username")), "popa_valentina10@yahoo.com");
        populateInputViaActions(chromeDriver, chromeDriver.findElement(By.id("password")), "333");

        clickElementViaActions(chromeDriver, chromeDriver.findElement(By.cssSelector("button[name=login]")));
        sleep(1000);

        WebElement errorElement = chromeDriver.findElement(By.className("error"));
        Assert.assertTrue("Error message is not displayed", errorElement.isDisplayed());
        Assert.assertEquals("Parola trebuie sa aiba cel putin 7 caractere.", errorElement.getText());
    }

    @Test
    public void testInvalidUsername() throws InterruptedException {
        navigateToLoginPage();
        populateInputViaActions(chromeDriver, chromeDriver.findElement(By.id("username")), "popa_valentina10@yahoo.");
        populateInputViaActions(chromeDriver, chromeDriver.findElement(By.id("password")), "Sample password");

        clickElementViaActions(chromeDriver, chromeDriver.findElement(By.cssSelector("button[name=login]")));
        sleep(1000);

        WebElement errorElement = chromeDriver.findElement(By.className("error"));
        Assert.assertTrue("Error message is not displayed", errorElement.isDisplayed());
        Assert.assertEquals("Parolă sau username greșite.", errorElement.getText());
    }

    @Test
    public void testIncompletePassword() throws InterruptedException {
        navigateToLoginPage();
        populateInputViaActions(chromeDriver, chromeDriver.findElement(By.id("username")), "popa_valentina10@yahoo.com");
        populateInputViaActions(chromeDriver, chromeDriver.findElement(By.id("password")), "12345");

        clickElementViaActions(chromeDriver, chromeDriver.findElement(By.cssSelector("button[name=login]")));
        sleep(1000);

        WebElement errorElement = chromeDriver.findElement(By.className("error"));
        Assert.assertTrue("Error message is not displayed", errorElement.isDisplayed());
        Assert.assertEquals("Parola trebuie sa aiba cel putin 7 caractere.", errorElement.getText());
    }

    @Test
    public void testShowPassword() throws InterruptedException {
        navigateToLoginPage();

        sleep(500);
        clickElementViaJavaScript(chromeDriver, chromeDriver.findElement(By.cssSelector("a[title=Inregistrare]")));
        sleep(500);
        scrollToElementViaActions(chromeDriver, chromeDriver.findElement(By.id("terms")));
        populateInputViaActions(chromeDriver, chromeDriver.findElement(By.id("username")), "test@test.com");

        WebDriverWait wait = new WebDriverWait(chromeDriver, Duration.ofSeconds(30));
        WebElement passwordElement = wait.until(ExpectedConditions.elementToBeClickable(By.id("password")));
        passwordElement.sendKeys("test");

        WebElement showElement = chromeDriver.findElement(By.className("show-password-input-pass"));
        clickElementViaActions(chromeDriver, showElement);
        Assert.assertTrue("Error message is not displayed", showElement.isDisplayed());
        Assert.assertEquals("show-password-input-pass display-password", showElement.getDomAttribute("class"));
    }

    @Test
    public void testLostPasswordWithValidEmail() throws InterruptedException {
        navigateToLoginPage();
        populateInputViaActions(chromeDriver, chromeDriver.findElement(By.id("username")), "popa_valentina10@yahoo.com");
        clickElementViaActions(chromeDriver, chromeDriver.findElement(By.className("lost_password")));
        scrollToElementViaJavaScript(chromeDriver, chromeDriver.findElement(By.className("account_section_header")));
        populateInputViaActions(chromeDriver, chromeDriver.findElement(By.id("user_login")), "popa_valentina10@yahoo.com");
        clickElementViaActions(chromeDriver, chromeDriver.findElement(By.cssSelector("button[value=\"Resetează parola\"]")));
        sleep(2000);

        WebElement emailSendElement = chromeDriver.findElement(By.className("woocommerce-message"));
        Assert.assertTrue("Error message is not displayed", emailSendElement.isDisplayed());
        Assert.assertEquals("A fost trimis un email pentru resetarea parolei.", emailSendElement.getText());
    }

    @Test
    public void testLostPasswordWithInvalidEmail() throws InterruptedException {
        navigateToLoginPage();
        populateInputViaActions(chromeDriver, chromeDriver.findElement(By.id("username")), "popa_valentina10@yahoo.com");
        clickElementViaActions(chromeDriver, chromeDriver.findElement(By.className("lost_password")));
        scrollToElementViaJavaScript(chromeDriver, chromeDriver.findElement(By.className("account_section_header")));
        populateInputViaActions(chromeDriver, chromeDriver.findElement(By.id("user_login")), "popa_valentina10@yahoo.a");
        clickElementViaActions(chromeDriver, chromeDriver.findElement(By.cssSelector("button[value=\"Resetează parola\"]")));
        sleep(2000);

        WebElement errorElement = chromeDriver.findElement(By.className("woocommerce-error"));
        Assert.assertTrue("Error message is not displayed", errorElement.isDisplayed());
        Assert.assertEquals("Nume utilizator sau email invalid.", errorElement.getText());
    }

    private void navigateToLoginPage() throws InterruptedException {
        WebElement loginElement = chromeDriver.findElement(By.className("profile-icon"));
        loginElement.click();
        sleep(500);
    }
}
