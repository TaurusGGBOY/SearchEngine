
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class LuceneIndex {
	/**
	 * ��������
	 */
	public void index() {
		IndexWriter writer = null;
		try {
			Analyzer analyzer = new StandardAnalyzer();
			Path indexPath = Paths.get("E:\\jsoup\\Index");
			// 1������Derictory
			// Directory directory = new RAMDirectory();//��������ǽ������ڴ��е�����
			Directory directory = FSDirectory.open(indexPath);// ��������ǽ����ڴ������������
			// 2������IndexWriter�������Ҫ�ر�
			IndexWriterConfig config = new IndexWriterConfig(analyzer);
			writer = new IndexWriter(directory, config);
			// 3������Document����
			Document document = null;
			File fl = new File("E:\\jsoup\\News_1_Org_E");
			// 4��ΪDocument���Field
			for (File file : fl.listFiles()) {
				// System.out.println(file.getName());
				document = new Document();
				document.add(new TextField("content", new FileReader(file)));
				// ���ļ�����ŵ�Ӳ���У������ִ�
				document.add(new TextField("fileName", file.getName(), Field.Store.YES));
				// �Ѿ���·���ŵ�Ӳ���У������ִ�
				document.add(new TextField("path", file.getAbsolutePath(), Field.Store.YES));
				// 5��ͨ��IndexWriter����ĵ���������
				writer.addDocument(document);
			}
			fl = new File("E:\\jsoup\\News_1_Org_C");
			// 4��ΪDocument���Field
			for (File file : fl.listFiles()) {
				// System.out.println(file.getName());
				document = new Document();
				document.add(new TextField("content", new FileReader(file)));
				// ���ļ�����ŵ�Ӳ���У������ִ�
				document.add(new TextField("fileName", file.getName(), Field.Store.YES));
				// �Ѿ���·���ŵ�Ӳ���У������ִ�
				document.add(new TextField("path", file.getAbsolutePath(), Field.Store.YES));
				// 5��ͨ��IndexWriter����ĵ���������
				writer.addDocument(document);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != writer) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * ����
	 */
	public Map<String, String> searcher(String string) {
		Map<String, String> map = new HashMap<>();
		try {
			Analyzer analyzer = new StandardAnalyzer();
			Path indexPath = Paths.get("E:\\jsoup\\Index");
			// 1������Directory
			Directory directory = FSDirectory.open(indexPath);// ��������ǽ����ڴ������������

			// 2������IndexReader����Ҫ�ر�
			DirectoryReader ireader = DirectoryReader.open(directory);

			// 3������IndexReader����IndexSearcher
			IndexSearcher isearcher = new IndexSearcher(ireader);

			// 4������������Query
			// �ڶ�������������Ҫ��������
			QueryParser parser = new QueryParser("content", analyzer);
			// ��ʾ����content�а���java���ĵ�
			Query query = parser.parse(string);
			// 5������searcher����������TopDocs
			// ��ʾ����ǰ��10��
			TopDocs topDocs = isearcher.search(query, 10);
			// 6������TopDocs��ȡScoreDoc����
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			for (ScoreDoc sd : scoreDocs) {
				// 7������Searcher��ScordDoc�����ȡ�����Document����
				// ��ȡ����ĵ���id
				int doc = sd.doc;
				Document document = isearcher.doc(doc);
				map.put(document.get("fileName"), document.get("path"));
				// 8������Document�����ȡ��Ҫ��ֵ
				System.out.println("���ҵ���" + document.get("fileName") + "    " + document.get("path") + " .."
						+ document.get("content"));
			}

			ireader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return map;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// new LuceneIndex().index();
		// new LuceneIndex().searcher();
	}

}
