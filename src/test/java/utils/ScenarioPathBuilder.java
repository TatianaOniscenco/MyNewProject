package utils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ScenarioPathBuilder {
    private static final String ROOT_DIR = "target/logs";
    private static final String DATE = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

    // STATIC timestamp for the entire run (set once)
    private static final String RUN_TIMESTAMP = LocalTime.now().format(DateTimeFormatter.ofPattern("HH_mm_ss"));

    public static String getExecutionBaseFolder(String testType) {
        return Paths.get(ROOT_DIR, DATE, testType, RUN_TIMESTAMP).toString();
    }

    public static Path getScenarioFolder(String testType, String featureName, String scenarioName) {
        Path path = Paths.get(ROOT_DIR, DATE, testType, RUN_TIMESTAMP, sanitize(scenarioName));
        new File(path.toString()).mkdirs();
        return path;
    }

    public static String sanitize(String name) {
        return name.replaceAll("[^a-zA-Z0-9.-]", "_");
    }


}