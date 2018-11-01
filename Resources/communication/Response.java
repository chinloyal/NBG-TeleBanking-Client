package communication;

import java.io.Serializable;

public class Response implements Serializable {
	
	private boolean success;
	private Object data;
	private String message;
	
	public Response(boolean success, Object data, String message) {
		super();
		this.success = success;
		this.data = data;
		this.message = message;
	}
	
	public Response(boolean success, String message) {
		this(success, null, message);
	}
	
	
	public Response(boolean success) {
		this(success, null, "");
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
	

}
