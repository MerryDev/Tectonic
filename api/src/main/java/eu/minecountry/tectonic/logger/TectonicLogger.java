package eu.minecountry.tectonic.logger;


import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

final class TectonicLogger implements DefaultLogger {

    private static TectonicLogger instance;
    private final Logger logger;

    private TectonicLogger() {
        this.logger = Logger.getLogger(TectonicLogger.class.getName());

        logger.addHandler(createConsoleHandler());
    }

    public static TectonicLogger logger() {
        if (instance == null) {
            instance = new TectonicLogger();
        }
        return instance;
    }


    @Override
    public void info(String template, Object... args) {
        logger.info(String.format(template, args));
    }

    @Override
    public void warn(String template, Object... args) {
        logger.warning(String.format(template, args));
    }

    @Override
    public void error(String template, Object... args) {
        logger.severe(String.format(template, args));
    }

    private ConsoleHandler createConsoleHandler() {
        ConsoleHandler consoleHandler = new ConsoleHandler();

        try {
            consoleHandler.setEncoding(StandardCharsets.UTF_8.name());
            consoleHandler.setFilter(DefaultLogger.filter());
            consoleHandler.setFormatter(DefaultLogger.formatter());

        } catch (UnsupportedEncodingException e) {
            logger.severe("Could not set encoding for console handler to utf-8: " + e.getMessage());
        }

        return consoleHandler;
    }
}
