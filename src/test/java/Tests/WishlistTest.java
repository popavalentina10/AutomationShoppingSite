package Tests;

import PageObjects.WishlistPage;
import org.junit.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class WishlistTest extends BaseTest {
    WishlistPage wishlistPage;

    @BeforeMethod
    private void goToMenuPage() throws InterruptedException {
        wishlistPage = new WishlistPage(driver);
        wishlistPage.goToMenuPage();
    }

    @Test
    public void testAddItemWithoutLogin() {
        wishlistPage.clickOnAddToWishListFromMenuPage();
        wishlistPage.renderIsLoginRequiredError();

        Assert.assertTrue("Error message is not displayed when user is not logged in", wishlistPage.isLoginRequiredErrorDisplayed());
        Assert.assertEquals("Please, log in to use the wishlist features", wishlistPage.getLoginRequiredErrorMessage());
    }

    @Test
    public void testLoginWithSuccessAndItemNotAdded() {
        wishlistPage.clickOnAddToWishListFromMenuPage();
        wishlistPage.renderIsLoginRequiredError();
        wishlistPage.login("popa_valentina10@yahoo.com", "Letmein2020!VP");
        wishlistPage.clickFavoriteProducts();

        Assert.assertTrue("Product has not been added", wishlistPage.isProductAdded());
    }

    @Test
    public void testLoginWithSuccessButWishIconIsMissing() throws InterruptedException {
        wishlistPage.goToLoginPage();
        wishlistPage.login("popa_valentina10@yahoo.com", "Letmein2020!VP");
        wishlistPage.redirectToMyAccountPage();

        Assert.assertTrue("Login failed with valid credentials", wishlistPage.isMyAccountPageLoaded());
        Assert.assertEquals("Detalii contul meu", wishlistPage.getMyAccountTitle());

        wishlistPage.goToMenuPage();

        Assert.assertTrue("Product has not been added because add to wish list button is missing", wishlistPage.isAddToWishListButtonDisplayed());

        wishlistPage.clickOnAddToWishListFromMenuPage();
        wishlistPage.clickFavoriteProducts();

        Assert.assertTrue("Product has not been added", wishlistPage.isProductAdded());
    }

    @Test
    public void testLoginWithSuccessAndAddItemToWishList() throws InterruptedException {
        wishlistPage.goToLoginPage();
        wishlistPage.login("popa_valentina10@yahoo.com", "Letmein2020!VP");
        wishlistPage.redirectToMyAccountPage();

        Assert.assertTrue("Login failed with valid credentials", wishlistPage.isMyAccountPageLoaded());
        Assert.assertEquals("Detalii contul meu", wishlistPage.getMyAccountTitle());

        wishlistPage.goToMenuPage();
        wishlistPage.clickOnProductElement();
        wishlistPage.clickOnAddToWishListFromItemPage();
        wishlistPage.clickFavoriteProducts();

        Assert.assertTrue("Product has not been added", wishlistPage.isProductAdded());
    }
}
