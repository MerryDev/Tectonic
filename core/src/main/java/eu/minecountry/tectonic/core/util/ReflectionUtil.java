package eu.minecountry.tectonic.core.util;

public final class ReflectionUtil {

    public static boolean hasClass(String clazz) {
        try {
            Class.forName(clazz);
            return true;
        } catch (ClassNotFoundException exception) {
            return false;
        }
    }

    public static boolean isPaper() {
        return hasClass("io.papermc.paper.plugin.configuration.PluginMeta");
    }

}
