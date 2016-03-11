package com.hengyi.japp.print.server.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hengyi.japp.print.server.query.SapMaraQuery;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;

import static com.hengyi.japp.print.server.Constant.getByKeyF;
import static com.hengyi.japp.print.server.Constant.keySapMaraF;

/**
 * Created by jzb on 16-2-29.
 */
public class LuceneService_SapMara {
    private Logger log = LoggerFactory.getLogger(getClass());
    private Directory directory;
    private IndexWriter indexWriter;

    public LuceneService_SapMara(String path) {
        try {
            directory = FSDirectory.open(Paths.get(path));
            indexWriter = new IndexWriter(directory, new IndexWriterConfig(new SmartChineseAnalyzer()));
        } catch (IOException e) {
            log.error("", e);
            System.exit(-1);
        }
    }

    public void indexAll(Collection<? extends JsonNode> sapMaras) throws IOException {
        indexWriter.deleteAll();
        for (JsonNode sapMara : sapMaras) {
            Document doc = new Document();
            doc.add(new StringField("matnr", sapMara.get("matnr").asText(), Field.Store.YES));
            doc.add(new StringField("maktx", sapMara.get("maktx").asText().toLowerCase(), Field.Store.NO));
            ArrayNode arrayNode = (ArrayNode) sapMara.get("sapT001wIds");
            arrayNode.forEach(node -> doc.add(new StringField("sapT001w", node.asText(), Field.Store.NO)));
            arrayNode = (ArrayNode) sapMara.get("sapT001Ids");
            arrayNode.forEach(node -> doc.add(new StringField("sapT001", node.asText(), Field.Store.NO)));
            indexWriter.updateDocument(new Term("matnr", sapMara.get("matnr").asText()), doc);
        }
        indexWriter.commit();
    }

    public JsonNode query(SapMaraQuery command) throws IOException {
        try (DirectoryReader indexReader = DirectoryReader.open(directory)) {
            BooleanQuery.Builder bqBuilder = new BooleanQuery.Builder();
            bqBuilder.add(new TermQuery(new Term("sapT001", command.principal().getName())), BooleanClause.Occur.MUST);
            if (StringUtils.isNotBlank(command.q()))
                bqBuilder.add(new WildcardQuery(new Term("maktx", "*" + command.q().toLowerCase() + "*")), BooleanClause.Occur.MUST);

            IndexSearcher searcher = new IndexSearcher(indexReader);
            TopDocs topDocs = searcher.search(bqBuilder.build(), command.first() + command.pageSize());
            ObjectNode result = JsonNodeFactory.instance.objectNode().put("count", topDocs.totalHits);
            if (topDocs.totalHits < command.first()) {
                return result;
            }

            ArrayNode arrayNode = JsonNodeFactory.instance.arrayNode();
            Arrays.stream(topDocs.scoreDocs).skip(command.first()).limit(command.pageSize()).forEach(sd -> {
                try {
                    Document doc = searcher.doc(sd.doc);
                    arrayNode.add(getByKeyF.compose(keySapMaraF).apply(doc.get("matnr")));
                } catch (IOException e) {
                    log.error("", e);
                }
            });
            return result.set("result", arrayNode);
        }
    }
}
