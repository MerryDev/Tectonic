package eu.minecountry.tectonic.core.logger;

import java.nio.file.Path;

final class TectonicLoggerBuilder implements DefaultLogger.Builder {

    @Override
    public DefaultLogger build() {
        return TectonicLogger.logger();
    }

    @Override
    public DefaultLogger.Builder toFile(Path path) {
        return new TectonicFileLoggerBuilder(path);
    }
}
