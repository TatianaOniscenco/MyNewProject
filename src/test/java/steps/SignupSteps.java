package steps;

import com.microsoft.playwright.Page;
import hooks.Hooks;
import io.cucumber.java.en.*;
import net.datafaker.Faker;
import pages.LoginPage;
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

        signupPage.enterPassword(password);
        signupPage.enterFirstName(faker.name().firstName());
        signupPage.enterLastName(faker.name().lastName());
        signupPage.enterAddress(faker.address().streetAddress());
        signupPage.selectCountry(selectedCountry);
        signupPage.enterState(faker.address().state());
        signupPage.enterCity(faker.address().city());
        signupPage.enterZipCode(faker.address().zipCode());
        signupPage.enterMobileNumber(faker.phoneNumber().phoneNumber());

        Hooks.setPassword(password);
    }

    @And("User submits the signup form clicking on Create Account button")
    public void submitSignupForm() {
        signupPage.clickCreateAccountButton();
    }


    @And("user is redirected to Signup page")
    public void isRedirectedToSignupPage() {
        assertTrue(signupPage.isEnterAccountInfoVisible());
    }
}