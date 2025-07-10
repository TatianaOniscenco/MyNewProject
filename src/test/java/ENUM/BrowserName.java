package ENUM;

public enum BrowserName {
    CHROMIUM("CHROMIUM"),
    FIREFOX("FIREFOX"),
    WEBKIT("WEBKIT");

    private final String key;

    BrowserName(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public static BrowserName fromString(String key) {
        for (BrowserName browser : BrowserName.values()) {
            if (browser.key.equalsIgnoreCase(key)) {
                return browser;
            }
        }
        throw new IllegalArgumentException("No enum found for key: " + key);
    }
}
