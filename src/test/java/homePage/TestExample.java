package homePage;

import com.microsoft.playwright.*;
import net.datafaker.Faker;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestExample {
    // Shared between all tests in this class.
    static Playwright playwright;
    static Browser browser;

    // New instance for each test method.
    BrowserContext context;
    Page page;

    Faker faker = new Faker();

    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
    }

    @AfterAll
    static void closeBrowser() {
        playwright.close();
    }

    @BeforeEach
    void createContextAndPage() {
        context = browser.newContext();
        page = context.newPage();
        playwright.selectors().setTestIdAttribute("data-qa");
    }

    @AfterEach
    void closeContext() {
        context.close();
    }

    @Test
    void loginUserWithIncorrectEmailAndPassword(){
        page.navigate("https://www.automationexercise.com");
        page.locator("text= Signup / Login").click();
        page.getByTestId("login-email").click();
        page.getByTestId("login-email").fill("newuser@mail.com");
        page.getByTestId("login-password").click();
        page.getByTestId("login-password").fill("12345");
        page.getByTestId("login-button").click();

        assertTrue(page.locator("text=Your email or password is incorrect!").isVisible());

    }

    @Test
    void registerUser(){

        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();

        List<String> countries = List.of("India", "United States", "Canada", "Australia", "Israel", "New Zealand", "Singapore");
        Random random = new Random();
        String selectedCountry = countries.get(random.nextInt(countries.size()));

        page.navigate("https://www.automationexercise.com");
        page.locator("text= Signup / Login").click();
        page.getByTestId("signup-name").click();
        page.getByTestId("signup-name").fill(faker.name().firstName()+" "+faker.name().lastName());
        page.getByTestId("signup-email").click();
        page.getByTestId("signup-email").fill(faker.internet().emailAddress());
        page.getByTestId("signup-button").click();
        assertTrue(page.locator("text=Enter Account Information").isVisible());
        page.getByTestId("password").click();
        page.getByTestId("password").fill(faker.internet().password());
        page.getByTestId("first_name").click();
        page.getByTestId("first_name").fill(firstName);
        page.getByTestId("last_name").click();
        page.getByTestId("last_name").fill(lastName);
        page.getByTestId("address").click();
        page.getByTestId("address").fill(faker.address().streetAddress());
        page.getByTestId("country").click();
        page.getByTestId("country").selectOption(selectedCountry);
        page.getByTestId("state").click();
        page.getByTestId("state").fill(faker.address().state());
        page.getByTestId("city").click();
        page.getByTestId("city").fill(faker.address().city());
        page.getByTestId("zipcode").click();
        page.getByTestId("zipcode").fill(faker.address().zipCode());
        page.getByTestId("mobile_number").click();
        page.getByTestId("mobile_number").fill(faker.phoneNumber().phoneNumber());
        page.getByTestId("create-account").click();

        assertTrue(page.locator("text=Account Created!").isVisible());


    }


}