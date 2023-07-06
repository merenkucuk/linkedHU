package com.example.demo.viewmodel;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Valid
public class UserAccount {

	@Email
    private String email;

	@NotNull(message = "not null")
	@Size(min = 8, max = 32, message = "Password must be between 8 and 32 characters")
    private String password;

	@NotNull
	private String firstName;	

	@NotNull
	private String lastName;

	@NotNull
	private String role;

    public UserAccount(){

    }
    public UserAccount(String firstName, String lastName, String email, String password, String role){
        this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.role=role;
    }

    public String getEmail() {
		return this.email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return this.password;
	}
	public void setPassword(String password) {
		this.password = password;
	} 
	public String getFirstName() {
		return this.firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return this.lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getRole() {
		return this.role;
	}
	public void setRole(String role) {
		this.role = role;
	}
}
