package io.wisp.holoitemclear.command.impl;

import io.wisp.holoitemclear.Main;
import io.wisp.holoitemclear.command.impl.sub.FloorClearSub;
import io.wisp.holoitemclear.command.impl.sub.ReloadConfigSub;
import io.wisp.holoitemclear.command.type.ICommand;
import io.wisp.holoitemclear.command.type.impl.PluginSuperCommand;
import io.wisp.holoitemclear.config.CommonConfig;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

@ICommand(aliases = "hic", permission = "holoitemclear.admin")
public class HoloItemClearCommand extends PluginSuperCommand {

    public HoloItemClearCommand(Main plugin) {
        super("holoitemclear", "holoitemclear.admin", plugin);
        registerSubCommand(new FloorClearSub());
        registerSubCommand(new ReloadConfigSub());
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        UUID playerUUID = player.getUniqueId();
        CommonConfig.USAGE_MESSAGE.getProvider().sendMessage(playerUUID);
    }
}
