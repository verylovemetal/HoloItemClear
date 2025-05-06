package io.wisp.holoitemclear.config.codec;

import org.apache.commons.lang.StringUtils;

import java.util.Map;
import java.util.UUID;

public interface ICodec<T> {
    T getValue(Object value, Map<String, Object> placeholders);

    Class<?> getClassType();

    default String replacePlaceholders(String input, Map<String, Object> placeholders) {
        for (Map.Entry<String, Object> entry : placeholders.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            input = StringUtils.replace(input, key, value + "");
        }

        return input;
    }
}
