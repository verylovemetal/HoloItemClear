package io.wisp.holoitemclear.util.color;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class ColorUtil {

    private final Cache<String, String> cachedMessages = CacheBuilder.newBuilder()
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .build();

    public String format(String from) {
        String providedFrom = from;
        if (cachedMessages.getIfPresent(from) != null) {
            return cachedMessages.getIfPresent(from);
        }

        Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
        Matcher matcher = pattern.matcher(from);
        while (matcher.find()) {
            String hexCode = from.substring(matcher.start(), matcher.end());
            String replaceSharp = hexCode.replace("#", "x");
            char[] ch = replaceSharp.toCharArray();
            StringBuilder builder = new StringBuilder();
            for (char c : ch)
                builder.append("&").append(c);
            from = from.replace(hexCode, builder.toString());
            matcher = pattern.matcher(from);
        }

        from = ChatColor.translateAlternateColorCodes('&', from);
        cachedMessages.put(providedFrom, from);
        return from;
    }
}
