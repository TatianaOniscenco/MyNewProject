package hooks;

import com.microsoft.playwright.Page;
import factory.PlaywrightFactory;
import io.cucumber.java.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import context.UserContext;
import utils.ScenarioPathBuilder;

import java.nio.file.Path;

public class Hooks {

    private static final Logger log = LoggerFactory.getLogger(Hooks.class);

    private static final ThreadLocal<Page> threadLocalPage = new ThreadLocal<>();
    private static final ThreadLocal<Path> threadLocalScenarioPath = new ThreadLocal<>();
    private static final ThreadLocal<UserContext> threadLocalUserContext = new ThreadLocal<>();

    public static Page getPage() {
        return threadLocalPage.get();
    }

    public static Path getScenarioPath() {
        return threadLocalScenarioPath.get();
    }

    public static UserContext getUserContext() {
        return threadLocalUserContext.get();
    }

    public static void setUserContext(UserContext userContext) {
        threadLocalUserContext.set(userContext);
    }

    @Before("@UI")
    public void beforeUIScenario(Scenario scenario) {
        String scenarioName = scenario.getName();
        Path scenarioPath = ScenarioPathBuilder.getScenarioFolder("UI", scenarioName);
        threadLocalScenarioPath.set(scenarioPath);

        MDC.put("scenarioLogPath", scenarioPath.resolve("log.txt").toString());
        log.info("START (UI) - {}", scenarioName);

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
            page.screenshot(new Page.ScreenshotOptions().setPath(screenshotPath));
            log.info("Screenshot saved: {}", screenshotPath);
        }

        log.info("END - {}", scenarioName);

        // Cleanup
        PlaywrightFactory.close();
        threadLocalPage.remove();
        threadLocalScenarioPath.remove();
        threadLocalUserContext.remove();
        MDC.clear();
    }
}

