package controllers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import abstracts.ArtificialIntelligence;
import connection.Client;

public class TransactionController extends ArtificialIntelligence{

	private Client client;
	
	
	public TransactionController() {
		this(null);
		mute = true;
	}
	
	public TransactionController(Client client) {
		this.client = client;
	}

	public void processAction(String response, String request) {
		
		new Thread(()-> {
//			System.out.println(response.substring(0, 35));
			if(response.length() > 35 && response.substring(0, 36).equals("OK, I need a few seconds to transfer")) {
				String regex = "(OK, I need a few seconds to transfer)(.*)(to)(.*)";
				Pattern pat = Pattern.compile(regex);
				
				Matcher mat = pat.matcher(response);
				
				if(mat.find()) {
					String amountString = 0 + mat.group(2);
					double amount = 0;
					
					amountString = amountString.replaceAll("[^0-9\\.]", "");
					
					try {
						amount = Double.parseDouble(amountString);
					}catch(NumberFormatException e) {
						JOptionPane.showMessageDialog(null, "Invalid amount.");
						return;
					}
					String emailRegex = "^([a-z0-9]+[\\-\\.]?[\\w]+){1,255}@([a-z0-9]+[\\-]{0,4}[a-z0-9]+){1,255}([\\.][a-z]{2,13}){1,2}$";
					String email = mat.group(4);
					
					if(emailRegex.matches(email)) {
						
					}else {
						JOptionPane.showMessageDialog(null, "Invalid email address '" + email + "'");
						return;
					}
					
					
					
				}
			}
			
			
		}).start();
		
		
		
		
	}
	
	

}
