package factory;

import ENUM.BrowserName;
import com.microsoft.playwright.*;
import config.ConfigReader;

public class PlaywrightFactory {

    private static final ThreadLocal<Playwright> playwright = new ThreadLocal<>();
    private static final ThreadLocal<Browser> browser = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> context = new ThreadLocal<>();
    private static final ThreadLocal<Page> page = new ThreadLocal<>();

    public static void initBrowser() {
        // Convert browser config value to enum
        BrowserName browserType = BrowserName.fromString(ConfigReader.get("browser"));
        boolean isHeadless = Boolean.parseBoolean(ConfigReader.get("headless"));

        // Create a new Playwright instance
        playwright.set(Playwright.create());

        // Set browser launch options
        com.microsoft.playwright.BrowserType.LaunchOptions options =
                new com.microsoft.playwright.BrowserType.LaunchOptions()
                        .setHeadless(isHeadless);

        // Launch the specified browser using enum
        switch (browserType) {
            case FIREFOX:
                browser.set(playwright.get().firefox().launch(options));
                break;
            case WEBKIT:
                browser.set(playwright.get().webkit().launch(options));
                break;
            case CHROMIUM:
            default:
                browser.set(playwright.get().chromium().launch(options));
                break;
        }

        // Create a context and page
        context.set(browser.get().newContext());
        page.set(context.get().newPage());
    }

    public static Page getPage() {
        return page.get();
    }


    // might need to:
    //Get cookies: getContext().cookies();
    //Set storage state: getContext().addCookies(...);
    //Save/restore session: getContext().storageState();
    //Trace/debug: getContext().tracing().start(...);
    public static BrowserContext getContext() {
        return context.get();
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