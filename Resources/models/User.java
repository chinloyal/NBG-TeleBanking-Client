package models;

import java.io.Serializable;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class User implements Serializable {

	private int id;
	private String firstName;
	private String lastName;
	private String type;
	private String email;
	private String password;
	private Photo photo;
	
	public User(int id, String firstName, String lastName, String type, String email, String password) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.type = type;
		this.email = email;
		this.password = BCrypt.hashpw(password, BCrypt.gensalt());
		this.photo = null;
	}

	public User(String firstName, String lastName, String type, String email, String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.type = type;
		this.email = email;
		this.password = BCrypt.hashpw(password, BCrypt.gensalt());
		this.photo = null;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = BCrypt.hashpw(password, BCrypt.gensalt());
	}

	public Photo getPhoto() {
		return photo;
	}

	public void setPhoto(Photo photo) {
		this.photo = photo;
	}
	
	public String toString() {
		
		return "User {"
				+ "id: " + id
				+ ", first_name: " + firstName
				+ ", last_name: " + lastName
				+ ", email: " + email
				+ ", type: " + type
				+ "}";
	}
	
	
}
