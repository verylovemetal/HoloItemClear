package io.wisp.holoitemclear.util;

import io.wisp.holoitemclear.Main;
import io.wisp.holoitemclear.config.CommonConfig;
import io.wisp.holoitemclear.tracker.DroppedItemTracker;
import lombok.experimental.UtilityClass;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

        String particleType = CommonConfig.PARTICLE_TYPE.getProvider().getValue();
        int particleCount = CommonConfig.PARTICLE_COUNT.getProvider().getValue();
        if (location.getWorld() == null) return;

        location.getWorld().spawnParticle(
                Particle.valueOf(particleType),
                location,
                particleCount);
    }

    public void floorItemRemove() {
        DroppedItemTracker droppedItemTracker = DroppedItemTracker.getInstance();
        Iterator<Map.Entry<Entity, Integer>> iterator = droppedItemTracker.getDroppedItems().entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<Entity, Integer> entry = iterator.next();
            Entity item = entry.getKey();

            item.remove();
            iterator.remove();
            droppedItemTracker.removeItem(item);
        }
    }

    public String getFormattedItemText(int timeLeft) {
        return CommonConfig.ITEM_TEXT.getProvider().getValue().toString().replace("%time%", String.valueOf(timeLeft));
    }
}
