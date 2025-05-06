package io.wisp.holoitemclear.config.codec.impl;


import io.wisp.holoitemclear.config.ConfigProvider;
import io.wisp.holoitemclear.config.codec.ICodec;

import java.util.Map;
import java.util.UUID;

public class StringCodec implements ICodec<String> {

    @Override
    public String getValue(Object value, Map<String, Object> placeholders) {
        if (value == null) {
            System.err.println("Config value is null");
            return "";
        }

        if (!(value instanceof String)) {
            System.out.println("Codec is not correct type: " + value.getClass().getName());
            return "";
        }

        value = replacePlaceholders((String) value, placeholders);

        return (String) value;
    }

    @Override
    public Class<?> getClassType() {
        return String.class;
    }
}
