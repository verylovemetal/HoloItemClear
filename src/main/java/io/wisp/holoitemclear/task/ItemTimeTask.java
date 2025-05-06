package io.wisp.holoitemclear.task;

import io.wisp.holoitemclear.config.CommonConfig;
import io.wisp.holoitemclear.util.item.EffectUtil;
import io.wisp.holoitemclear.util.item.ItemUtil;
import io.wisp.holoitemclear.tracker.DroppedItemTracker;
import io.wisp.holoitemclear.util.color.ColorUtil;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
public class ItemTimeTask extends BukkitRunnable {

    @Override
    public void run() {
        Iterator<Map.Entry<UUID, Integer>> iterator = DroppedItemTracker.getInstance().getDroppedItems().entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<UUID, Integer> entry = iterator.next();
            UUID itemUUID = entry.getKey();
            int timeLeft = entry.getValue();

            Entity entityItem = Bukkit.getEntity(itemUUID);
            if (!(entityItem instanceof Item item)) continue;

            item.getLocation().getChunk().load();

            boolean isHologramAfterTime = CommonConfig.HOLOGRAM_AFTER_TIME_ENABLE.getProvider().getValue();
            if (isHologramAfterTime) {
                int hologramActivationTime = CommonConfig.HOLOGRAM_AFTER_TIME_ACTIVATION.getProvider().getValue();
                if (timeLeft > hologramActivationTime) {
                    DroppedItemTracker.getInstance().addData(itemUUID, timeLeft - 1);
                    continue;
                }
            }

            if (timeLeft <= 0) {
                EffectUtil.initEffects(item);
                item.remove();
                iterator.remove();
            } else {
                DroppedItemTracker.getInstance().addData(itemUUID, timeLeft - 1);
                item.setCustomName(ColorUtil.format(ItemUtil.getFormattedItemText(timeLeft - 1, item)));
            }

            item.getLocation().getChunk().unload();
        }
    }
}
