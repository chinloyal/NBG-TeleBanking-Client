package controllers;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.chinloyal.listen.Hearable;

import javazoom.jl.decoder.JavaLayerException;

public class ChatTestDriver extends JFrame implements ActionListener {

	private JButton btnRecord;
	
	private static VoiceInputController vc = new VoiceInputController();
	private static TransactionController tc = new TransactionController();
	public static void main(String[] args) {
		
		EventQueue.invokeLater(() -> {
			ChatTestDriver frame = new ChatTestDriver();
			frame.setVisible(true);
			
			vc.listen();
			
			vc.addRespondListener(new Hearable() {
//				tc = new TransactionController();
				
				public void onRespond(String responseText) {
					
					String input = responseText;
					
					System.out.println("You: "+ input);
					
					String response = tc.respond(input);
					System.out.println("Assistant: " + response);
					
					try {
						tc.speak(response);
					}catch(IOException | JavaLayerException e) {
						e.printStackTrace();
					}
				}
			});
		});
		
		/*try {
			TransactionController tc = new TransactionController();
			tc.speak("I want to transfer $10 to bobby@gmail.com");
		}catch(IOException | JavaLayerException e) {
			e.printStackTrace();
		}
*/		
		
		
	}
	
	public ChatTestDriver() {
		setTitle("A.I.");
		setSize(100, 100);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		btnRecord = new JButton("Record");
		
		btnRecord.addActionListener(this);
		
		add(btnRecord);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource().equals(btnRecord)) {
			try {
				vc.record();
				if(tc.getPlayer() != null)
					tc.getPlayer().stop();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	}

}
