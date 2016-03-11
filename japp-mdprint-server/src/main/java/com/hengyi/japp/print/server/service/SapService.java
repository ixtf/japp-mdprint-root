package com.hengyi.japp.print.server.service;

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

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.hengyi.japp.print.server.Constant.*;
import static com.hengyi.japp.print.server.MyService.*;

/**
 * Created by Administrator on 2016/2/28.
 */
public class SapService extends BaseSapService {
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
        ObjectNode sapT001 = getByKeyF.compose(keySapT001F).apply(bukrs);
        if (sapT001 == null)
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
        BiFunction<JCoTable, JCoTable, ArrayNode> sapT001wsF = (LT_T001W, LT_T001L) -> {
            Function<JCoTable, ObjectNode> LT_T001L_F = t -> JsonNodeFactory.instance.objectNode().put("lgort", t.getString("LGORT")).put("lgobe", t.getString("LGOBE"));
            int numRows = LT_T001L.getNumRows();
            Multimap<String, JsonNode> multimap = ArrayListMultimap.create();
            for (int i = 0; i < numRows; i++) {
                LT_T001L.setRow(i);
                multimap.put(LT_T001L.getString("WERKS"), LT_T001L_F.apply(LT_T001L));
            }

            ArrayNode sapT001ws = JsonNodeFactory.instance.arrayNode();
            Function<JCoTable, ObjectNode> LT_T001W_F = t -> JsonNodeFactory.instance.objectNode().put("werks", t.getString("WERKS")).put("name1", t.getString("NAME1"));
            numRows = LT_T001W.getNumRows();
            for (int i = 0; i < numRows; i++) {
                LT_T001W.setRow(i);
                ObjectNode sapT001w = LT_T001W_F.apply(LT_T001W);
                ArrayNode sapT001ls = JsonNodeFactory.instance.arrayNode().addAll(multimap.get(LT_T001W.getString("WERKS")));
                sapT001w.set("sapT001ls", sapT001ls);
                sapT001ws.add(sapT001w);
            }
            return sapT001ws;
        };

        log.info("====================syncSapData开始=========================");
        Date syncDate = new Date();
        byte[] key, data;

        JCoDestination dest = getDestination();
        JCoFunction f = getFunction("Z_RFC_PRINT002", dest);
        f.getImportParameterList().setValue("P_BUKRS", bukrs);
        f.execute(dest);
        sapT001.set("sapYmmzhixs", arrayNodeF.apply(f.getTableParameterList().getTable("L_T_YMMZHIX"), sapYmmzhixF));
        sapT001.set("sapYmmtonggs", arrayNodeF.apply(f.getTableParameterList().getTable("L_T_YMMTONGG"), sapYmmtonggF));
        sapT001.set("sapYmmmachs", arrayNodeF.apply(f.getTableParameterList().getTable("L_T_YMMMACH"), sapYmmmachF));
        _putDb(f.getTableParameterList().getTable("L_T_PACKAGE"), sapZpackageF, "DOMVALUE_L", keySapZpackageF);
        _putDb(f.getTableParameterList().getTable("L_T_YMMBANCI"), sapYmmbanciF, "ZBANCI", keySapYmmbanciF);

        f = sapService.getFunction("Z_RFC_PRINT001", dest);
        f.getImportParameterList().setValue("P_BUKRS", bukrs);
        f.execute(dest);
        sapT001.set("sapT001ws", sapT001wsF.apply(f.getTableParameterList().getTable("LT_T001W"), f.getTableParameterList().getTable("LT_T001L")));

        JCoTable t = f.getTableParameterList().getTable("IT_TAB");
        int numRows = t.getNumRows();
        Map<String, ObjectNode> sapMaraMap = Maps.newHashMapWithExpectedSize(numRows);
        for (int i = 0; i < numRows; ++i) {
            t.setRow(i);
            ObjectNode sapMara = sapMaraMap.get(t.getString("MATNR"));
            if (sapMara == null) {
                sapMara = sapMaraF.apply(t);
                sapMaraMap.put(t.getString("MATNR"), sapMara);
                sapMara.set("sapT001wIds", JsonNodeFactory.instance.arrayNode());

                //物料多公司情况处理
                ObjectNode oldSapMara = getByKeyF.compose(keySapMaraF).apply(sapMara.get("matnr"));
                if (oldSapMara != null) {
                    ArrayNode sapT001IdsOld = (ArrayNode) oldSapMara.get("sapT001Ids");
                    Set<String> bukrsSet = Sets.newHashSet(bukrs);
                    sapT001IdsOld.forEach(n -> bukrsSet.add(n.asText()));
                    ArrayNode sapT001Ids = JsonNodeFactory.instance.arrayNode();
                    bukrsSet.forEach(s -> sapT001Ids.add(s));
                    sapMara.set("sapT001Ids", sapT001Ids);
                } else
                    sapMara.set("sapT001Ids", JsonNodeFactory.instance.arrayNode().add(bukrs));
            }
            ArrayNode arrayNode = (ArrayNode) sapMara.get("sapT001wIds");
            arrayNode.add(t.getString("WERKS"));
        }

        luceneSapMara.indexAll(sapMaraMap.values());
        for (ObjectNode sapMara : sapMaraMap.values()) {
            putByKeyF.apply(keySapMaraF.apply(sapMara.get("matnr")), sapMara);
        }

        sapT001.put("syncDate", JappDateTimeUtil.toDateTimeString(syncDate));
        putByKeyF.apply(keySapT001F.apply(sapT001.get("bukrs")), sapT001);
        log.info("====================syncSapData结束=========================");
    }

    private void _putDb(JCoTable t, Function<JCoTable, ObjectNode> f, String keyFiled, Function<Object, byte[]> keyF) {
        int numRows = t.getNumRows();
        for (int i = 0; i < numRows; ++i) {
            t.setRow(i);
            byte[] key = keyF.apply(t.getString(keyFiled));
            putByKeyF.apply(key, f.apply(t));
        }
    }
}
