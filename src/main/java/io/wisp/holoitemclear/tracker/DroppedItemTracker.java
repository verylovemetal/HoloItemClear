package io.wisp.holoitemclear.tracker;

import lombok.Getter;
import org.bukkit.entity.Entity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;

@Getter
public class DroppedItemTracker {

    @Getter
    private static final DroppedItemTracker instance = new DroppedItemTracker();

    private final Map<UUID, Integer> droppedItems = new HashMap<>();

    public void addItem(UUID itemUUID, int time) {
        droppedItems.put(itemUUID, time);
    }

    public void removeItem(UUID itemUUID) {
        droppedItems.remove(itemUUID);
    }

    public void setItemTime(UUID itemUUID, int time) {
        droppedItems.put(itemUUID, time);
    }
}
