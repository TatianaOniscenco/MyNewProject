package hooks;

import ENUM.BrowserName;
import com.microsoft.playwright.Page;
import config.ConfigReader;
import context.ScenarioContextManager;
import factory.PlaywrightFactory;
import helpers.LogPathManager;
import helpers.ScreenshotHelper;
import io.cucumber.java.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.nio.file.Path;

/**
 * Cucumber lifecycle hooks for UI and API tests.
 * Handles setup and teardown of browser, scenario context, logging, and screenshots.
 */
public class Hooks {

    // Initialize the logger for this class
    private final Logger log = LoggerFactory.getLogger(Hooks.class);
    // Declare ThreadLocal variables to store per-scenario instances of Playwright Page and scenario log folder Path
    private final ThreadLocal<Page> threadLocalPage = new ThreadLocal<>();
    private final ThreadLocal<Path> threadLocalScenarioPath = new ThreadLocal<>();

    @Before("@UI")
    public void beforeUIScenario(Scenario scenario) {
        // Retrieves the scenario name from the Cucumber Scenario object
        String scenarioName = scenario.getName();
        // Reads the browser type (e.g. "chromium" or "firefox") from your config.properties
        String browser = ConfigReader.getInstance().get("browser");

        // Set up the scenario log path and initialize the logging system MDC
        Path path = LogPathManager.setup("UI", scenarioName);
        threadLocalScenarioPath.set(path);

        // Initialize the scenario context to store data specific to this scenario
        ScenarioContextManager.get();
        // Initialize Playwright and create a new browser page for this scenario
        PlaywrightFactory.initBrowser(BrowserName.valueOf(browser.toUpperCase()));
        // Store the Playwright Page in a ThreadLocal variable to ensure each scenario has its own instance
        threadLocalPage.set(PlaywrightFactory.getPage());
        log.info("START (UI) - {}", scenarioName);
    }

    @Before("@API")
    public void beforeAPIScenario(Scenario scenario) {
        String scenarioName = scenario.getName();

        Path path = LogPathManager.setup("API", scenarioName);
        threadLocalScenarioPath.set(path);

        ScenarioContextManager.get();
        log.info("START (API) - {}", scenarioName);
    }

    @After
    public void afterScenario(Scenario scenario) {
        String scenarioName = scenario.getName();
        String result = scenario.isFailed() ? "FAILED" : "PASSED";
        log.info("{} - {}", result, scenarioName);

        ScreenshotHelper.capture(threadLocalPage.get(), threadLocalScenarioPath.get(), scenario);

        log.info("END - {}", scenarioName);

        // Cleanup to avoid memory/thread leaks
        PlaywrightFactory.close();
        threadLocalPage.remove();
        threadLocalScenarioPath.remove();
        ScenarioContextManager.reset();
        MDC.clear();
    }
}
