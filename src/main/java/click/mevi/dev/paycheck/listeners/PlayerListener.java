package click.mevi.dev.paycheck.listeners;

import click.mevi.dev.paycheck.PaycheckPlugin;
import click.mevi.dev.paycheck.objects.Paycheck;
import click.mevi.dev.paycheck.providers.ConfigProvider;
import click.mevi.dev.paycheck.services.PaycheckService;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerListener implements Listener {
    private final ConfigProvider configProvider;
    private final PaycheckService paycheckService;
    private final PaycheckPlugin paycheckPlugin;

    public PlayerListener(ConfigProvider configProvider, PaycheckService paycheckService, PaycheckPlugin paycheckPlugin) {
        this.configProvider = configProvider;
        this.paycheckService = paycheckService;
        this.paycheckPlugin = paycheckPlugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player whoClicked = event.getPlayer();
        ItemStack itemStack = whoClicked.getInventory().getItemInMainHand();
        ItemMeta itemMeta = itemStack.getItemMeta();
        Material itemMaterial = configProvider.getPluginConfig().itemConfig().material();

        if (itemStack.getType().equals(itemMaterial) && itemMeta.getCustomModelData() == configProvider.getPluginConfig().itemConfig().customModelDataId()) {
            Paycheck paycheck = paycheckService.fromItem(paycheckPlugin, itemStack);

            paycheckService.execute(whoClicked, paycheck);
        }
    }

}
