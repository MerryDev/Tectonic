package eu.minecountry.tectonic.messaging.messages;

import eu.minecountry.tectonic.core.localization.ILocalizer;
import eu.minecountry.tectonic.core.logger.Logger;
import eu.minecountry.tectonic.messaging.localization.IMessageComposer;
import eu.minecountry.tectonic.messaging.messages.conversion.MiniMessageConversion;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.Context;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.kyori.adventure.title.Title;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class MessageSender {

    private static final Map<Class<? extends Plugin>, MessageSender> PLUGIN_SENDER = new HashMap<>();
    private final Class<? extends Plugin> ownerPlugin;
    private final Plugin plugin;
    private final Logger logger;
    private MiniMessage miniMessage;
    private TagResolver messageTagResolver;
    private TagResolver errorTagResolver;
    private Component prefix;

    public MessageSender(Plugin plugin, MiniMessage miniMessage, TagResolver messageTagResolver, TagResolver errorTagResolver, Component prefix) {
        this.ownerPlugin = plugin.getClass();
        this.plugin = plugin;
        this.miniMessage = miniMessage;
        this.messageTagResolver = messageTagResolver;
        this.errorTagResolver = errorTagResolver;
        this.prefix = prefix;

        this.logger = Logger.builder().toFile(new File(plugin.getDataFolder() + "/logs/").toPath()).build();
    }

    public abstract Audience asAudience(Player player);

    public abstract void sendMessage(CommandSender sender, Component component);

    public abstract void broadcast(String message);

    public abstract void sendTitle(Player player, Title title);

    public abstract void sendActionBar(Player player, String message, TagResolver... placeholder);

    public abstract void sendErrorActionBar(Player player, String message, TagResolver... placeholder);

    public abstract void sendBossBar(Player player, BossBar bossBar);

    public abstract BossBar sendBossBar(Player player, String message, float progress, BossBar.Color color, BossBar.Overlay overlay, Set<BossBar.Flag> flags);

    public abstract void hideBossBar(Player player, BossBar bossBar);

    public static MessageSenderBuilder builder(Plugin plugin) {
        return new MessageSenderBuilder(plugin);
    }

    public static void register(MessageSender sender) {
        if (sender.ownerPlugin == null) {
            return;
        }
        PLUGIN_SENDER.put(sender.ownerPlugin, sender);
    }

    public static MessageSender pluginMessageSender(Plugin plugin) {
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin cannot be null");
        }
        return pluginMessageSender(plugin.getClass());
    }

    public static MessageSender pluginMessageSender(Class<? extends Plugin> plugin) {
        if (!PLUGIN_SENDER.containsKey(plugin)) {
            throw new IllegalStateException("No message sender was created for " + plugin.getName());
        }
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin cannot be null");
        }
        return PLUGIN_SENDER.get(plugin);
    }

    public void sendMessage(CommandSender sender, String message, TagResolver... placeholder) {
        sendMessage(sender, serialize(sender, message, messageTagResolver, placeholder));
    }

    public void sendMessage(CommandSender sender, IMessageComposer composer) {
        sendMessage(sender, serialize(sender, composer.build(), messageTagResolver, composer.replacements().toArray(new TagResolver[0])));
    }

    public void sendError(CommandSender sender, String message, TagResolver... placeholder) {
        sendMessage(sender, serialize(sender, message, errorTagResolver, placeholder));
    }

    public void sendError(CommandSender sender, IMessageComposer composer) {
        sendMessage(sender, serialize(sender, composer.build(), errorTagResolver, composer.replacements().toArray(new TagResolver[0])));
    }

    public void sendTitle(Player player, String title, String subtitle, Title.Times times, TagResolver... placeholder) {
        sendTitle(player, Title.title(serialize(player, title, messageTagResolver, placeholder), serialize(player, subtitle, messageTagResolver, placeholder), times));
    }

    public String translatePlain(String message, TagResolver... replacements) {
        return translatePlain(null, message, replacements);
    }

    public String translatePlain(CommandSender sender, String message, TagResolver... replacements) {
        return PlainTextComponentSerializer.plainText().serialize(serialize(sender, message, messageTagResolver, replacements));
    }

    public Component serializeMessage(String message, TagResolver... placeholder) {
        return serializeMessage(null, message, placeholder);
    }

    public Component serializeMessage(CommandSender sender, String message, TagResolver... placeholder) {
        return serialize(sender, message, messageTagResolver, placeholder);
    }

    public Component serializeError(String message, TagResolver... placeholder) {
        return serializeError(null, message, placeholder);
    }

    public Component serializeError(CommandSender sender, String message, TagResolver... placeholder) {
        return serialize(sender, message, errorTagResolver, placeholder);
    }

    public MiniMessage miniMessage() {
        return miniMessage;
    }

    public boolean anonymous() {
        return ownerPlugin == null;
    }

    public Component prefix() {
        return prefix;
    }

    protected TagResolver messageTagResolver() {
        return messageTagResolver;
    }

    protected TagResolver errorTagResolver() {
        return errorTagResolver;
    }

    protected Plugin plugin() {
        return plugin;
    }

    protected Component applyPrefix(Component component) {
        return prefix.appendSpace().append(component);
    }

    protected Component serialize(CommandSender sender, String message, TagResolver resolver, TagResolver... placeholder) {
        var converted = MiniMessageConversion.convertLegacyColorCodes(message);
        if (!converted.equals(message)) {
            logger.warn("Found legacy color codes in message.");
            logger.warn(message);
            message = converted;
        }
        if (ILocalizer.localeCode(message)) {
            message = ILocalizer.escape(message);
        }

        message = "<default>" + message;
        var finalResolver = new TagResolver[]{resolver};
        if (placeholder.length > 0) {
            var tags = Arrays.copyOf(placeholder, placeholder.length + 1);
            tags[tags.length - 1] = resolver;
            finalResolver = tags;
        }
        return resolveTags(message, addI18nTag(sender, TagResolver.resolver(finalResolver)));
    }

    private Component resolveTags(String message, TagResolver... resolver) {
        var component = miniMessage.deserialize(message, resolver);
        var newMessage = miniMessage.serialize(component);

        if (newMessage.equals(message)) {
            return component;
        }
        return resolveTags(newMessage, resolver);
    }

    private MessageSender update(MiniMessage miniMessage, TagResolver messageTagResolver, TagResolver errorTagResolver, Component prefix) {
        this.miniMessage = miniMessage;
        this.messageTagResolver = messageTagResolver;
        this.errorTagResolver = errorTagResolver;
        this.prefix = prefix;
        return this;
    }

    private ILocalizer localizer() {
        return ILocalizer.pluginLocalizer(ownerPlugin);
    }

    private TagResolver addI18nTag(CommandSender sender, TagResolver resolvers) {
        if (localizer() != ILocalizer.DEFAULT) {
            return TagResolver.resolver(resolvers, TagResolver.builder().tag("i18n", (argumentQueue, context) -> localizeTag(sender, argumentQueue, context)).build());
        }
        return resolvers;
    }

    private Tag localizeTag(CommandSender sender, ArgumentQueue args, Context context) {
        return Tag.selfClosingInserting(context.deserialize(localizer().localize(sender, args.popOr("locale tag required").value())));
    }
}
