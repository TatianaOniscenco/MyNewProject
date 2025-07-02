package hooks;

import com.microsoft.playwright.Page;
import context.UserContext;
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
    private static final ThreadLocal<Path> threadLocalScenarioPath = new ThreadLocal<>();
    private static final ThreadLocal<UserContext> threadLocalUserContext = new ThreadLocal<>();

    private static final Logger log = LoggerFactory.getLogger(Hooks.class);

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

        // Initialize browser for UI tests
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

        // Clean up
        PlaywrightFactory.close();
        threadLocalPage.remove();
        threadLocalScenarioPath.remove();
        threadLocalUserContext.remove();
        MDC.clear();
    }
}

