package click.mevi.dev.paycheck.services;

import click.mevi.dev.PlaceholderAPI.ReplaceForMe;
import click.mevi.dev.PlaceholderAPI.api.objects.PlaceholderContext;
import click.mevi.dev.paycheck.PaycheckPlugin;
import click.mevi.dev.paycheck.objects.Paycheck;
import click.mevi.dev.paycheck.utils.PluginConfig;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class PaycheckService {
    private final PluginConfig pluginConfig;
    private final ConfigService configService;
    private final ReplaceForMe placeholder;

    public PaycheckService(PluginConfig pluginConfig, ConfigService configService, ReplaceForMe placeholder) {
        this.pluginConfig = pluginConfig;
        this.configService = configService;
        this.placeholder = placeholder;
    }

    public Paycheck create(double amount) {
        return new Paycheck(amount);
    }
    public void giveItem(PaycheckPlugin plugin, Player player, Paycheck paycheck) {
        ItemStack itemStack = toItem(plugin, paycheck);

        PlayerInventory inventory = player.getInventory();

        inventory.addItem(itemStack);
    }
    private ItemStack toItem(PaycheckPlugin plugin, Paycheck paycheck) {
        ItemStack itemStack = new ItemStack(pluginConfig.itemConfig().material());
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.displayName(MiniMessage.miniMessage().deserialize(pluginConfig.itemConfig().displayName()));

        PlaceholderContext placeholderContext = new PlaceholderContext();
        placeholderContext.set("amount", paycheck.amount());

        List<Component> lore = pluginConfig.itemConfig().lore()
                .stream()
                .map(s -> placeholder.parseText(s, placeholderContext))
                .map(s -> MiniMessage.miniMessage().deserialize(s))
                .toList();

        itemMeta.lore(lore);
        itemMeta.setCustomModelData(pluginConfig.itemConfig().customModelDataId());

        NamespacedKey amountKey = new NamespacedKey(plugin, "amount");

        itemMeta.getPersistentDataContainer().set(amountKey, PersistentDataType.DOUBLE, paycheck.amount());

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }
    public Paycheck fromItem(PaycheckPlugin plugin, ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        NamespacedKey amountKey = new NamespacedKey(plugin, "amount");

        double amount = itemMeta.getPersistentDataContainer().get(amountKey, PersistentDataType.DOUBLE);

        return new Paycheck(amount);
    }


    public void execute(Player player, Paycheck paycheck) {
        String commandToExecute = configService.parseCommand(player, paycheck.amount());

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), commandToExecute);
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        itemStack.setAmount(itemStack.getAmount() - 1);
    }
}
