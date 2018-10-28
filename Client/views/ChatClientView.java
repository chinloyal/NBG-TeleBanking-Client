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
	private BufferedImage imgChatBubble;

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
		setSize(500,500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		setLayout(new GridLayout(4,1));
		
		// ------ Avatar of the Virtual Assistant ----- //
		JPanel panelR1 = new JPanel(new FlowLayout());
		
		/*
		 * The following lines of code don't work:
		 * "ImageIcon image = new ImageIcon(getClass().getResource("/Client/storage/images/Lisa_Chat_Bot.jpg"));
		 * JLabel img = new JLabel();
		 * img.setIcon(image);"
		 */
		
		/*
		 * These don't work either:
		 *BufferedImage img = ImageIO.read(new File("images/star.jpg"));
			JLabel avatar = new JLabel();
			avatar.setIcon((Icon) img);
		 */
		
		JLabel avatar = new JLabel("<Insert Avatar for Virtual Assistant");
		panelR1.add(avatar);
		this.add(panelR1);
		
		// ------ Response Text Area ------ //
		JPanel panelR2 = new JPanel(new BorderLayout());
		JLabel response = new JLabel("Response:");
		panelR2.add(response, BorderLayout.NORTH);
		txtResponse = new JTextArea("\t\tRESPONSES");
		panelR2.add(txtResponse, BorderLayout.CENTER);
		this.add(panelR2);
		
		
		// ------ Request Area ------ //
		JPanel panelR3 = new JPanel(new BorderLayout());
		JLabel request = new JLabel("Type a Request Below:");
		panelR3.add(request, BorderLayout.NORTH); 
		txtRequests = new JTextArea();
		panelR3.add(txtRequests, BorderLayout.CENTER);
		this.add(panelR3);
		
		
		// ------ Speak to Lisa (Click the Mic) ----- //
		JPanel panelR4 = new JPanel(new GridLayout(3,1));
		
		JPanel panelOR = new JPanel(new FlowLayout());
		JLabel or = new JLabel("OR");
		panelOR.add(or);
		
		panelR4.add(panelOR);
		
		JPanel panelSpeak = new JPanel(new FlowLayout());
		JLabel speak = new JLabel("Speak to Lisa!");
		panelSpeak.add(speak);	
		panelR4.add(panelSpeak);
		
		btnMic = new JButton ("<Insert Microphone Icon>");
		panelR4.add(btnMic);
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
