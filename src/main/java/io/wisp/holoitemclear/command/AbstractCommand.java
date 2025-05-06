package io.wisp.holoitemclear.command;

import io.wisp.holoitemclear.Main;
import io.wisp.holoitemclear.config.CommonConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class AbstractCommand extends Command {

    private final String permission;
    protected final Main plugin;

    protected AbstractCommand(String commandName, String permission, Main plugin) {
        super(commandName);

        this.permission = permission;
        this.plugin = plugin;
    }

    public void attemptRunCommand(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) return;

        if (!permission.isEmpty()) {
            if (!player.hasPermission(permission)) {
                CommonConfig.NO_PERMISSION.getProvider().sendMessage(player.getUniqueId());
                return;
            }
        }

        onCommand(sender, args);
    }

    public abstract void onCommand(CommandSender sender, String[] args);
}
