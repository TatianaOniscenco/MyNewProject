package hooks;

import com.microsoft.playwright.Page;
import factory.PlaywrightFactory;
import io.cucumber.java.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import utils.ScenarioPathBuilder;

import java.nio.file.Path;

public class Hooks {
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

    private static final Logger log = LoggerFactory.getLogger(Hooks.class);

    // ------------------ User Info Getters/Setters ------------------

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

    public static void setEmail(String email) {
        threadLocalEmail.set(email);
    }

    public static void setPassword(String password) {
        threadLocalPassword.set(password);
    }

    public static String getEmail() {
        return threadLocalEmail.get();
    }

    public static String getPassword() {
        return threadLocalPassword.get();
    }

    public static Page getPage() {
        return threadLocalPage.get();
    }

    @Before
    public void beforeScenario(Scenario scenario) {
        this.featureName = scenario.getUri().toString().replaceAll(".*features/", "").replace(".feature", "");
        this.scenarioName = scenario.getName();
        this.testType = scenario.getSourceTagNames().contains("@API") ? "API" : "UI";

        scenarioPath = ScenarioPathBuilder.getScenarioFolder(testType, scenarioName);
        threadLocalScenarioPath.set(scenarioPath);

        String fullPath = scenarioPath.resolve("log.txt").toString();
        MDC.put("scenarioLogPath", fullPath);

        log.info("START - {}", scenario.getName());

        if (testType.equals("API")) {
            log.info("API test — skipping browser setup.");
            return;
        }

        // Initialize Playwright using the factory
        PlaywrightFactory.initBrowser();
        threadLocalPage.set(PlaywrightFactory.getPage());
    }

    @After
    public void afterScenario(Scenario scenario) {
        String result = scenario.isFailed() ? "FAILED" : "PASSED";
        log.info("{} - {}", result, scenario.getName());

        if (!testType.equals("API") && getPage() != null) {
            Path screenshotPath = threadLocalScenarioPath.get().resolve(result + ".png");
            getPage().screenshot(new Page.ScreenshotOptions().setPath(screenshotPath));
            log.info("Screenshot saved: {}", screenshotPath);
        }

        log.info("END - {}", scenario.getName());

        // Close browser resources
        PlaywrightFactory.close();

        // Cleanup thread-local data
        threadLocalPage.remove();
        threadLocalFirstName.remove();
        threadLocalLastName.remove();
        threadLocalEmail.remove();
        threadLocalPassword.remove();
        threadLocalScenarioPath.remove();
        MDC.clear(); // Reset logging context
    }
}

