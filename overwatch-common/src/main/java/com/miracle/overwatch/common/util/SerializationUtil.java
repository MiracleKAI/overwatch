package com.miracle.overwatch.common.util;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author QiuKai
 * @date 2021/9/29 3:13 下午
 */
public class SerializationUtil {

    private static final Map<Class<?>, Schema<?>> schemaMap = new ConcurrentHashMap<>();

    private static final Objenesis objenesis = new ObjenesisStd();
    private static final LinkedBuffer linkedBuffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);

    private SerializationUtil() {
    }

    public static <T> byte[] serialize(T obj){
        Schema<T> schema = (Schema<T>) getSchema(obj.getClass());
        try {
            return ProtostuffIOUtil.toByteArray(obj, schema, linkedBuffer);
        }finally {
            linkedBuffer.clear();
        }
    }

    public static <T> T deserialize(byte[] date, Class<T> tClass){
        T instance = objenesis.newInstance(tClass);
        Schema<T> schema = getSchema(tClass);
        ProtostuffIOUtil.mergeFrom(date,instance, schema);
        return instance;
    }

    private static <T> Schema<T> getSchema(Class<T> obj){
        Schema<T> schema = (Schema<T>) schemaMap.get(obj);
        if(schema == null){
            schema = RuntimeSchema.getSchema(obj);
            schemaMap.put(obj, schema);
        }
        return schema;
    }
}
