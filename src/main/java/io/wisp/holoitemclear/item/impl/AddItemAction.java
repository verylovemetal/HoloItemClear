package io.wisp.holoitemclear.item.impl;

import io.wisp.holoitemclear.Main;
import io.wisp.holoitemclear.config.CommonConfig;
import io.wisp.holoitemclear.tracker.DroppedItemTracker;
import io.wisp.holoitemclear.item.IActionItem;
import io.wisp.holoitemclear.util.item.ItemUtil;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Item;

import java.util.List;
import java.util.Set;

public class AddItemAction implements IActionItem {

    @Getter
    private static final AddItemAction instance = new AddItemAction();

    @Override
    public void onAction(Item item) {
        String worldName = item.getWorld().getName();
        List<String> blacklistedWorlds = CommonConfig.WORLD_BLACKLIST.getProvider().getValue();

        if (blacklistedWorlds.contains(worldName)) return;

        FileConfiguration config = Main.getInstance().getConfig();
        int clearTime = CommonConfig.TIME_CLEAR_ITEM.getProvider().getAsInt();

        String materialName = item.getItemStack().getType().name();
        if (config.isSet("items-settings." + materialName + ".start-clear-item")) {
            clearTime = config.getInt("items-settings." + materialName + ".start-clear-item");
        }

        if (clearTime <= 0) return;

        DroppedItemTracker.getInstance().addData(item.getUniqueId(), clearTime);
        item.setCustomNameVisible(true);

        if (!CommonConfig.HOLOGRAM_AFTER_TIME_ENABLE.getProvider().getAsBoolean()) return;

        int hologramActivationTime = CommonConfig.HOLOGRAM_AFTER_TIME_ACTIVATION.getProvider().getValue();
        if (clearTime > hologramActivationTime) return;

        item.setCustomName(ItemUtil.getFormattedItemText(clearTime, item));
    }
}
