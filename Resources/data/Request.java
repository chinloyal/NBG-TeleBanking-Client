package data;

import java.io.Serializable;

public class Request implements Serializable {
	
	private String action;
	private Object data;
	
	public Request(String action, Object data) {
		super();
		this.action = action;
		this.data = data;
	}
	
	public Request(String action) {
		this(action, null);
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	

}
