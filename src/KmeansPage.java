import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class KmeansPage extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTable table_1;
	JTextArea textArea;
	JTextArea textArea_1;
	JComboBox<Integer> comboBox;
	JComboBox<Integer> comboBox_1;
	Map<Integer, Set<Integer>> map_c;
	Map<Integer, Set<Integer>> map_e;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					KmeansPage frame = new KmeansPage();
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
	public KmeansPage() {
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

		comboBox = new JComboBox<>();
		comboBox.setBounds(94, 19, 150, 21);
		contentPane.add(comboBox);

		comboBox_1 = new JComboBox<>();
		comboBox_1.setBounds(470, 19, 150, 21);
		contentPane.add(comboBox_1);

		JLabel lblNewLabel = new JLabel("\u4E2D\u6587");
		lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 16));
		lblNewLabel.setBounds(30, 22, 54, 15);
		contentPane.add(lblNewLabel);

		JLabel label = new JLabel("\u82F1\u6587");
		label.setFont(new Font("宋体", Font.PLAIN, 16));
		label.setBounds(407, 22, 54, 15);
		contentPane.add(label);
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
			}
		});
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loadTable1();

			}
		});
		comboBox_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loadTable2();

			}
		});
		init();

	}

	void init() {
		map_c = new KmeansAlg().kmeans_C();
		for (int temp : map_c.keySet()) {
			comboBox.addItem(temp);
		}
		map_e = new KmeansAlg().kmeans_E();
		for (int temp : map_e.keySet()) {
			comboBox_1.addItem(temp);
		}

	}

	void loadTable1() {

		Vector<String> columns2 = new Vector<String>();
		Vector<Vector<String>> table2Columns = new Vector<>();

		columns2.add("文件名");
		columns2.add("路径");

		Vector<String> temp = new Vector<>();
		for (int num : map_c.get(Integer.parseInt(comboBox.getSelectedItem().toString()))) {
			String string1 = "News_1_C (" + String.valueOf(num + 1) + ").txt";
			String string2 = "E:\\jsoup\\News_1_C\\News_1_C (" + String.valueOf(num + 1) + ").txt";
			temp = new Vector<>();
			temp.add(string1);
			temp.add(string2);
			table2Columns.add(temp);
		}

		DefaultTableModel model_temp = new DefaultTableModel(table2Columns, columns2);
		table.setModel(model_temp);
	}

	void loadTable2() {

		Vector<String> columns2 = new Vector<String>();
		Vector<Vector<String>> table2Columns = new Vector<>();

		columns2.add("文件名");
		columns2.add("路径");

		Vector<String> temp = new Vector<>();
		for (int num : map_c.get(Integer.parseInt(comboBox.getSelectedItem().toString()))) {
			String string1 = "News_1_E (" + String.valueOf(num + 1) + ").txt";
			String string2 = "E:\\jsoup\\News_1_E\\News_1_E (" + String.valueOf(num + 1) + ").txt";
			temp = new Vector<>();
			temp.add(string1);
			temp.add(string2);
			table2Columns.add(temp);
		}

		DefaultTableModel model_temp = new DefaultTableModel(table2Columns, columns2);
		table_1.setModel(model_temp);
	}
}
