package helpers;

import org.slf4j.MDC;
import utils.ScenarioPathBuilder;

import java.nio.file.Path;

public class LogPathManager {

    private LogPathManager() {
        throw new UnsupportedOperationException("Utility class — cannot be instantiated.");
    }

    /**
     * Sets up log path for the given scenario and test type, and initializes MDC.
     */
    public static Path setup(String testType, String scenarioName) {
        Path scenarioPath = ScenarioPathBuilder.getScenarioFolder(testType, scenarioName);
        MDC.put("scenarioLogPath", scenarioPath.resolve("log.txt").toString());
        return scenarioPath;
    }
}
