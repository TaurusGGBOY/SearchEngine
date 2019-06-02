
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
	 * 创建索引
	 */
	public void index() {
		IndexWriter writer = null;
		try {
			Analyzer analyzer = new StandardAnalyzer();
			Path indexPath = Paths.get("E:\\jsoup\\Index");
			// 1、创建Derictory
			// Directory directory = new RAMDirectory();//这个方法是建立在内存中的索引
			Directory directory = FSDirectory.open(indexPath);// 这个方法是建立在磁盘上面的索引
			// 2、创建IndexWriter，用完后要关闭
			IndexWriterConfig config = new IndexWriterConfig(analyzer);
			writer = new IndexWriter(directory, config);
			// 3、创建Document对象
			Document document = null;
			File fl = new File("E:\\jsoup\\News_1_Org_E");
			// 4、为Document添加Field
			for (File file : fl.listFiles()) {
				// System.out.println(file.getName());
				document = new Document();
				document.add(new TextField("content", new FileReader(file)));
				// 把文件名存放到硬盘中，不作分词
				document.add(new TextField("fileName", file.getName(), Field.Store.YES));
				// 把绝对路径放到硬盘中，不作分词
				document.add(new TextField("path", file.getAbsolutePath(), Field.Store.YES));
				// 5、通过IndexWriter添加文档到索引中
				writer.addDocument(document);
			}
			fl = new File("E:\\jsoup\\News_1_Org_C");
			// 4、为Document添加Field
			for (File file : fl.listFiles()) {
				// System.out.println(file.getName());
				document = new Document();
				document.add(new TextField("content", new FileReader(file)));
				// 把文件名存放到硬盘中，不作分词
				document.add(new TextField("fileName", file.getName(), Field.Store.YES));
				// 把绝对路径放到硬盘中，不作分词
				document.add(new TextField("path", file.getAbsolutePath(), Field.Store.YES));
				// 5、通过IndexWriter添加文档到索引中
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
	 * 搜索
	 */
	public Map<String, String> searcher(String string) {
		Map<String, String> map = new HashMap<>();
		try {
			Analyzer analyzer = new StandardAnalyzer();
			Path indexPath = Paths.get("E:\\jsoup\\Index");
			// 1、创建Directory
			Directory directory = FSDirectory.open(indexPath);// 这个方法是建立在磁盘上面的索引

			// 2、创建IndexReader，需要关闭
			DirectoryReader ireader = DirectoryReader.open(directory);

			// 3、根据IndexReader创建IndexSearcher
			IndexSearcher isearcher = new IndexSearcher(ireader);

			// 4、创建索引的Query
			// 第二个参数代表着要搜索的域
			QueryParser parser = new QueryParser("content", analyzer);
			// 表示搜索content中包含java的文档
			Query query = parser.parse(string);
			// 5、根据searcher搜索并返回TopDocs
			// 表示返回前面10条
			TopDocs topDocs = isearcher.search(query, 10);
			// 6、根据TopDocs获取ScoreDoc对象
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			for (ScoreDoc sd : scoreDocs) {
				// 7、根据Searcher和ScordDoc对象获取具体的Document对象
				// 获取这个文档的id
				int doc = sd.doc;
				Document document = isearcher.doc(doc);
				map.put(document.get("fileName"), document.get("path"));
				// 8、根据Document对象获取需要的值
				System.out.println("【找到】" + document.get("fileName") + "    " + document.get("path") + " .."
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
