package eu.minecountry.tectonic.core.logger;

import java.nio.file.Path;

final class TectonicFileLoggerBuilder implements DefaultLogger.Builder {

    private final Path path;

    public TectonicFileLoggerBuilder(Path path) {
        this.path = path;
    }

    @Override
    public DefaultLogger build() {
        return TectonicFileLogger.logger(path);
    }

    @Override
    public DefaultLogger.Builder toFile(Path path) {
        return this;
    }
}
