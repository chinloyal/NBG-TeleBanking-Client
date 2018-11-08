package views;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.springframework.security.crypto.bcrypt.BCrypt;

import communication.Response;
import connection.Client;
import controllers.AuthController;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;

import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.JCheckBox;



public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField emailField;
	private final ButtonGroup rbtnAccType = new ButtonGroup();
	private JPasswordField passwordField;
	JInternalFrame frame;
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
		
		JLabel lblemail = new JLabel("Email:");
		lblemail.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblemail.setBounds(94, 61, 103, 36);
		getContentPane().add(lblemail);
		
		emailField = new JTextField();
		emailField.setColumns(10);
		emailField.setBounds(207, 61, 221, 36);
		getContentPane().add(emailField);
		
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPassword.setBounds(94, 131, 103, 36);
		getContentPane().add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(207, 131, 221, 32);
		getContentPane().add(passwordField);
		
		// ------ CheckBox Gives Option to Show Password
		JLabel lblShowPwd = new JLabel("Show My Password:");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblPassword.setBounds(94, 131, 103, 36);
		getContentPane().add(lblShowPwd);
		
		JCheckBox checkShowPassword = new JCheckBox();
		add(checkShowPassword);
		getContentPane().add(checkShowPassword);
		// ----- User Can Show Password to Locate Errors
		
		final JRadioButton rdbtnCustomer = new JRadioButton("Customer");
		rbtnAccType.add(rdbtnCustomer);
		rdbtnCustomer.setSelected(true);
		rdbtnCustomer.setFont(new Font("Tahoma", Font.PLAIN, 20));
		rdbtnCustomer.setBounds(94, 264, 149, 36);
		getContentPane().add(rdbtnCustomer);
		
		JRadioButton rdbtnManager = new JRadioButton("Manager");
		rbtnAccType.add(rdbtnManager);
		rdbtnManager.setFont(new Font("Tahoma", Font.PLAIN, 20));
		rdbtnManager.setBounds(301, 264, 149, 36);
		getContentPane().add(rdbtnManager);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(rdbtnCustomer.isSelected()) {
					if(emailField.getText().equals("") | passwordField.equals(null)) {
						JOptionPane.showMessageDialog(null, "Required field is empty. Please enter Valid credentials");
					} else {
<<<<<<< HEAD
						if(auth.login(emailField.getText(), new String(passwordField.getPassword())).isSuccess()) {
=======
<<<<<<< HEAD
						if(auth.login(emailField.getText(), new String(passwordField.getPassword()))) {
>>>>>>> 551dc900c80d8f23e1d12469f472ee580facd9f9
							JOptionPane.showMessageDialog(null, "Welcome");
=======
						Response response = auth.login(emailField.getText(), new String(passwordField.getPassword()));
						if(response.isSuccess()) {
							JOptionPane.showMessageDialog(null, "Login Successful! :)");
							setVisible(false);
			
							//Sending Customer Information to Customer Dashboard
							new CustomerDashboard(response.getData());
>>>>>>> 95a3bb07cbb63b3d2834b89862f1d9da9b329ee4
						}else {
							JOptionPane.showMessageDialog(null, "Invalid Credentials! :(\nPlease Try Again.");
						}
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
		
		JCheckBox passwordCheckBox = new JCheckBox(" Show Password");
		passwordCheckBox.setFont(new Font("Tahoma", Font.PLAIN, 20));
		passwordCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(passwordCheckBox.isSelected()) {
					passwordField.setEchoChar((char) 0);
				} else {
					passwordField.setEchoChar('\u2022');
				}
			}
		});
		passwordCheckBox.setBounds(95, 218, 181, 23);
		getContentPane().add(passwordCheckBox);
		
		
		
	}
}
