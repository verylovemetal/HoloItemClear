package io.wisp.holoitemclear.config.codec.impl;


import io.wisp.holoitemclear.config.ConfigProvider;
import io.wisp.holoitemclear.config.codec.ICodec;

import java.util.UUID;

public class StringCodec implements ICodec<String> {

    private final ConfigProvider configProvider;

    public StringCodec(ConfigProvider configProvider) {
        this.configProvider = configProvider;
    }

    @Override
    public String getValue(Object value) {
        if (value == null) {
            System.err.println("Config value is null");
            return "";
        }

        if (!(value instanceof String)) {
            System.out.println("Codec is not correct type: " + value.getClass().getName());
            return "";
        }

        return (String) value;
    }

    @Override
    public Class<?> getClassType() {
        return String.class;
    }
}
