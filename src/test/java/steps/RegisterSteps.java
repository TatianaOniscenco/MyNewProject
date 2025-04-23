package steps;

import com.microsoft.playwright.Page;
import hooks.Hooks;
import io.cucumber.java.en.*;
import net.datafaker.Faker;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegisterSteps {
    Page page = Hooks.getPage(); // ✅ Use the one set in @Before
    Faker faker = new Faker();

    @Given("User is on the signup page")
    public void user_is_on_the_signup_page() {
        page.navigate("https://www.automationexercise.com");
        page.waitForLoadState(); // ✅ wait until full load
        page.locator("text= Signup / Login").click();
        page.waitForSelector("[data-qa='signup-name']"); // ✅ wait until field is ready
    }

    @When("User enters valid user information")
    public void user_enters_valid_user_information() {
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

    @And("User submits the registration form")
    public void user_submits_the_registration_form() {
        page.getByTestId("create-account").click();
    }
}