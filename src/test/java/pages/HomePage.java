package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitUntilState;

public class HomePage {

    private final Page page;

    // Selectors
    private final String homePageLink = "[href='https://www.automationexercise.com/']";
    private final String signupLoginLink = "[href='/login']";
    private final String productsLink = "[href='/products']";
    private final String cartLink = "[href='/view_cart']";
    private final String testCasesLink = "[href='/test_cases']";
    private final String apiTestingLink = "[href='/api_list']";
    private final String videoTutorialsLink = "[href='https://www.youtube.com/c/AutomationExercise']";
    private final String contactUsLink = "[href='/contact_us']";

    public HomePage(Page page) {
        this.page = page;
    }

    public void openHomePage() {
        page.navigate("https://www.automationexercise.com",
                new Page.NavigateOptions().setWaitUntil(WaitUntilState.DOMCONTENTLOADED));
        page.waitForSelector("[href='/login']");
    }

    public void goToLoginPage() {
        page.locator(signupLoginLink).click();
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

    public void isVisible() {
        page.locator(homePageLink).isVisible();
    }
}
