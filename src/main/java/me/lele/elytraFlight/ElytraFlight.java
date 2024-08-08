package me.lele.elytraFlight;

import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteCommandsBukkit;
import me.lele.elytraFlight.command.ElytraFlightCommand;
import me.lele.elytraFlight.listener.PlayerJoinEventListener;
import me.lele.elytraFlight.listener.PlayerToggleFlightListener;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class ElytraFlight extends JavaPlugin {

    private LiteCommands<CommandSender> liteCommands;

    @Override
    public void onEnable() {
        // Plugin startup logic

        // 加载指令
        loadCommand();

        // 加载功能
        loadFeatures();

        getLogger().info("插件加载完毕!");
    }

    @Override
    public void onDisable() {
        getLogger().info("插件已卸载!");
        // Plugin shutdown logic
    }

    private void loadCommand() {

        // 注册重载命令
        this.liteCommands = LiteCommandsBukkit.builder("ElytraFlight").commands(new ElytraFlightCommand(this)).build();

    }

    private void loadFeatures() {

        //加载监听器
        getServer().getPluginManager().registerEvents(new PlayerToggleFlightListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinEventListener(), this);

    }

}
