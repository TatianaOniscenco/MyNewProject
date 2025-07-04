package ENUM;

public enum BrowserName {
    CHROMIUM,
    FIREFOX,
    WEBKIT;

    public static BrowserName fromString(String value) {
        if (value == null) return CHROMIUM;
        switch (value.trim().toLowerCase()) {
            case "firefox": return FIREFOX;
            case "webkit": return WEBKIT;
            case "chromium":
            case "chrome":
            default: return CHROMIUM;
        }
    }
}
