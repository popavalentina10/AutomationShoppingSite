package PageObjects;

import Utils.DOMUtils;
import Utils.GenericUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class RegisterPage extends BasePage {
    @FindBy(xpath = "//div[@class='user-icon profile-icon']")
    private WebElement profileIcon;

    @FindBy(xpath = "//div[@class='socials_login_register']//a[@title='Inregistrare']")
    private WebElement goToRegisterPageButton;

    @FindBy(xpath = "//input[@id='terms']")
    private WebElement termsCheckbox;

    @FindBy(xpath = "//input[@id='reg_username']")
    private WebElement usernameInput;

    @FindBy(xpath = "//input[@id='reg_email']")
    private WebElement emailInput;

    @FindBy(xpath = "//div[contains(@class, 'register-validate')]")
    private WebElement validateFormButton;

    @FindBy(name = "register")
    private WebElement submitFormButton;

    @FindBy(xpath = "//div[@class='woocommerce-notices-wrapper']//ul[@class='woocommerce-error']")
    private WebElement submitFormError;

    @FindBy(xpath = "//p[@class='email-password']//span[@class='error']")
    private WebElement validateFormError;

    @FindBy(id = "my-account-menu")
    private WebElement myAccountDetails;

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    public void goToRegisterPage() throws InterruptedException {
        profileIcon.click();
        GenericUtils.sleep(500);
        DOMUtils.clickElementViaJavaScript(driver, goToRegisterPageButton);
        DOMUtils.scrollToElementViaActions(driver, termsCheckbox);
    }

    public void register(String username, String email) {
        usernameInput.sendKeys(username);
        emailInput.sendKeys(email);
    }

    public void validateForm() throws InterruptedException {
        DOMUtils.clickElementViaJavaScript(driver, validateFormButton);
        GenericUtils.sleep(1500);
        driver.findElement(By.xpath("//input[@id='username']")).clear();
    }

    public void submitForm() {
        DOMUtils.clickElementViaJavaScript(driver, submitFormButton);
    }

    public void renderSubmitFormError() {
        wait.until(ExpectedConditions.visibilityOf(submitFormError));
    }

    public boolean isSubmitFormErrorVisible() {
        return isElementDisplayed(submitFormError);
    }

    public String getSubmitFormErrorMessage() {
        return submitFormError.getText();
    }

    public boolean isValidateFormErrorVisible() {
        return isElementDisplayed(validateFormError);
    }

    public String getValidateFormErrorMessage() {
        return validateFormError.getText();
    }

    public void uncheckTermsCheckbox() {
        if (termsCheckbox.isSelected()) {
            DOMUtils.clickElementViaJavaScript(driver, termsCheckbox);
        }
    }

    public void renderLoginSuccessPage() {
        wait.until(ExpectedConditions.visibilityOf(myAccountDetails));
    }

    public boolean isMyAccountDetailsVisible() {
        return isElementDisplayed(myAccountDetails);
    }
}
