package me.lele.elytraFlight.command;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import me.lele.elytraFlight.ElytraFlight;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Command(name = "fly")
public class ElytraFlightCommand {

    private final ElytraFlight plugin;

    public ElytraFlightCommand(ElytraFlight plugin) {
        this.plugin = plugin;
    }

    @Execute
    public void reloadCommand(@Context CommandSender sender) {

            if (sender instanceof Player) {
                Player player = (Player) sender;
                ItemStack chestplate = player.getInventory().getChestplate();

                if (chestplate != null && chestplate.getType() == Material.ELYTRA) {
                    player.setAllowFlight(!player.getAllowFlight());
                    player.sendMessage(ChatColor.AQUA + "[飞行系统] " + ChatColor.WHITE + "飞行模式: " + (player.getAllowFlight() ? "开启" : "关闭"));

//                    if (player.getAllowFlight()) {
//                        new ElytraDurabilityTask(player).runTaskTimer(plugin, 0L, 20L); // 每秒检查一次鞘翅耐久
//                    }
                } else {
                    player.sendMessage(ChatColor.AQUA + "[飞行系统] " + ChatColor.WHITE + "你需要佩戴鞘翅才能使用飞行模式！");
                }
            }

    }

}
