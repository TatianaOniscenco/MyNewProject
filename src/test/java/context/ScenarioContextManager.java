package context;

public class ScenarioContextManager {
    private static final ThreadLocal<ScenarioContext> scenarioContext = ThreadLocal.withInitial(ScenarioContext::new);

    public static ScenarioContext get() {
        return scenarioContext.get();
    }

    public static void reset() {
        scenarioContext.remove();
    }
}
