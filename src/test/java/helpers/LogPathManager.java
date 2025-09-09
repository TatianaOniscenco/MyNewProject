package helpers;

import org.slf4j.MDC;
import utils.ScenarioPathBuilder;

import java.nio.file.Path;

public class LogPathManager {

    private LogPathManager() {
        throw new UnsupportedOperationException("Utility class — cannot be instantiated.");
    }

    /**
     * Create a folder like: target/logs/15.07.2025/UI/14_50_22/Scenario_Name
     * Configure MDC so Logback knows where to write logs for this scenario
     */
    public static Path setup(String testType, String scenarioName) {
        Path scenarioPath = ScenarioPathBuilder.getScenarioFolder(testType, scenarioName);
        MDC.put("scenarioLogPath", scenarioPath.resolve("log.txt").toString());
        return scenarioPath;
    }
}
