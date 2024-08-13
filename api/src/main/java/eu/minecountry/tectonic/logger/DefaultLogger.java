package eu.minecountry.tectonic.logger;

public sealed interface DefaultLogger extends Logger permits TectonicFileLogger, TectonicLogger {

    static LogFilter filter() {
        return new LogFilter();
    }

    static LofFormatter formatter() {
        return new LofFormatter();
    }

    sealed interface Builder extends Logger.Builder<Builder> permits TectonicFileLoggerBuilder, TectonicLoggerBuilder {
        DefaultLogger build();
    }

}
