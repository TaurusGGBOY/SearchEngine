import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
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

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class ComparePage extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTable table_1;
	JTextArea textArea;
	JTextArea textArea_1;
	JLabel label_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ComparePage frame = new ComparePage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ComparePage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(30, 65, 367, 234);
		contentPane.add(scrollPane);

		table = new JTable();

		scrollPane.setViewportView(table);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(31, 309, 366, 244);
		contentPane.add(scrollPane_1);

		textArea = new JTextArea();
		textArea.setLineWrap(true);
		scrollPane_1.setViewportView(textArea);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(407, 65, 367, 234);
		contentPane.add(scrollPane_2);

		table_1 = new JTable();

		scrollPane_2.setViewportView(table_1);

		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(408, 309, 366, 244);
		contentPane.add(scrollPane_3);

		textArea_1 = new JTextArea();
		textArea_1.setLineWrap(true);
		scrollPane_3.setViewportView(textArea_1);

		JLabel label = new JLabel("\u4F59\u5F26\u76F8\u4F3C\u5EA6");
		label.setFont(new Font("宋体", Font.PLAIN, 16));
		label.setBounds(307, 25, 85, 15);
		contentPane.add(label);

		label_1 = new JLabel("");
		label_1.setForeground(Color.RED);
		label_1.setFont(new Font("宋体", Font.PLAIN, 16));
		label_1.setBounds(401, 25, 208, 15);
		contentPane.add(label_1);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				DefaultTableModel dtm = (DefaultTableModel) table.getModel();
				String value = (String) dtm.getValueAt(row, 1);
				try {

					InputStreamReader isr = new InputStreamReader(new FileInputStream(value), "UTF-8");
					BufferedReader reader = new BufferedReader(isr);

					String str = reader.readLine();
					String end = null;
					while ((str != null)) {
						end = end + str + "\n";
						str = reader.readLine();
					}

					textArea.setText(end.substring(4));
				}
				//
				catch (Exception e1) {
					// TODO: handle exception
					e1.printStackTrace();
				}
				calcu();
			}
		});
		table_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table_1.getSelectedRow();
				DefaultTableModel dtm = (DefaultTableModel) table_1.getModel();
				String value = (String) dtm.getValueAt(row, 1);
				try {

					InputStreamReader isr = new InputStreamReader(new FileInputStream(value), "UTF-8");
					BufferedReader reader = new BufferedReader(isr);

					String str = reader.readLine();
					String end = null;
					while ((str != null)) {
						end = end + str + "\n";
						str = reader.readLine();
					}

					textArea_1.setText(end.substring(4));
				}
				//
				catch (Exception e1) {
					// TODO: handle exception
					e1.printStackTrace();
				}
				calcu();
			}
		});
		init();

	}

	void init() {
		File fl = new File("E:\\jsoup\\News_1_Res_E");
		// 4、为Document添加Field
		Map<String, String> map = new HashMap<String, String>();
		for (File file : fl.listFiles()) {
			map.put(file.getName(), file.getAbsolutePath());
		}
		fl = new File("E:\\jsoup\\News_1_Res_C");
		for (File file : fl.listFiles()) {
			map.put(file.getName(), file.getAbsolutePath());
		}

		Vector<String> columns2 = new Vector<String>();
		Vector<Vector<String>> table2Columns = new Vector<>();

		columns2.add("文件名");
		columns2.add("路径");

		Vector<String> temp = new Vector<>();
		for (String string : map.keySet()) {
			temp = new Vector<>();
			temp.add(string);
			temp.add(map.get(string));
			table2Columns.add(temp);
		}

		DefaultTableModel model_temp = new DefaultTableModel(table2Columns, columns2);
		table.setModel(model_temp);
		DefaultTableModel model_temp_2 = model_temp;
		table_1.setModel(model_temp_2);
	}

	/**
	 * 获取两组字符串的词频向量
	 * 
	 * @param str1List
	 * @param str2List
	 * @return
	 */
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
			System.out.println(word);
			for (String str : str1List) {
				if (word.equals(str)) {
					s1++;
				}
			}
			System.out.println("s1=" + s1);
			res[0][i] = s1;
			for (String str : str2List) {
				if (word.equals(str)) {
					s2++;
				}
			}
			res[1][i] = s2;
			System.out.println("s2=" + s2);
			i++;
		}
		return res;
	}

	/*
	 * public static void main(String[] args) { String text1 = "专四语法词汇单项精讲"; String
	 * text2 = "专四语法词汇单项精讲"; List<String> str1List = stringParticiple(text1);
	 * List<String> str2List = stringParticiple(text2); int [][] res =
	 * getStringFrequency(str1List,str2List);
	 * System.out.println(getDoubleStrForCosValue(res)); }
	 */
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

	void calcu() {
		try {
			String[] string1 = textArea.getText().split(" ");
			String[] string2 = textArea_1.getText().split(" ");
			if (string1.length < 2)
				string1 = textArea.getText().split("\n|\r");
			if (string2.length < 2)
				string2 = textArea_1.getText().split("\n|\r");
			List<String> list1 = new ArrayList<>();
			List<String> list2 = new ArrayList<>();
			for (String string : string1) {
				list1.add(string);
			}
			for (String string : string2) {
				list2.add(string);
			}
			int[][] res = getStringFrequency(list1, list2);
			label_1.setText(String.valueOf(getDoubleStrForCosValue(res)));
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
}
