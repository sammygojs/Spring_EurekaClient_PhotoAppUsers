package online.sumitakoliya.photoapp.api.users.ui.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateUserRequestModel {
	@NotNull(message="First name cannot be null")
	@Size(min=2, message="First name must not be less than 2 chars")
	private String firstName;
	@NotNull(message="Last name cannot be null")
	@Size(min=2, message="Last name must not be less than 2 chars")
	private String lastName;
	@NotNull(message="password cannot be null")
	@Size(min=2, message="Pwd must be equal or greater than 8 chars and less than 16 chars")
	private String password;
	@NotNull(message="Email cannot be null")
	@Email
	private String email;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
