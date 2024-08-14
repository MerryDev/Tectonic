package eu.minecountry.tectonic.core.logger;

public sealed interface DefaultLogger extends Logger permits TectonicFileLogger, TectonicLogger {

    static LogFilter filter() {
        return new LogFilter();
    }

    static LogFormatter formatter() {
        return new LogFormatter();
    }

    sealed interface Builder extends Logger.Builder<Builder> permits TectonicFileLoggerBuilder, TectonicLoggerBuilder {
        DefaultLogger build();
    }

}
