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

import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;



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
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch(Exception e) {  
			JOptionPane.showMessageDialog(null,"Cannot set UI");
		}
		
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
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(emailField.getText().equals("") | passwordField.equals(null)) {
					JOptionPane.showMessageDialog(null, "Required field is empty. Please enter Valid credentials");
				} else {
					Response response = auth.login(emailField.getText(), new String(passwordField.getPassword()));
					if(response.isSuccess()) {
						JOptionPane.showMessageDialog(null, "Welcome " + AuthController.getLoggedInUser().getFirstName());
					}else {
						JOptionPane.showMessageDialog(null, "Invalid credentials");
					}
				}
			}
		});
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnLogin.setBounds(94, 223, 149, 42);
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
		btnRegister.setBounds(279, 223, 149, 42);
		getContentPane().add(btnRegister);
		
		
	}
}
