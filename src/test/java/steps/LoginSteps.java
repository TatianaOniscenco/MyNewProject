package steps;

import com.microsoft.playwright.Page;
import hooks.Hooks;
import io.cucumber.java.en.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginSteps {
    Page page = Hooks.getPage(); // ✅ Use shared page set by Hooks

    @Given("User is on the login page")
    public void user_is_on_login_page() {
        page.navigate("https://www.automationexercise.com");
        page.waitForLoadState(); // ⏳ Wait for full load
        page.locator("text= Signup / Login").click();
        page.waitForSelector("[data-qa='login-email']"); // ✅ Wait for login field
    }

    @When("User enters incorrect credentials")
    public void user_enters_incorrect_credentials() {
        page.getByTestId("login-email").fill("newuser@mail.com");
        page.getByTestId("login-password").fill("12345");
    }

    @And("User submits the login form")
    public void user_submits_the_login_form() {
        page.getByTestId("login-button").click();
    }


}


