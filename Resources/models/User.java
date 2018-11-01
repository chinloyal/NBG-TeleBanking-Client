package models;

import java.io.Serializable;

import javax.validation.constraints.*;
import javax.validation.constraints.Email;
//import javax.per

//import org.hibernate.validator.constraints.*;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class User implements Serializable {

	private int id;
	
	@NotEmpty(message = "- First Name cannot be empty.")
	@Size(min = 2, max = 200, message = "- First Name should be between 2 and 200 characters.")
	@Pattern(regexp = "^[a-zA-Z]+[a-zA-Z\\-]*?$", message = "- First Name can only contain leters and a dash(-).")
	private String firstName;
	
	@NotEmpty(message = "- Last Name cannot be empty.")
	@Size(min = 2, max = 200, message = "- Last Name should be between 2 and 200 characters.")
	@Pattern(regexp = "^[a-zA-Z]+[a-zA-Z\\\\-]*?$", message = "- Last Name can only contain leters and a dash(-).")
	private String lastName;
	
	@NotEmpty
	private String type;
	
	@NotEmpty(message = "- Email address cannot be empty.")
	@Email(message = "- Invalid email address")
	private String email;
	
	@Size(min = 8, message = "- Password should be a minimum of 8 characters")
	private String password;
	private Photo photo;
	
	public User() {
		super();
		this.id = 0;
		this.firstName = "";
		this.lastName = "";
		this.type = "";
		this.email = "";
		this.password = "";
		this.photo = null;
	}
	
	public User(int id, String firstName, String lastName, String type, String email, @Size(min = 8, message = "- Password should be a minimum of 8 characters") String password) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.type = type.trim().toLowerCase();
		this.email = email;
		this.password = BCrypt.hashpw(password, BCrypt.gensalt());
		this.photo = null;
	}

	public User(String firstName, String lastName, String type, String email, String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.type = type.trim().toLowerCase();
		this.email = email;
		this.password = password;
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
		this.type = type.trim().toLowerCase();
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
		this.password = password;
	}

	public Photo getPhoto() {
		return photo;
	}

	public void setPhoto(Photo photo) {
		this.photo = photo;
	}
	
	@Override
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
