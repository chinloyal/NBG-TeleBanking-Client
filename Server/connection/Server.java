package connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import interfaces.Connection;
import models.User;
import data.Request;
import data.Response;
import database.UserProvider;


public class Server implements Connection<Response> {
	private ServerSocket serverSocket;
	private Socket socket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	
	private Logger logger  = LogManager.getLogger(Server.class);
	
	public Server() {
		start();
		waitForRequest();
	}
	
	public void getStreams() throws IOException{
		oos = new ObjectOutputStream(socket.getOutputStream());
		ois = new ObjectInputStream(socket.getInputStream());
	}
	
	private void start(){
		try{
			serverSocket = new ServerSocket(9000, 1);
			logger.info("Server is starting...");
		}catch(IOException e){
			logger.error("Could not initialize server socket", e.getMessage());
		}
	}
	
	private void waitForRequest(){
		if(serverSocket == null){
			logger.warn("The server has not been initialized");
			return;
		}
		
		try{
			while(true){
				logger.info("Waiting for requests...");
				socket = serverSocket.accept();
				getStreams();
				Request request = null;
				
				do{
					try {
						request = (Request) ois.readObject();
						
						if(request.getAction().equals("register_user")){
							UserProvider provider = new UserProvider();
							
							if(provider.store((User) request.getData()) > 0) {
								send(new Response(true, "Customer has been registered"));
							}else {
								send(new Response(false));
							}
							
						}else if(request.getAction().equals("login")) {
							UserProvider pro = new UserProvider();
							String recieveData [] = (String[]) request.getData();
							pro.authenticate(recieveData[0], recieveData[1]);
							//
						}
					} catch (ClassNotFoundException e) {
						logger.error("Cannot locate class.");
					}catch(ClassCastException e) {
						logger.error("Could not cast class.");
					}
				}while(!request.getAction().equals("EXIT"));
				closeConnection();
			}
		}catch(IOException e){
			logger.error("Could not accept connection");
		}catch(NullPointerException e) {
			logger.error("The request is empty (null).");
		}
	}
	
	public void closeConnection(){
		try{
			ois.close();
			oos.close();
			socket.close();
			logger.info("Server closed connection.");
		}catch(NullPointerException | IOException e){
			logger.error("Could not close all connections");
		}
	}

	@Override
	public void send(Response data) throws IOException {
		oos.writeObject(data);
	}
}
