import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import kmeans.kmeans;
import kmeans.kmeans_data;
import kmeans.kmeans_param;

public class KmeansAlg {

	static public void main(String[] arg) {
		// new KmeansAlg().writeCosMatrix_C();
		new KmeansAlg().kmeans_C();
	}

	public static int[][] getStringFrequency(List<String> str1List, List<String> str2List) {
		Set<String> cnSet = new HashSet<String>();
		cnSet.addAll(str1List);
		cnSet.addAll(str2List);
		int[][] res = new int[2][cnSet.size()];
		Iterator it = cnSet.iterator();
		int i = 0;
		while (it.hasNext()) {
			String word = it.next().toString();
			int s1 = 0;
			int s2 = 0;

			for (String str : str1List) {
				if (word.equals(str)) {
					s1++;
				}
			}

			res[0][i] = s1;
			for (String str : str2List) {
				if (word.equals(str)) {
					s2++;
				}
			}
			res[1][i] = s2;
			/*
			 * if (s1 != s2) { System.out.println(word); System.out.println(word.length());
			 * System.out.println("s1=" + s1); System.out.println("s2=" + s2); }
			 */
			i++;
		}
		return res;
	}

	/**
	 * 获取两组向量的余弦值
	 * 
	 * @param ints
	 * @return
	 */
	public static float getDoubleStrForCosValue(int[][] ints) {
		BigDecimal fzSum = new BigDecimal(0);
		BigDecimal fmSum = new BigDecimal(0);
		int num = ints[0].length;
		for (int i = 0; i < num; i++) {
			BigDecimal adb = new BigDecimal(ints[0][i]).multiply(new BigDecimal(ints[1][i]));
			fzSum = fzSum.add(adb);
		}

		BigDecimal seq1SumBigDecimal = new BigDecimal(0);
		BigDecimal seq2SumBigDecimal = new BigDecimal(0);
		for (int i = 0; i < num; i++) {
			seq1SumBigDecimal = seq1SumBigDecimal.add(new BigDecimal(Math.pow(ints[0][i], 2)));
			seq2SumBigDecimal = seq2SumBigDecimal.add(new BigDecimal(Math.pow(ints[1][i], 2)));
		}
		double sqrt1 = Math.sqrt(seq1SumBigDecimal.doubleValue());
		double sqrt2 = Math.sqrt(seq2SumBigDecimal.doubleValue());
		fmSum = new BigDecimal(sqrt1).multiply(new BigDecimal(sqrt2));

		return fzSum.divide(fmSum, 10, RoundingMode.HALF_UP).floatValue();
	}

	public static float getDoubleStrForCosValue(float[][] ints) {
		float fzSum = 0f;
		float fmSum = 0f;
		int num = ints[0].length;
		for (int i = 0; i < num; i++) {
			float adb = ints[0][i] * ints[1][i];
			fzSum += adb;
		}

		float seq1SumBigDecimal = 0f;
		float seq2SumBigDecimal = 0f;
		for (int i = 0; i < num; i++) {
			seq1SumBigDecimal += Math.pow(ints[0][i], 2);
			seq2SumBigDecimal += Math.pow(ints[1][i], 2);
		}
		double sqrt1 = Math.sqrt(seq1SumBigDecimal);
		double sqrt2 = Math.sqrt(seq2SumBigDecimal);
		fmSum = (float) (sqrt1 * sqrt2);

		return fzSum / fmSum;
	}

	public void writeCosMatrix() {
		writeCosMatrix_C();
		writeCosMatrix_E();
	}

