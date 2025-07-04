package utils;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
/**
 * Utility class for building scenario-specific paths for test logs.
 * It generates paths based on the current date, test type, and scenario name,
 * ensuring that each scenario has a dedicated folder for its logs.
 */
public class ScenarioPathBuilder {
    private static final String ROOT_DIR = "target/logs";
    private static final String DATE = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

    // STATIC timestamp for the entire run (set once)
    private static final String RUN_TIMESTAMP = LocalTime.now().format(DateTimeFormatter.ofPattern("HH_mm_ss"));


    public static Path getScenarioFolder(String testType, String scenarioName) {
        Path path = Paths.get(ROOT_DIR, DATE, testType, RUN_TIMESTAMP, sanitize(scenarioName));
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to create scenario directory: " + path, e);
        }
        return path.toAbsolutePath();
    }

    public static String sanitize(String name) {
        return name.replaceAll("[^a-zA-Z0-9.-]", "_");
    }


}