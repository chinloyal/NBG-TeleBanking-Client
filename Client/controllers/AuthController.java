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

import connection.Client;
import data.Request;
import data.Response;
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

}
