package eu.minecountry.tectonic.commands.executor;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public interface ITabExecutor {

    void onCommand(@NotNull CommandSender sender, @NotNull String alias, @NotNull String args);

}
