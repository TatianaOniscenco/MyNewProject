package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LogUtil {
    private static final Logger logger = LoggerFactory.getLogger(LogUtil.class);
    private static final String ROOT_DIR = "target/logs";

    private static final String DATE = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    private static final String TIME = LocalTime.now().format(DateTimeFormatter.ofPattern("HH_mm_ss"));

    public static String getExecutionBaseFolder(String testType) {
        return Paths.get(ROOT_DIR, DATE, testType, TIME).toString();
    }

    public static Path getScenarioFolder(String testType, String featureName, String scenarioName) {
        Path path = Paths.get(getExecutionBaseFolder(testType), sanitize(featureName), sanitize(scenarioName));
        new File(path.toString()).mkdirs();  // Ensure directory exists
        return path;
    }

    public static String sanitize(String name) {
        return name.replaceAll("[^a-zA-Z0-9.-]", "_");
    }

    public static Logger getLogger() {
        return logger;
    }
}