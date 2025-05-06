package io.wisp.holoitemclear.command.impl.sub;

import io.wisp.holoitemclear.Main;
import io.wisp.holoitemclear.command.type.ISubCommand;
import io.wisp.holoitemclear.command.type.impl.PluginSubCommand;
import io.wisp.holoitemclear.config.CommonConfig;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

@ISubCommand(argumentsLength = 0, requiredArguments = "reload")
public class ReloadConfigSub extends PluginSubCommand {

    public ReloadConfigSub() {
        super("holoitemclear");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        UUID playerUUID = player.getUniqueId();

        Main.getInstance().reloadConfig();
        CommonConfig.CONFIG_RELOAD_MESSAGE.getProvider().sendMessage(playerUUID);
    }
}
