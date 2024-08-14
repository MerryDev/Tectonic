package eu.minecountry.tectonic.logger;


import eu.minecountry.tectonic.util.StringUtil;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

final class TectonicFileLogger implements DefaultLogger {

    private static TectonicFileLogger instance;
    private final Logger logger;
    private final Path path;

    private TectonicFileLogger(Path path) {
        this.logger = Logger.getLogger(TectonicFileLogger.class.getName());
        this.path = path;

        logger.addHandler(createFileHandler());
    }

    public static TectonicFileLogger logger(Path path) {
        if (instance == null) {
            instance = new TectonicFileLogger(path);
        }
        return instance;
    }

    @Override
    public void info(String template, Object... args) {
        logger.info(StringUtil.format(template, args));
    }

    @Override
    public void warn(String template, Object... args) {
        logger.warning(StringUtil.format(template, args));
    }

    @Override
    public void error(String template, Object... args) {
        logger.severe(StringUtil.format(template, args));
    }

    private FileHandler createFileHandler() {
        FileHandler fileHandler = null;

        try {
            var directory = new File(path.toFile().getAbsolutePath());
            directory.mkdirs();

            fileHandler = new FileHandler(new File(directory, "log_%g.log").getAbsolutePath(), 4098, 20, true);
            fileHandler.setEncoding(StandardCharsets.UTF_8.name());
            fileHandler.setFilter(DefaultLogger.filter());
            fileHandler.setFormatter(DefaultLogger.formatter());

        } catch (IOException exception) {
            logger.severe("Could not create file handler: " + exception.getMessage());
        }

        return fileHandler;
    }
}
