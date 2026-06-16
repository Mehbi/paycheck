package click.mevi.dev.paycheck.utils;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public record ItemConfig(Material material, String displayName, int customModelDataId, List<String> lore) {
}
