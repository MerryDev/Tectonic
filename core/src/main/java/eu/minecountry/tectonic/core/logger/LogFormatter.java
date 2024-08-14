package eu.minecountry.tectonic.core.logger;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class LogFormatter extends Formatter {

    @Override
    public String format(LogRecord record) {
        var dateFormat = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy");
        dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));

        var formattedDate = dateFormat.format(Date.from(Instant.now()));

        return String.format("%s [%s] » %s \n", formattedDate, record.getLevel(), record.getMessage());
    }
}
