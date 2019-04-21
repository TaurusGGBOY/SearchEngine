import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apdplat.word.WordSegmenter;

public class Filter {
	static int chineseNum = 561;
	static int engliashNum = 539;
	static Set<String> stopWords = new HashSet<>();

	public static void main(String[] arg) {
		dealChinese();
		dealEnglish();

	}

	static String specialPattern(String string) {
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(string);
		string = m.replaceAll(" ").trim();
		string = string.replaceAll("\\s{1,}", " ");
		return string;

	}

	static String chineseWord(String str) {
		String reg = "[^\u4e00-\u9fa5]";
		str = str.replaceAll(reg, " ");
		str = str.replaceAll("\\s{1,}", " ");
		return str;
	}

	static String englishWord(String str) {
		String reg = "[^a-zA-Z]";
		str = str.replaceAll(reg, " ");
		str = str.replaceAll("\\s{1,}", " ");
		return str;
	}

	static void englishStop() {
		File file1 = new File("E:" + File.separator + "jsoup" + File.separator + "stopWords.txt");// 停用词
		String string1 = null;
		BufferedReader br1;
		try {
			br1 = new BufferedReader(new FileReader(file1));
			while ((string1 = br1.readLine()) != null) {// 使用readLine方法，一次读一行 读取停用词
				stopWords.add(string1);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 构造一个BufferedReader类来读取totalstop文件
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static void dealChinese() {
		for (int i = 1; i <= chineseNum; i++) {
			String str = "";
			File in_file = new File("E:" + File.separator + "jsoup" + File.separator + "News_1_Org_C" + File.separator
					+ "News_1_Org_C (" + String.valueOf(i) + ").txt");
			try {
				FileInputStream in = new FileInputStream(in_file);
				// size 为字串的长度 ，这里一次性读完
				int size = in.available();
				byte[] buffer = new byte[size];
				in.read(buffer);
				in.close();
				str = new String(buffer, "GBK");
			} catch (IOException e) {
				e.printStackTrace();
			}
			str = specialPattern(str);
			str = chineseWord(str);

			File out_file = new File("E:" + File.separator + "jsoup" + File.separator + "News_1_C" + File.separator
					+ "News_1_C (" + String.valueOf(i) + ").txt");

			if (!out_file.getParentFile().exists()) {
				out_file.getParentFile().mkdirs();
			}
			Writer out;
			try {
				out = new FileWriter(out_file);
				out.write(str);
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String input = "E:" + File.separator + "jsoup" + File.separator + "News_1_C" + File.separator + "News_1_C ("
					+ String.valueOf(i) + ").txt";
			String output = "E:" + File.separator + "jsoup" + File.separator + "News_1_Res_C" + File.separator
					+ "News_1_Res_C (" + String.valueOf(i) + ").txt";
			try {
				WordSegmenter.seg(new File(input), new File(output));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	static void dealEnglish() {
		englishStop();
		char[] w = new char[501];
		Stemmer s = new Stemmer();
		for (int i = 1; i <= engliashNum; i++) {
			String str = "";
			File in_file = new File("E:" + File.separator + "jsoup" + File.separator + "News_1_Org_E" + File.separator
					+ "News_1_Org_E (" + String.valueOf(i) + ").txt");
			try {
				FileInputStream in = new FileInputStream(in_file);
				// size 为字串的长度 ，这里一次性读完
				int size = in.available();
				byte[] buffer = new byte[size];
				in.read(buffer);
				in.close();
				str = new String(buffer, "GBK");
			} catch (IOException e) {
				e.printStackTrace();
			}
			str = specialPattern(str);
			str = englishWord(str);

			File out_file = new File("E:" + File.separator + "jsoup" + File.separator + "News_1_E" + File.separator
					+ "News_1_E (" + String.valueOf(i) + ").txt");

			if (!out_file.getParentFile().exists()) {
				out_file.getParentFile().mkdirs();
			}
			Writer out;
			try {
				out = new FileWriter(out_file);
				out.write(str);
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				FileInputStream in = new FileInputStream("E:" + File.separator + "jsoup" + File.separator + "News_1_E"
						+ File.separator + "News_1_E (" + String.valueOf(i) + ").txt");

				try {
					StringBuilder stringBuilder2 = new StringBuilder();
					while (true)

					{
						int ch = in.read();
						if (Character.isLetter((char) ch)) {
							int j = 0;

							while (true) {
								ch = Character.toLowerCase((char) ch);
								w[j] = (char) ch;
								if (j < 500)
									j++;
								ch = in.read();
								if (!Character.isLetter((char) ch)) {
									/* to test add(char ch) */
									for (int c = 0; c < j; c++)
										s.add(w[c]);

									/* or, to test add(char[] w, int j) */
									/* s.add(w, j); */

									s.stem();
									{
										// String u;

										/* and now, to test toString() : */
										if (!stopWords.contains(s.toString())) {
											stringBuilder2.append(s.toString());
											stringBuilder2.append("\r\n");
										}
										/* to test getResultBuffer(), getResultLength() : */
										/* u = new String(s.getResultBuffer(), 0, s.getResultLength()); */

										// System.out.print(u);
									}
									break;
								}
							}
							File out_file2 = new File("E:" + File.separator + "jsoup" + File.separator + "News_1_Res_E"
									+ File.separator + "News_1_Res_E (" + String.valueOf(i) + ").txt");

							if (!out_file2.getParentFile().exists()) {
								out_file2.getParentFile().mkdirs();
							}
							Writer out2;
							try {
								out2 = new FileWriter(out_file2);
								out2.write(stringBuilder2.toString());
								out2.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
						if (ch < 0)
							break;
						System.out.print((char) ch);
					}
				} catch (IOException e) {
					System.out.println("error reading ");
					break;
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
}
