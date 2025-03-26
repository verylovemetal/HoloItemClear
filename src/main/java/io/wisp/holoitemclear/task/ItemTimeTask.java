package io.wisp.holoitemclear.task;

import io.wisp.holoitemclear.config.CommonConfig;
import io.wisp.holoitemclear.util.ItemUtils;
import io.wisp.holoitemclear.tracker.DroppedItemTracker;
import io.wisp.holoitemclear.util.ChatUtils;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Iterator;
import java.util.Map;

@RequiredArgsConstructor
public class ItemTimeTask extends BukkitRunnable {

    @Override
    public void run() {
        Iterator<Map.Entry<Entity, Integer>> iterator = DroppedItemTracker.getInstance().getDroppedItems().entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<Entity, Integer> entry = iterator.next();
            Entity item = entry.getKey();
            int timeLeft = entry.getValue();

            boolean isHologramAfterTime = CommonConfig.HOLOGRAM_AFTER_TIME_ENABLE.getProvider().getValue();
            if (isHologramAfterTime) {
                int hologramActivationTime = CommonConfig.HOLOGRAM_AFTER_TIME_ACTIVATION.getProvider().getValue();

                if (timeLeft > hologramActivationTime) {
                    DroppedItemTracker.getInstance().setItemTime(item, timeLeft - 1);
                    return;
                }
            }

            if (timeLeft <= 0) {
                ItemUtils.applyEffectsOnClear(item);
                item.remove();
                iterator.remove();
                DroppedItemTracker.getInstance().removeItem(item);
            } else {
                DroppedItemTracker.getInstance().setItemTime(item, timeLeft - 1);
                item.setCustomName(ChatUtils.format(ItemUtils.getFormattedItemText(timeLeft - 1)));
            }
        }
    }
}
