package io.wisp.holoitemclear.listener.item;

import io.wisp.holoitemclear.item.impl.RemoveItemAction;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

public class RemoveItemListener implements Listener {

    @EventHandler
    public void onTakeItem(EntityPickupItemEvent event) {
        Item takedItem = event.getItem();
        RemoveItemAction.getInstance().onAction(takedItem);
    }
}
