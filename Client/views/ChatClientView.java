package views;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTextPane;
import java.awt.SystemColor;
import java.awt.Font;
import javax.swing.JSplitPane;
import javax.swing.JProgressBar;

public class ChatClientView extends JFrame implements ActionListener{
	private BufferedImage imgLisa;
	private BufferedImage imgChatBubble;
	private JLabel label;
	private JTextField txtRequest;

	public static void main(String[] args) {
		EventQueue.invokeLater(
				new Runnable(){
					public void run(){
						new ChatClientView();
					}
				}
		);
	}
	
	public ChatClientView() {
		getContentPane().setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		label = new JLabel("");
		label.setIcon(new ImageIcon(new ImageIcon(ChatClientView.class.getResource("/storage/images/lisa2.jpg")).getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT)));
		getContentPane().add(label);
		
		JTextArea txtAreaInstructions = new JTextArea();
		txtAreaInstructions.setFont(new Font("SansSerif", Font.PLAIN, 12));
		txtAreaInstructions.setForeground(SystemColor.desktop);
		txtAreaInstructions.setText("Instructions:\r\n1. You can have a regular conversation\r\nwith our banking bot LISA.\r\n\r\n2. Say \"I want to make a transaction\"\r\nto have the bot process transactions\r\nfor you.\r\n\r\n3. Say \"I want to pay a bill\" and the \r\nbot will assist you in paying a bill\r\n\r\n4. If using voice input speak clearly\r\nso the bot can understand you.");
		txtAreaInstructions.setEnabled(false);
		txtAreaInstructions.setEditable(false);
		txtAreaInstructions.setColumns(19);
		txtAreaInstructions.setRows(12);
		getContentPane().add(txtAreaInstructions);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel);
		panel.setLayout(new GridLayout(4, 1, 0, 0));
		
		JTextArea txtrSpeakNow = new JTextArea();
		txtrSpeakNow.setFont(new Font("SansSerif", Font.BOLD, 17));
		txtrSpeakNow.setEnabled(false);
		txtrSpeakNow.setEditable(false);
		txtrSpeakNow.setColumns(29);
		txtrSpeakNow.setText("Speak now...");
		panel.add(txtrSpeakNow);
		
		JTextArea txtrBotResponse = new JTextArea();
		txtrBotResponse.setEnabled(false);
		txtrBotResponse.setEditable(false);
		txtrBotResponse.setText("Bot Response");
		panel.add(txtrBotResponse);
		
		JTextArea txtrUserResponse = new JTextArea();
		txtrUserResponse.setEnabled(false);
		txtrUserResponse.setEditable(false);
		txtrUserResponse.setText("User Response");
		panel.add(txtrUserResponse);
		
		JProgressBar progressBar = new JProgressBar();
		panel.add(progressBar);
		
		JLabel lblMessageHit = new JLabel("Message (Hit <Enter> to send a message to the bot):");
		getContentPane().add(lblMessageHit);
		
		txtRequest = new JTextField();
		getContentPane().add(txtRequest);
		txtRequest.setColumns(33);
		
		JButton btnRecord = new JButton("");
		btnRecord.setToolTipText("Click this icon to utilize voice input, rather than text");
		btnRecord.setIcon(new ImageIcon(new ImageIcon(ChatClientView.class.getResource("/storage/images/mic.png")).getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT)));
		getContentPane().add(btnRecord);
		initView();
		configureListeners();
	}
	
	public void initView() {
	}
	
	private void configureListeners() {
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
