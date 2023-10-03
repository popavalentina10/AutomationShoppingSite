package PageObjects;

import Utils.DOMUtils;
import Utils.GenericUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class WishlistPage extends BasePage {
    @FindBy(xpath = "//div[@class='site-header-holder']//li[contains(@id, 'menu-item')]//a[contains(@href, 'sucuri-medicinale')]")
    private WebElement medicinalJuices;

    @FindBy(xpath = "(//div[@class='yith-wcwl-add-button']//a)[1]")
    private WebElement addToWishListFromMenuPage;

    @FindBy(xpath = "(//div[@class='pdp_form_container']//div[@class='pdp_form_cart ']//div[@class='ar__pdp_wishlist'])[1]")
    private WebElement addToWishListFromItemPage;

    @FindBy(xpath = "//div[@class='woocommerce-notices-wrapper']//ul[@class='woocommerce-error']")
    private WebElement loginRequiredError;

    @FindBy(xpath = "//input[@id='username']")
    private WebElement usernameInput;

    @FindBy(xpath = "//input[@id='password']")
    private WebElement passwordInput;

    @FindBy(xpath = "//button[@name='login']")
    private WebElement loginButton;

    @FindBy(xpath = "//div[@class='site-header-holder']//div[@class='user-icon wishlist-icon']//a")
    private WebElement favoriteIcon;

    @FindBy(xpath = "//tbody[@class='wishlist-items-wrapper']")
    private WebElement favoriteProductsTable;

    @FindBy(xpath = "//div[@class='user-icon profile-icon']")
    private WebElement profileIcon;

    @FindBy(xpath = "//div[@id='my-account-menu']")
    private WebElement myAccountHeader;

    @FindBy(xpath = "//div[contains(@class, 'user_details_title')]")
    private WebElement myAccountTitle;

    @FindBy(xpath = "(//div[@class='product-title']/ancestor::div[@class='recomended-infos']/ancestor::div[@class='each-recomended-product']//div[@class='recomended-image'])[1]")
    private WebElement productElement;

    public WishlistPage(WebDriver driver) {
        super(driver);
    }

    public void goToMenuPage() throws InterruptedException {
        medicinalJuices.click();
        GenericUtils.sleep(500);
    }

    public void goToLoginPage() throws InterruptedException {
        profileIcon.click();
        GenericUtils.sleep(500);
    }

    public void redirectToMyAccountPage() {
        wait.until(ExpectedConditions.elementToBeClickable(myAccountHeader));
    }

    public void clickOnAddToWishListFromMenuPage() {
        DOMUtils.clickElementViaJavaScript(driver, addToWishListFromMenuPage);
    }

    public void clickOnAddToWishListFromItemPage() {
        wait.until(ExpectedConditions.visibilityOf(addToWishListFromItemPage));
        DOMUtils.clickElementViaJavaScript(driver, addToWishListFromItemPage);
    }

    public void renderIsLoginRequiredError() {
        wait.until(ExpectedConditions.visibilityOf(loginRequiredError));
    }

    public boolean isAddToWishListButtonDisplayed() {
        try {
            return isElementDisplayed(addToWishListFromMenuPage);
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public String getLoginRequiredErrorMessage() {
        return loginRequiredError.getText();
    }

    public void login(String username, String password) {
        DOMUtils.scrollToElementViaJavaScript(driver, loginButton);
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
        DOMUtils.clickElementViaJavaScript(driver, loginButton);
    }

    public void clickFavoriteProducts() {
        wait.until(ExpectedConditions.visibilityOf(favoriteIcon));
        DOMUtils.clickElementViaJavaScript(driver, favoriteIcon);
        DOMUtils.clickElementViaActions(driver, favoriteIcon);
    }

    public boolean isProductAdded() {
        wait.until(ExpectedConditions.visibilityOf(favoriteProductsTable));

        return !favoriteProductsTable
                .findElement(By.xpath("//tbody[@class='wishlist-items-wrapper']//td[@class='wishlist-empty']"))
                .isDisplayed();
    }

    public boolean isMyAccountPageLoaded() {
        return myAccountTitle.isDisplayed();
    }

    public String getMyAccountTitle() {
        return myAccountTitle.getText();
    }

    public boolean isLoginRequiredErrorDisplayed() {
        return isElementDisplayed(loginRequiredError);
    }

    public void clickOnProductElement() {
        productElement.click();
    }
}
