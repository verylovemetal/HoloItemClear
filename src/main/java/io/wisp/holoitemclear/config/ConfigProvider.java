package io.wisp.holoitemclear.config;

import io.wisp.holoitemclear.Main;
import io.wisp.holoitemclear.config.codec.CodecRegister;
import io.wisp.holoitemclear.config.codec.ICodec;
import io.wisp.holoitemclear.util.color.ColorUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class ConfigProvider {

    private final Map<String, Object> placeholders = new HashMap<>();

    protected final String path;
    protected final Class<?> classType;

    private final CodecRegister codecRegistry = new CodecRegister();

    public ConfigProvider(String path, Class<?> classType) {
        this.path = path;
        this.classType = classType;

        codecRegistry.registerCodecs();
    }

    @SuppressWarnings("unchecked")
    public <T> T getValue() {
        FileConfiguration config = Main.getInstance().getConfig();
        Object value = config.get(path);

        if (value == null) {
            Bukkit.getLogger().warning("Value on: " + path + " is null, please, create this path and add some value!");
            return null;
        }

        T returnValue = null;
        for (ICodec<?> codec : codecRegistry.getAllCodec()) {
            if (codec.getClassType() != classType) continue;
            returnValue = (T) codec.getValue(value, placeholders);
        }

        return returnValue;
    }

    public int getAsInt() {
        return getValue();
    }

    public List<String> getAsList() {
        return getValue();
    }

    public String getAsString() {
        return getValue();
    }

    public boolean getAsBoolean() {
        return getValue();
    }

    public ConfigProvider withPlaceholder(String placeholder, Object value) {
        placeholders.put(placeholder, value);
        return this;
    }

    private  <T> void also(Class<T> classType, Consumer<T> actionInBukkitThread) {
        T value = getValue();
        actionInBukkitThread.accept(classType.cast(value));
    }

    @SuppressWarnings("unchecked")
    public void sendMessage(UUID playerUUID) {
        if (classType == String.class) {
            also(String.class, message ->
                    sendPlayerMessage(playerUUID, ColorUtil.format(message)));
        } else {
            also(List.class, messages -> {
                messages.forEach(msg -> sendPlayerMessage(playerUUID, ColorUtil.format((String) msg)));
            });
        }
    }

    private void sendPlayerMessage(UUID playerUUID, String message) {
        Player player = Bukkit.getPlayer(playerUUID);
        if (player == null) return;

        player.sendMessage(message);
    }
}
