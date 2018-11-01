package views;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controllers.AuthController;

public class MainScreenView extends JFrame implements ActionListener {
	
	private AuthController controller;
	private JTextField txtEmail;
	private JPasswordField txtPassword;
	private JButton btnLogin;
	private JButton btnRegister;
	private JCheckBox checkPassword;
	private JLabel showP;
	
	public MainScreenView(){
		initView();
		configureListeners();
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(
				new Runnable(){
					public void run(){
						new MainScreenView();
					}
				}
		);
	}
	
	private void initView() {
		setTitle("NBG TeleBanking - Login");
		setSize(400,225);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		setLayout(new GridLayout(5,1));
		
		// ------ ROW 1: Email Address ----- //
		JPanel panelR1 = new JPanel(new GridLayout(1,2));
		panelR1.add(new Label("Email Address:"));
		txtEmail = new JTextField();
		panelR1.add(txtEmail);
		this.add(panelR1);
		
		// ------ ROW 2: Password ----- //
		JPanel panelR2 = new JPanel(new GridLayout(1,2));
		panelR2.add(new Label("Password:"));
		txtPassword = new JPasswordField();
		panelR2.add(txtPassword);
		this.add(panelR2);
		
		// ------ ROW 3: Password Check ---- //
		JPanel panelR3 = new JPanel (new GridLayout(1,3));
		panelR3.add(new Label("Show My Password:"));
		checkPassword = new JCheckBox();
		panelR3.add(checkPassword);
		showP = new JLabel(" ");
		panelR3.add(showP);	
		this.add(panelR3);
		
		// ------ ROW 4: Login Button ----- //
		JPanel panelR4 = new JPanel(new FlowLayout());
		btnLogin = new JButton("Login");
		panelR4.add(btnLogin);
		this.add(panelR4);
		
		// ------ ROW 5: Register Button ----- //
		JPanel panelR5 = new JPanel(new FlowLayout());
		panelR5.add(new Label("New to NBG TeleBanking? "));
		btnRegister = new JButton("Register");
		panelR5.add(btnRegister);
		this.add(panelR5);
		
	}

	private void configureListeners() {
		btnLogin.addActionListener(this);
		btnRegister.addActionListener(this);
		checkPassword.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource().equals(btnLogin)) {
			
			// Checks Authorization & Authentication for User
			
			
		}else if (event.getSource().equals(btnRegister)) {
			
			
			// Open Registration View
			
			
		} else if (event.getSource().equals(checkPassword)) {
			if (checkPassword.isSelected()) {
				char[] input = txtPassword.getPassword();
				
				// Show password in text box
			}else {
				// Show password with dots/asterisk
			}
		}
	}
}
