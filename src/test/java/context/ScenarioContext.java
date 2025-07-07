package context;

import java.util.HashMap;
import java.util.Map;

public class ScenarioContext {
    private final Map<String, Object> context = new HashMap<>();

    public <T> void set(String key, T value) {
        context.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        return (T) context.get(key);
    }

    public boolean contains(String key) {
        return context.containsKey(key);
    }

    @Override
    public String toString() {
        return "ScenarioContext{" + "context=" + context + '}';
    }
}