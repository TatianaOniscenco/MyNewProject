package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitUntilState;
import config.ConfigReader;

public class HomePage {

    private final Page page;

    // Locators
    private static final String SIGNUP_LOGIN_LINK = "[href='/login']";
    private static final String PRODUCTS_LINK = "[href='/products']";
    private static final String CART_LINK = "[href='/view_cart']";
    private static final String TEST_CASES_LINK = "[href='/test_cases']";
    private static final String API_TESTING_LINK = "[href='/api_list']";
    private static final String VIDEO_TUTORIALS_LINK = "[href='https://www.youtube.com/c/AutomationExercise']";
    private static final String CONTACT_US_LINK = "[href='/contact_us']";
    private static final String LOGGED_IN_TEXT_LOCATOR = "li:has-text('Logged in as')";
    private static final String DELETE_BUTTON = "[href='/delete_account']";
    private static final String LOGOUT_BUTTON = "[href='/logout']";

    // Constructor
    public HomePage(Page page) {
        this.page = page;
    }

    // Actions
    public void openHomePage() {
        String url = ConfigReader.getInstance().get("base.url");
        page.navigate(url, new Page.NavigateOptions().setWaitUntil(WaitUntilState.DOMCONTENTLOADED));
        page.waitForSelector(SIGNUP_LOGIN_LINK);
    }

    public void goToLoginPage() {
        page.locator(SIGNUP_LOGIN_LINK).click();
    }

    public String getLoggedInText() {
        return page.locator(LOGGED_IN_TEXT_LOCATOR).innerText().trim();
    }

    public Locator getLoggedInHeaderLocator(String fullName) {
        return page.getByText("Logged in as " + fullName);
    }

    public void goToProductsPage() {
        page.locator(PRODUCTS_LINK).click();
    }

    public void goToCartPage() {
        page.locator(CART_LINK).click();
    }

    public void goToTestCases() {
        page.locator(TEST_CASES_LINK).click();
    }

    public void goToApiTesting() {
        page.locator(API_TESTING_LINK).click();
    }

    public void goToVideoTutorials() {
        page.locator(VIDEO_TUTORIALS_LINK).click();
    }

    public void goToContactUs() {
        page.locator(CONTACT_US_LINK).click();
    }

    public String getCurrentUrl() {
        return page.url();
    }

    public void clickDeleteButton() {
        page.locator(DELETE_BUTTON).click();
    }

    public void clickLogoutButton() {
        page.locator(LOGOUT_BUTTON).click();
    }
}
