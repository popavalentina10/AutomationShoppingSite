package PageObjects;

import Utils.DOMUtils;
import Utils.GenericUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AddToCartPage extends BasePage {

    @FindBy(xpath = "//div[@class='site-header-holder']//li[contains(@id, 'menu-item')]//a[contains(@href, 'sucuri-medicinale')]")
    private WebElement medicinalJuices;

    @FindBy(xpath = "(//div[contains(@class,'add_to_cart_button')])[1]")
    private WebElement addToCartFromListButton;

    @FindBy(xpath = "//div[@class='minicart_modal_item']")
    private WebElement addToCartModal;

    @FindBy(xpath = "//div[@class='mini_cart_items']//ul[contains(@class,'product_list_widget')]")
    private WebElement cartProductsList;

    @FindBy(xpath = "//li[contains(@class,'cart_item_grupat')]//div[@class='js-qty']//button[contains(@class,'js-qty__adjust--plus')]")
    private WebElement buttonPlus;

    @FindBy(xpath = "//div[@class='minicart_header']//div[@class='mesaj-produse-cos']")
    private WebElement noOfProductsInCart;

    @FindBy(xpath = "//li[contains(@class,'cart_item_grupat')]//div[@class='js-qty']//button[contains(@class,'js-qty__adjust--minus')]")
    private WebElement buttonMinus;

    @FindBy(xpath = "//div[@class='cart_quantity_container']//div[@class='minicart_form_coll delete']//span[contains(@class,'remove_from_cart_button_grupat')]")
    private WebElement buttonRemove;

    @FindBy(xpath = "//div[@class='minicart_body']//div[@class='mini_cart_buttons']//a[contains(@class,'buton checkout')]")
    private WebElement buttonCheckout;

    @FindBy(xpath = "//tr[@class='order_place_order']//div[contains(@class,'place_order_button_right')]")
    private WebElement buttonPlaceOrder;

    @FindBy(xpath = "//div[@class='woocommerce-NoticeGroup woocommerce-NoticeGroup-checkout']//ul[contains(@class,'woocommerce-error')]")
    private WebElement orderError;


    public AddToCartPage(WebDriver driver) {
        super(driver);
    }

    public void goToMenuPage() throws InterruptedException {
        medicinalJuices.click();
        GenericUtils.sleep(500);
    }

    public void clickOnAddToCartListFromMenuPage() {
        DOMUtils.clickElementViaJavaScript(driver, addToCartFromListButton);
    }

    public void renderCartModal() {
        wait.until(ExpectedConditions.visibilityOf(addToCartModal));
    }

    public String getCartProductListText() {

        return cartProductsList.getText();
    }

    public void clickButtonPlus() {
        DOMUtils.clickElementViaJavaScript(driver, buttonPlus);
    }

    public String getNoOfProductInCart() {

        return noOfProductsInCart.getText();
    }

    public void clickButtonMinus() {
        DOMUtils.clickElementViaJavaScript(driver, buttonMinus);
    }

    public void clickButtonRemove() {
        wait.until(ExpectedConditions.visibilityOf(buttonRemove));
        DOMUtils.clickElementViaActions(driver, buttonRemove);
    }

    public void clickButtonCheckout() {
        DOMUtils.clickElementViaJavaScript(driver, buttonCheckout);
    }

    public void clickButtonPlaceOrder() {
        DOMUtils.clickElementViaJavaScript(driver, buttonPlaceOrder);
    }

    public void renderOrderError() {
        wait.until(ExpectedConditions.visibilityOf(orderError));
    }

    public boolean isOrderErrorVisible() {
        return orderError.isDisplayed();
    }

}