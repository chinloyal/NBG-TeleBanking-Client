package views;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.chinloyal.listen.Hearable;

import controllers.TransactionController;
import controllers.VoiceInputController;
import javazoom.jl.decoder.JavaLayerException;

import java.awt.SystemColor;
import java.awt.Font;
import javax.swing.JProgressBar;
import java.awt.Component;
import java.awt.Color;

public class ChatClientView extends JFrame implements ActionListener, KeyListener, Hearable{
	private JLabel label;
	private JTextField txtRequest;
	private JButton btnRecord;
	private JTextArea txtrUserRequest;
	private JTextArea txtrBotResponse;
	private JProgressBar progressBar;
	private JTextArea txtrSpeakNow;
	
	private TransactionController tc = new TransactionController();
	private VoiceInputController vc = new VoiceInputController();
	private Logger logger = LogManager.getLogger(ChatClientView.class);
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch(Exception e) {  
			JOptionPane.showMessageDialog(null,"Cannot set UI");
		}
		
		EventQueue.invokeLater(
				new Runnable(){
					public void run(){
						ChatClientView frame = new ChatClientView();
						frame.setVisible(true);
					}
				}
		);
	}
	
	public ChatClientView() {
		super("NBG Tele-Banking - Transactions");
		getContentPane().setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		setSize(450, 518);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		
		label = new JLabel("");
		label.setIcon(new ImageIcon(new ImageIcon(ChatClientView.class.getResource("/storage/uploads/lisa2.jpg")).getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT)));
		getContentPane().add(label);
		
		JTextArea txtAreaInstructions = new JTextArea();
		txtAreaInstructions.setLineWrap(true);
		txtAreaInstructions.setFont(new Font("SansSerif", Font.PLAIN, 12));
		txtAreaInstructions.setForeground(SystemColor.desktop);
		txtAreaInstructions.setText("Instructions:\r\n1. You can have a regular conversation with our banking bot LISA.\r\n\r\n2. Say \"I want to make a transaction\" to have the bot process transactions for you.\r\n\r\n3. Say \"I want to pay a bill\" and the bot will assist you in paying a bill\r\n\r\n4. If using voice input speak clearly.\r\nso the bot can understand you.");
		txtAreaInstructions.setEnabled(false);
		txtAreaInstructions.setEditable(false);
		txtAreaInstructions.setColumns(19);
		txtAreaInstructions.setRows(12);
		getContentPane().add(txtAreaInstructions);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel);
		panel.setLayout(new GridLayout(4, 1, 0, 0));
		
		txtrSpeakNow = new JTextArea();
		txtrSpeakNow.setDisabledTextColor(SystemColor.desktop);
		txtrSpeakNow.setFont(new Font("SansSerif", Font.BOLD, 17));
		txtrSpeakNow.setEnabled(false);
		txtrSpeakNow.setEditable(false);
		txtrSpeakNow.setColumns(29);
		txtrSpeakNow.setText("");
		panel.add(txtrSpeakNow);
		
		txtrBotResponse = new JTextArea();
		txtrBotResponse.setLineWrap(true);
		txtrBotResponse.setDisabledTextColor(new Color(255, 69, 0));
		txtrBotResponse.setEnabled(false);
		txtrBotResponse.setEditable(false);
		txtrBotResponse.setText("");
		panel.add(txtrBotResponse);
		
		txtrUserRequest = new JTextArea();
		txtrUserRequest.setLineWrap(true);
		txtrUserRequest.setRows(2);
		txtrUserRequest.setDisabledTextColor(new Color(0, 0, 0));
		txtrUserRequest.setEnabled(false);
		txtrUserRequest.setEditable(false);
		txtrUserRequest.setText("");
		panel.add(txtrUserRequest);
		
		progressBar = new JProgressBar();
		panel.add(progressBar);
		
		JLabel lblMessageHit = new JLabel("Message (Hit <Enter> to send a message to the bot):");
		getContentPane().add(lblMessageHit);
		
		txtRequest = new JTextField();
		getContentPane().add(txtRequest);
		txtRequest.setColumns(33);
		
		btnRecord = new JButton("");
		btnRecord.setToolTipText("Click this icon to utilize voice input, rather than text");
		btnRecord.setIcon(new ImageIcon(new ImageIcon(ChatClientView.class.getResource("/storage/uploads/mic.png")).getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT)));
		getContentPane().add(btnRecord);
		configureListeners();
	}
	
	private void configureListeners() {
		txtRequest.addKeyListener(this);
		btnRecord.addActionListener(this);
		
		vc.setRecordTime(5000);
		vc.listen();
		vc.addRespondListener(this);
		
		progressBar.setValue(VoiceInputController.PROGRESS);
	}


	public void actionPerformed(ActionEvent event) {
		if(event.getSource().equals(btnRecord)) {
			try {
				txtRequest.setEnabled(false);
				txtrSpeakNow.setText("Speak now...");
				vc.record();
				txtrSpeakNow.setText("Stop speaking...");
				txtRequest.setEnabled(true);
			} catch (InterruptedException | IOException e) {
				logger.error("Unable to record user's voice");
			}
		}
		
	}


	public void keyPressed(KeyEvent event) {
		if(event.getKeyCode() == KeyEvent.VK_ENTER) {
			String request = txtRequest.getText();
			
			txtRequest.setText("");
			
			txtrUserRequest.setText("You: " + request);
			
			String response = tc.respond(request);
			
			txtrBotResponse.setText("Assistant: " + response);
			try {
				tc.speak(response);
			} catch (IOException | JavaLayerException e) {
				logger.error("The assistant was unable to produce voice output.");
			}
		}
		
	}


	public void keyReleased(KeyEvent event) {}

	public void keyTyped(KeyEvent event) {}

	
	public void onRespond(String responseText) {
		
		txtrUserRequest.setText("You: "+ responseText);
		String response = tc.respond(responseText);
		
		txtrBotResponse.setText("Assistant: " + response);
		
		try {
			tc.speak(response);
		} catch (IOException | JavaLayerException e) {
			logger.error("The assistant was unable to produce voice output.");
		}
	}
}
