package io.wisp.holoitemclear.task;

import io.wisp.holoitemclear.Main;
import io.wisp.holoitemclear.config.CommonConfig;
import io.wisp.holoitemclear.data.DroppedItemTracker;
import io.wisp.holoitemclear.system.ParticleSystem;
import io.wisp.holoitemclear.utils.ChatUtils;
import lombok.Setter;
import org.bukkit.Chunk;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Setter
public class ClearTask extends BukkitRunnable {

    @Override
    public void run() {
        DroppedItemTracker droppedItemTracker = DroppedItemTracker.getInstance();
        Iterator<Map.Entry<Entity, Integer>> iterator = droppedItemTracker.getDroppedItems().entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<Entity, Integer> entry = iterator.next();
            Entity item = entry.getKey();
            int itemTime = entry.getValue();

            if (itemTime <= 0) {
                boolean useParticleOnClear = CommonConfig.USE_PARTICLE_ON_CLEAR.getProvider().getValue();
                if (useParticleOnClear) {
                    ParticleSystem.playerParticle(item.getLocation());
                }

                boolean useSoundOnClear = CommonConfig.USE_SOUND_ON_CLEAR.getProvider().getValue();
                if (useSoundOnClear) {
                    double radius = Main.getInstance().getConfig().getDouble("sound-settings.radius");
                    double pitchOnClear = Main.getInstance().getConfig().getDouble("sound-settings.pitch-on-clear");
                    double volumeOnClear = Main.getInstance().getConfig().getDouble("sound-settings.volume-on-clear");

                    List<Entity> nearbyEntities = item.getNearbyEntities(radius, radius, radius);
                    nearbyEntities.forEach(entity -> {
                        if (!(entity instanceof Player player)) return;
                        player.playSound(
                                item.getLocation(),
                                Sound.valueOf(CommonConfig.SOUND_NAME_ON_CLEAR.getProvider().getValue()),
                                (float) volumeOnClear,
                                (float) pitchOnClear
                        );
                    });
                }

                Chunk itemChunk = item.getLocation().getChunk();
                itemChunk.load();

                item.remove();
                iterator.remove();
                droppedItemTracker.removeItem(item);
                itemChunk.unload();
            } else {
                droppedItemTracker.setTimeItem(item, itemTime - 1);
                item.setCustomName(ChatUtils.format(CommonConfig.ITEM_TEXT.getProvider().getValue()
                        .toString()
                        .replace("%time%", String.valueOf(itemTime - 1))));
            }
        }
    }

}
