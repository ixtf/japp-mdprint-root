package com.hengyi.japp.print.server.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.hengyi.japp.print.server.query.MdQuery;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * Created by jzb on 16-2-29.
 */
public class LuceneService_Md {
    private Logger log = LoggerFactory.getLogger(getClass());
    private Directory directory;
    private IndexWriter indexWriter;

    public LuceneService_Md(String path) {
        try {
            directory = FSDirectory.open(Paths.get(path));
            indexWriter = new IndexWriter(directory, new IndexWriterConfig(new SmartChineseAnalyzer()));
        } catch (IOException e) {
            log.error("", e);
            System.exit(-1);
        }
    }

    public void index(JsonNode md) throws IOException {
        Document doc = new Document();
        doc.add(new StringField("charg", md.get("charg").asText(), Field.Store.YES));
        doc.add(new TextField("maktx", md.get("maktx").asText(), Field.Store.NO));
        ArrayNode arrayNode = (ArrayNode) md.get("sapT001wIds");
        arrayNode.forEach(node -> doc.add(new StringField("sapT001w", node.asText(), Field.Store.NO)));
        indexWriter.updateDocument(new Term("charg", md.get("charg").asText()), doc);
        indexWriter.commit();
    }

    public JsonNode query(MdQuery command) throws IOException {
        return null;
    }
}
