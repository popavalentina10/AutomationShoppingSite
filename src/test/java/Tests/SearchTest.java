package Tests;

import PageObjects.SearchPage;
import org.junit.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SearchTest extends BaseTest {
    SearchPage searchPage;

    @BeforeMethod
    private void goToSearchPage() {
        searchPage = new SearchPage(driver);
    }

    @Test
    public void testAutocompleteNotTriggered() {
        searchPage.populateSearchInput("as");

        Assert.assertFalse("Autocomplete has been triggerd", searchPage.isAutocompleteResultElementDisplayed());
    }

    @Test
    public void testAutocompleteNoResultFound(){
        searchPage.populateSearchInput("asd");
        searchPage.renderAutocompleteResultElement();

        Assert.assertTrue("Autocomplete returned results", searchPage.isAutocompleteResultElementDisplayed());
        Assert.assertEquals("Ne pare rău, nu am găsit niciun rezultat.",searchPage.getAutocompleteResultElementMessage());
    }

    @Test
    public void testAutocompleteResultFound(){
        searchPage.populateSearchInput("rodie");
        searchPage.renderAutocompleteResultElement();

        Assert.assertTrue("Autocomplete box is not displayed", searchPage.isAutocompleteResultElementDisplayed());
        Assert.assertTrue("Autocomplete did not return any results",searchPage.isAutocompleteResultItemDisplayed());
    }

    @Test
    public void testSubmitResultFound(){
        searchPage.populateSearchInput("suc de rodie");
        searchPage.submitSearch();

        Assert.assertTrue("Search did not return any results",searchPage.resultSubmitItemDisplayed());
    }

    @Test
    public void testSubmitResultNotFound() {
        searchPage.populateSearchInput("failed");
        searchPage.submitSearch();

        Assert.assertTrue("Search returned results", searchPage.resultNotSubmitItemDisplayed());
        Assert.assertEquals("Nu am găsit niciun rezultat pentru failed.", searchPage.getResultNotSubmitItemMessage());
    }
}