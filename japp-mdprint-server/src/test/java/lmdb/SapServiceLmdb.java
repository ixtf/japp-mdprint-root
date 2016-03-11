package lmdb;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.hengyi.japp.common.JappDateTimeUtil;
import com.hengyi.japp.sap.DestinationType;
import com.hengyi.japp.sap.service.impl.BaseSapService;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import static lmdb.MyServiceLmdb.*;
import static org.fusesource.lmdbjni.Constants.bytes;

/**
 * Created by Administrator on 2016/2/28.
 */
public class SapServiceLmdb extends BaseSapService {
    @Override
    public DestinationType getDestinationType() {
        return DestinationType.PRO;
    }

    public List<JsonNode> uploadMd(String bukrs) {
        LOCK.lock();
        try {

        } finally {
            LOCK.unlock();
        }
        return null;
    }

    public Date syncData(String bukrs) throws Exception {
        Date syncDate = new Date();
        BiFunction<JCoTable, Function<JCoTable, ? extends JsonNode>, ArrayNode> arrayNodeF = (t, f) -> {
            ArrayNode result = JsonNodeFactory.instance.arrayNode();
            int numRows = t.getNumRows();
            for (int i = 0; i < numRows; i++) {
                t.setRow(i);
                result.add(f.apply(t));
            }
            return result;
        };
        Function<JCoTable, ObjectNode> sapYmmzhixF = t -> JsonNodeFactory.instance.objectNode().put("yzxtype", t.getString("YZXTYPE")).put("yzxspec", t.getString("YZXSPEC")).put("yzxwght", t.getBigDecimal("YZXWGHT"));
        Function<JCoTable, ObjectNode> sapYmmtonggF = t -> JsonNodeFactory.instance.objectNode().put("ztgtype", t.getString("ZTGTYPE")).put("ztgspec", t.getString("ZTGSPEC")).put("ztgwght", t.getBigDecimal("ZTGWGHT"));
        Function<JCoTable, ObjectNode> sapYmmmachF = t -> JsonNodeFactory.instance.objectNode().put("zmcnum", t.getString("ZMCNUM")).put("zplant", t.getString("ZPLANT"));
        Function<JCoTable, ObjectNode> sapZpackageF = t -> JsonNodeFactory.instance.objectNode().put("value", t.getString("DOMVALUE_L")).put("text", t.getString("DDTEXT"));
        Function<JCoTable, ObjectNode> sapYmmbanciF = t -> JsonNodeFactory.instance.objectNode().put("zbanci", t.getString("ZBANCI")).put("btext", t.getString("BTEXT"));
        BiFunction<JCoTable, JCoTable, ArrayNode> sapT001wArrayF = (LT_T001W, LT_T001L) -> {
            Function<JCoTable, ObjectNode> LT_T001L_F = t -> JsonNodeFactory.instance.objectNode().put("lgort", t.getString("LGORT")).put("lgobe", t.getString("LGOBE"));
            ArrayNode result = JsonNodeFactory.instance.arrayNode();
            int numRows = LT_T001L.getNumRows();
            Multimap<String, JsonNode> multimap = ArrayListMultimap.create();
            for (int i = 0; i < numRows; i++) {
                LT_T001L.setRow(i);
                multimap.put(LT_T001L.getString("WERKS"), LT_T001L_F.apply(LT_T001L));
            }

            Function<JCoTable, ObjectNode> LT_T001W_F = t -> JsonNodeFactory.instance.objectNode().put("werks", t.getString("WERKS")).put("name1", t.getString("NAME1"));
            numRows = LT_T001W.getNumRows();
            for (int i = 0; i < numRows; i++) {
                LT_T001W.setRow(i);
                ObjectNode node = LT_T001W_F.apply(LT_T001W);
                ArrayNode sapT001ls = JsonNodeFactory.instance.arrayNode().addAll(multimap.get(LT_T001W.getString("WERKS")));
                node.set("sapT001ls", sapT001ls);
            }
            return result;
        };
        Function<JCoTable, ObjectNode> sapMaraF = t -> JsonNodeFactory.instance.objectNode().put("matnr", t.getString("MATNR")).put("maktx", t.getString("MAKTX"));

        byte[] sapT001Key = bytes("SapT001_" + bukrs);
        byte[] sapT001Data = db.get(sapT001Key);
        ObjectNode sapT001Node = objectMapper.readValue(sapT001Data, ObjectNode.class);

        log.debug("====================syncSapData[" + syncDate + "],开始=========================");

        JCoDestination dest = getDestination();
        JCoFunction f = getFunction("Z_RFC_PRINT002", dest);
        f.getImportParameterList().setValue("P_BUKRS", bukrs);
        f.execute(dest);

        sapT001Node.set("sapYmmzhixs", arrayNodeF.apply(f.getTableParameterList().getTable("L_T_YMMZHIX"), sapYmmzhixF));
        sapT001Node.set("sapYmmtonggs", arrayNodeF.apply(f.getTableParameterList().getTable("L_T_YMMTONGG"), sapYmmtonggF));
        sapT001Node.set("sapYmmmachs", arrayNodeF.apply(f.getTableParameterList().getTable("L_T_YMMMACH"), sapYmmmachF));

        _putDb(f.getTableParameterList().getTable("L_T_PACKAGE"), "SapZpackage_", "DOMVALUE_L", sapZpackageF);
        _putDb(f.getTableParameterList().getTable("L_T_YMMBANCI"), "SapYmmbanci_", "ZBANCI", sapYmmbanciF);

        f = sapService.getFunction("Z_RFC_PRINT001", dest);
        f.getImportParameterList().setValue("P_BUKRS", bukrs);
        f.execute(dest);

        sapT001Node.set("sapT001ws", sapT001wArrayF.apply(f.getTableParameterList().getTable("LT_T001W"), f.getTableParameterList().getTable("LT_T001L")));

        JCoTable t = f.getTableParameterList().getTable("IT_TAB");
        int numRows = t.getNumRows();
        Map<String, ObjectNode> sapMaraMap = Maps.newHashMapWithExpectedSize(numRows);
        for (int i = 0; i < numRows; ++i) {
            t.setRow(i);
            ObjectNode sapMaraNode = sapMaraMap.get(t.getString("MATNR"));
            if (sapMaraNode == null) {
                sapMaraNode = sapMaraF.apply(t);
                sapMaraMap.put(t.getString("MATNR"), sapMaraNode);
                sapMaraNode.set("sapT001wIds", JsonNodeFactory.instance.arrayNode());
            }
            ArrayNode arrayNode = (ArrayNode) sapMaraNode.get("sapT001wIds");
            arrayNode.add(t.getString("WERKS"));
        }

        luceneSapMara.indexAll(sapMaraMap.values());
        sapMaraMap.values().forEach(sapMaraNode -> {
            byte[] key = bytes("SapMara_" + sapMaraNode.get("matnr").asText());
            byte[] data = bytes(sapMaraNode.toString());
            db.put(key, data);
        });

        sapT001Node.put("syncDate", JappDateTimeUtil.toDateTimeString(syncDate));
        sapT001Data = bytes(sapT001Node.toString());
        db.put(sapT001Key, sapT001Data);

        log.debug("====================syncSapData[" + syncDate + "],结束=========================");
        return syncDate;
    }

    private void _putDb(JCoTable t, String prefix, String keyFiled, Function<JCoTable, ObjectNode> f) {
        int numRows = t.getNumRows();
        for (int i = 0; i < numRows; ++i) {
            t.setRow(i);
            byte[] key = bytes(prefix + t.getString(keyFiled));
            byte[] data = bytes(f.apply(t).toString());
            db.put(key, data);
        }
    }
}
