package hooks;

import com.microsoft.playwright.*;
import io.cucumber.java.*;

import java.nio.file.Paths;

public class Hooks {
    public static Playwright playwright;
    public static Browser browser;

    public static BrowserContext context;
    public static Page page;

    @BeforeAll
    public static void beforeAll() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false)
        );
    }

    @AfterAll
    public static void afterAll() {
        if (playwright != null) {
            playwright.close();
        }
    }

    @Before
    public void beforeScenario() {
        context = browser.newContext();
        page = context.newPage();
        playwright.selectors().setTestIdAttribute("data-qa"); // ✅ set testId attribute
    }

    @After

    public void afterScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            Hooks.page.screenshot(new Page.ScreenshotOptions()
                    .setPath(Paths.get("target/screenshots/" + scenario.getName().replaceAll(" ", "_") + "_FAILED.png")));
            System.out.println("📸 Screenshot taken for failed scenario: " + scenario.getName());
        }

        if (Hooks.context != null) {
            Hooks.context.close();
        }
        if (context != null) {
            context.close();
        }

    }
    // Getters to access shared Page instance from step files
    public static Page getPage() {
        return page;
    }
}
