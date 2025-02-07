package io.wisp.holoitemclear.item.impl;

import io.wisp.holoitemclear.Main;
import io.wisp.holoitemclear.config.CommonConfig;
import io.wisp.holoitemclear.data.DroppedItemTracker;
import io.wisp.holoitemclear.item.IActionItem;
import io.wisp.holoitemclear.utils.ChatUtils;
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

        item.setCustomName(ChatUtils.format(CommonConfig.ITEM_TEXT.getProvider().getValue().toString()
                .replace("%time%", String.valueOf(clearTime))));
        item.setCustomNameVisible(true);

        DroppedItemTracker.getInstance().addItem(item, clearTime);
    }

}
