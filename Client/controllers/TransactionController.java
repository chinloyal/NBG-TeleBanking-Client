package controllers;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import abstracts.ArtificialIntelligence;
import communication.Request;
import communication.Response;
import connection.Client;
import models.User;

public class TransactionController extends ArtificialIntelligence{

	private Client client;
	
	private Logger logger = LogManager.getLogger(TransactionController.class);
	TransactionController(Client client) {
		this.client = client;
	}
	
	
	
	public TransactionController() {
		
	}

	public void processAction(String response, String request) {
		// TODO Auto-generated method stub
		
	}
	
	public double managerchartvalues(String type)
	{
		
		try {
			client.connect();
			client.send(new Request("values_for_chart", type));
			
			Response response = (Response)client.readResponse();
			
			client.send(new Request("EXIT"));
			
			return (double)response.getData();
			
		}catch(IOException e)
		{
			logger.error("Unable to send request for chart values ", e.getMessage());
		}catch(ClassCastException | ClassNotFoundException e)
		{
			logger.error("Unable to read response");
		}
		return 0;
		

		
	}
	
	/*public boolean register(User user) {
		logger.debug("About to send register request");
		if(isValid(user)) {
			Response response = null;
			try {
				client.connect();
				client.send(new Request("register_user", user));
				
				response = client.readResponse();
				
				client.send(new Request("EXIT"));
				
				return response.isSuccess();
			}catch(IOException e) {
				logger.error("Unable to send register request to server", e.getMessage());
			}catch(ClassCastException | ClassNotFoundException e) {
				logger.error("Unable to read response");
			}
		}
		
		return false;
	}*/
	
	

}
