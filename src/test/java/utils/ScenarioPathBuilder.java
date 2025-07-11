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
 * Generates structured file paths like:
 * target/logs/11.07.2025/UI/14_35_12/My_Scenario_Name
 */
public class ScenarioPathBuilder {
    private static final String ROOT_DIR = "target/logs";
    private static final String DATE = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    private static final String RUN_TIMESTAMP = LocalTime.now().format(DateTimeFormatter.ofPattern("HH_mm_ss"));

    // Prevent instantiation of this utility class
    private ScenarioPathBuilder() {
        throw new UnsupportedOperationException("Utility class — cannot be instantiated.");
    }

    public static Path getScenarioFolder(String testType, String scenarioName) {
        Path path = Paths.get(ROOT_DIR, DATE, testType, RUN_TIMESTAMP, sanitize(scenarioName));
        createDirectories(path);
        return path.toAbsolutePath();
    }

    public static String sanitize(String name) {
        return name.replaceAll("[^a-zA-Z0-9.-]", "_");
    }

    private static void createDirectories(Path path) {
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to create scenario directory: " + path, e);
        }
    }


}