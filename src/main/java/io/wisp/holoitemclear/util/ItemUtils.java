package io.wisp.holoitemclear.util;
import io.wisp.holoitemclear.Main;
import io.wisp.holoitemclear.config.CommonConfig;
import io.wisp.holoitemclear.tracker.DroppedItemTracker;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@UtilityClass
public class ItemUtils {

    public void applyEffectsOnClear(Entity item) {
        if (CommonConfig.USE_PARTICLE_ON_CLEAR.getProvider().getValue()) {
            playItemParticle(item);
        }

        if (CommonConfig.USE_SOUND_ON_CLEAR.getProvider().getValue()) {
            playClearSound(item);
        }

        Chunk itemChunk = item.getLocation().getChunk();
        itemChunk.load();
        itemChunk.unload();
    }

    private void playClearSound(Entity item) {
        double radius = Main.getInstance().getConfig().getDouble("sound-settings.radius");
        float volume = (float) Main.getInstance().getConfig().getDouble("sound-settings.volume-on-clear");
        float pitch = (float) Main.getInstance().getConfig().getDouble("sound-settings.pitch-on-clear");
        Sound sound = Sound.valueOf(CommonConfig.SOUND_NAME_ON_CLEAR.getProvider().getValue());

        List<Entity> nearbyEntities = item.getNearbyEntities(radius, radius, radius);
        for (Entity entity : nearbyEntities) {
            if (entity instanceof Player player) {
                player.playSound(item.getLocation(), sound, volume, pitch);
            }
        }
    }

    public void playItemParticle(Entity item) {
        Location location = item.getLocation();
        World world = location.getWorld();
        if (world == null) return;

        String particleType = CommonConfig.PARTICLE_TYPE.getProvider().getValue();
        int particleCount = CommonConfig.PARTICLE_COUNT.getProvider().getValue();

        world.spawnParticle(Particle.valueOf(particleType), location, particleCount);
    }

    public void floorItemsRemove() {
        DroppedItemTracker droppedItemTracker = DroppedItemTracker.getInstance();
        Iterator<Map.Entry<UUID, Integer>> iterator = droppedItemTracker.getDroppedItems().entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<UUID, Integer> entry = iterator.next();
            UUID itemUUID = entry.getKey();
            Item item = (Item) Bukkit.getEntity(itemUUID);
            if (item == null) continue;

            item.remove();
            iterator.remove();
            droppedItemTracker.removeItem(item.getUniqueId());
        }
    }

    public String getFormattedItemText(int timeLeft, Item item) {
        return ((String) CommonConfig.ITEM_TEXT.getProvider().getValue())
                .replace("%time%", timeLeft + "")
                .replace("%amount%", item.getItemStack().getAmount() + "");
    }
}
