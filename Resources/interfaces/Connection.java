package interfaces;

import java.io.IOException;

/**
 * This class should be implemented by the Server and Client
 * @author Romario Chinloy
 *
 * @param <T>
 */
public interface Connection<T> {
	
	/**
	 * This method should be used to send a Request or Response object.
	 * @param data
	 */
	public void send(T data) throws IOException;
	
	/**
	 * This method should close all the streams.
	 */
	public void closeConnection();
	
	/**
	 * This method should initialize the streams.
	 */
	public void getStreams() throws IOException;
}
