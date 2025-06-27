package factory;

public enum BrowserName {
    CHROMIUM,
    FIREFOX,
    WEBKIT;

    public static BrowserName fromString(String value) {
        switch (value.toLowerCase()) {
            case "firefox":
                return FIREFOX;
            case "webkit":
                return WEBKIT;
            case "chromium":
            case "chrome":
            default:
                return CHROMIUM;
        }
    }
}
