package io.wisp.holoitemclear.config;

import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

public enum CommonConfig {
    NO_PERMISSION(String.class, "messages.no-permission"),
    CONFIG_RELOAD_MESSAGE(String.class, "messages.config-reload-message"),
    TIME_CLEAR_ITEM(Integer.class, "settings.time-clear-item"),
    ITEM_TEXT(String.class, "settings.item-text"),
    USAGE_MESSAGE(String.class, "messages.usage-message"),
    ON_CLEAR_MESSAGE(String.class, "messages.on-clear-message"),
    ITEMS_SETTINGS(ConfigurationSection.class, "items-settings"),
    PARTICLE_TYPE(String.class, "particle-settings.particle-type"),
    PARTICLE_COUNT(Integer.class, "particle-settings.particle-count"),
    WORLD_BLACKLIST(List.class, "settings.world-blacklist"),
    USE_PARTICLE_ON_CLEAR(Boolean.class, "particle-settings.use-particle-on-clear"),
    SOUND_NAME_ON_CLEAR(String.class, "sound-settings.sound-name-on-clear"),
    USE_SOUND_ON_CLEAR(Boolean.class, "sound-settings.use-sound-on-clear"),
    HOLOGRAM_AFTER_TIME_ENABLE(Boolean.class, "settings.hologram-after-time.enable"),
    HOLOGRAM_AFTER_TIME_ACTIVATION(Integer.class, "settings.hologram-after-time.holo-activation-time"),;

    private final Class<?> classType;
    private final String path;

    private ConfigProvider provider;

    CommonConfig(Class<?> classType, String path) {
        this.classType = classType;
        this.path = path;
    }

    public ConfigProvider getProvider() {
        if(provider == null) {
            provider = new ConfigProvider(path, classType);
        }

        return provider;
    }
}
