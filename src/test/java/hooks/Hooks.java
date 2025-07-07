package hooks;

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

public class Hooks {

    private static final Logger log = LoggerFactory.getLogger(Hooks.class);

    private static final ThreadLocal<Page> threadLocalPage = new ThreadLocal<>();
    private static final ThreadLocal<Path> threadLocalScenarioPath = new ThreadLocal<>();

    @Before("@UI")
    public void beforeUIScenario(Scenario scenario) {
        String scenarioName = scenario.getName();
        Path scenarioPath = ScenarioPathBuilder.getScenarioFolder("UI", scenarioName);
        threadLocalScenarioPath.set(scenarioPath);

        MDC.put("scenarioLogPath", scenarioPath.resolve("log.txt").toString());
        log.info("START (UI) - {}", scenarioName);

        ScenarioContextManager.get(); // ensure context is initialized

        PlaywrightFactory.initBrowser();
        threadLocalPage.set(PlaywrightFactory.getPage());
    }

    @Before("@API")
    public void beforeAPIScenario(Scenario scenario) {
        String scenarioName = scenario.getName();
        Path scenarioPath = ScenarioPathBuilder.getScenarioFolder("API", scenarioName);
        threadLocalScenarioPath.set(scenarioPath);

        MDC.put("scenarioLogPath", scenarioPath.resolve("log.txt").toString());
        log.info("START (API) - {}", scenarioName);
        log.info("API test — skipping browser setup.");

        ScenarioContextManager.get(); // ensure context is initialized
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
            // Always saves a screenshot to disk
            byte[] screenshot = page.screenshot(new Page.ScreenshotOptions().setPath(screenshotPath));
            log.info("Screenshot saved: {}", screenshotPath);

            // Screenshot to Allure only on failure
            if (scenario.isFailed()) {
                Allure.addAttachment("Failure Screenshot", new ByteArrayInputStream(screenshot));
            }
        }

        log.info("END - {}", scenarioName);

        // Cleanup
        PlaywrightFactory.close();
        threadLocalPage.remove();
        threadLocalScenarioPath.remove();
        ScenarioContextManager.reset();
        MDC.clear();
    }
}
