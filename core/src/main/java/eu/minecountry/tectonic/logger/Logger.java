package eu.minecountry.tectonic.logger;

import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

public interface Logger {

    interface Builder<T extends Builder<T>> {
        T toFile(Path path);
    }

    void info(String template, Object... args);

    void warn(String template, Object... args);

    void error(String template, Object... args);

    static DefaultLogger.@NotNull Builder builder() {
        return new TectonicLoggerBuilder();
    }

}
