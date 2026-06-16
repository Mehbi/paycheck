package click.mevi.dev.paycheck;

import click.mevi.dev.PlaceholderAPI.ReplaceForMe;
import click.mevi.dev.paycheck.commands.PaycheckCommandExecutor;
import click.mevi.dev.paycheck.listeners.PlayerListener;
import click.mevi.dev.paycheck.placeholders.MoneyAmountPlaceholder;
import click.mevi.dev.paycheck.placeholders.UsernamePlaceholder;
import click.mevi.dev.paycheck.providers.ConfigProvider;
import click.mevi.dev.paycheck.services.ConfigService;
import click.mevi.dev.paycheck.services.PaycheckService;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class PaycheckPlugin extends JavaPlugin {


    @Override
    public void onEnable() {
        ConfigProvider configProvider = new ConfigProvider(this);

        ReplaceForMe replaceForMe = ReplaceForMe.getInstance();

        replaceForMe.registerPlaceholder(new UsernamePlaceholder());
        replaceForMe.registerPlaceholder(new MoneyAmountPlaceholder());

        ConfigService configService = new ConfigService(configProvider.getPluginConfig(), replaceForMe);
        PaycheckService paycheckService = new PaycheckService(configProvider.getPluginConfig(), configService, replaceForMe);

        getCommand("paycheck").setExecutor(new PaycheckCommandExecutor(this, paycheckService));

        Bukkit.getPluginManager().registerEvents(new PlayerListener(configProvider, paycheckService, this), this);
        // Plugin startup logic
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
