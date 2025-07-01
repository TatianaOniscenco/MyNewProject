package steps;

import com.microsoft.playwright.Page;
import hooks.Hooks;
import io.cucumber.java.en.*;
import net.datafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.SignupPage;
import enums.SignupCountry;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SignupSteps {
    private static final Logger log = LoggerFactory.getLogger(SignupSteps.class);

    Page page = Hooks.getPage();
    SignupPage signupPage = new SignupPage(page);
    Faker faker = new Faker();

    @When("User enters valid account information")
    public void enterValidUserInformation() {
        String password = faker.internet().password();
        SignupCountry selectedCountry = SignupCountry.getRandom();

        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String address = faker.address().streetAddress();
        String state = faker.address().state();
        String city = faker.address().city();
        String zipCode = faker.address().zipCode();
        String phoneNumber = faker.phoneNumber().phoneNumber();

        log.info("[DATA] First Name: {}", firstName);
        log.info("[DATA] Last Name: {}", lastName);
        log.info("[DATA] Address: {}", address);
        log.info("[DATA] Country: {}", selectedCountry.getLabel());
        log.info("[DATA] State: {}", state);
        log.info("[DATA] City: {}", city);
        log.info("[DATA] Zip Code: {}", zipCode);
        log.info("[DATA] Phone Number: {}", phoneNumber);
        log.info("[DATA] Generated Password: {}", password);

        signupPage.enterPassword(password);
        signupPage.enterFirstName(firstName);
        signupPage.enterLastName(lastName);
        signupPage.enterAddress(address);
        signupPage.selectCountry(selectedCountry.getLabel());
        signupPage.enterState(state);
        signupPage.enterCity(city);
        signupPage.enterZipCode(zipCode);
        signupPage.enterMobileNumber(phoneNumber);

        Hooks.setPassword(password);
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