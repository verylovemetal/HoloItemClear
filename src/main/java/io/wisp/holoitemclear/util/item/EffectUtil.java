package io.wisp.holoitemclear.util.item;

import io.wisp.holoitemclear.Main;
import io.wisp.holoitemclear.config.CommonConfig;
import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.List;

@UtilityClass
public class EffectUtil {

    public void initEffects(Entity item) {
        if (CommonConfig.USE_PARTICLE_ON_CLEAR.getProvider().getValue()) {
            initializeParticle(item);
        }

        if (CommonConfig.USE_SOUND_ON_CLEAR.getProvider().getValue()) {
            initializeSound(item);
        }
    }

    private void initializeSound(Entity item) {
        FileConfiguration configuration = Main.getInstance().getConfig();

        double radius = configuration.getDouble("sound-settings.radius");
        float volume = (float) configuration.getDouble("sound-settings.volume-on-clear");
        float pitch = (float) configuration.getDouble("sound-settings.pitch-on-clear");

        Sound sound = Sound.valueOf(CommonConfig.SOUND_NAME_ON_CLEAR.getProvider().getValue());

        item.getNearbyEntities(radius, radius, radius).forEach(entity -> {
            if (!(entity instanceof Player player)) return;
            player.playSound(item.getLocation(), sound, volume, pitch);
        });
    }

    private void initializeParticle(Entity item) {
        Location location = item.getLocation();
        World world = location.getWorld();

        String particleType = CommonConfig.PARTICLE_TYPE.getProvider().getValue();
        int particleCount = CommonConfig.PARTICLE_COUNT.getProvider().getValue();

        world.spawnParticle(Particle.valueOf(particleType), location, particleCount);
    }
}
