package hooks;

import ENUM.BrowserName;
import com.microsoft.playwright.Page;
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

    private final Logger log = LoggerFactory.getLogger(Hooks.class);
    private final ThreadLocal<Page> threadLocalPage = new ThreadLocal<>();
    private final ThreadLocal<Path> threadLocalScenarioPath = new ThreadLocal<>();

    @Before("@UI")
    public void beforeUIScenario(Scenario scenario) {
        String scenarioName = scenario.getName();

        log.info("START (UI) - {}", scenarioName);
        ScenarioContextManager.get(); // Initialize scenario context

        PlaywrightFactory.initBrowser(BrowserName.CHROMIUM);
        threadLocalPage.set(PlaywrightFactory.getPage());
    }

    @Before("@API")
    public void beforeAPIScenario(Scenario scenario) {
        String scenarioName = scenario.getName();

        log.info("START (API) - {}", scenarioName);
        log.info("API test — skipping browser setup.");
        ScenarioContextManager.get(); // Initialize scenario context
    }

    @After
    public void afterScenario(Scenario scenario) {
        String scenarioName = scenario.getName();
        String result = scenario.isFailed() ? "FAILED" : "PASSED";
        log.info("{} - {}", result, scenarioName);

        Page page = threadLocalPage.get();
        Path path = threadLocalScenarioPath.get();

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
     * Creates a log folder for the current scenario and configures MDC logging path.
     *
     * @param testType     UI or API
     * @param scenarioName scenario name from Cucumber
     * @return created path for this scenario
     */
    private Path setupScenarioLogPath(String testType, String scenarioName) {
        Path scenarioPath = ScenarioPathBuilder.getScenarioFolder(testType, scenarioName);
        threadLocalScenarioPath.set(scenarioPath);
        MDC.put("scenarioLogPath", scenarioPath.resolve("log.txt").toString());
        return scenarioPath;
    }
}
