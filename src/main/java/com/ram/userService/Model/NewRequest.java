package com.ram.userService.Model;

public class NewRequest {
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	private String username;
	private String password;
	public NewRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public NewRequest(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	
	

}
