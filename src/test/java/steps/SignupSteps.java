package steps;

import com.microsoft.playwright.Page;
import context.UserContext;
import enums.SignupCountry;
import hooks.Hooks;
import io.cucumber.java.en.*;
import net.datafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.SignupPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SignupSteps {
    private static final Logger log = LoggerFactory.getLogger(SignupSteps.class);

    Page page = Hooks.getPage();
    SignupPage signupPage = new SignupPage(page);
    Faker faker = new Faker();

    @When("User enters valid account information")
    public void enterValidUserInformation() {
        UserContext user = Hooks.getUserContext();

        // generate data
        String password = faker.internet().password();
        String address = faker.address().streetAddress();
        String state = faker.address().state();
        String city = faker.address().city();
        String zipCode = faker.address().zipCode();
        String phoneNumber = faker.phoneNumber().phoneNumber();
        SignupCountry selectedCountry = SignupCountry.getRandom();

        // fill form
        signupPage.enterPassword(password);
        signupPage.enterFirstName(user.getFirstName());
        signupPage.enterLastName(user.getLastName());
        signupPage.enterAddress(address);
        signupPage.selectCountry(selectedCountry.getLabel());
        signupPage.enterState(state);
        signupPage.enterCity(city);
        signupPage.enterZipCode(zipCode);
        signupPage.enterMobileNumber(phoneNumber);

        // update context
        user.setPassword(password);
        user.setAddress(address);
        user.setCountry(selectedCountry.getLabel());
        user.setState(state);
        user.setCity(city);
        user.setZipCode(zipCode);
        user.setPhoneNumber(phoneNumber);
        Hooks.setUserContext(user);

        log.info("[DATA] Final UserContext: {}", user);
    }

    @And("User submits the signup form clicking on Create Account button")
    public void submitSignupForm() {
        log.info("[ACTION] Clicking 'Create Account' button");
        signupPage.clickCreateAccountButton();
    }

    @And("user is redirected to Signup page")
    public void isRedirectedToSignupPage() {
        log.info("[ASSERT] Checking visibility of 'Enter Account Information' section");
        assertTrue(signupPage.isEnterAccountInfoVisible());
    }
}