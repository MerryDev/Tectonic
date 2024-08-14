package eu.minecountry.tectonic.core.util;

import java.util.Optional;

public final class Parser {

    private Parser() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static Optional<Integer> parseInt(String string) {
        try {
            return Optional.of(Integer.parseInt(string));

        } catch (NumberFormatException exception) {
            return Optional.empty();
        }
    }

    public static Optional<Double> parseDouble(String string) {
        try {
            return Optional.of(Double.parseDouble(string.replace(",", ".")));

        } catch (NumberFormatException exception) {
            return Optional.empty();
        }
    }

    public static Optional<Long> parseLong(String string) {
        try {
            return Optional.of(Long.parseLong(string.replace(",", ".")));

        } catch (NumberFormatException exception) {
            return Optional.empty();
        }
    }

    public static Optional<Boolean> parseBoolean(String string) {
        return parseBoolean(string, "true", "false");
    }

    public static Optional<Boolean> parseBoolean(String string, String trueValue, String falseValue) {
        if (string.equals(trueValue)) {
            return Optional.of(true);
        }
        if (string.equals(falseValue)) {
            return Optional.of(false);
        }

        return Optional.empty();
    }
}
