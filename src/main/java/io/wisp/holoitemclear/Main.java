package io.wisp.holoitemclear;

import io.wisp.holoitemclear.commands.PluginCommands;
import io.wisp.holoitemclear.data.DroppedItemData;
import io.wisp.holoitemclear.listener.PlayerListener;
import io.wisp.holoitemclear.runnable.ClearTimer;
import lombok.Getter;
import net.advancedplugins.ae.impl.utils.hooks.plugins.AdvancedEnchantmentsHook;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Iterator;
import java.util.Map;

@Getter
public final class Main extends JavaPlugin {

    @Getter
    private static Main instance;
    private DroppedItemData droppedItemData;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        AdvancedEnchantmentsHook advancedEnchantmentsHook = null;

        if(getServer().getPluginManager().isPluginEnabled("AdvancedEnchantments")) {
            Bukkit.getLogger().info("");
            Bukkit.getLogger().info(ChatColor.GREEN + "HIC " + ChatColor.GRAY + "* Найден AdvancedEnchantments для использования!");
            Bukkit.getLogger().info("");
            advancedEnchantmentsHook = new AdvancedEnchantmentsHook();
        }

        droppedItemData = new DroppedItemData();

        PlayerListener listener = new PlayerListener(droppedItemData, advancedEnchantmentsHook);
        getServer().getPluginManager().registerEvents(listener, this);

        ClearTimer clearTimer = new ClearTimer(droppedItemData);
        clearTimer.runTaskTimer(this, 20L, 20L);

        PluginCommands commands = new PluginCommands(droppedItemData);

        getCommand("holoitemclear").setExecutor(commands);
    }

    @Override
    public void onDisable() {
        Iterator<Map.Entry<Entity, Integer>> iterator = droppedItemData.getDroppedItems().entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<Entity, Integer> entry = iterator.next();
            Entity item = entry.getKey();
            item.remove();
            iterator.remove();
        }
    }
}