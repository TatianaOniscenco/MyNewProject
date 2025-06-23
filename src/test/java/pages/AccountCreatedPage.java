package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.options.LoadState;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccountCreatedPage {
    private final Page page;
    private final String continueButton = "[data-qa='continue-button']";


    public AccountCreatedPage(Page page) {
        this.page = page;
    }

    public void clickContinueButton() {
        page.locator(continueButton).click(new Locator.ClickOptions()
                .setTimeout(1000));

        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
    }

    public void isVisible() {
        Locator heading = page.locator("h2:has-text('Account Created!')");

        try {
            heading.waitFor(new Locator.WaitForOptions().setTimeout(10000)); // wait for the heading
            assertTrue(heading.isVisible(), "'Account Created!' heading is not visible.");

            // Optional URL check
            assertTrue(page.url().contains("/account_created"), "Unexpected URL: " + page.url());

        } catch (PlaywrightException e) {
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("account_created_error_" + System.currentTimeMillis() + ".png")));
            throw new AssertionError("Failed to confirm Account Created page", e);
        }

    }
}
