package PageObjects;

import Utils.DOMUtils;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SearchPage extends BasePage {
    @FindBy(xpath = "//div[@class='site-header-holder']//div[@class='header_search_container']//input[@id= 'top-search']")
    private WebElement searchElement;

    @FindBy(xpath = "//div[@class='site-header-holder']//div[@class='predictive_search_container']")
    private WebElement autocompleteResultElement;

    @FindBy(xpath = "(//div[@class='predictive_search_container']//li[@class='predictive_search_result'])[1]")
    private WebElement autocompleteResultItem;

    @FindBy(xpath = "//div[@class='site-header-holder']//form[contains(@class,'searchHistoryReady')]//div//button")
    private WebElement submitFound;

    @FindBy(xpath = "(//div[@class='search-results-content']//div[@class='blog-post search-post product-search-item'])[1]")
    private WebElement resultSubmit;

    @FindBy(xpath = "//div[@class='search--content']//div[@class='word-notfound']//h2")
    private WebElement resultNotSubmit;

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    public void populateSearchInput(String text) {
        DOMUtils.populateInputViaActions(driver, searchElement, text);
    }

    public boolean isAutocompleteResultElementDisplayed() {
        try {
            return isElementDisplayed(autocompleteResultElement);
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void renderAutocompleteResultElement() {
        wait.until(ExpectedConditions.visibilityOf(autocompleteResultElement));
    }

    public String getAutocompleteResultElementMessage() {
        return autocompleteResultElement.getText();
    }

    public boolean isAutocompleteResultItemDisplayed() {
        try {
            return isElementDisplayed(autocompleteResultItem);
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void submitSearch() {
        DOMUtils.clickElementViaJavaScript(driver, submitFound);
    }

    public boolean resultSubmitItemDisplayed() {
        try {
            return isElementDisplayed(resultSubmit);
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean resultNotSubmitItemDisplayed() {
        try {
            return isElementDisplayed(resultNotSubmit);
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public String getResultNotSubmitItemMessage() {
        return resultNotSubmit.getText();
    }
}