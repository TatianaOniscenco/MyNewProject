package steps;

import com.microsoft.playwright.*;
import io.cucumber.java.en.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginSteps {
    Playwright playwright;
    Browser browser;
    BrowserContext context;
    Page page;

    @Given("I am on the login page")
    public void i_am_on_the_login_page() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        context = browser.newContext();
        page = context.newPage();
        page.navigate("https://www.automationexercise.com");
        page.locator("text= Signup / Login").click();
    }

    @When("I enter incorrect credentials")
    public void i_enter_incorrect_credentials() {
        page.getByTestId("login-email").fill("wronguser@example.com");
        page.getByTestId("login-password").fill("wrongpassword");
    }

    @And("I submit the login form")
    public void i_submit_the_login_form() {
        page.getByTestId("login-button").click();
    }

    @Then("I should see the {string} message")
    public void i_should_see_the_message(String message) {
        assertTrue(page.locator("text=" + message).isVisible());
        context.close();
        browser.close();
        playwright.close();
    }
}
