package ENUM;

public enum BrowserName {
    CHROMIUM ("CHROMIUM"),
    CHROME ("CHROMIUM"),
    FIREFOX("FIREFOX"),
    WEBKIT("WEBKIT");

    private final String key;

    BrowserName(String key) {
        this.key = key;
    }
}
