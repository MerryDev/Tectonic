package eu.minecountry.tectonic.core.logger;

import java.util.Set;
import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class LogFilter implements Filter {

    private final Set<Level> levels = Set.of(Level.INFO, Level.WARNING, Level.SEVERE);

    @Override
    public boolean isLoggable(LogRecord record) {
        return levels.contains(record.getLevel());
    }
}
