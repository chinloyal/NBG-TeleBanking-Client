package controllers;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import abstracts.ArtificialIntelligence;
import communication.Request;
import communication.Response;
import connection.Client;
import models.Transaction;

public class TransactionController extends ArtificialIntelligence{

	/*private Client client;*/
	
	
	public TransactionController() {
		
	}

	public void processAction(String response, String request) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processResponse(String response) {
		// TODO Auto-generated method stub
		
	}
	
	public List<Transaction> filter (String[] start, String[] end){
		return null;
		
	}
	
	public boolean storeMessage(String message, String email, String queryType) {
		boolean success = false;
		
		try {
			Client client = new Client();
			client.connect();
			
			List<String> cusMessage = new ArrayList<String>();
			cusMessage.add(email);
			cusMessage.add(message);
			cusMessage.add(queryType);
			
			client.send(new Request("store_message",cusMessage));
			Response response = client.readResponse();
			
			success = response.isSuccess();
			
			client.send(new Request("EXIT"));
			return success;
		}catch(IOException | ClassNotFoundException | ClassCastException e) {
			e.printStackTrace();
		}
		
		return success;
	}

}
