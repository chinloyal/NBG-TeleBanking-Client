package views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import controllers.TransactionController;
import models.User;

import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class QueryView extends JDialog {

	private final JPanel contentPanel = new JPanel();
	
	private static User customer;
	private JTextField txtInsertFullName;
	private JTextField textEmail;
	private JButton btnSend;
	private JButton btnCancel;
	private JTextArea textMessage;
	private JComboBox comboBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			QueryView dialog = new QueryView(customer, null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public QueryView(User customer, JFrame parent) {
		super(parent, "NBG TeleBanking - Send a Message", true);
		this.customer = customer;
		setBounds(100, 100, 588, 428);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("90dlu"),
				ColumnSpec.decode("50dlu"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(94dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(82dlu;default)"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(12dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));
		{
			JLabel lblTitle = new JLabel("Leave us a message");
			lblTitle.setFont(new Font("SansSerif", Font.BOLD, 14));
			contentPanel.add(lblTitle, "2, 2, 3, 1, center, default");
		}
		{
			JLabel lblName = new JLabel("Name:");
			contentPanel.add(lblName, "2, 4");
		}
		{
			txtInsertFullName = new JTextField();
			String fullName = (customer.getFirstName() + " " + customer.getLastName()).toUpperCase();
			txtInsertFullName.setText(fullName);
			txtInsertFullName.setEditable(false);
			contentPanel.add(txtInsertFullName, "2, 6, 3, 1, fill, default");
			txtInsertFullName.setColumns(10);
		}
		{
			JLabel lblNewLabel = new JLabel("Email Address:");
			contentPanel.add(lblNewLabel, "2, 8");
		}
		{
			textEmail = new JTextField();
			textEmail.setText(customer.getEmail());
			textEmail.setEditable(false);
			contentPanel.add(textEmail, "2, 10, 3, 1, fill, default");
			textEmail.setColumns(10);
		}
		{
			JLabel lblQueryType = new JLabel("Query Type:");
			contentPanel.add(lblQueryType, "2, 12");
		}
		{
			comboBox = new JComboBox();
			comboBox.setModel(new DefaultComboBoxModel(new String[] {"Balance Inquiry", "Transfer", "Payment", "Support", "Other"}));
			contentPanel.add(comboBox, "2, 14, 3, 1, fill, default");
		}
		{
			JLabel lblMessage = new JLabel("Message:");
			contentPanel.add(lblMessage, "2, 16");
		}
		{ // Text Area/Message
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			contentPanel.add(scrollPane, "2, 18, 3, 1, fill, fill");
			{
				textMessage = new JTextArea();
				textMessage.setLineWrap(true);
				scrollPane.setViewportView(textMessage);
			}
		}
		{// Buttons
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnSend = new JButton("Send");
				buttonPane.add(btnSend);
				getRootPane().setDefaultButton(btnSend);
			}
			{
				btnCancel = new JButton("Cancel");
				buttonPane.add(btnCancel);
			}
		}
		
		configureListeners();
	}
	
	public void configureListeners() {
		btnSend.addActionListener((event)->{
			if(textMessage.getText().trim().length() < 1) {
				JOptionPane.showMessageDialog(null, "Cannot send blank message", "Warning", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			TransactionController message = new TransactionController();
			boolean success = message.storeMessage(textMessage.getText(), customer.getEmail(), (String)comboBox.getSelectedItem());
			if (success) {
				JOptionPane.showMessageDialog(null,"Message Sent Successfully! :)");
				dispose();
			}else {
				JOptionPane.showMessageDialog(null,"Message Not Sent! :(\nPlease Try Again.", "An issue occured", JOptionPane.ERROR_MESSAGE);
			}
		});
		
		btnCancel.addActionListener((event)->{
			dispose();
		});
		
	}

}
