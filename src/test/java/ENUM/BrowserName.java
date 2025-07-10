package ENUM;

public enum BrowserName {
    CHROMIUM("CHROMIUM"),
    FIREFOX("FIREFOX"),
    WEBKIT("WEBKIT");

    private final String key;

    BrowserName(String key) {
        this.key = key;
    }


}
