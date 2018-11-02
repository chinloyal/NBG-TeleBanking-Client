package views;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.springframework.security.crypto.bcrypt.BCrypt;

import connection.Client;
import controllers.AuthController;
import data.Response;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;



public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField idField;
	private final ButtonGroup rbtnAccType = new ButtonGroup();
	private JPasswordField passwordField;
	AuthController auth = new AuthController(new Client());

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
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
	public Login() {
		super("NBG TeleBanking Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 578, 560);
		getContentPane().setLayout(null);
		
		JLabel lblId = new JLabel("ID:");
		lblId.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblId.setBounds(94, 61, 103, 36);
		getContentPane().add(lblId);
		
		idField = new JTextField();
		idField.setColumns(10);
		idField.setBounds(207, 61, 221, 36);
		getContentPane().add(idField);
		
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPassword.setBounds(94, 131, 103, 36);
		getContentPane().add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(207, 131, 221, 32);
		getContentPane().add(passwordField);
		
		
		final JRadioButton rdbtnCustomer = new JRadioButton("Customer");
		rbtnAccType.add(rdbtnCustomer);
		rdbtnCustomer.setSelected(true);
		rdbtnCustomer.setFont(new Font("Tahoma", Font.PLAIN, 20));
		rdbtnCustomer.setBounds(94, 232, 149, 36);
		getContentPane().add(rdbtnCustomer);
		
		JRadioButton rdbtnManager = new JRadioButton("Manager");
		rbtnAccType.add(rdbtnManager);
		rdbtnManager.setFont(new Font("Tahoma", Font.PLAIN, 20));
		rdbtnManager.setBounds(302, 232, 149, 36);
		getContentPane().add(rdbtnManager);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(rdbtnCustomer.isSelected()) {
					if(idField.getText().equals("") | passwordField.equals(null)) {
						JOptionPane.showMessageDialog(null, "Either the ID or password field is empty. Please enter Valid credentials");
					} else {
						auth.login(idField.getText(), new String(passwordField.getPassword()));
								}
				}
			}
		});
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnLogin.setBounds(94, 356, 149, 42);
		getContentPane().add(btnLogin);
		
		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				RegistrationView registrationWindow = new RegistrationView();
				registrationWindow.setVisible(true);
				dispose();
				
			}
		});
		btnRegister.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnRegister.setBounds(279, 356, 149, 42);
		getContentPane().add(btnRegister);
		
		
	}
}
