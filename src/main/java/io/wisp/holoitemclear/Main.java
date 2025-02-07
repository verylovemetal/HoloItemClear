package io.wisp.holoitemclear;

import io.wisp.holoitemclear.command.PluginCommands;
import io.wisp.holoitemclear.data.DroppedItemTracker;
import io.wisp.holoitemclear.listener.ItemListener;
import io.wisp.holoitemclear.task.ClearTask;
import lombok.Getter;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Iterator;
import java.util.Map;

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
        DroppedItemTracker droppedItemTracker = DroppedItemTracker.getInstance();
        Iterator<Map.Entry<Entity, Integer>> iterator = droppedItemTracker.getDroppedItems().entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<Entity, Integer> entry = iterator.next();
            Entity item = entry.getKey();
            item.remove();
            iterator.remove();
            droppedItemTracker.removeItem(item);
        }
    }

    private void initRunnable() {
        ClearTask clearTimer = new ClearTask();
        clearTimer.runTaskTimer(this, 20L, 20L);
    }

    private void registerListener() {
        getServer().getPluginManager().registerEvents(new ItemListener(), this);
    }

    private void registerCommand() {
        getCommand("holoitemclear").setExecutor(new PluginCommands());
    }
}