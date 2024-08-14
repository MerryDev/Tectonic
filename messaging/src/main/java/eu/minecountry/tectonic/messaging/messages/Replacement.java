package eu.minecountry.tectonic.messaging.messages;

import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Locale;

public final class Replacement {

    private Replacement() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static TagResolver create(String key, String value) {
        return Placeholder.parsed(sanitizeKey(key), value);
    }

    public static TagResolver replacement(String key, String value) {
        return create(key, value);
    }

    public static TagResolver create(String key, Object value) {
        return Placeholder.parsed(sanitizeKey(key), String.valueOf(value));
    }

    public static TagResolver replacement(String key, Object value) {
        return create(key, value);
    }

    public static TagResolver create(String key, Double value) {
        return create(key, String.format("%.2f", value));
    }

    public static TagResolver create(String key, Float value) {
        return create(key, String.format("%.2f", value));
    }

    public static TagResolver number(String key, Double value) {
        return create(key, value);
    }

    public static TagResolver number(String key, Float value) {
        return create(key, value);
    }

    public static TagResolver create(String key, Enum<?> anEnum) {
        return create(key, anEnum.name());
    }

    public static TagResolver name(String key, Enum<?> anEnum) {
        return create(key, anEnum);
    }

    public static TagResolver create(String key, Player player) {
        return create(key, player.getName());
    }

    public static TagResolver player(String key, Player player) {
        return create(key, player);
    }

    public static TagResolver create(String key, World world) {
        return create(key, world.getName());
    }

    public static TagResolver world(String key, World world) {
        return create(key, world);
    }

    private static String sanitizeKey(String key) {
        return key.toLowerCase(Locale.ROOT).replaceAll(" ", "_");
    }
}
