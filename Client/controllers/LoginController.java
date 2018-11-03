package controllers;

import java.io.IOException;

import javax.swing.JOptionPane;

import communication.Request;
import connection.Client;

public class LoginController {
	private Client client;
	private String txtEmail;
	private String txtPassword;

	public LoginController(String txtEmail, String txtPassword) {
		client = new Client();
		this.txtEmail = txtEmail;
		this.txtPassword = txtPassword;

		inputValidation();
		boolean success = CheckCredentials();
		if(success) {
			JOptionPane.showMessageDialog(null, "You Have Been Successfully Logged In! :)");
		}else {
			JOptionPane.showMessageDialog(null, "Your Credentials Didn't Match Our Records!\nPlease Try Again.");
		}
	}

	private boolean CheckCredentials() {
		boolean success = false;
		// RPC Method - "login"
		try {

			boolean connected = client.connect();
			if (connected) {
				client.send(new Request("login"));
				client.sendObject(txtEmail);
				client.sendObject(txtPassword);
			} else
				JOptionPane.showMessageDialog(null, "Couldn't Connect to Server! :(\n\nPlease Try Logging in Again.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return success;
	}

	private void inputValidation() {

		char[] emailChars = txtEmail.toCharArray();
		int emailLength = txtEmail.length(), spaces = 0, atSymbols = 0;

		// CHECKING FOR SPACES IN EMAIL TEXT BOX & FOR THE PRESENCE OF THE '@' SIGN
		for (int i = 0; i < emailLength; i++) {
			if (emailChars[i] == ' ') {
				spaces++;
			} else if (emailChars[i] == '@') {
				atSymbols++;
			}
		}
		if (spaces > 0)
			JOptionPane.showMessageDialog(null,
					"No spaces can be entered within your email!\n\nPlease omit the space(s).");
		else if (atSymbols < 1)
			JOptionPane.showMessageDialog(null, "Please don't forget the '@' symbol!\n\nPlease re-enter your email.");

		// CHECKING FOR BLANK EMAIL BOX (NO INPUT FROM USER)
		if (emailLength < 1)
			JOptionPane.showMessageDialog(null, "Please enter your email!\n\nIt cannot be left blank.");

		int pwdLength = txtPassword.length();
		// CHECKING FOR BLANK PASSWORD FIELD (NO INPUT FROM USER)
		if (pwdLength < 1) {
			JOptionPane.showMessageDialog(null, "Please enter your password!\n\nIt cannot be left blank.");
		}
	}
}
