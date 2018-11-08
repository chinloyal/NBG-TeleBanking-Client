package views;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public class QueryView extends JFrame {

	private JPanel contentPane;
	private JTextField txtInsertFullName;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					QueryView frame = new QueryView();
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
	public QueryView() {
		super("NBG TeleBanking");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 643, 413);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Leave us a message!");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(204, 24, 215, 29);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Name:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNewLabel_1.setBounds(31, 63, 79, 24);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblEmailAddress = new JLabel("Email Address:");
		lblEmailAddress.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblEmailAddress.setBounds(31, 114, 120, 24);
		contentPane.add(lblEmailAddress);
		
		JLabel lblQueryType = new JLabel("Query Type:");
		lblQueryType.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblQueryType.setBounds(31, 163, 120, 29);
		contentPane.add(lblQueryType);
		
		JLabel lblMessage = new JLabel("Message:");
		lblMessage.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblMessage.setBounds(31, 218, 79, 24);
		contentPane.add(lblMessage);
		
		txtInsertFullName = new JTextField();
		txtInsertFullName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtInsertFullName.setBounds(178, 63, 215, 29);
		contentPane.add(txtInsertFullName);
		txtInsertFullName.setColumns(10);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textField.setColumns(10);
		textField.setBounds(178, 113, 215, 29);
		contentPane.add(textField);
		
		String[] messageStrings= {"Balance Inquiry", "Transfer", "Payment", "Support", "Other"};
		
		JComboBox comboBox = new JComboBox(messageStrings);
		comboBox.setSelectedIndex(-1);
		comboBox.setEditable(true);
		comboBox.setBounds(178, 163, 215, 29);
		contentPane.add(comboBox);
		
		JTextArea textArea = new JTextArea();
		textArea.setRows(20);
		textArea.setTabSize(4);
		textArea.setLineWrap(true);
		textArea.setBounds(178, 221, 297, 79);
		contentPane.add(textArea);
		
		JButton btnNewButton = new JButton("Send");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNewButton.setBounds(250, 329, 127, 34);
		contentPane.add(btnNewButton);
		
		  JScrollPane scrollPane = new JScrollPane(textArea);
		  scrollPane.setViewportView(textArea);
		  scrollPane.setBounds(178, 220, 330, 79);
		  scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
		  contentPane.add(scrollPane);
		
	}
}
