package com.kevin.chap6.collector;

import com.kevin.util.TestUtil;
import junit.framework.TestCase;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;

import java.io.IOException;
import java.util.Map;

/**
 * @类名: CollectorTest
 * @包名：com.kevin.chap6.collector
 * @作者：kevin[wangqi2017@xinhua.org]
 * @时间：2018/7/21 16:17
 * @版本：1.0
 * @描述：
 */
public class CollectorTest extends TestCase {

    private Directory directory;
    private IndexReader reader;
    private IndexSearcher searcher;

    @Override
    public void setUp() throws IOException {
        directory = TestUtil.getBookIndexDirectory();
        reader = DirectoryReader.open(directory);
        searcher = new IndexSearcher(reader);
    }

    @Override
    public void tearDown() throws IOException {
        reader.close();
        directory.close();
    }

    public void testCollecting() throws IOException {
        TermQuery query = new TermQuery(new Term("contents", "junit"));
        BookLinkCollector collector = new BookLinkCollector();
        searcher.search(query, collector);
        Map<String, String> linkMap = collector.getLinks();
        for (Map.Entry<String, String> entry : linkMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        TopDocs hits = searcher.search(query, 10);
        TestUtil.dumpHits(searcher, hits);
    }
}
