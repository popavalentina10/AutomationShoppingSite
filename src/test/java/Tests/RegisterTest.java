package Tests;

import PageObjects.RegisterPage;
import Utils.GenericUtils;
import org.junit.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class RegisterTest extends BaseTest {
    RegisterPage registerPage;

    @BeforeMethod
    private void preRegister() throws InterruptedException {
        registerPage = new RegisterPage(driver);
        registerPage.goToRegisterPage();
    }

    @Test
    public void testMissingUsername() throws InterruptedException {
        registerPage.register("", "vale.po@yahoo.com");
        registerPage.validateForm();
        registerPage.submitForm();
        registerPage.renderSubmitFormError();

        Assert.assertTrue("Error message is not displayed when usernamed is missing", registerPage.isSubmitFormErrorVisible());
        Assert.assertEquals("Eroare: Te rog introdu un nume utilizator cu cont valid.", registerPage.getSubmitFormErrorMessage());
    }

    @Test
    public void testMissingEmail() throws InterruptedException {
        registerPage.register("NN", "");
        registerPage.validateForm();

        Assert.assertTrue("Error message is not displayed when email is missing", registerPage.isValidateFormErrorVisible());
        Assert.assertEquals("Introduceți o adresă de email validă.", registerPage.getValidateFormErrorMessage());
    }


    @Test
    public void testMissingCredentials() throws Exception {
        registerPage.register("", "");
        registerPage.validateForm();

        Assert.assertTrue("Error message is not displayed when credentials is missing", registerPage.isValidateFormErrorVisible());
        Assert.assertEquals("Introduceți o adresă de email validă.", registerPage.getValidateFormErrorMessage());
    }

    @Test
    public void testInvalidEmail() throws InterruptedException {
        registerPage.register("", "test");
        registerPage.validateForm();

        Assert.assertTrue("Error message is not displayed when is invalid email", registerPage.isValidateFormErrorVisible());
        Assert.assertEquals("Introduceți o adresă de email validă.", registerPage.getValidateFormErrorMessage());
    }

    @Test
    public void testInvalidUsername() throws InterruptedException {
        registerPage.register("  ", "vale.p999999@yahoo.com");
        registerPage.validateForm();
        registerPage.submitForm();
        registerPage.renderSubmitFormError();

        Assert.assertTrue("Error message is not displayed when username is invalid", registerPage.isSubmitFormErrorVisible());
        Assert.assertEquals("Eroare: Te rog introdu un nume utilizator cu cont valid.", registerPage.getSubmitFormErrorMessage());
    }


    @Test
    public void testUncheckTermsAndConditions() throws InterruptedException {
        registerPage.register("sample", "sample@yahoo.com");
        registerPage.uncheckTermsCheckbox();
        registerPage.validateForm();
        GenericUtils.refreshPage(driver);
        registerPage.uncheckTermsCheckbox();
        registerPage.submitForm();
        registerPage.renderSubmitFormError();


        Assert.assertTrue("Error message is not displayed when terms is not checked", registerPage.isSubmitFormErrorVisible());
        Assert.assertEquals("Eroare: Te rog citește și acceptă termenii și condițiile pentru a continua.", registerPage.getSubmitFormErrorMessage());
    }


    @Test
    public void testCreateAnotherAccountWithSameEmail() throws InterruptedException {
        registerPage.register("SameEmail", "popa_valentina10@yahoo.com");
        registerPage.validateForm();

        Assert.assertTrue("Error message is not displayed when is create another account with same email.", registerPage.isValidateFormErrorVisible());
        Assert.assertEquals("Este înregistrat deja un cont cu adresa ta de email.", registerPage.getValidateFormErrorMessage());
    }

    @Test
    public void testCreateAccount() throws InterruptedException {
        long timestamp = GenericUtils.generateTimestamp();

        registerPage.register("validAccount" + timestamp, timestamp + "popa_valentina10@yahoo.com");
        registerPage.validateForm();
        GenericUtils.sleep(2500);
        registerPage.submitForm();
        registerPage.renderLoginSuccessPage();

        Assert.assertTrue("Error creating account.", registerPage.isMyAccountDetailsVisible());
    }
}