package io.wisp.holoitemclear.util.item;
import io.wisp.holoitemclear.Main;
import io.wisp.holoitemclear.config.CommonConfig;
import io.wisp.holoitemclear.tracker.DroppedItemTracker;
import lombok.experimental.UtilityClass;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@UtilityClass
public class ItemUtil {

    public void floorClear() {
        DroppedItemTracker tracker = DroppedItemTracker.getInstance();
        Iterator<Map.Entry<UUID, Integer>> iterator = tracker.getDroppedItems().entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<UUID, Integer> entry = iterator.next();
            UUID itemUUID = entry.getKey();

            Entity entity = Bukkit.getEntity(itemUUID);
            if (!(entity instanceof Item item)) continue;

            item.remove();
            iterator.remove();
        }
    }

    public String getFormattedItemText(int timeLeft, Item item) {
        return CommonConfig.ITEM_TEXT.getProvider()
                .withPlaceholder("%time%", timeLeft)
                .withPlaceholder("%amount%", item.getItemStack().getAmount())
                .getValue();
    }
}
