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

public class ChatClientView extends JFrame implements ActionListener{
	
	private JTextArea txtResponse;
	private JTextArea txtRequests;
	private JButton btnMic;
	private BufferedImage imgLisa;

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
		initView();
		configureListeners();
	}
	
	public void initView() {
		setTitle("NBG TeleBanking - Chat with Lisa!");
		setSize(500,750);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		setLayout(new GridLayout(4,1));
		
		// ------ Avatar of the Virtual Assistant ----- //
		JPanel panelR1 = new JPanel(new FlowLayout());
	
		JLabel avatar = new JLabel(new ImageIcon("Client/storage/Lisa_Chat_Bot.jpg"));
		panelR1.add(avatar);
		this.add(panelR1);
		
		// ------ Response Text Area ------ //
		JPanel panelR2 = new JPanel(new BorderLayout());
		JLabel response = new JLabel("RESPONSE:");
		panelR2.add(response, BorderLayout.NORTH);
		txtResponse = new JTextArea("\t\tRESPONSES");
		panelR2.add(txtResponse, BorderLayout.CENTER);
		this.add(panelR2);
		
		
		// ------ Request Area ------ //
		JPanel panelR3 = new JPanel(new BorderLayout());
		JLabel request = new JLabel("TYPE A REQUEST BELOW:");
		panelR3.add(request, BorderLayout.NORTH); 
		txtRequests = new JTextArea();
		panelR3.add(txtRequests, BorderLayout.CENTER);
		this.add(panelR3);
		
		
		// ------ Speak to Lisa (Click the Mic) ----- //
		JPanel panelR4 = new JPanel(new GridLayout(3,1));
		
		// -- 1
		JPanel panelOR = new JPanel(new FlowLayout());
		JLabel or = new JLabel("OR");
		panelOR.add(or);
		panelR4.add(panelOR);
		
		// -- 2
		JPanel panelSpeak = new JPanel(new FlowLayout());
		JLabel speak = new JLabel("SPEAK TO LISA!");
		panelSpeak.add(speak);	
		panelR4.add(panelSpeak);
		
		// -- 3
		JPanel panelMic = new JPanel(new FlowLayout());
		btnMic = new JButton (new ImageIcon("Client/storage/microphone.png"));
		panelMic.add(btnMic);
		panelR4.add(panelMic);
		
		this.add(panelR4);
	}
	
	private void configureListeners() {
		btnMic.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource().equals(btnMic)) {
			
			/*
			 * Once the Mic button is clicked,
			 * the chat bot should request commands
			 * from the user.
			 * 
			 * */
		}
	}
}
