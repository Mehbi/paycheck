package click.mevi.dev.paycheck.commands;

import click.mevi.dev.paycheck.PaycheckPlugin;
import click.mevi.dev.paycheck.objects.Paycheck;
import click.mevi.dev.paycheck.services.PaycheckService;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PaycheckCommandExecutor implements CommandExecutor {

    private final PaycheckPlugin plugin;
    private final PaycheckService paycheckService;

    public PaycheckCommandExecutor(PaycheckPlugin plugin, PaycheckService paycheckService) {
        this.plugin = plugin;
        this.paycheckService = paycheckService;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("paycheck.admin")) {
            sender.sendMessage(Component.text("No tienes permiso para usar esto", NamedTextColor.RED));
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "new" -> {
                if (args.length != 3) {
                    sender.sendMessage(Component.text("Use: /paycheck new <player> <amount>", NamedTextColor.RED));
                    return true;
                }

                Player target = Bukkit.getPlayer(args[1]);

                if (target == null) {
                    sender.sendMessage(Component.text("Player not online", NamedTextColor.RED));
                    return true;
                }

                double amount;

                try {
                    amount = Double.parseDouble(args[2]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(Component.text("The amount has to be valid", NamedTextColor.RED));
                    return true;
                }

                if (amount <= 0) {
                    sender.sendMessage("§cThe amount has to be longer than zero");
                    return true;
                }

                Paycheck paycheck = paycheckService.create(amount);

                paycheckService.giveItem(plugin, target, paycheck);

                sender.sendMessage(MiniMessage.miniMessage().deserialize("<green>Paycheck creado para <yellow>" + target.getName() + " por <gold>$" + amount + "</gold>.</green>"));
                target.sendMessage(MiniMessage.miniMessage().deserialize("<green>Has recibido un paycheck por <gold>" + amount + "</gold>.</green>"));

                return true;
            }

            default -> {
                sender.sendMessage("§cSubcomando desconocido.");
                sender.sendMessage("§cUso: /paycheck new <player> <amount>");
                return true;
            }
        }
    }
}
