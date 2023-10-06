package Tests;

import PageObjects.AddToCartPage;
import Utils.GenericUtils;
import org.junit.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AddToCartTest extends BaseTest {
    AddToCartPage addToCartPage;

    @BeforeMethod
    private void goToAddToCartPage() throws InterruptedException {
        addToCartPage = new AddToCartPage(driver);
        addToCartPage.goToMenuPage();
    }

    @Test
    public void testProductIsVisibleInCart() {
        addToCartPage.clickOnAddToCartListFromMenuPage();
        addToCartPage.renderCartModal();

        Assert.assertNotEquals("", addToCartPage.getCartProductListText());
    }

    @Test
    public void testIncreasingNoOfItems() throws InterruptedException {
        addToCartPage.clickOnAddToCartListFromMenuPage();
        addToCartPage.renderCartModal();
        addToCartPage.clickButtonPlus();
        GenericUtils.sleep(2500);

        Assert.assertEquals("2 produse în coș", addToCartPage.getNoOfProductInCart());
    }

    @Test
    public void testDecreasingNoOfItems() throws InterruptedException {
        addToCartPage.clickOnAddToCartListFromMenuPage();
        addToCartPage.renderCartModal();
        addToCartPage.clickButtonMinus();
        GenericUtils.sleep(2500);

        Assert.assertEquals("0 produse în coș", addToCartPage.getNoOfProductInCart());
    }

    @Test
    public void testRemoveAllItems() throws InterruptedException {
        addToCartPage.clickOnAddToCartListFromMenuPage();
        addToCartPage.renderCartModal();
        addToCartPage.clickButtonRemove();
        GenericUtils.sleep(2500);

        Assert.assertEquals("0 produse în coș", addToCartPage.getNoOfProductInCart());
    }

    @Test
    public void testCheckoutWithErrors() {
        addToCartPage.clickOnAddToCartListFromMenuPage();
        addToCartPage.renderCartModal();
        addToCartPage.clickButtonCheckout();
        addToCartPage.clickButtonPlaceOrder();
        addToCartPage.renderOrderError();

        Assert.assertTrue("Checkout performed with invalid information", addToCartPage.isOrderErrorVisible());
    }
}