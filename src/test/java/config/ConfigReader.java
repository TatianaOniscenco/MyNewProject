package config;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    // 1. Private static instance
    private static ConfigReader instance;

    private final Properties properties = new Properties();

    // 2. Private constructor to load config file
    private ConfigReader() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("config.properties not found in classpath");
            }
            properties.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    // 3. Public static method to access the instance (lazy-loaded + thread-safe)
    public static synchronized ConfigReader getInstance() {
        if (instance == null) {
            instance = new ConfigReader();
        }
        return instance;
    }

    // Public method to get value by key
    public String get(String key) {
        return properties.getProperty(key);
    }
}
