package eu.minecountry.tectonic.messaging.messages.impl;

import eu.minecountry.tectonic.messaging.messages.MessageSender;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.title.Title;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Set;

public final class SpigotMessageSender extends MessageSender {

    private BukkitAudiences audiences;

    public SpigotMessageSender(Plugin plugin, MiniMessage miniMessage, TagResolver messageTagResolver, TagResolver errorTagResolver, Component prefix) {
        super(plugin, miniMessage, messageTagResolver, errorTagResolver, prefix);

        this.audiences = BukkitAudiences.create(plugin);
    }

    @Override
    public Audience asAudience(Player player) {
        return audiences.player(player);
    }

    @Override
    public void sendMessage(CommandSender sender, Component component) {
        audiences.sender(sender).sendMessage(applyPrefix(component));
    }

    @Override
    public void broadcast(String message) {
        audiences.all().sendMessage(serialize(null, message, messageTagResolver()));
    }

    @Override
    public void sendTitle(Player player, Title title) {
        audiences.player(player).showTitle(title);
    }

    @Override
    public void sendActionBar(Player player, String message, TagResolver... placeholder) {
        audiences.player(player).sendActionBar(serialize(player, message, messageTagResolver(), placeholder));
    }

    @Override
    public void sendErrorActionBar(Player player, String message, TagResolver... placeholder) {
        audiences.player(player).sendActionBar(serialize(player, message, errorTagResolver(), placeholder));
    }

    @Override
    public void sendBossBar(Player player, BossBar bossBar) {
        audiences.player(player).showBossBar(bossBar);
    }

    @Override
    public BossBar sendBossBar(Player player, String message, float progress, BossBar.Color color, BossBar.Overlay overlay, Set<BossBar.Flag> flags) {
        var bossBar = BossBar.bossBar(serialize(player, message, messageTagResolver()), progress, color, overlay, flags);
        audiences.player(player).showBossBar(bossBar);
        return bossBar;
    }

    @Override
    public void hideBossBar(Player player, BossBar bossBar) {
        audiences.player(player).hideBossBar(bossBar);
    }
}
