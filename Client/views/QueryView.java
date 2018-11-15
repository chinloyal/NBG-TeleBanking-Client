package views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controllers.TransactionController;
import models.User;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JScrollPane;

public class QueryView extends JDialog  {

	private static User customer;
	private JPanel contentPane;
	private JTextField txtInsertFullName;
	private JTextField textEmail;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					QueryView frame = new QueryView(customer, null);
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
	public QueryView(User customer, JFrame parent) {

		super(parent, "NBG TeleBanking - Send a Message", true);
		this.customer = customer;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
		String fullName = (customer.getFirstName() + " " + customer.getLastName()).toUpperCase();
		txtInsertFullName.setText(fullName);
		txtInsertFullName.setEditable(false);
		contentPane.add(txtInsertFullName);
		txtInsertFullName.setColumns(10);

		textEmail = new JTextField();
		textEmail.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textEmail.setColumns(10);
		textEmail.setBounds(178, 113, 215, 29);
		textEmail.setText(customer.getEmail());
		textEmail.setEditable(false);
		contentPane.add(textEmail);

		String[] messageStrings = { "Balance Inquiry", "Transfer", "Payment", "Support", "Other" };

		JComboBox comboBox = new JComboBox(messageStrings);
		comboBox.setSelectedIndex(-1);
		comboBox.setEditable(true);
		comboBox.setBounds(178, 163, 215, 29);
		contentPane.add(comboBox);

		JTextArea textMessage = new JTextArea();
		textMessage.setRows(20);
		textMessage.setTabSize(4);
		textMessage.setLineWrap(true);
		textMessage.setBounds(178, 221, 297, 79);
		contentPane.add(textMessage);

		JButton btnSend = new JButton("Send");
		btnSend.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnSend.setBounds(250, 329, 127, 34);
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TransactionController message = new TransactionController();
				boolean success = message.storeMessage(textMessage.getText(), customer.getEmail(), (String)comboBox.getSelectedItem());
				if (success) {
					JOptionPane.showMessageDialog(null,"Message Sent Successfully! :)");
					dispose();
				}else {
					JOptionPane.showMessageDialog(null,"Message Not Sent! :(\nPlease Try Again.", "An issue occured", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		contentPane.add(btnSend);

		JScrollPane scrollPane = new JScrollPane(textMessage);
		scrollPane.setViewportView(textMessage);
		scrollPane.setBounds(178, 220, 330, 79);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		contentPane.add(scrollPane);

	}
}
