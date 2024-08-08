package me.lele.elytraFlight.listener;

import me.lele.elytraFlight.ElytraFlight;
import me.lele.elytraFlight.task.ElytraDurabilityTask;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

import java.util.HashMap;
import java.util.Map;

public class PlayerToggleFlightListener implements Listener {

    private final ElytraFlight plugin;
    private final Map<Player, ElytraDurabilityTask> taskMap = new HashMap<>();

    public PlayerToggleFlightListener(ElytraFlight plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerToggleFlight(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();
        if (!player.isFlying()) {
            startDurabilityTask(player);
        } else {
            stopDurabilityTask(player);
        }
    }

    @EventHandler
    public void onPlayerItemBreak(PlayerItemBreakEvent event) {
        if (event.getBrokenItem().getType() == Material.ELYTRA) {
            Player player = event.getPlayer();
            stopDurabilityTask(player);
            player.setAllowFlight(false);
            player.setFlying(false);
            player.sendMessage(ChatColor.AQUA + "[飞行系统] " + ChatColor.WHITE + "你的鞘翅已经损坏，飞行模式已关闭！");
        }
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();

            if (!player.getAllowFlight())
                return;
            if (event.getSlotType() != InventoryType.SlotType.ARMOR)
                return;
            if (event.getCurrentItem() == null)
                return;
            if (event.getCurrentItem().getType() != Material.ELYTRA)
                return;
            stopDurabilityTask(player);
            player.setAllowFlight(false);
            player.setFlying(false);
            player.sendMessage(ChatColor.AQUA + "[飞行系统] " + ChatColor.WHITE + "你脱下了鞘翅，飞行模式已关闭！");
        }
    }

    private void startDurabilityTask(Player player) {
        if (!taskMap.containsKey(player)) {
            ElytraDurabilityTask task = new ElytraDurabilityTask(player);
            task.runTaskTimer(plugin, 0L, 20L); // 每秒检查一次鞘翅耐久
            taskMap.put(player, task);
        }
    }

    private void stopDurabilityTask(Player player) {
        ElytraDurabilityTask task = taskMap.remove(player);
        if (task != null) {
            task.cancel();
        }
    }

}
