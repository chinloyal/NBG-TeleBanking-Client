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
import models.User;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;

import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.JCheckBox;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.Dimension;



public class Login extends JFrame {

	private JTextField emailField;
	private AuthController auth = new AuthController(new Client());
	private JPasswordField passwordField;

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
		setBounds(100, 100, 572, 328);
		getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("10px"),
				ColumnSpec.decode("10px"),
				ColumnSpec.decode("83px"),
				ColumnSpec.decode("95px"),
				ColumnSpec.decode("244px"),
				ColumnSpec.decode("32px"),
				ColumnSpec.decode("27px"),
				ColumnSpec.decode("30px"),
				ColumnSpec.decode("38px"),},
			new RowSpec[] {
				RowSpec.decode("35px"),
				RowSpec.decode("15px"),
				RowSpec.decode("36px"),
				RowSpec.decode("25px"),
				RowSpec.decode("15px"),
				RowSpec.decode("36px"),
				RowSpec.decode("23px"),
				RowSpec.decode("27px"),
				RowSpec.decode("27px"),}));
		
		JLabel lblemail = new JLabel("Email:");
		lblemail.setFont(new Font("Tahoma", Font.PLAIN, 13));
		getContentPane().add(lblemail, "4, 2, fill, fill");
		
		emailField = new JTextField();
		emailField.setColumns(10);
		getContentPane().add(emailField, "4, 3, 2, 1, fill, fill");

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(emailField.getText().equals("") | passwordField.equals(null)) {
					JOptionPane.showMessageDialog(null, "Required field is empty. Please enter Valid credentials");
				} else {
					Response response = auth.login(emailField.getText(), new String(passwordField.getPassword()));
					if(response.isSuccess()) {
						User loggedInUser = AuthController.getLoggedInUser();
						JOptionPane.showMessageDialog(null, "Welcome " + loggedInUser.getFirstName());
						dispose();		
						//Sending Customer Information to Customer Dashboard
						if(loggedInUser.getType().equals("customer")) {
							new CustomerDashboard(loggedInUser);
						}else if(loggedInUser.getType().equals("manager")) {
							new ManagerDashboard();
						}
					}else {
						JOptionPane.showMessageDialog(null, "Invalid credentials");
					}
				}
			}
		});
		
		JLabel lblPassword = new JLabel("Password:");
		getContentPane().add(lblPassword, "4, 5");
		
		passwordField = new JPasswordField();
		passwordField.setPreferredSize(new Dimension(13, 40));
		getContentPane().add(passwordField, "4, 6, 2, 1, fill, default");
		
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 13));
		getContentPane().add(btnLogin, "4, 9, fill, fill");
		
		JCheckBox passwordCheckBox = new JCheckBox(" Show Password");
		passwordCheckBox.setFont(new Font("Tahoma", Font.PLAIN, 13));
		passwordCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(passwordCheckBox.isSelected()) {
					passwordField.setEchoChar((char) 0);
				} else {
					passwordField.setEchoChar('\u2022');
				}
			}
		});
		getContentPane().add(passwordCheckBox, "4, 7, 2, 1, fill, fill");
		
		JButton btnRegister = new JButton("<html><u style=\"color: blue\">Don't have an account? Create one.</u></html>");
		btnRegister.setContentAreaFilled(false);
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				RegistrationView registrationWindow = new RegistrationView();
				registrationWindow.setVisible(true);
				dispose();
				
			}
		});
		btnRegister.setFont(new Font("Tahoma", Font.PLAIN, 13));
		getContentPane().add(btnRegister, "5, 9, fill, fill");
		
		
		
	}
}
