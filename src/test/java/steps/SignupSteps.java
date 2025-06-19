package steps;

import com.microsoft.playwright.Page;
import hooks.Hooks;
import io.cucumber.java.en.*;
import net.datafaker.Faker;
import pages.SignupPage;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SignupSteps {
    Page page = Hooks.getPage();
    SignupPage signupPage = new SignupPage(page);
    Faker faker = new Faker();

    @When("User enters valid account information")
    public void enterValidUserInformation() {
        String password = faker.internet().password();
        List<String> countries = List.of("India", "United States", "Canada", "Australia", "Israel", "New Zealand", "Singapore");
        String selectedCountry = countries.get(new Random().nextInt(countries.size()));

        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String address = faker.address().streetAddress();
        String state = faker.address().state();
        String city = faker.address().city();
        String zipCode = faker.address().zipCode();
        String phoneNumber = faker.phoneNumber().phoneNumber();

        Hooks.logToFile("[DATA] First Name: " + firstName);
        Hooks.logToFile("[DATA] Last Name: " + lastName);
        Hooks.logToFile("[DATA] Address: " + address);
        Hooks.logToFile("[DATA] Country: " + selectedCountry);
        Hooks.logToFile("[DATA] State: " + state);
        Hooks.logToFile("[DATA] City: " + city);
        Hooks.logToFile("[DATA] Zip Code: " + zipCode);
        Hooks.logToFile("[DATA] Phone Number: " + phoneNumber);
        Hooks.logToFile("[DATA] Generated Password: " + password);

        signupPage.enterPassword(password);
        signupPage.enterFirstName(firstName);
        signupPage.enterLastName(lastName);
        signupPage.enterAddress(address);
        signupPage.selectCountry(selectedCountry);
        signupPage.enterState(state);
        signupPage.enterCity(city);
        signupPage.enterZipCode(zipCode);
        signupPage.enterMobileNumber(phoneNumber);

        Hooks.setPassword(password);
    }

    @And("User submits the signup form clicking on Create Account button")
    public void submitSignupForm() {
        Hooks.logToFile("[ACTION] Clicking 'Create Account' button");
        signupPage.clickCreateAccountButton();
    }

    @And("user is redirected to Signup page")
    public void isRedirectedToSignupPage() {
        Hooks.logToFile("[ASSERT] Checking visibility of 'Enter Account Information' section");
        assertTrue(signupPage.isEnterAccountInfoVisible());
    }
}