	public void writeCosMatrix_C() {
		Vector<Vector<Float>> vector = new Vector<>();
		File ine = new File("E:\\jsoup\\News_1_Res_C");
		int lene = ine.listFiles().length;
		// int lene = 2;
		try {
			for (int i = 1; i <= lene; i++) {
				File infile1 = new File("E:" + File.separator + "jsoup" + File.separator + "News_1_Res_C"
						+ File.separator + "News_1_Res_C (" + String.valueOf(i) + ").txt");
				InputStreamReader isr = new InputStreamReader(new FileInputStream(infile1), "UTF-8");
				BufferedReader reader = new BufferedReader(isr);
				String str = reader.readLine();
				String end = "";
				while ((str != null)) {
					end = end + str + "\n";
					str = reader.readLine();
				}
				String[] string1 = end.split(" ");
				if (string1.length < 2)
					string1 = end.split("\n|\r");
				List<String> list1 = new ArrayList<>();
				for (String string : string1) {
					list1.add(string);
				}
				Vector<Float> temp = new Vector<>();
				System.out.println(i);
				for (int j = 1; j <= lene; j++) {
					File infile2 = new File("E:" + File.separator + "jsoup" + File.separator + "News_1_Res_C"
							+ File.separator + "News_1_Res_C (" + String.valueOf(j) + ").txt");
					InputStreamReader isr2 = new InputStreamReader(new FileInputStream(infile2), "UTF-8");
					BufferedReader reader2 = new BufferedReader(isr2);
					String str2 = reader2.readLine();
					String end2 = "";
					while ((str2 != null)) {
						end2 = end2 + str2 + "\n";
						str2 = reader2.readLine();
					}
					String[] string2 = end2.split(" ");
					if (string2.length < 2)
						string2 = end2.split("\n|\r");
					List<String> list2 = new ArrayList<>();
					for (String string : string2) {
						// System.out.println(string);
						list2.add(string);
					}
					if (list1.isEmpty() || list2.isEmpty()) {
						temp.add((float) 0);
					} else
						temp.add(getDoubleStrForCosValue(getStringFrequency(list1, list2)));
					reader2.close();
				}
				vector.add(temp);
				reader.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		StringBuilder sBuilder = new StringBuilder();
		for (Vector<Float> vector2 : vector) {
			for (float tempfloat : vector2) {
				// System.out.println(tempfloat);
				sBuilder.append(String.valueOf(tempfloat));
				sBuilder.append(" ");
			}
			sBuilder.append("\r\n");
		}

		File out_file2 = new File(
				"E:" + File.separator + "jsoup" + File.separator + "Matrix" + File.separator + "CosMatrix_C.txt");

		if (!out_file2.getParentFile().exists()) {
			out_file2.getParentFile().mkdirs();
		}
		Writer out2;
		try {
			out2 = new FileWriter(out_file2);
			out2.write(sBuilder.toString());
			out2.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void writeCosMatrix_E() {
		Vector<Vector<Float>> vector = new Vector<>();
		File ine = new File("E:\\jsoup\\News_1_Res_E");
		int lene = ine.listFiles().length;
		// int lene = 20;
		try {
			for (int i = 1; i <= lene; i++) {
				File infile1 = new File("E:" + File.separator + "jsoup" + File.separator + "News_1_Res_E"
						+ File.separator + "News_1_Res_E (" + String.valueOf(i) + ").txt");
				BufferedReader reader = new BufferedReader(new FileReader(infile1));
				String str = reader.readLine();
				String end = null;
				while ((str != null)) {
					end = end + str + "\n";
					str = reader.readLine();
				}
				String[] string1 = end.split(" ");
				if (string1.length < 2)
					string1 = end.split("\n|\r");
				List<String> list1 = new ArrayList<>();
				for (String string : string1) {
					list1.add(string);
				}
				Vector<Float> temp = new Vector<>();
				for (int j = 1; j <= lene; j++) {
					File infile2 = new File("E:" + File.separator + "jsoup" + File.separator + "News_1_Res_E"
							+ File.separator + "News_1_Res_E (" + String.valueOf(j) + ").txt");
					BufferedReader reader2 = new BufferedReader(new FileReader(infile2));
					String str2 = reader2.readLine();
					String end2 = null;
					while ((str2 != null)) {
						end2 = end2 + str2 + "\n";
						str2 = reader2.readLine();
					}
					String[] string2 = end2.split(" ");
					if (string2.length < 2)
						string2 = end2.split("\n|\r");
					List<String> list2 = new ArrayList<>();
					for (String string : string2) {
						list2.add(string);
					}
					temp.add(getDoubleStrForCosValue(getStringFrequency(list1, list2)));
					reader2.close();
				}
				vector.add(temp);
				reader.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		StringBuilder sBuilder = new StringBuilder();
		for (Vector<Float> vector2 : vector) {
			for (float tempfloat : vector2) {
				sBuilder.append(String.valueOf(tempfloat));
				sBuilder.append(" ");
			}
			sBuilder.append("\r\n");
		}

		File out_file2 = new File(
				"E:" + File.separator + "jsoup" + File.separator + "Matrix" + File.separator + "CosMatrix_E.txt");

		if (!out_file2.getParentFile().exists()) {
			out_file2.getParentFile().mkdirs();
		}
		Writer out2;
		try {
			out2 = new FileWriter(out_file2);
			out2.write(sBuilder.toString());
			out2.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Map<Integer, Set<Integer>> kmeans_C() {
		try {
			File infile1 = new File(
					"E:" + File.separator + "jsoup" + File.separator + "Matrix" + File.separator + "CosMatrix_C.txt");
			BufferedReader reader = new BufferedReader(new FileReader(infile1));
			String str = reader.readLine();
			String[] string1 = str.split(" ");
			Vector<Float> temp = new Vector<>();
			int row = 0;
			for (String string : string1) {
				try {
					temp.add(Float.parseFloat(string));
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			int len = temp.size();
			double[][] points = new double[len][len];
			for (int i = 0; i < len; i++) {
				points[row][i] = (double) temp.get(i);
			}
			row++;
			while ((str != null)) {
				str = reader.readLine();
				if (row == len)
					break;
				temp = new Vector<>();
				string1 = str.split(" ");
				for (String string : string1) {
					try {
						temp.add(Float.parseFloat(string));
					} catch (Exception e) {
						// TODO: handle exception
						// System.out.println(string);
					}
				}
				for (int i = 0; i < len; i++) {
					points[row][i] = (double) temp.get(i);
					// System.out.println(points[row][i]);
				}
				row++;

			}
			kmeans_data data = new kmeans_data(points, len, len); // 初始化数据结构
			kmeans_param param = new kmeans_param(); // 初始化参数结构
			param.initCenterMehtod = kmeans_param.CENTER_RANDOM;
			// 做kmeans计算，分两类
			kmeans.doKmeans(20, data, param);

			// 查看每个点的所属聚类标号
			int[] max3 = new int[3];
			int[] maxindex = new int[3];
			int j = 0;
			for (int maxCluster : data.centerCounts) {
				int min = max3[0];
				int minIndex = 0;
				for (int i = 1; i < 3; i++) {
					if (max3[i] < min) {
						min = max3[i];
						minIndex = i;
					}
				}
				if (maxCluster > min) {
					max3[minIndex] = maxCluster;
					maxindex[minIndex] = j;
				}
				j++;
			}

			/*
			 * for (int maxmax : max3) { System.out.println(maxmax);
			 * System.out.println(maxindex[i]); i++; }
			 * System.out.println(data.centers.length);
			 * System.out.println(data.centers[0].length);
			 */
			Set<Integer> indexSet = new HashSet<>();
			Map<Integer, Map<Integer, Float>> disMap = new HashMap<>();
			for (int temp1 : maxindex) {
				Map<Integer, Float> indexMap = new HashMap<>();
				indexSet.add(temp1);
				disMap.put(temp1, indexMap);
			}

			int i = 0;
			for (int label : data.labels) {
				if (indexSet.contains(label)) {
					Map<Integer, Float> indexMap2 = disMap.get(label);

					float[][] tempfloat = new float[2][len];
					for (int m = 0; m < len; m++) {
						tempfloat[0][m] = (float) points[i][m];
						tempfloat[1][m] = (float) data.centers[label][m];
					}
					float cosv = getDoubleStrForCosValue(tempfloat);
					indexMap2.put(i, cosv);
					disMap.put(label, indexMap2);

				}
				i++;
			}
			// 每一个类

			Map<Integer, Set<Integer>> max5Set = new HashMap<>();
			for (int temp2 : maxindex) {
				Map<Integer, Float> indexMap2 = disMap.get(temp2);
				Set<Integer> maxSet = new HashSet<>();

				// 类里面的每一个距离
				for (int indexv : indexMap2.keySet()) {
					if (maxSet.size() < 5) {
						maxSet.add(indexv);
					} else {
						int minIndex = 0;
						float minFloat = 999f;
						for (int setIndex : maxSet) {
							if (indexMap2.get(setIndex) < minFloat) {
								minIndex = setIndex;
								minFloat = indexMap2.get(setIndex);
							}
						}

						if (minFloat < indexMap2.get(indexv)) {
							maxSet.remove(minIndex);
							maxSet.add(indexv);
						}
					}
				}
				max5Set.put(temp2, maxSet);
			}

			return max5Set;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	public Map<Integer, Set<Integer>> kmeans_E() {
		try {
			File infile1 = new File(
					"E:" + File.separator + "jsoup" + File.separator + "Matrix" + File.separator + "CosMatrix_E.txt");
			BufferedReader reader = new BufferedReader(new FileReader(infile1));
			String str = reader.readLine();
			String[] string1 = str.split(" ");
			Vector<Float> temp = new Vector<>();
			int row = 0;
			for (String string : string1) {
				try {
					temp.add(Float.parseFloat(string));
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			int len = temp.size();
			double[][] points = new double[len][len];
			for (int i = 0; i < len; i++) {
				points[row][i] = (double) temp.get(i);
			}
			row++;
			while ((str != null)) {
				str = reader.readLine();
				if (row == len)
					break;
				temp = new Vector<>();
				string1 = str.split(" ");
				for (String string : string1) {
					try {
						temp.add(Float.parseFloat(string));
					} catch (Exception e) {
						// TODO: handle exception
						// System.out.println(string);
					}
				}
				for (int i = 0; i < len; i++) {
					points[row][i] = (double) temp.get(i);
					// System.out.println(points[row][i]);
				}
				row++;

			}
			kmeans_data data = new kmeans_data(points, len, len); // 初始化数据结构
			kmeans_param param = new kmeans_param(); // 初始化参数结构
			param.initCenterMehtod = kmeans_param.CENTER_RANDOM;
			// 做kmeans计算，分两类
			kmeans.doKmeans(20, data, param);

			// 查看每个点的所属聚类标号
			int[] max3 = new int[3];
			int[] maxindex = new int[3];
			int j = 0;
			for (int maxCluster : data.centerCounts) {
				int min = max3[0];
				int minIndex = 0;
				for (int i = 1; i < 3; i++) {
					if (max3[i] < min) {
						min = max3[i];
						minIndex = i;
					}
				}
				if (maxCluster > min) {
					max3[minIndex] = maxCluster;
					maxindex[minIndex] = j;
				}
				j++;
			}

			/*
			 * for (int maxmax : max3) { System.out.println(maxmax);
			 * System.out.println(maxindex[i]); i++; }
			 * System.out.println(data.centers.length);
			 * System.out.println(data.centers[0].length);
			 */
			Set<Integer> indexSet = new HashSet<>();
			Map<Integer, Map<Integer, Float>> disMap = new HashMap<>();
			for (int temp1 : maxindex) {
				Map<Integer, Float> indexMap = new HashMap<>();
				indexSet.add(temp1);
				disMap.put(temp1, indexMap);
			}

			int i = 0;
			for (int label : data.labels) {
				if (indexSet.contains(label)) {
					Map<Integer, Float> indexMap2 = disMap.get(label);

					float[][] tempfloat = new float[2][len];
					for (int m = 0; m < len; m++) {
						tempfloat[0][m] = (float) points[i][m];
						tempfloat[1][m] = (float) data.centers[label][m];
					}
					float cosv = getDoubleStrForCosValue(tempfloat);
					indexMap2.put(i, cosv);
					disMap.put(label, indexMap2);

				}
				i++;
			}
			// 每一个类

			Map<Integer, Set<Integer>> max5Set = new HashMap<>();
			for (int temp2 : maxindex) {
				Map<Integer, Float> indexMap2 = disMap.get(temp2);
				Set<Integer> maxSet = new HashSet<>();

				// 类里面的每一个距离
				for (int indexv : indexMap2.keySet()) {
					if (maxSet.size() < 5) {
						maxSet.add(indexv);
					} else {
						int minIndex = 0;
						float minFloat = 999f;
						for (int setIndex : maxSet) {
							if (indexMap2.get(setIndex) < minFloat) {
								minIndex = setIndex;
								minFloat = indexMap2.get(setIndex);
							}
						}

						if (minFloat < indexMap2.get(indexv)) {
							maxSet.remove(minIndex);
							maxSet.add(indexv);
						}
					}
				}
				max5Set.put(temp2, maxSet);
			}

			return max5Set;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	public void removeC() {
		Vector<Vector<Float>> vector = new Vector<>();
		File ine = new File("E:\\jsoup\\News_1_Res_C");
		int lene = ine.listFiles().length;
		// int lene = 2;
		try {
			for (int i = 1; i <= lene; i++) {
				File infile1 = new File("E:" + File.separator + "jsoup" + File.separator + "News_1_Res_C"
						+ File.separator + "News_1_Res_C (" + String.valueOf(i) + ").txt");
				InputStreamReader isr = new InputStreamReader(new FileInputStream(infile1), "UTF-8");
				BufferedReader reader = new BufferedReader(isr);
				String str = reader.readLine();
				str = str.substring(1);
				String end = "";
				while ((str != null)) {
					end = end + str + "\n";
					str = reader.readLine();
				}
				String[] string1 = end.split(" ");
				if (string1.length < 2) {
					infile1.delete();
					System.out.println(string1.length);
				}
				reader.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
