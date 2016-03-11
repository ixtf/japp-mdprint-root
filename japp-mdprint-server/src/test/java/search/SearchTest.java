package search;

import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by jzb on 16-3-3.
 */
public class SearchTest {
    private Directory directory;
    private IndexWriter indexWriter;

    public SearchTest() throws IOException {
        this.directory = new RAMDirectory();
        this.indexWriter = new IndexWriter(directory, new IndexWriterConfig(new SmartChineseAnalyzer()));
    }

    public static void main(String[] args) throws IOException {
        SearchTest test = new SearchTest();
        test.index();
        test.query();
    }

    private void index() throws IOException {
        Document doc = new Document();
        doc.add(new StringField("maktx", "FDY-137dtex/36f-GD71211S-优等", Field.Store.NO));
        indexWriter.addDocument(doc);
        indexWriter.commit();
    }

    public void query() throws IOException {
        try (DirectoryReader indexReader = DirectoryReader.open(directory)) {
            Query query = new WildcardQuery(new Term("maktx", "*FDY*"));

            IndexSearcher searcher = new IndexSearcher(indexReader);
            TopDocs topDocs = searcher.search(query, 100);
            System.out.println(topDocs.totalHits);
            Arrays.stream(topDocs.scoreDocs).forEach(sd -> {
            });
        }
    }
}
