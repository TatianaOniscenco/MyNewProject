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
    LoginPage loginPage = new LoginPage(page);
    SignupPage signupPage = new SignupPage(page);
    Faker faker = new Faker();

    @When("User enters valid user information")
    public void enterValidUserInformation() {
        loginPage.enterSignupName(faker.name().fullName());
        loginPage.enterSignupEmail(faker.internet().emailAddress());
        loginPage.clickSignupButton();

        assertTrue(signupPage.isEnterAccountInfoVisible());

        List<String> countries = List.of("India", "United States", "Canada", "Australia", "Israel", "New Zealand", "Singapore");
        String selectedCountry = countries.get(new Random().nextInt(countries.size()));

        signupPage.enterPassword(faker.internet().password());
        signupPage.enterFirstName(faker.name().firstName());
        signupPage.enterLastName(faker.name().lastName());
        signupPage.enterAddress(faker.address().streetAddress());
        signupPage.selectCountry(selectedCountry);
        signupPage.enterState(faker.address().state());
        signupPage.enterCity(faker.address().city());
        signupPage.enterZipCode(faker.address().zipCode());
        signupPage.enterMobileNumber(faker.phoneNumber().phoneNumber());
    }

    @And("User submits the signup form")
    public void submitSignupForm() {
        signupPage.clickCreateAccountButton();
    }
}