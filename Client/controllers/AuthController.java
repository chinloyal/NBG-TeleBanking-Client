package controllers;

import java.io.IOException;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import communication.Request;
import communication.Response;
import connection.Client;
import models.User;

public class AuthController {
	private Client client;
	
	private Logger logger = LogManager.getLogger(AuthController.class);
	
	public AuthController(Client client) {
		this.client = client;
	}
	
	public boolean register(User user) {
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
	}
	
	public boolean isValid(User user) {
		
		try {
			ValidatorFactory validatoryFactory = Validation.buildDefaultValidatorFactory();
			Validator validator = validatoryFactory.getValidator();
			Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
			
			if(!constraintViolations.isEmpty()) {
				String errors = "";
				
				for(ConstraintViolation<User> constraintViolation : constraintViolations) {
					errors += constraintViolation.getMessage() + "\n";
				}
				
				JOptionPane.showMessageDialog(null, errors, "Error", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}catch(ConstraintViolationException e) {
			logger.error("Constraint violation", e.getMessage());
		}
		
		
		return true;
	}
	
	public Response login(String email, String userPassword) {
		Response response = null;
		try {
			String [] arrData = {email, userPassword};
			Request request = new Request("login", arrData);
			client.connect();
			client.send(request);
			response = client.readResponse();
			/*client.send(new Request("EXIT"));
			client.closeConnection();*/
		} catch(IOException | ClassNotFoundException e ) {
			logger.error("Invalid data", e.getMessage());
		}
		return response;		
	}
	
	// This can only be accessed if the server is running
	public static User getLoggedInUser() {
		try {
			Request request = new Request("get_session");
			Client client = new Client();
			
			client.connect();
			client.send(request);
			
			Response response = client.readResponse();
			/*client.send(new Request("EXIT"));
			client.closeConnection();*/
			
			return (User) response.getData();
		}catch(IOException | ClassNotFoundException | NullPointerException e) {
			LogManager.getLogger().error("Unable to get session from server, the server may not be running.");
		}
		
		return null;
	}
	
	public static boolean logout() {
		try {
			Client client = new Client();
			
			client.connect();
			client.send(new Request("logout"));
			
			Response response = (Response) client.readResponse();
			
			/*client.send(new Request("EXIT"));
			client.closeConnection();*/

			return response.isSuccess();
		}catch(IOException | ClassNotFoundException e) {
			LogManager.getLogger().error("Unabale to send or read logout response/request");
		}
		
		return false;
	}

}
