package io.wisp.holoitemclear.listener;

import io.wisp.holoitemclear.item.impl.AddItemAction;
import io.wisp.holoitemclear.item.impl.RemoveItemAction;
import lombok.Setter;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

@Setter
public class ItemListener implements Listener {

    @EventHandler
    public void onItemSpawn(ItemSpawnEvent event) {
        Item droppedItem = event.getEntity();
       AddItemAction.getInstance().onAction(droppedItem);
    }

    @EventHandler
    public void onTakeItem(EntityPickupItemEvent event) {
        Item takedItem = event.getItem();
        RemoveItemAction.getInstance().onAction(takedItem);
    }
}
