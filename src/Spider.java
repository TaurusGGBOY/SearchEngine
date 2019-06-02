import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

// 爬虫代码
public class Spider {
	static int i;

	public static void main(String[] arg) {
		i = 0;
		Spider spider = new Spider();
		while (i <= 500) {
			try {
				// spider.testcsdn();
				spider.spiderArticleE();
				System.out.println(i);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public void spiderArticleE() throws Exception {
		Document document = Jsoup.connect("http://www.hxen.com/englishnews/")
				// 模拟火狐浏览器
				// .userAgent("Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)")
				.userAgent("Mozilla").get();
		Elements url = document.select("#content").select("div").select("div.w690.fl").select("div").select("div")
				.select("div.pagecon.tc.clearfix.ptb25").select("div").select("select").select("option");
		// #content > div > div.w690.fl > div > div > div.pagecon.tc.clearfix.ptb25 >
		// div > select > option:nth-child(1)
		for (Element question : url) {
			String URL = question.attr("abs:value");
			System.out.println(URL);
			Document document2 = Jsoup.connect(URL).userAgent("Mozilla").get();
			Elements articles = document2.select("#content").select("div").select("div.w690.fl").select("div")
					.select("div").select("div.newsRankBar.mb20").select("div").select("ul").select("li").select("div")
					.select("div").select("h3").select("a");
			// #content > div > div.w690.fl > div > div > div.newsRankBar.mb20 > div > div >
			// ul > li:nth-child(1) > div > div > h3 > a
			for (Element article : articles) {
				String URL2 = article.attr("abs:href");
				Document document3 = Jsoup.connect(URL2).userAgent("Mozilla").get();
				Elements title = document3.select("#slistl").select("div.arcinfo.center").select("b").select("h1");
				// #slistl > div.arcinfo.center > b > h1
				Elements answer = document3.select("#arctext").select("p");
				// #arctext > p:nth-child(2)
				System.out.println("\n" + "链接：" + URL2 + "\n" + "标题：" + title.text() + "\n" + "回答：" + answer.text());

				String titleString = title.text().replaceAll("[^0-9a-zA-Z]J*", "");
				String filepath = "E:" + File.separator + "jsoup" + File.separator + "word" + File.separator
						+ titleString + ".txt";
				System.out.println(filepath);
				File file = new File(filepath);

				if (!file.getParentFile().exists()) {
					file.getParentFile().mkdirs();
					i++;
					System.out.println(i);
				}
				Writer out = new FileWriter(file);
				out.write(answer.text());
				out.close();
			}

		}
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			System.exit(0);// 退出程序
		}
	}

	public void spiderArticleC() throws Exception {
		Document document = Jsoup.connect("http://www.zuowenwang.net/")
				// 模拟火狐浏览器
				// .userAgent("Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)")
				.userAgent("Mozilla").get();
		Elements url = document.select("body").select("div.menu_nz").select("dl.menu_nz_one").select("dd").select("a");
		// body > div.menu_nz > dl.menu_nz_one > dd:nth-child(2) > a:nth-child(1)
		for (Element question : url) {

			String URL = question.attr("abs:href");
			Document document2 = Jsoup.connect(URL).userAgent("Mozilla").get();
			Elements articles = document2.select("body").select("div.wrap.wrap3").select("div").select("div.left")
					.select("div").select("ul").select("li").select("h3").select("a");
			// body > div.wrap.wrap3 > div > div.left > div > ul > li:nth-child(1) > h3 > a
			for (Element article : articles) {
				String URL2 = article.attr("abs:href");
				Document document3 = Jsoup.connect(URL2).userAgent("Mozilla").get();
				Elements title = document3.select("#tm_wrap3").select("div").select("div.left").select("div")
						.select("div.article-t").select("h1");
				// #tm_wrap3 > div > div.left > div > div.article-t > h1
				Elements answer = document3.select("#tm_wrap3").select("div").select("div.left").select("div")
						.select(" div.content").select("p");
				// #tm_wrap3 > div > div.left > div > div.content > p:nth-child(3)
				System.out.println("\n" + "链接：" + URL + "\n" + "标题：" + title.text() + "\n" + "回答：" + answer.text());

				String filepath = "E:" + File.separator + "jsoup" + File.separator + "word" + File.separator
						+ title.text() + ".txt";
				System.out.println(filepath);
				File file = new File(filepath);

				if (!file.getParentFile().exists()) {
					file.getParentFile().mkdirs();
					i++;
					System.out.println(i);
				}
				Writer out = new FileWriter(file);
				out.write(answer.text());
				out.close();
			}

		}
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			System.exit(0);// 退出程序
		}
	}

	public void spiderCsdn() throws Exception {
		Document document = Jsoup.connect("https://www.csdn.net/")
				// 模拟火狐浏览器
				// .userAgent("Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)")
				.userAgent("Mozilla").get();
		Elements url = document.select("#feedlist_id").select("li").select("div").select("div.title").select("h2")
				.select("a");
		for (Element question : url) {
			String URL = question.attr("abs:href");
			Document document2 = Jsoup.connect(URL).userAgent("Mozilla").get();
			Elements title = document2.select("#mainBox").select("main").select("div.blog-content-box").select("div")
					.select("div").select("div.article-title-box").select("h1");
			Elements answer = document2.select("#content_views").select("p");
			System.out.println("\n" + "链接：" + URL + "\n" + "标题：" + title.text() + "\n" + "回答：" + answer.text());

			String filepath = "E:" + File.separator + "jsoup" + File.separator + "word" + File.separator + title.text()
					+ ".txt";
			System.out.println(filepath);
			File file = new File(filepath);

			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
				i++;
				System.out.println(i);
			}
			Writer out = new FileWriter(file);
			out.write(answer.text());
			out.close();

		}
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			System.exit(0);// 退出程序
		}
	}

	public void spiderFriend() throws Exception {
		Document document = Jsoup.connect("https://www.csdn.net/")
				// 模拟火狐浏览器
				// .userAgent("Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)")
				.userAgent("Mozilla").get();
		Elements url = document.select("#feedlist_id").select("li").select("div").select("div.title").select("h2")
				.select("a");
		for (Element question : url) {
			String URL = question.attr("abs:href");
			Document document2 = Jsoup.connect(URL).userAgent("Mozilla").get();
			Elements title = document2.select("#mainBox").select("main").select("div.blog-content-box").select("div")
					.select("div").select("div.article-title-box").select("h1");
			Elements answer = document2.select("#content_views").select("p");
			System.out.println("\n" + "链接：" + URL + "\n" + "标题：" + title.text() + "\n" + "回答：" + answer.text());

			String filepath = "E:" + File.separator + "jsoup" + File.separator + "word" + File.separator + title.text()
					+ ".txt";
			System.out.println(filepath);
			File file = new File(filepath);

			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
				i++;
				System.out.println(i);
			}
			Writer out = new FileWriter(file);
			out.write(answer.text());
			out.close();

		}
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			System.exit(0);// 退出程序
		}

	}
}
