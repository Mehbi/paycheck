package click.mevi.dev.paycheck.providers;

import click.mevi.dev.paycheck.PaycheckPlugin;
import click.mevi.dev.paycheck.utils.ItemConfig;
import click.mevi.dev.paycheck.utils.PluginConfig;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Item;

import java.util.List;

public class ConfigProvider {
    private PluginConfig pluginConfig;
    public ConfigProvider(PaycheckPlugin plugin) {
        plugin.saveDefaultConfig();

        FileConfiguration config = plugin.getConfig();

        String command = config.getString("command");
        Material itemMaterial = Material.valueOf(config.getString("item.material"));
        String displayName = config.getString("item.display-name");
        int customModelData = config.getInt("item.customModelData");
        List<String> lore = config.getStringList("item.lore");
        ItemConfig itemConfig = new ItemConfig(itemMaterial, displayName, customModelData, lore);

        this.pluginConfig = new PluginConfig(command, itemConfig);
    }

    public PluginConfig getPluginConfig() {
        return pluginConfig;
    }
}
