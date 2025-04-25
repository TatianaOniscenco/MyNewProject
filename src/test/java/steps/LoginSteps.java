package steps;

import com.microsoft.playwright.Page;
import hooks.Hooks;
import io.cucumber.java.en.*;

public class LoginSteps {
    Page page = Hooks.getPage(); // ✅ Use shared page set by Hooks

    @Given("User is on the login page")
    public void user_is_on_login_page() {
        page.navigate("https://www.automationexercise.com");
        page.waitForLoadState(); // ⏳ Wait for full load
        page.locator("text= Signup / Login").click();
        page.waitForSelector("[data-qa='login-email']"); // ✅ Wait for login field
    }

    @When("User enters invalid {string} and {string} credentials")
    public void user_enters_incorrect_credentials(String login, String password) {
        page.getByTestId("login-email").fill(login);
        page.getByTestId("login-password").fill(password);
    }

    @And("User submits the login form")
    public void user_submits_the_login_form() {
        page.getByTestId("login-button").click();
    }

}


