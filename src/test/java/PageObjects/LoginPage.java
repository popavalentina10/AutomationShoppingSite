package PageObjects;

import Utils.DOMUtils;
import Utils.GenericUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {
    @FindBy(xpath = "//div[@class='user-icon profile-icon']")
    private WebElement profileIcon;

    @FindBy(xpath = "//input[@id='username']")
    private WebElement usernameInput;

    @FindBy(xpath = "//input[@id='password']")
    private WebElement passwordInput;

    @FindBy(xpath = "//button[@name='login']")
    private WebElement loginButton;

    @FindBy(xpath = "//div[@id='my-account-menu']")
    private WebElement myAccountHeader;

    @FindBy(xpath = "//div[contains(@class, 'user_details_title')]")
    private WebElement myAccountTitle;

    @FindBy(xpath = "//div[@class='ajax_form_errors']//span[@class='error']")
    private WebElement formError;

    @FindBy(xpath = "//p[contains(@class, 'login-password-row')]//span[@class='show-password-input-pass']")
    private WebElement previewPassword;

    @FindBy(xpath = "//p[contains(@class, 'lost_password')]//a[contains(@href, 'lost-password')]")
    private WebElement lostPassword;

    @FindBy(xpath = "//section[@class='account-header-content']//div[@class='account_section_header']")
    private WebElement accountSectionHeader;

    @FindBy(xpath = "//input[@id='user_login']")
    private WebElement lostPasswordEmail;

    @FindBy(xpath = "//button[@value='Resetează parola']")
    private WebElement sendLostPasswordEmail;

    @FindBy(xpath = "//div[@class='woocommerce-message']")
    private WebElement lostPasswordEmailSuccess;

    @FindBy(xpath = "//div[@class='woocommerce-notices-wrapper']//ul[@class='woocommerce-error']")
    private WebElement lostPasswordEmailError;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void goToLoginPage() throws InterruptedException {
        profileIcon.click();
        GenericUtils.sleep(500);
    }

    public void login(String username, String password) {
        DOMUtils.populateInputViaActions(driver, usernameInput, username);
        DOMUtils.populateInputViaActions(driver, passwordInput, password);
        DOMUtils.clickElementViaActions(driver, loginButton);
    }

    public void redirectToMyAccountPage() {
        wait.until(ExpectedConditions.elementToBeClickable(myAccountHeader));
    }

    public boolean isMyAccountPageLoaded() {
        return myAccountTitle.isDisplayed();
    }

    public String getMyAccountTitle() {
        return myAccountTitle.getText();
    }

    public void renderError() {
        wait.until(ExpectedConditions.visibilityOf(formError));
    }

    public boolean isFormErrorVisible() {
        return isElementDisplayed(formError);
    }

    public String getFormErrorMessage() {
        return formError.getText();
    }

    public void clickPreviewPassword() {
        DOMUtils.clickElementViaActions(driver, previewPassword);
    }

    public boolean isPreviewPasswordVisible() {
        return isElementDisplayed(previewPassword);
    }

    public String getPreviewPasswordCssClass() {
        return previewPassword.getDomAttribute("class");
    }

    public void clickLostPassword() {
        DOMUtils.clickElementViaActions(driver, lostPassword);
    }

    public void scrollToResetPasswordButton() {
        DOMUtils.scrollToElementViaJavaScript(driver, accountSectionHeader);
    }

    public void sendLostPasswordEmail(String email) {
        DOMUtils.populateInputViaActions(driver, lostPasswordEmail, email);
        DOMUtils.clickElementViaActions(driver, sendLostPasswordEmail);
    }

    public boolean isLostPasswordEmailSuccessMessageDisplayed() {
        return isElementDisplayed(lostPasswordEmailSuccess);
    }

    public boolean isLostPasswordEmailErrorMessageDisplayed() {
        return isElementDisplayed(lostPasswordEmailError);
    }

    public String getLostPasswordEmailSuccessMessage() {
        return lostPasswordEmailSuccess.getText();
    }

    public String getLostPasswordEmailErrorMessage() {
        return lostPasswordEmailError.getText();
    }

    public void renderLostPasswordSuccessEmailMessage() {
        wait.until(ExpectedConditions.visibilityOf(lostPasswordEmailSuccess));
    }

    public void renderLostPasswordErrorEmailMessage() {
        wait.until(ExpectedConditions.visibilityOf(lostPasswordEmailError));
    }
}
