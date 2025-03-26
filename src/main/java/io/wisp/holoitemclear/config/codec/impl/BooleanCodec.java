package io.wisp.holoitemclear.config.codec.impl;

import io.wisp.holoitemclear.config.ConfigProvider;
import io.wisp.holoitemclear.config.codec.ICodec;

public class BooleanCodec implements ICodec<Boolean> {

    private final ConfigProvider configProvider;

    public BooleanCodec(ConfigProvider configProvider) {
        this.configProvider = configProvider;
    }

    @Override
    public Boolean getValue(Object value) {
        if (value == null) {
            System.err.println("Config value is null");
            return false;
        }

        if (!(value instanceof Boolean)) {
            System.out.println("Codec is not correct type: " + value.getClass().getName());
            return false;
        }

        return (Boolean) value;
    }

    @Override
    public Class<?> getClassType() {
        return Boolean.class;
    }
}