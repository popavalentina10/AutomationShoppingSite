package Tests;

import ObjectModels.LoginModel;
import PageObjects.LoginPage;
import org.junit.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {
    LoginPage loginPage;

    @BeforeMethod
    private void preLogin() throws InterruptedException {
        loginPage = new LoginPage(driver);
        loginPage.goToLoginPage();
    }

    @Test(dataProvider = "jsonDataProvider")
    public void testValidCredentials(LoginModel loginModel) {
        loginPage.login(loginModel.getAccount().getUsername(), loginModel.getAccount().getPassword());
        loginPage.redirectToMyAccountPage();

        Assert.assertTrue("Login failed with valid credentials", loginPage.isMyAccountPageLoaded());
        Assert.assertEquals("Detalii contul meu", loginPage.getMyAccountTitle());
    }

    @Test
    public void testMissingUsername() {
        loginPage.login("", "Sample password");

        Assert.assertTrue("Error message is not displayed if username is missing", loginPage.isFormErrorVisible());
        Assert.assertEquals("Introduceti un nume de utilizator sau un email.", loginPage.getFormErrorMessage());
    }

    @Test
    public void testMissingPassword() {
        loginPage.login("popa_valentina10@yahoo.com", "");

        Assert.assertTrue("Error message is not displayed if password is missing", loginPage.isFormErrorVisible());
        Assert.assertEquals("Parola trebuie sa aiba cel putin 7 caractere.", loginPage.getFormErrorMessage());
    }

    @Test
    public void testInvalidPassword() {
        loginPage.login("popa_valentina10@yahoo.com", "sample password");
        loginPage.renderError();

        Assert.assertTrue("Error message is not displayed if password is invalid", loginPage.isFormErrorVisible());
        Assert.assertEquals("Parolă sau username greșite.", loginPage.getFormErrorMessage());
    }

    @Test
    public void testInvalidUsername() {
        loginPage.login("popa_valentina10@yahoo.", "Sample password");
        loginPage.renderError();

        Assert.assertTrue("Error message is not displayed if username is invalid", loginPage.isFormErrorVisible());
        Assert.assertEquals("Parolă sau username greșite.", loginPage.getFormErrorMessage());
    }

    @Test
    public void testIncompletePassword() {
        loginPage.login("popa_valentina10@yahoo.", "12345");
        loginPage.renderError();

        Assert.assertTrue("Error message is not displayed if password is incomplete", loginPage.isFormErrorVisible());
        Assert.assertEquals("Parola trebuie sa aiba cel putin 7 caractere.", loginPage.getFormErrorMessage());
    }

    @Test
    public void testShowPassword() {
        loginPage.login("", "sample password");
        loginPage.clickPreviewPassword();

        Assert.assertTrue("Preview password is not visible", loginPage.isPreviewPasswordVisible());
        Assert.assertEquals("show-password-input-pass", loginPage.getPreviewPasswordCssClass());
    }

    @Test
    public void testLostPasswordWithValidEmail() {
        loginPage.login("popa_valentina10@yahoo.com", "lost_password");
        loginPage.clickLostPassword();
        loginPage.scrollToResetPasswordButton();
        loginPage.sendLostPasswordEmail("popa_valentina10@yahoo.com");
        loginPage.renderLostPasswordSuccessEmailMessage();

        Assert.assertTrue("The reset password email has not been sent", loginPage.isLostPasswordEmailSuccessMessageDisplayed());
        Assert.assertEquals("A fost trimis un email pentru resetarea parolei.", loginPage.getLostPasswordEmailSuccessMessage());
    }

    @Test
    public void testLostPasswordWithInvalidEmail() {
        loginPage.login("popa_valentina10@yahoo.com", "lost_password");
        loginPage.clickLostPassword();
        loginPage.scrollToResetPasswordButton();
        loginPage.sendLostPasswordEmail("popa_valentina10@yahoo.a");
        loginPage.renderLostPasswordErrorEmailMessage();

        Assert.assertTrue("The reset password email has been sent", loginPage.isLostPasswordEmailErrorMessageDisplayed());
        Assert.assertEquals("Nume utilizator sau email invalid.", loginPage.getLostPasswordEmailErrorMessage());
    }
}
