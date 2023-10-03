package Tests;

import ObjectModels.LoginModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.utils.BrowserUtils;
import core.utils.ConfigUtils;
import core.utils.ConstantUtils;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class BaseTest {
    public WebDriver driver;
    String baseUrl;

    @BeforeClass
    public void setUp() {
        baseUrl = ConfigUtils.getGenericElement(ConstantUtils.CONFIG_FILE, "hostname");
    }

    public void setUpDriver(String browserName) {
        String browser = browserName;
        if (browser.isEmpty()) {
            browser = ConfigUtils.getGenericElement(ConstantUtils.CONFIG_FILE, "browser");
        }
        System.out.println("Set up webdriver for browser:" + browser);
        driver = BrowserUtils.getBrowser(browser);
    }

    @BeforeMethod
    public void startBrowser() {
        String browserName = ConfigUtils.getGenericElement(ConstantUtils.CONFIG_FILE, "browser");
        setUpDriver(browserName);
        driver.get(baseUrl);
    }

    @AfterMethod(alwaysRun = true)
    public void cleanUp() {
        if (driver != null) {
            driver.quit();
        }
    }

    @DataProvider(name = "jsonDataProvider")
    public Iterator<Object[]> jsonDpCollection() throws IOException {
        Collection<Object[]> dp = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("src\\test\\resources\\Data\\login.json");

        LoginModel[] lms = objectMapper.readValue(file, LoginModel[].class);

        for (LoginModel lm : lms)
            dp.add(new Object[]{lm});

        return dp.iterator();
    }
}
