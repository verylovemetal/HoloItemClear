package io.wisp.holoitemclear.config.codec.impl;

import io.wisp.holoitemclear.config.ConfigProvider;
import io.wisp.holoitemclear.config.codec.ICodec;

import java.util.UUID;

public class IntegerCodec implements ICodec<Integer> {

    private final ConfigProvider configProvider;

    public IntegerCodec(ConfigProvider configProvider) {
        this.configProvider = configProvider;
    }

    @Override
    public Integer getValue(Object value) {
        if (value == null) {
            System.err.println("Config value is null");
            return 0;
        }

        if (!(value instanceof Integer)) {
            System.out.println("Codec is not correct type: " + value.getClass().getName());
            return 0;
        }

        return (Integer) value;
    }

    @Override
    public Class<?> getClassType() {
        return Integer.class;
    }
}