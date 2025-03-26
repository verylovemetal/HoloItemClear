package io.wisp.holoitemclear.command;

import io.wisp.holoitemclear.Main;
import io.wisp.holoitemclear.config.CommonConfig;
import io.wisp.holoitemclear.tracker.DroppedItemTracker;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class PluginCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player player)) return true;
        UUID playerUUID = player.getUniqueId();

        if (!sender.hasPermission("holoitemclear.admin")) {
            CommonConfig.NO_PERMISSION.getProvider().sendMessage(playerUUID);
            return true;
        }

        if (args.length < 1) {
            CommonConfig.USAGE_MESSAGE.getProvider().sendMessage(playerUUID);
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            CommonConfig.CONFIG_RELOAD_MESSAGE.getProvider().sendMessage(playerUUID);
            Main.getInstance().reloadConfig();
            return true;
        }

        if (args[0].equalsIgnoreCase("clear")) {
            Iterator<Map.Entry<org.bukkit.entity.Entity, Integer>> iterator = DroppedItemTracker.getInstance().getDroppedItems().entrySet().iterator();

            while (iterator.hasNext()) {
                Map.Entry<org.bukkit.entity.Entity, Integer> entry = iterator.next();
                Entity item = entry.getKey();
                item.remove();
                iterator.remove();
            }

            CommonConfig.ON_CLEAR_MESSAGE.getProvider().sendMessage(playerUUID);
        }

        return true;
    }
}
