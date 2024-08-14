package eu.minecountry.tectonic.commands.util;

import org.bukkit.plugin.Plugin;

public class Input {

    private final Plugin plugin;
    private final String arg;

    private Input(Plugin plugin, String arg) {
        this.plugin = plugin;
        this.arg = arg;
    }

    public static Input of(Plugin plugin, String arg) {
        return new Input(plugin, arg);
    }

    public String asString() {
        return arg;
    }
}
