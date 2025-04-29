package steps;

import com.microsoft.playwright.Page;
import hooks.Hooks;
import io.cucumber.java.en.*;
import pages.LoginPage;

public class LoginSteps {
    Page page = Hooks.getPage();
    LoginPage loginPage = new LoginPage(page);

    @When("User enters invalid {string} and {string} credentials")
    public void user_enters_incorrect_credentials(String login, String password) {
        loginPage.enterLoginEmail(login);
        loginPage.enterLoginPassword(password);
    }

    @And("User submits the login form")
    public void user_submits_the_login_form() {
        loginPage.clickLoginButton();
    }
}


