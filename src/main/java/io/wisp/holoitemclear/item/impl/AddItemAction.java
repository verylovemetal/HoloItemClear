package io.wisp.holoitemclear.item.impl;

import io.wisp.holoitemclear.Main;
import io.wisp.holoitemclear.config.CommonConfig;
import io.wisp.holoitemclear.tracker.DroppedItemTracker;
import io.wisp.holoitemclear.item.IActionItem;
import io.wisp.holoitemclear.util.ChatUtils;
import io.wisp.holoitemclear.util.ItemUtils;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Item;

import java.util.Set;

public class AddItemAction implements IActionItem {

    @Getter
    private static final AddItemAction instance = new AddItemAction();

    @Override
    public void onAction(Item item) {
        FileConfiguration config = Main.getInstance().getConfig();
        Set<String> itemsSettings = config.getConfigurationSection("items-settings").getKeys(false);

        int clearTime = CommonConfig.TIME_CLEAR_ITEM.getProvider().getValue();
        for (String itemSetting : itemsSettings) {
            if (item.getItemStack().getType() == Material.valueOf(itemSetting)) {
                clearTime = config.getInt("items-settings." + itemSetting + ".start-clear-item");
                break;
            }
        }

        if (clearTime <= 0) {
            return;
        }

        DroppedItemTracker.getInstance().addItem(item.getUniqueId(), clearTime);
        item.setCustomNameVisible(true);

        if (!(Boolean) CommonConfig.HOLOGRAM_AFTER_TIME_ENABLE.getProvider().getValue()) {
            return;
        }

        int hologramActivationTime = CommonConfig.HOLOGRAM_AFTER_TIME_ACTIVATION.getProvider().getValue();
        if (clearTime > hologramActivationTime) {
            return;
        }

        item.setCustomName(ItemUtils.getFormattedItemText(clearTime, item));
    }
}
