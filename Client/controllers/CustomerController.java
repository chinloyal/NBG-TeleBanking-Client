package controllers;

import java.io.IOException;

import communication.Request;
import communication.Response;
import connection.Client;
import models.User;

public class CustomerController {
	
	private User customer;

	public CustomerController(User customer) {
		this.customer = customer;
	}
	
	public String getPhotoFromDatabase() {
		String photo = null;
		try {
			Client client = new Client();
			
			client.connect();
			client.send(new Request("get_customer_photo", customer));
			
			Response response = client.readResponse();
			photo = (String)response.getData();
			
			client.send(new Request("EXIT"));
			return photo;
			
		}catch(IOException | ClassNotFoundException | ClassCastException e) {
			e.printStackTrace();
		}
		
		return photo;
	}
	
	public float getBalanceFromDatabase() {
		float balance = 0.00f;
		
		return balance;
	}
}
