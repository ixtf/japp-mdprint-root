package com.hengyi.japp.print.server;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

import java.io.IOException;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.hengyi.japp.print.server.MyService.db;
import static com.hengyi.japp.print.server.MyService.objectMapper;
import static org.fusesource.lmdbjni.Constants.bytes;

/**
 * Created by jzb on 16-3-4.
 */
public class Constant {
    public static Function<byte[], ObjectNode> getByKeyF = (key) -> {
        try {
            byte[] data = db.get(key);
            return data == null ? null : objectMapper.readValue(data, ObjectNode.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    };
    public static BiFunction<byte[], ObjectNode, Void> putByKeyF = (key, node) -> {
        db.put(key, bytes(node.toString()));
        return null;
    };
    private static Function<Object, String> nodeOrStringF = o -> {
        if (o instanceof String) return (String) o;
        if (o instanceof TextNode) return ((TextNode) o).asText();
        else return o.toString();
    };
    public static Function<Object, byte[]> keySapT001F = nodeOrStringF.andThen(id -> bytes("SapT001_" + id));
    public static Function<Object, byte[]> keySapZpackageF = nodeOrStringF.andThen(id -> bytes("SapZpackage_" + id));
    public static Function<Object, byte[]> keySapYmmbanciF = nodeOrStringF.andThen(id -> bytes("SapYmmbanci_" + id));
    public static Function<Object, byte[]> keySapMaraF = nodeOrStringF.andThen(id -> bytes("SapMara_" + id));
}
