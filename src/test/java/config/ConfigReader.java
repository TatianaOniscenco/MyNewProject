package config;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static final Properties properties = new Properties();

    static {
        InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties");
        if (input == null) {
            throw new RuntimeException("config.properties not found in classpath");
        }

        try (input) {
            properties.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}
