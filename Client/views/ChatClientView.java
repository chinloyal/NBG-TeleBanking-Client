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
import java.util.concurrent.ExecutionException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.chinloyal.listen.Hearable;

import controllers.TransactionController;
import controllers.VoiceInputController;

import java.awt.SystemColor;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.Timer;
import javax.swing.JToggleButton;
import javax.swing.JSlider;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import java.awt.Dimension;

public class ChatClientView extends JDialog implements ActionListener, KeyListener, ChangeListener, Hearable{
	private JLabel label;
	private JTextField txtRequest;
	private JButton btnRecord;
	private JTextArea txtrUserRequest;
	private JTextArea txtrBotResponse;
	private JTextArea txtrSpeakNow;
	
	private TransactionController tc = new TransactionController();
	private VoiceInputController vc = new VoiceInputController();
	private Logger logger = LogManager.getLogger(ChatClientView.class);
	private JScrollPane scrollPaneInstructions;
	private JScrollPane scrollPaneBot;
	private JScrollPane scrollPane;
	private JPanel pnlAction;
	private JToggleButton tglbtnMute;
	private JSlider slider;
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch(Exception e) {  
			JOptionPane.showMessageDialog(null,"Cannot set UI");
		}
		
		EventQueue.invokeLater(
				new Runnable(){
					public void run(){
						ChatClientView frame = new ChatClientView(null);
						frame.setVisible(true);
					}
				}
		);
	}
	
	public ChatClientView(JFrame parent) {
		super(parent, "NBG TeleBanking - Transactions", true);
		getContentPane().setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		setSize(450, 518);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		
		label = new JLabel("");
		label.setIcon(new ImageIcon(new ImageIcon(ChatClientView.class.getResource("/storage/images/lisa2.jpg")).getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT)));
		getContentPane().add(label);
		
		scrollPaneInstructions = new JScrollPane();
		scrollPaneInstructions.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		getContentPane().add(scrollPaneInstructions);
		
		JTextArea txtAreaInstructions = new JTextArea();
		scrollPaneInstructions.setViewportView(txtAreaInstructions);
		txtAreaInstructions.setLineWrap(true);
		txtAreaInstructions.setFont(new Font("SansSerif", Font.PLAIN, 12));
		txtAreaInstructions.setForeground(SystemColor.desktop);
		txtAreaInstructions.setText("Instructions:\r\n1. You can have a regular conversation with our banking bot LISA.\r\n\r\n2. Say \"I want to make a transaction\" to have the bot process transactions for you.\r\n\r\n3. Say \"I want to pay a bill\" and the bot will assist you in paying a bill\r\n\r\n4. If using voice input speak clearly.\r\nso the bot can understand you.");
		txtAreaInstructions.setEnabled(false);
		txtAreaInstructions.setEditable(false);
		txtAreaInstructions.setColumns(17);
		txtAreaInstructions.setRows(12);
		
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
		
		scrollPaneBot = new JScrollPane();
		panel.add(scrollPaneBot);
		
		txtrBotResponse = new JTextArea();
		txtrBotResponse.setRows(2);
		scrollPaneBot.setViewportView(txtrBotResponse);
		txtrBotResponse.setLineWrap(true);
		txtrBotResponse.setDisabledTextColor(new Color(255, 69, 0));
		txtrBotResponse.setEnabled(false);
		txtrBotResponse.setEditable(false);
		
		scrollPane = new JScrollPane();
		panel.add(scrollPane);
		
		txtrUserRequest = new JTextArea();
		scrollPane.setViewportView(txtrUserRequest);
		txtrUserRequest.setLineWrap(true);
		txtrUserRequest.setRows(2);
		txtrUserRequest.setDisabledTextColor(new Color(0, 0, 0));
		txtrUserRequest.setEnabled(false);
		txtrUserRequest.setEditable(false);
		
		pnlAction = new JPanel();
		FlowLayout flowLayout = (FlowLayout) pnlAction.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel.add(pnlAction);
		
		tglbtnMute = new JToggleButton("Mute");
		pnlAction.add(tglbtnMute);
		
		if(tc.getMute() == true) {
			tglbtnMute.setSelected(true);
			tglbtnMute.setText("Unmute");
		}
		
		JSeparator separator = new JSeparator();
		separator.setPreferredSize(new Dimension(25, 20));
		separator.setOrientation(SwingConstants.VERTICAL);
		pnlAction.add(separator);
		
		JLabel lblIncreaseTalkkTime = new JLabel("Change Talk Time:");
		pnlAction.add(lblIncreaseTalkkTime);
		
		slider = new JSlider();
		slider.setValue(5);
		slider.setPaintTicks(true);
		slider.setMajorTickSpacing(1);
		slider.setMaximum(10);
		pnlAction.add(slider);
		
		JLabel lblMessageHit = new JLabel("Message (Hit <Enter> to send a message to the bot):");
		getContentPane().add(lblMessageHit);
		
		txtRequest = new JTextField();
		getContentPane().add(txtRequest);
		txtRequest.setColumns(33);
		
		btnRecord = new JButton("");
		btnRecord.setToolTipText("Click this icon to utilize voice input, rather than text");
		btnRecord.setIcon(new ImageIcon(new ImageIcon(ChatClientView.class.getResource("/storage/images/mic.png")).getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT)));
		getContentPane().add(btnRecord);
		configureListeners();
	}
	
	private void configureListeners() {
		txtRequest.addKeyListener(this);
		btnRecord.addActionListener(this);
		tglbtnMute.addActionListener(this);
		
		slider.addChangeListener(this);
		
		vc.setRecordTime(5000);
		vc.listen();
		vc.addRespondListener(this);
	}


	public void actionPerformed(ActionEvent event) {
		if(event.getSource().equals(btnRecord)) {
			txtRequest.setEnabled(false);
			slider.setEnabled(false);
			txtrSpeakNow.setText("Speak now...");
			btnRecord.setEnabled(false);
			
			new Thread(()-> {
				try {
					vc.record();
				}catch (InterruptedException | IOException e) {
					logger.error("Unable to record user's voice");
				}
			}).start();

			ActionListener listener = (ev) -> {
				txtrSpeakNow.setText("Stop speaking...");
				slider.setEnabled(true);
				txtRequest.setEnabled(true);
				btnRecord.setEnabled(true);
			};
			
			Timer timer = new Timer(vc.getRecordTime(), listener);
			timer.setRepeats(false);
		    timer.start();
		}else if(event.getSource().equals(tglbtnMute)) {
			if(tglbtnMute.isSelected()) {
				tc.setMute(true);
				tglbtnMute.setText("Unmute");
			}else {
				tc.setMute(false);
				tglbtnMute.setText("Mute");
			}
		}
		
	}


	public void keyPressed(KeyEvent event) {
		if(event.getKeyCode() == KeyEvent.VK_ENTER) {
			
			String request = txtRequest.getText();
			
			if(request.trim().length() <= 140) {
				if(!request.trim().equals("")) {
					txtRequest.setText("");

					txtrUserRequest.setText("You: " + request);

					String response = tc.respond(request);

					txtrBotResponse.setText("Assistant: " + response);
					try {
						tc.speak(response);
					} catch (InterruptedException | ExecutionException e) {
						logger.error("The assistant was unable to produce voice output.");
					}
				}
			}else {
				JOptionPane.showMessageDialog(null, "Your request cannot exceed more than 140 characters.", "Warning", JOptionPane.WARNING_MESSAGE);
			}
			
		}
		
	}


	public void keyReleased(KeyEvent event) {}

	public void keyTyped(KeyEvent event) {}

	// For voice
	public void onRespond(String responseText) {
		
		txtrUserRequest.setText("You: "+ responseText);
		String response = tc.respond(responseText);



		txtrBotResponse.setText("Assistant: " + response);

		try {
			tc.speak(response);
		} catch (InterruptedException | ExecutionException e) {
			logger.error("The assistant was unable to produce voice output.");
		}
	}

	public void stateChanged(ChangeEvent event) {
		if(event.getSource().equals(slider)) {
			vc.setRecordTime(slider.getValue() * 1000);
		}
		
	}
}
