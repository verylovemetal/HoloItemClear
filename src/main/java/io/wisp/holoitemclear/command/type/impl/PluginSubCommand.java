package io.wisp.holoitemclear.command.type.impl;

import io.wisp.holoitemclear.command.type.ISubCommand;
import io.wisp.holoitemclear.config.CommonConfig;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Getter
public abstract class PluginSubCommand {

    private final String command;
    private final ISubCommand iSubCommand;

    public PluginSubCommand(String command) {
        this.command = command;
        this.iSubCommand = getClass().getAnnotation(ISubCommand.class);
    }

    public void attemptToRun(CommandSender sender, String[] args, String permission) {
        Player player = (Player) sender;

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
