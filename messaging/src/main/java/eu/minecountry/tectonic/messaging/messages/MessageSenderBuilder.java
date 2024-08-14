package eu.minecountry.tectonic.messaging.messages;

import eu.minecountry.tectonic.core.localization.ILocalizer;
import eu.minecountry.tectonic.messaging.messages.impl.PaperMessageSender;
import eu.minecountry.tectonic.messaging.messages.impl.SpigotMessageSender;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.minimessage.tag.standard.StandardTags;
import org.bukkit.plugin.Plugin;

import java.util.function.Consumer;
import java.util.function.UnaryOperator;

import static eu.minecountry.tectonic.core.util.ReflectionUtil.isPaper;

public class MessageSenderBuilder {

    private final MiniMessage.Builder miniMessage = MiniMessage.builder();
    private final TagResolver.Builder messageTagResolver = TagResolver.builder().tag("default", Tag.styling(NamedTextColor.GREEN));
    private final TagResolver.Builder errorTagResolver = TagResolver.builder().tag("default", Tag.styling(NamedTextColor.RED));
    private final TagResolver.Builder defaultTagResolver = TagResolver.builder();

    private final Plugin plugin;
    private Component prefix = Component.empty();
    private ILocalizer localizer = ILocalizer.DEFAULT;
    private UnaryOperator<String> preProcessor = s -> s;

    public MessageSenderBuilder(Plugin plugin) {
        this.plugin = plugin;
    }

    public MessageSenderBuilder localizer(ILocalizer localizer) {
        this.localizer = localizer;
        return this;
    }

    public MessageSenderBuilder prefix(Component prefix) {
        this.prefix = prefix;
        defaultTagResolver.tag("prefix", Tag.selfClosingInserting(this.prefix));

        return this;
    }

    public MessageSenderBuilder prefix(String prefix) {
        return prefix(MiniMessage.miniMessage().deserialize(prefix));
    }

    public MessageSenderBuilder messageColor(TextColor color) {
        messageTagResolver.tag("default", Tag.styling(color));
        return this;
    }

    public MessageSenderBuilder errorColor(TextColor color) {
        errorTagResolver.tag("default", Tag.styling(color));
        return this;
    }

    public MessageSenderBuilder addTag(Consumer<TagResolver.Builder> consumer) {
        consumer.accept(defaultTagResolver);
        return this;
    }

    public MessageSenderBuilder addMessageTag(Consumer<TagResolver.Builder> consumer) {
        consumer.accept(messageTagResolver);
        return this;
    }

    public MessageSenderBuilder addErrorTag(Consumer<TagResolver.Builder> consumer) {
        consumer.accept(errorTagResolver);
        return this;
    }

    public MessageSenderBuilder preProcessor(UnaryOperator<String> preProcessor) {
        this.preProcessor = preProcessor;
        return this;
    }

    public MiniMessage.Builder strict(boolean strict) {
        return miniMessage.strict(strict);
    }

    public MiniMessage.Builder debug(Consumer<String> output) {
        return miniMessage.debug(output);
    }

    public MiniMessage.Builder postProcessor(UnaryOperator<Component> postProcessor) {
        return miniMessage.postProcessor(postProcessor);
    }

    public MessageSender register() {
        var defaultResolver = defaultTagResolver.resolver(StandardTags.defaults()).build();

        MessageSender messageSender;
        if (!isPaper()) {
            messageSender = new SpigotMessageSender(plugin,
                    miniMessage.tags(defaultResolver)
                            .preProcessor(in -> preProcessor.apply(localizer.localize(in)))
                            .build(),
                    TagResolver.resolver(defaultResolver, messageTagResolver.build()),
                    TagResolver.resolver(defaultResolver, errorTagResolver.build()),
                    prefix);
        } else {
            messageSender = new PaperMessageSender(plugin,
                    miniMessage.tags(defaultResolver)
                            .preProcessor(in -> preProcessor.apply(localizer.localize(in)))
                            .build(),
                    TagResolver.resolver(defaultResolver, messageTagResolver.build()),
                    TagResolver.resolver(defaultResolver, errorTagResolver.build()),
                    prefix);
        }
        MessageSender.register(messageSender);
        return messageSender;
    }
}
