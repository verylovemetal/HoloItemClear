package io.wisp.holoitemclear.config.codec.impl;


import io.wisp.holoitemclear.config.ConfigProvider;
import io.wisp.holoitemclear.config.codec.ICodec;

import java.util.*;

public class ListCodec implements ICodec<List<String>> {

    @SuppressWarnings("unchecked")
    @Override
    public List<String> getValue(Object value, Map<String, Object> placeholder) {
        if (value == null) {
            System.err.println("Config value is null");
            return Collections.emptyList();
        }

        if (!(value instanceof List)) {
            System.out.println("Codec is not correct type: " + value.getClass().getName());
            return Collections.emptyList();
        }

        List<String> formattedList = new ArrayList<>();
        for (String line : (List<String>) value) {
            line = replacePlaceholders(line, placeholder);
            formattedList.add(line);
        }


        return formattedList;
    }

    @Override
    public Class<?> getClassType() {
        return List.class;
    }
}
