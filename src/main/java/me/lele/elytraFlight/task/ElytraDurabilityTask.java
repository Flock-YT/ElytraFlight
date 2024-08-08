package me.lele.elytraFlight.task;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class ElytraDurabilityTask extends BukkitRunnable {

    private final Player player;

    public ElytraDurabilityTask(Player player) {
        this.player = player;
    }

    @Override
    public void run() {
        ItemStack elytra = player.getInventory().getChestplate();

        if (elytra != null && elytra.getType() == Material.ELYTRA) {
            if (player.isFlying()) {
                // 扣除耐久度
                if (!deductElytraDurability(elytra)) {
                    // 如果耐久度耗尽，则关闭飞行模式
                    player.setAllowFlight(false);
                    player.setFlying(false);
                    player.sendMessage("你的鞘翅已经损坏，飞行模式已关闭！");
                    cancel();
                }
            } else {
                // 如果玩家不再飞行，停止任务
                cancel();
            }
        } else {
            // 如果玩家脱下鞘翅，停止任务
            cancel();
        }
    }

    private boolean deductElytraDurability(ItemStack elytra) {
        if (elytra.getType() != Material.ELYTRA) {
            return false;
        }

        // 获取当前耐久度和最大耐久度
        short currentDurability = elytra.getDurability();
        short maxDurability = elytra.getType().getMaxDurability();

        // 获取Unbreaking附魔等级
        int unbreakingLevel = elytra.getEnchantmentLevel(Enchantment.UNBREAKING);

        // 计算耐久度扣除几率
        double chance = 1.0 / (unbreakingLevel + 1);

        // 检查是否减少耐久度
        if (Math.random() < chance) {
            currentDurability++;
        }

        // 更新鞘翅的耐久度
        elytra.setDurability(currentDurability);

        // 检查耐久度是否耗尽
        return currentDurability < maxDurability;
    }
}
