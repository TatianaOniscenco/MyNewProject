package homePage;

import java.util.regex.Pattern;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class App {
    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch();
            Page page = browser.newPage();
            page.navigate("https://www.automationexercise.com");

            // Expect a title "to contain" a substring.
            assertThat(page).hasTitle(Pattern.compile("Automation Exercise"));

            // create a locator
            Locator signUpLogIn = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(" Signup / Login"));

            // Expect an attribute "to be strictly equal" to the value.
            assertThat(signUpLogIn).hasAttribute("href", "/login");

            // Click the get started link.
            signUpLogIn.click();

            // Expects page to have a heading with the name of Login to your account.
            assertThat(page.getByRole(AriaRole.HEADING,
                    new Page.GetByRoleOptions().setName("Login to your account"))).isVisible();
        }
    }
}