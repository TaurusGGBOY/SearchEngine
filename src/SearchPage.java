import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class SearchPage extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SearchPage frame = new SearchPage();
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
	public SearchPage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textField = new JTextField();
		textField.setFont(new Font("宋体", Font.PLAIN, 16));
		textField.setBounds(31, 10, 533, 36);
		contentPane.add(textField);
		textField.setColumns(10);

		JButton btnNewButton = new JButton("\u8C37\u6B4C\u4E00\u4E0B");

		btnNewButton.setFont(new Font("宋体", Font.PLAIN, 16));
		btnNewButton.setBounds(592, 9, 182, 36);
		contentPane.add(btnNewButton);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(30, 65, 367, 485);
		contentPane.add(scrollPane);

		table = new JTable();

		scrollPane.setViewportView(table);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(401, 65, 375, 486);
		contentPane.add(scrollPane_1);

		JTextArea textArea = new JTextArea();
		textArea.setLineWrap(true);
		scrollPane_1.setViewportView(textArea);

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loadTable();
			}
		});
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				DefaultTableModel dtm = (DefaultTableModel) table.getModel();
				String value = (String) dtm.getValueAt(row, 1);
				try {
					BufferedReader reader = new BufferedReader(new FileReader(value));

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

	}

	void loadTable() {
		Map<String, String> map = new LuceneIndex().searcher(textField.getText());
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
	}
}
