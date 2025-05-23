package hooks;

import com.microsoft.playwright.*;
import io.cucumber.java.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Paths;

public class Hooks {
    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;

    // Thread-local for page access in step defs
    private static final ThreadLocal<Page> threadLocalPage = new ThreadLocal<>();
    private static final ThreadLocal<String> threadLocalFirstName = new ThreadLocal<>();
    private static final ThreadLocal<String> threadLocalLastName = new ThreadLocal<>();

    private static final Logger log = LoggerFactory.getLogger(Hooks.class);

    public static void setFirstName(String firstName) {
        threadLocalFirstName.set(firstName);
    }

    public static void setLastName(String lastName) {
        threadLocalLastName.set(lastName);
    }

    public static String getFirstName() {
        return threadLocalFirstName.get();
    }

    public static String getLastName() {
        return threadLocalLastName.get();
    }

    public static String getFullName() {
        return getFirstName() + " " + getLastName();
    }

    // Global setup (runs once per test run)
    @BeforeAll
    public static void beforeAll() {
        System.out.println(" [BeforeAll] Global test setup"+
                " | Time: " + java.time.LocalTime.now());
    }

    @AfterAll
    public static void afterAll() {
        System.out.println(" [AfterAll] Global test teardown"+
                " | Time: " + java.time.LocalTime.now());
    }

    @Before
    public void beforeScenario(Scenario scenario) {
        log.info("START [{}] - {}", scenario.getSourceTagNames(), scenario.getName());
        log.info("Feature: {} (Line: {})", scenario.getUri(), scenario.getLine());

        System.out.println(" Scenario: " + scenario.getName() +
                " | Thread: " + Thread.currentThread().getName() +
                " | Time: " + java.time.LocalTime.now());

        playwright = Playwright.create();

        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false)
        );

        context = browser.newContext();
        page = context.newPage();
        playwright.selectors().setTestIdAttribute("data-qa");

        threadLocalPage.set(page);
    }

    @After
    public void afterScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            page.screenshot(new Page.ScreenshotOptions()
                    .setPath(Paths.get("target/screenshots/" + scenario.getName().replaceAll(" ", "_") + "_FAILED.png")));
            System.out.println(" Screenshot taken for failed scenario: " + scenario.getName());
            log.error("FAILED: {}", scenario.getName());
        } else {
            log.info("PASSED: {}", scenario.getName());
        }
        log.info("END - {}", scenario.getId());

        if (context != null) context.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();

        threadLocalPage.remove();
    }

    public static Page getPage() {
        return threadLocalPage.get();
    }
}
