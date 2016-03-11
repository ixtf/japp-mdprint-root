package je;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.hengyi.japp.common.JappDateTimeUtil;
import com.hengyi.japp.sap.DestinationType;
import com.hengyi.japp.sap.service.impl.BaseSapService;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;
import com.sleepycat.bind.tuple.StringBinding;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.OperationStatus;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

import static je.MyServiceJe.*;

/**
 * Created by Administrator on 2016/2/28.
 */
public class SapServiceJe extends BaseSapService {
    @Override
    public DestinationType getDestinationType() {
        return DestinationType.PRO;
    }

    public List<JsonNode> uploadMd(String bukrs) {
        gLock.lock();
        try {

        } finally {
            gLock.unlock();
        }
        return null;
    }

    public void syncData(String bukrs) throws Exception {
        ObjectNode sapT001Node = getByKeyF.apply(sapT001Db, bukrs);
        if (sapT001Node == null)
            throw new Exception("Sap[" + bukrs + "],找不到数据!");

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
        Function<JCoTable, ObjectNode> sapMaraF = t -> JsonNodeFactory.instance.objectNode().put("matnr", t.getString("MATNR")).put("maktx", t.getString("MAKTX"));
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

        log.info("====================syncSapData开始=========================");
        Date syncDate = new Date();
        DatabaseEntry key = new DatabaseEntry();
        DatabaseEntry data = new DatabaseEntry();

        JCoDestination dest = getDestination();
        JCoFunction f = getFunction("Z_RFC_PRINT002", dest);
        f.getImportParameterList().setValue("P_BUKRS", bukrs);
        f.execute(dest);
        sapT001Node.set("sapYmmzhixs", arrayNodeF.apply(f.getTableParameterList().getTable("L_T_YMMZHIX"), sapYmmzhixF));
        sapT001Node.set("sapYmmtonggs", arrayNodeF.apply(f.getTableParameterList().getTable("L_T_YMMTONGG"), sapYmmtonggF));
        sapT001Node.set("sapYmmmachs", arrayNodeF.apply(f.getTableParameterList().getTable("L_T_YMMMACH"), sapYmmmachF));
        _putDb(sapZpackageBb, f.getTableParameterList().getTable("L_T_PACKAGE"), "DOMVALUE_L", sapZpackageF);
        _putDb(sapYmmbanciDb, f.getTableParameterList().getTable("L_T_YMMBANCI"), "ZBANCI", sapYmmbanciF);

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

                //物料多公司情况处理
                StringBinding.stringToEntry(sapMaraNode.get("matnr").asText(), key);
                if (OperationStatus.SUCCESS == sapMaraDb.get(null, key, data, LockMode.DEFAULT)) {
                    ObjectNode oldNode = objectMapper.readValue(StringBinding.entryToString(data), ObjectNode.class);
                    ArrayNode sapT001IdsOld = (ArrayNode) oldNode.get("sapT001Ids");
                    Set<String> bukrsSet = Sets.newHashSet(bukrs);
                    sapT001IdsOld.forEach(n -> bukrsSet.add(n.asText()));
                    ArrayNode sapT001Ids = JsonNodeFactory.instance.arrayNode();
                    bukrsSet.forEach(s -> sapT001Ids.add(s));
                    sapMaraNode.set("sapT001Ids", sapT001Ids);
                } else
                    sapMaraNode.set("sapT001Ids", JsonNodeFactory.instance.arrayNode().add(bukrs));
            }
            ArrayNode arrayNode = (ArrayNode) sapMaraNode.get("sapT001wIds");
            arrayNode.add(t.getString("WERKS"));
        }

        luceneSapMara.indexAll(sapMaraMap.values());
        for (ObjectNode sapMaraNode : sapMaraMap.values()) {
            StringBinding.stringToEntry(sapMaraNode.get("matnr").asText(), key);
            StringBinding.stringToEntry(sapMaraNode.toString(), data);
            sapMaraDb.put(null, key, data);
        }

        sapT001Node.put("syncDate", JappDateTimeUtil.toDateTimeString(syncDate));
        StringBinding.stringToEntry(bukrs, key);
        StringBinding.stringToEntry(sapT001Node.toString(), data);
        sapT001Db.put(null, key, data);
        log.info("====================syncSapData结束=========================");
    }

    private void _putDb(Database db, JCoTable t, String keyField, Function<JCoTable, ObjectNode> f) {
        DatabaseEntry key = new DatabaseEntry();
        DatabaseEntry data = new DatabaseEntry();
        int numRows = t.getNumRows();
        for (int i = 0; i < numRows; ++i) {
            t.setRow(i);
            StringBinding.stringToEntry(t.getString(keyField), key);
            StringBinding.stringToEntry(f.apply(t).toString(), data);
            db.put(null, key, data);
        }
    }
}
