package io.wisp.holoitemclear.tracker;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class DroppedItemTracker {

    @Getter
    private static final DroppedItemTracker instance = new DroppedItemTracker();

    private final Map<UUID, Integer> droppedItems = new HashMap<>();

    public void addData(UUID itemUUID, int time) {
        droppedItems.put(itemUUID, time);
    }

    public void removeData(UUID itemUUID) {
        droppedItems.remove(itemUUID);
    }
}
