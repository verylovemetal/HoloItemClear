package io.wisp.holoitemclear;

import io.wisp.holoitemclear.command.PluginCommands;
import io.wisp.holoitemclear.listener.ItemListener;
import io.wisp.holoitemclear.task.ItemTimeTask;
import io.wisp.holoitemclear.util.ItemUtils;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class Main extends JavaPlugin {

    @Getter
    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        registerListener();
        registerCommand();
        initRunnable();
    }

    @Override
    public void onDisable() {
        ItemUtils.floorItemsRemove();
    }

    private void initRunnable() {
        ItemTimeTask clearTimer = new ItemTimeTask();
        clearTimer.runTaskTimer(this, 20L, 20L);
    }

    private void registerListener() {
        getServer().getPluginManager().registerEvents(new ItemListener(), this);
    }

    private void registerCommand() {
        getCommand("holoitemclear").setExecutor(new PluginCommands());
    }
}