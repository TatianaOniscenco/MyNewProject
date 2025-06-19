package hooks;

import com.microsoft.playwright.*;
import io.cucumber.java.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.LogUtil;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class Hooks {
    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;

    private Path scenarioPath;
    private String featureName;
    private String scenarioName;
    private String testType;

    private static final ThreadLocal<Page> threadLocalPage = new ThreadLocal<>();
    private static final ThreadLocal<String> threadLocalFirstName = new ThreadLocal<>();
    private static final ThreadLocal<String> threadLocalLastName = new ThreadLocal<>();
    private static final ThreadLocal<String> threadLocalEmail = new ThreadLocal<>();
    private static final ThreadLocal<String> threadLocalPassword = new ThreadLocal<>();
    private static final ThreadLocal<Path> threadLocalScenarioPath = new ThreadLocal<>();

    public static void setFirstName(String firstName) { threadLocalFirstName.set(firstName); }
    public static void setLastName(String lastName) { threadLocalLastName.set(lastName); }
    public static String getFirstName() { return threadLocalFirstName.get(); }
    public static String getLastName() { return threadLocalLastName.get(); }
    public static String getFullName() { return getFirstName() + " " + getLastName(); }
    public static void setEmail(String email) { threadLocalEmail.set(email); }
    public static void setPassword(String password) { threadLocalPassword.set(password); }
    public static String getEmail() { return threadLocalEmail.get(); }
    public static String getPassword() { return threadLocalPassword.get(); }

    public static Page getPage() {
        return threadLocalPage.get();
    }

    @BeforeAll
    public static void beforeAll() {
        System.out.println(" [BeforeAll] Global test setup | Time: " + java.time.LocalTime.now());
    }

    @AfterAll
    public static void afterAll() {
        System.out.println(" [AfterAll] Global test teardown | Time: " + java.time.LocalTime.now());
    }

    @Before
    public void beforeScenario(Scenario scenario) {
        this.featureName = scenario.getUri().toString().replaceAll(".*features/", "").replace(".feature", "");
        this.scenarioName = scenario.getName();
        this.testType = scenario.getSourceTagNames().contains("@API") ? "API" : "UI";

        scenarioPath = LogUtil.getScenarioFolder(testType, featureName, scenarioName);
        threadLocalScenarioPath.set(scenarioPath);

        logToFile("START - " + scenario.getName());

        if (testType.equals("API")) {
            System.out.println("API test — skipping browser setup.");
            return;
        }

        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        context = browser.newContext();
        page = context.newPage();
        threadLocalPage.set(page);
    }

    @After
    public void afterScenario(Scenario scenario) {
        String result = scenario.isFailed() ? "FAILED" : "PASSED";
        String logMsg = (scenario.isFailed() ? "Test failed: " : "Test passed: ") + scenario.getName();

        logToFile(result + " - " + scenario.getName());

        if (!testType.equals("API") && page != null) {
            Path screenshotPath = threadLocalScenarioPath.get().resolve(result + ".png");
            page.screenshot(new Page.ScreenshotOptions().setPath(screenshotPath));
        }

        logToFile("END - " + scenario.getName());

        if (context != null) context.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();

        threadLocalPage.remove();
        threadLocalScenarioPath.remove();
    }

    public static void logToFile(String message) {
        Path scenarioPath = threadLocalScenarioPath.get(); // <-- use thread-safe instance
        if (scenarioPath == null) return;

        try {
            Path logFile = scenarioPath.resolve("log.txt");
            FileWriter writer = new FileWriter(logFile.toFile(), true);
            writer.write("[" + java.time.LocalTime.now() + "] " + message + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

