package factory;

import com.microsoft.playwright.*;
import config.ConfigReader;

public class PlaywrightFactory {

    private static final ThreadLocal<Playwright> playwright = new ThreadLocal<>();
    private static final ThreadLocal<Browser> browser = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> context = new ThreadLocal<>();
    private static final ThreadLocal<Page> page = new ThreadLocal<>();

    public static void initBrowser() {
        // Load from config
        String browserName = ConfigReader.get("browser");
        boolean isHeadless = Boolean.parseBoolean(ConfigReader.get("headless"));

        // Create Playwright instance
        playwright.set(Playwright.create());

        // Launch options
        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
                .setHeadless(isHeadless);

        // Launch selected browser
        switch (browserName.toLowerCase()) {
            case "firefox":
                browser.set(playwright.get().firefox().launch(options));
                break;
            case "webkit":
                browser.set(playwright.get().webkit().launch(options));
                break;
            case "chromium":
            case "chrome": // alias
            default:
                browser.set(playwright.get().chromium().launch(options));
                break;
        }

        // Create context and page
        context.set(browser.get().newContext());
        page.set(context.get().newPage());
    }

    public static Page getPage() {
        return page.get();
    }

    public static void close() {
        if (page.get() != null) page.get().close();
        if (context.get() != null) context.get().close();
        if (browser.get() != null) browser.get().close();
        if (playwright.get() != null) playwright.get().close();

        page.remove();
        context.remove();
        browser.remove();
        playwright.remove();
    }
}