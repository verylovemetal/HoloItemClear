package io.wisp.holoitemclear.listener.item;

import io.wisp.holoitemclear.item.impl.AddItemAction;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;

public class SpawnItemListener implements Listener {

    @EventHandler
    public void onItemSpawn(ItemSpawnEvent event) {
        Item droppedItem = event.getEntity();
        AddItemAction.getInstance().onAction(droppedItem);
    }
}
