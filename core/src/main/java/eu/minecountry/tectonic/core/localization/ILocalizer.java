package eu.minecountry.tectonic.core.localization;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public interface ILocalizer {

    Map<Class<? extends Plugin>, ILocalizer> LOCALIZER = new HashMap<>();
    ILocalizer DEFAULT = new DummyLocalizer();
    Pattern LOCALIZATION_CODE = Pattern.compile("([a-zA-Z0-9_\\-.]+?)\\\\.([a-zA-Z0-9_\\-.]+)");

    static ILocalizer pluginLocalizer(Plugin plugin) {
        if (plugin == null) {
            return DEFAULT;
        }
        return pluginLocalizer(plugin.getClass());
    }

    static ILocalizer pluginLocalizer(Class<? extends Plugin> plugin) {
        if (plugin == null) {
            return DEFAULT;
        }
        return LOCALIZER.getOrDefault(plugin, DEFAULT);
    }

    static String escape(String propertyKey) {
        return String.format("<i18n:%s>", propertyKey);
    }

    static boolean localeCode(String message) {
        return LOCALIZATION_CODE.matcher(message).matches();
    }

    void locale(String language);

    String message(String key);

    String[] includedLocales();

    void addLocaleCodes(Map<String, String> runtimeLocaleCodes);

    String message(String key, CommandSender sender);

    String message(String key, String message);

    String value(String key);

    String value(String key, CommandSender sender);

    String value(String key, String language);

    ResourceBundle localeBundle(String language);

    ResourceBundle defaultBundle();

    default String localize(String message) {
        return localize(null, message);
    }

    String localize(CommandSender sender, String message);

    void registerChild(ILocalizer localizer);

    class DummyLocalizer implements ILocalizer {
        @Override
        public void locale(String language) {
        }

        @Override
        public String message(String key) {
            return key;
        }

        @Override
        public String[] includedLocales() {
            return new String[0];
        }

        @Override
        public void addLocaleCodes(Map<String, String> runtimeLocaleCodes) {
        }

        @Override
        public String message(String key, CommandSender sender) {
            return key;
        }

        @Override
        public String message(String key, String message) {
            return key;
        }

        @Override
        public String value(String key) {
            return key;
        }

        @Override
        public String value(String key, CommandSender sender) {
            return key;
        }

        @Override
        public String value(String key, String language) {
            return key;
        }

        @Override
        public ResourceBundle localeBundle(String language) {
            return null;
        }

        @Override
        public ResourceBundle defaultBundle() {
            return null;
        }

        @Override
        public String localize(CommandSender sender, String message) {
            return message;
        }

        @Override
        public void registerChild(ILocalizer localizer) {
        }
    }

}
