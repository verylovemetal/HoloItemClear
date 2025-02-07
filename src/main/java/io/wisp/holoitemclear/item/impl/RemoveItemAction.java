package io.wisp.holoitemclear.item.impl;

import io.wisp.holoitemclear.data.DroppedItemTracker;
import io.wisp.holoitemclear.item.IActionItem;
import lombok.Getter;
import org.bukkit.entity.Item;

public class RemoveItemAction implements IActionItem {

    @Getter
    private static final RemoveItemAction instance = new RemoveItemAction();

    @Override
    public void onAction(Item item) {
        DroppedItemTracker droppedItemTracker = DroppedItemTracker.getInstance();
        droppedItemTracker.removeItem(item);
    }
}
