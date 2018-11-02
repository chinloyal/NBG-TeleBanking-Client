package connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import interfaces.Connection;
import data.Request;
import data.Response;

public class Client implements Connection<Request>{
	
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private Socket socket;
	
	private Logger logger = LogManager.getLogger(Client.class);

	public Client() {
		// TODO Auto-generated constructor stub
	}
		
	public boolean connect() {
		boolean status = false;
		
		try {
			socket = new Socket(InetAddress.getLocalHost(), 9000);
			getStreams();
			status = true;
			
		}catch(IOException e) {
			logger.error("Could not connect to server");
		}
		
		return status;
	}
	
	public void getStreams() throws IOException {
		oos = new ObjectOutputStream(socket.getOutputStream());
		ois = new ObjectInputStream(socket.getInputStream());
	}
	
	public void exit() throws IOException {
		oos.writeObject("EXIT");
	}

	public void send(Request data) throws IOException {
		oos.writeObject(data);
	}

	public void closeConnection() {
		try{
			exit();
			ois.close();
			oos.close();
			socket.close();
			logger.info("Server closed connection.");
		}catch(NullPointerException | IOException e){
			logger.error("Could not close all connections");
		}
		
	}
	
	public Response readResponse() throws ClassNotFoundException, ClassCastException, IOException {
		return (Response) ois.readObject();
	}
	
	

}
