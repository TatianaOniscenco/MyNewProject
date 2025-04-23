package steps;

import com.microsoft.playwright.Page;
import hooks.Hooks;
import io.cucumber.java.en.Then;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommonSteps {

    // ✅ Get the shared Playwright Page instance
    Page page = Hooks.getPage();

    @Then("System displays the {string} message")
    public void system_displays_the_message(String message) {
        assertTrue(page.locator("text=" + message).isVisible());
    }
}
