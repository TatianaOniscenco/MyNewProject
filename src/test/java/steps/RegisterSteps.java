package steps;

import com.microsoft.playwright.*;
import io.cucumber.java.en.*;
import net.datafaker.Faker;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegisterSteps {
    Playwright playwright;
    Browser browser;
    BrowserContext context;
    Page page;
    Faker faker = new Faker();

    @Given("I am on the signup page")
    public void i_am_on_the_signup_page() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        context = browser.newContext();
        page = context.newPage();
        page.navigate("https://www.automationexercise.com");
        page.locator("text= Signup / Login").click();
    }

    @When("I enter valid user information")
    public void i_enter_valid_user_information() {
        page.getByTestId("signup-name").fill(faker.name().fullName());
        page.getByTestId("signup-email").fill(faker.internet().emailAddress());
        page.getByTestId("signup-button").click();

        assertTrue(page.locator("text=Enter Account Information").isVisible());

        List<String> countries = List.of("India", "United States", "Canada", "Australia", "Israel", "New Zealand", "Singapore");
        String selectedCountry = countries.get(new Random().nextInt(countries.size()));

        page.getByTestId("password").fill(faker.internet().password());
        page.getByTestId("first_name").fill(faker.name().firstName());
        page.getByTestId("last_name").fill(faker.name().lastName());
        page.getByTestId("address").fill(faker.address().streetAddress());
        page.getByTestId("country").selectOption(selectedCountry);
        page.getByTestId("state").fill(faker.address().state());
        page.getByTestId("city").fill(faker.address().city());
        page.getByTestId("zipcode").fill(faker.address().zipCode());
        page.getByTestId("mobile_number").fill(faker.phoneNumber().phoneNumber());
    }

    @And("I submit the registration form")
    public void i_submit_the_registration_form() {
        page.getByTestId("create-account").click();
    }

    @Then("I should see the {string} message")
    public void i_should_see_the_message(String message) {
        assertTrue(page.locator("text=" + message).isVisible());
        context.close();
        browser.close();
        playwright.close();
    }
}
