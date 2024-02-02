package online.sumitakoliya.photoapp.api.users.shared;

import java.io.Serializable;
import java.util.UUID;

public class UserDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6570394146993622612L;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private UUID userId;
	private String encryptedPassword;
	public String getFirstname() {
		return firstName;
	}
	public void setFirstname(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
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
	public UUID getUserId() {
		return userId;
	}
	public void setUserId(UUID userId) {
		this.userId = userId;
	}
	public String getEncryptedPassword() {
		return encryptedPassword;
	}
	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}
	
	
}
