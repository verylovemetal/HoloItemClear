package io.wisp.holoitemclear.command.impl.sub;

import io.wisp.holoitemclear.command.type.ISubCommand;
import io.wisp.holoitemclear.command.type.impl.PluginSubCommand;
import io.wisp.holoitemclear.config.CommonConfig;
import io.wisp.holoitemclear.util.item.ItemUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

@ISubCommand(argumentsLength = 0, requiredArguments = "clear")
public class FloorClearSub extends PluginSubCommand {

    public FloorClearSub() {
        super("holoitemclear");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        UUID playerUUID = player.getUniqueId();

        ItemUtil.floorClear();
        CommonConfig.ON_CLEAR_MESSAGE.getProvider().sendMessage(playerUUID);
    }
}
