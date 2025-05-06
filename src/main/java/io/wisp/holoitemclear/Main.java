package io.wisp.holoitemclear;

import io.wisp.holoitemclear.command.CommandController;
import io.wisp.holoitemclear.command.impl.HoloItemClearCommand;
import io.wisp.holoitemclear.command.type.impl.PluginSuperCommand;
import io.wisp.holoitemclear.listener.item.RemoveItemListener;
import io.wisp.holoitemclear.listener.item.SpawnItemListener;
import io.wisp.holoitemclear.task.ItemTimeTask;
import io.wisp.holoitemclear.util.item.ItemUtil;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

@Getter
public final class Main extends JavaPlugin {

    @Getter
    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        CommandController commandController = CommandController.getInstance();
        registerCommands().forEach(commandController::registerCommand);

        registerListener();
        initRunnable();
    }

    @Override
    public void onDisable() {
        ItemUtil.floorClear();
    }

    private void initRunnable() {
        ItemTimeTask clearTimer = new ItemTimeTask();
        clearTimer.runTaskTimer(this, 20L, 20L);
    }

    private void registerListener() {
        List.of(
                new SpawnItemListener(),
                new RemoveItemListener()
        ).forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, this));
    }

    private List<PluginSuperCommand> registerCommands() {
        return List.of(
                new HoloItemClearCommand(this)
        );
    }
}