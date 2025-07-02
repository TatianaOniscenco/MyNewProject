package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitUntilState;
import config.ConfigReader;
import context.UserContext;
import hooks.Hooks;

public class HomePage {

    private final Page page;

    // Selectors
    private final String signupLoginLink = "[href='/login']";
    private final String productsLink = "[href='/products']";
    private final String cartLink = "[href='/view_cart']";
    private final String testCasesLink = "[href='/test_cases']";
    private final String apiTestingLink = "[href='/api_list']";
    private final String videoTutorialsLink = "[href='https://www.youtube.com/c/AutomationExercise']";
    private final String contactUsLink = "[href='/contact_us']";
    private final String loggedInTextLocator = "li:has-text('Logged in as')";
    private final String deleteButton = "[href='/delete_account']";
    private final String logoutButton = "[href='/logout']";

    public HomePage(Page page) {
        this.page = page;
    }

    private UserContext getUserContext() {
        UserContext context = Hooks.getUserContext();
        if (context == null) {
            throw new IllegalStateException("UserContext is null — it must be initialized before use.");
        }
        return context;
    }

    public void openHomePage() {
        String url = ConfigReader.get("base.url");
        page.navigate(url, new Page.NavigateOptions().setWaitUntil(WaitUntilState.DOMCONTENTLOADED));
        page.waitForSelector(signupLoginLink);
    }

    public void goToLoginPage() {
        page.locator(signupLoginLink).click();
    }

    public String getLoggedInText() {
        return page.locator(loggedInTextLocator).innerText().trim();
    }

    public void goToProductsPage() {
        page.locator(productsLink).click();
    }

    public void goToCartPage() {
        page.locator(cartLink).click();
    }

    public void goToTestCases() {
        page.locator(testCasesLink).click();
    }

    public void goToApiTesting() {
        page.locator(apiTestingLink).click();
    }

    public void goToVideoTutorials() {
        page.locator(videoTutorialsLink).click();
    }

    public void goToContactUs() {
        page.locator(contactUsLink).click();
    }

    public void assertRedirectedToHomeUrl() {
        String expectedUrl = ConfigReader.get("base.url").replaceAll("/+$", "");
        String actualUrl = page.url().replaceAll("/+$", "");
        if (!actualUrl.equals(expectedUrl)) {
            throw new AssertionError("Expected to be at: " + expectedUrl + " but was: " + actualUrl);
        }
    }

    public void clickDeleteButton() {
        page.locator(deleteButton).click();
    }

    public void clickLogoutButton() {
        page.locator(logoutButton).click();
    }

    public Locator getLoggedInHeaderLocator() {
        return page.getByText("Logged in as " + getUserContext().getFullName());
    }
}
