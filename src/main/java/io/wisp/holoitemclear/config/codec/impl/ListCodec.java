package io.wisp.holoitemclear.config.codec.impl;


import io.wisp.holoitemclear.config.ConfigProvider;
import io.wisp.holoitemclear.config.codec.ICodec;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class ListCodec implements ICodec<List<String>> {

    private final ConfigProvider configProvider;

    public ListCodec(ConfigProvider configProvider) {
        this.configProvider = configProvider;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<String> getValue(Object value) {
        if (value == null) {
            System.err.println("Config value is null");
            return Collections.emptyList();
        }

        if (!(value instanceof List)) {
            System.out.println("Codec is not correct type: " + value.getClass().getName());
            return Collections.emptyList();
        }

        return new ArrayList<>(((List<String>) value));
    }

    @Override
    public Class<?> getClassType() {
        return List.class;
    }
}
