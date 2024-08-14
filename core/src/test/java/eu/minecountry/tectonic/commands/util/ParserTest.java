package eu.minecountry.tectonic.commands.util;

import eu.minecountry.tectonic.util.Parser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ParserTest {

    @Test
    @DisplayName("Parse Integer from String")
    void parseInt() {
        Parser.parseInt("4").ifPresent(parsed -> assertEquals(4, parsed));
    }

    @Test
    @DisplayName("Parse Double from String")
    void parseDouble() {
        Parser.parseDouble("3").ifPresent(parsed -> assertEquals(3.0D, parsed));
        Parser.parseDouble("3,14").ifPresent(parsed -> assertEquals(3.14D, parsed));
    }

    @Test
    @DisplayName("Parse Long from String")
    void parseLong() {
        Parser.parseLong("4").ifPresent(parsed -> assertEquals(4L, parsed));
        Parser.parseLong("3,14").ifPresent(parsed -> assertEquals(3L, parsed));
    }

    @Test
    @DisplayName("Parse standard Boolean from String")
    void parseBoolean() {
        Parser.parseBoolean("true").ifPresent(Assertions::assertTrue);
        Parser.parseBoolean("false").ifPresent(Assertions::assertFalse);
    }

    @Test
    @DisplayName("Parse custom Boolean from String")
    void testParseBoolean() {
        Parser.parseBoolean("yes", "yes", "no").ifPresent(Assertions::assertTrue);
        Parser.parseBoolean("no", "yes", "no").ifPresent(Assertions::assertFalse);

        assertTrue(Parser.parseBoolean("foo", "yes", "no").isEmpty());
    }
}