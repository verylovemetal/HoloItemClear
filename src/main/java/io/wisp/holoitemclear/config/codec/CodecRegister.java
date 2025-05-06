package io.wisp.holoitemclear.config.codec;


import io.wisp.holoitemclear.config.ConfigProvider;
import io.wisp.holoitemclear.config.codec.impl.BooleanCodec;
import io.wisp.holoitemclear.config.codec.impl.IntegerCodec;
import io.wisp.holoitemclear.config.codec.impl.ListCodec;
import io.wisp.holoitemclear.config.codec.impl.StringCodec;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CodecRegister {

    private final Map<Class<?>, ICodec<?>> registry = new HashMap<>();

    public void registerCodecs() {
        register(new ListCodec());
        register(new StringCodec());
        register((new IntegerCodec()));
        register((new BooleanCodec()));
    }

    public void register(ICodec<?> codec) {
        registry.put(codec.getClassType(), codec);
    }

    public Collection<ICodec<?>> getAllCodec() {
        return registry.values();
    }
}
