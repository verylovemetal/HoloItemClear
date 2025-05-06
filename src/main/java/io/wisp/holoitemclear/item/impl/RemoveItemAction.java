package io.wisp.holoitemclear.item.impl;

import io.wisp.holoitemclear.tracker.DroppedItemTracker;
import io.wisp.holoitemclear.item.IActionItem;
import lombok.Getter;
import org.bukkit.entity.Item;

public class RemoveItemAction implements IActionItem {

    @Getter
    private static final RemoveItemAction instance = new RemoveItemAction();

    @Override
    public void onAction(Item item) {
        DroppedItemTracker.getInstance().removeData(item.getUniqueId());
    }
}
