package enums;

public enum SignupCountry {
    INDIA("India"),
    UNITED_STATES("United States"),
    CANADA("Canada"),
    AUSTRALIA("Australia"),
    ISRAEL("Israel"),
    NEW_ZEALAND("New Zealand"),
    SINGAPORE("Singapore");

    private final String label;

    SignupCountry(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static SignupCountry getRandom() {
        SignupCountry[] values = SignupCountry.values();
        return values[new java.util.Random().nextInt(values.length)];
    }
}
