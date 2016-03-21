package com.hengyi.japp.print.server.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hengyi.japp.print.server.MyService;
import com.hengyi.japp.print.server.dto.MdUpdateDTO;
import org.fusesource.lmdbjni.Entry;
import org.fusesource.lmdbjni.EntryIterator;
import org.fusesource.lmdbjni.Transaction;

import static com.hengyi.japp.print.server.MyService.*;
import static org.fusesource.lmdbjni.Constants.bytes;
import static org.fusesource.lmdbjni.Constants.string;

/**
 * Created by Administrator on 2016/2/28.
 */
public class OperatorService {
    public JsonNode listSapT001() throws Exception {
        ArrayNode result = JsonNodeFactory.instance.arrayNode();
        byte[] key = bytes("SapT001_");
        try (Transaction tx = dbEnv.createReadTransaction()) {
            try (EntryIterator it = db.seek(tx, key)) {
                for (Entry next : it.iterable()) {
                    if (!string(next.getKey()).startsWith("SapT001_"))
                        break;
                    result.add(objectMapper.readValue(next.getValue(), ObjectNode.class));
                }
            }
        }
        return result;
    }

    public Object listSapYmmbancis(String bukrs) throws Exception {
        ArrayNode result = JsonNodeFactory.instance.arrayNode();
        byte[] key = bytes("SapYmmbanci_");
        try (Transaction tx = dbEnv.createReadTransaction()) {
            try (EntryIterator it = db.seek(tx, key)) {
                for (Entry next : it.iterable()) {
                    if (!string(next.getKey()).startsWith("SapYmmbanci_"))
                        break;
                    result.add(objectMapper.readValue(next.getValue(), ObjectNode.class));
                }
            }
        }
        return result;
    }

    public Object listSapZpackages(String bukrs) throws Exception {
        ArrayNode result = JsonNodeFactory.instance.arrayNode();
        byte[] key = bytes("SapZpackage_");
        try (Transaction tx = dbEnv.createReadTransaction()) {
            try (EntryIterator it = db.seek(tx, key)) {
                for (Entry next : it.iterable()) {
                    if (!string(next.getKey()).startsWith("SapZpackage_"))
                        break;
                    result.add(objectMapper.readValue(next.getValue(), ObjectNode.class));
                }
            }
        }
        return result;
    }

    public JsonNode create(MdUpdateDTO dto) throws Exception {
        return update(SeqManagerService.getNextCharg(dto.getBukrs()), dto);
    }

    public JsonNode update(String mdId, MdUpdateDTO dto) throws Exception {
        MyService.gLock.lock();
        try {
            ObjectNode result = JsonNodeFactory.instance.objectNode();
            return result;
        } finally {
            MyService.gLock.unlock();
        }
    }

    public JsonNode getMd(String mdId) {
        ObjectNode result = JsonNodeFactory.instance.objectNode();
        result.put("id", mdId);
        return result;
    }

    public void delete(String mdId) throws Exception {
        MyService.gLock.lock();
        try {
        } finally {
            MyService.gLock.unlock();
        }
    }
}
