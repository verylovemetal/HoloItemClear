package io.wisp.holoitemclear.config.codec;

import java.util.UUID;

public interface ICodec<T> {
    T getValue(Object value);

    Class<?> getClassType();
}
