package hooks;

import ENUM.BrowserName;
import com.microsoft.playwright.Page;
import config.ConfigReader;
import context.ScenarioContextManager;
import factory.PlaywrightFactory;
import io.cucumber.java.*;
import io.qameta.allure.Allure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import utils.ScenarioPathBuilder;

import java.io.ByteArrayInputStream;
import java.nio.file.Path;

/**
 * Cucumber lifecycle hooks for UI and API tests.
 * Handles setup and teardown of browser, scenario context, logging, and screenshots.
 */
public class Hooks {

    // Logger for logging scenario start, end, and results, tagged with the class name
    private final Logger log = LoggerFactory.getLogger(Hooks.class);
    // These fields store per-thread (per-scenario) instances of the Playwright Page and the scenario log folder Path
    private final ThreadLocal<Page> threadLocalPage = new ThreadLocal<>();
    private final ThreadLocal<Path> threadLocalScenarioPath = new ThreadLocal<>();

    @Before("@UI")
    public void beforeUIScenario(Scenario scenario) {
        String scenarioName = scenario.getName();
        String browser = ConfigReader.getInstance().get("browser");

        // Set up the scenario log path and initialize the logging system
        setupScenarioLogPath("UI", scenarioName);
        log.info("START (UI) - {}", scenarioName);
        // Initialize the scenario context to store data specific to this scenario
        ScenarioContextManager.get();
        // Initialize Playwright and create a new browser page for this scenario
        PlaywrightFactory.initBrowser(BrowserName.valueOf(browser.toUpperCase()));
        // Store the Playwright Page in a ThreadLocal variable to ensure each scenario has its own instance
        threadLocalPage.set(PlaywrightFactory.getPage());
    }

    @Before("@API")
    public void beforeAPIScenario(Scenario scenario) {
        String scenarioName = scenario.getName();

        setupScenarioLogPath("API", scenarioName);
        log.info("START (API) - {}", scenarioName);
        ScenarioContextManager.get();
    }

    @After
    public void afterScenario(Scenario scenario) {
        String scenarioName = scenario.getName();
        String result = scenario.isFailed() ? "FAILED" : "PASSED";
        log.info("{} - {}", result, scenarioName);

        // Retrieve page and path from current thread for the screenshot and cleanup
        Page page = threadLocalPage.get();
        Path path = threadLocalScenarioPath.get();

        // If it’s a UI test (page is not null), take a screenshot.
        // Always save it to disk; attach to Allure only on failure.
        if (page != null && path != null) {
            Path screenshotPath = path.resolve(result + ".png");
            byte[] screenshot = page.screenshot(new Page.ScreenshotOptions().setPath(screenshotPath));
            log.info("Screenshot saved: {}", screenshotPath);

            if (scenario.isFailed()) {
                Allure.addAttachment("Failure Screenshot", "image/png", new ByteArrayInputStream(screenshot), ".png");
            }
        }

        log.info("END - {}", scenarioName);

        // Cleanup to avoid memory/thread leaks
        PlaywrightFactory.close();
        threadLocalPage.remove();
        threadLocalScenarioPath.remove();
        ScenarioContextManager.reset();
        MDC.clear();
    }

    /**
     * Sets up the logging system for the scenario using the path from ScenarioPathBuilder
     */
    private void setupScenarioLogPath(String testType, String scenarioName) {
        Path scenarioPath = ScenarioPathBuilder.getScenarioFolder(testType, scenarioName);
        threadLocalScenarioPath.set(scenarioPath);
        MDC.put("scenarioLogPath", scenarioPath.resolve("log.txt").toString());
    }
}
