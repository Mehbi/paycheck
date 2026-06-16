package click.mevi.dev.paycheck.services;

import click.mevi.dev.PlaceholderAPI.ReplaceForMe;
import click.mevi.dev.PlaceholderAPI.api.objects.PlaceholderContext;
import click.mevi.dev.paycheck.utils.PluginConfig;
import org.bukkit.entity.Player;

public class ConfigService {
    private final PluginConfig pluginConfig;
    private final ReplaceForMe replaceForMe;
    public ConfigService(PluginConfig pluginConfig, ReplaceForMe replaceForMe) {
        this.pluginConfig = pluginConfig;
        this.replaceForMe = replaceForMe;
    }
    public String parseCommand(Player player, double amount) {
        PlaceholderContext placeholderContext = new PlaceholderContext();

        placeholderContext.set("username", player.getName());
        placeholderContext.set("amount", amount);

        String commandParsed = replaceForMe.parseText(pluginConfig.command(), placeholderContext);

        return commandParsed;
    }
}
